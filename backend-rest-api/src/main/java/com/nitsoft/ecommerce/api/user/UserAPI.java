package com.nitsoft.ecommerce.api.user;

import com.nitsoft.ecommerce.api.APIName;
import com.nitsoft.ecommerce.api.controller.AbstractBaseController;
import com.nitsoft.ecommerce.api.request.model.AuthRequestModel;
import com.nitsoft.ecommerce.api.request.model.UserChangePasswordModel;
import com.nitsoft.ecommerce.api.request.model.UserListRequestModel;
import com.nitsoft.ecommerce.api.request.model.UserRequestModel;
import com.nitsoft.ecommerce.api.response.model.APIResponse;
import com.nitsoft.ecommerce.api.response.model.PagingResponseModel;
import com.nitsoft.ecommerce.api.response.model.UserDetailResponseModel;
import com.nitsoft.ecommerce.api.response.util.APIStatus;
import com.nitsoft.ecommerce.database.model.User;
import com.nitsoft.ecommerce.database.model.UserAddress;
import com.nitsoft.ecommerce.database.model.UserToken;
import com.nitsoft.ecommerce.exception.ApplicationException;
import com.nitsoft.ecommerce.service.OtpService;
import com.nitsoft.ecommerce.service.UserAddressService;
import com.nitsoft.ecommerce.service.UserService;
import com.nitsoft.ecommerce.service.UserTokenService;
import com.nitsoft.ecommerce.service.auth.AuthService;
import com.nitsoft.ecommerce.validators.UserValidator;
import com.nitsoft.util.Constant;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(APIName.USERS)
public class UserAPI extends AbstractBaseController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserTokenService userTokenService;
    @Autowired
    private UserAddressService userAddressService;
    @Autowired
    private AuthService authService;

    @Autowired
    private OtpService otpService;

    @RequestMapping(value = APIName.USERS_LOGIN, method = RequestMethod.POST, produces = APIName.CHARSET)
    public ResponseEntity<APIResponse> login(@PathVariable Long company_id, @RequestBody AuthRequestModel authRequestModel) {
        UserValidator.login(authRequestModel);
        if (!otpService.verifyOtp(authRequestModel.getPhone(), authRequestModel.getOtp())) {
            throw new ApplicationException(APIStatus.INVALID_OTP);
        }
        return responseUtil.successResponse(APIStatus.OK);
    }

    @RequestMapping(value = APIName.USERS_LOGOUT, method = RequestMethod.POST, produces = APIName.CHARSET)
    public ResponseEntity<APIResponse> logout(
            @PathVariable Long company_id,
            HttpServletRequest request) {

        String authToken = request.getHeader(Constant.HEADER_TOKEN);
        UserToken userToken = userTokenService.getTokenById(authToken);
        if (userToken != null) {
            userTokenService.invalidateToken(userToken);
            return responseUtil.successResponse(APIStatus.OK);
        } else {
            throw new ApplicationException(APIStatus.ERR_UNAUTHORIZED);
        }

    }

    @RequestMapping(path = APIName.USER_REGISTER, method = RequestMethod.POST, produces = APIName.CHARSET)
    public ResponseEntity<APIResponse> register(@PathVariable Long company_id, @RequestBody UserRequestModel user) {

        UserValidator.registerUser(user);
        if (!otpService.verifyOtp(user.getPhone(), user.getOtp())) {
            throw new ApplicationException(APIStatus.INVALID_OTP);
        }
        User existedUser = userService.getUserByPhoneNumber(user.getPhone(), Constant.USER_STATUS.ACTIVE.getStatus());
        if (existedUser == null) {
            User userSignUp = new User();
            userSignUp.setEmail( user.getEmail());
            userSignUp.setFirstName(user.getFirstName());
            userSignUp.setLastName(user.getLastName());
            userSignUp.setMiddleName(user.getMiddleName());
            userSignUp.setRoleId(Constant.USER_ROLE.NORMAL_USER.getRoleId());
            userSignUp.setStatus(Constant.USER_STATUS.ACTIVE.getStatus());
            userSignUp.setPhone(user.getPhone());
            userSignUp.setCompanyId(company_id);
            userService.save(userSignUp);
            return responseUtil.successResponse(userSignUp);
        } else {
            throw new ApplicationException(APIStatus.USER_ALREADY_EXIST);
        }
    }

    @RequestMapping(value = APIName.USER_LIST, method = RequestMethod.POST, produces = APIName.CHARSET)
    public ResponseEntity<APIResponse> getUserList(
            HttpServletRequest httpRequest,
            @PathVariable Long company_id,
            @RequestBody UserListRequestModel request) {

        try {
            Long userId = getAuthUserFromSession(httpRequest).getId();
            Page<User> users = userService.doFilterSearchSortPagingUser(userId, company_id, request.getSearchKey(), request.getSortCase(), request.getAscSort(), request.getPageSize(), request.getPageNumber());
            PagingResponseModel finalRes = new PagingResponseModel(users.getContent(), users.getTotalElements(), users.getTotalPages(), users.getNumber());
            return responseUtil.successResponse(finalRes);
        } catch (Exception ex) {
            throw new ApplicationException(APIStatus.ERR_GET_LIST_USERS);
        }

    }

    @RequestMapping(path = APIName.USER_DETAILS, method = RequestMethod.GET, produces = APIName.CHARSET)
    public ResponseEntity<APIResponse> getUserDetails(
            @PathVariable Long company_id,
            @PathVariable Long userId
    ) {

        // check user already exists
        User existedUser = userService.getUserByUserIdAndComIdAndStatus(userId, company_id, Constant.USER_STATUS.ACTIVE.getStatus());
        if (existedUser != null) {
            UserAddress userAddress = userAddressService.getAddressByUserIdAndStatus(userId, Constant.STATUS.ACTIVE_STATUS.getValue());
            if (userAddress != null) {
                UserDetailResponseModel response = new UserDetailResponseModel();
                response.setUserId(userId);
                response.setCompanyId(existedUser.getCompanyId());
                response.setRoleId(existedUser.getRoleId());
                response.setFirstName(existedUser.getFirstName());
                response.setLastName(existedUser.getLastName());
                response.setMiddleName(existedUser.getMiddleName());
                response.setEmail(existedUser.getEmail());
                response.setPhone(userAddress.getPhone());
                response.setCity(userAddress.getCity());
                return responseUtil.successResponse(response);
            } else {
                // notify user already exists
                throw new ApplicationException(APIStatus.ERR_USER_NOT_FOUND);
            }

        } else {
            // notify user already exists
            throw new ApplicationException(APIStatus.ERR_USER_NOT_FOUND);
        }
    }

    @RequestMapping(path = APIName.UPDATE_USER, method = RequestMethod.POST, produces = APIName.CHARSET)
    public ResponseEntity<APIResponse> updateUser(
            @PathVariable Long company_id,
            @RequestBody UserRequestModel user
    ) {

        // check user already exists
        User existedUser = userService.getUserByUserIdAndComIdAndStatus(user.getUserId(), company_id, Constant.USER_STATUS.ACTIVE.getStatus());
        if (existedUser != null) {
            existedUser.setFirstName(user.getFirstName());
            existedUser.setLastName(user.getLastName());
            if (user.getMiddleName() != null && !user.getMiddleName().isEmpty()) {
                existedUser.setMiddleName(user.getMiddleName());
            }
            userService.save(existedUser);
            UserAddress userAddress = userAddressService.getAddressByUserIdAndStatus(user.getUserId(), Constant.STATUS.ACTIVE_STATUS.getValue());
            if (userAddress != null) {
                userAddress.setCity(user.getCity());
                userAddress.setPhone(user.getPhone());
                userAddressService.save(userAddress);
            } else {
                throw new ApplicationException(APIStatus.ERR_USER_ADDRESS_NOT_FOUND);
            }
            return responseUtil.successResponse(existedUser);
        } else {
            // notify user already exists
            throw new ApplicationException(APIStatus.ERR_USER_NOT_FOUND);
        }
    }

    @RequestMapping(path = APIName.DELETE_USER, method = RequestMethod.POST, produces = APIName.CHARSET)
    public ResponseEntity<APIResponse> deleteUsers(
            @PathVariable Long company_id,
            @RequestBody List<Long> userIds
    ) {
        if (userIds != null && userIds.size() > 0) {

            for (Long userId : userIds) {
                User user = userService.getUserByUserIdAndComIdAndStatus(userId, company_id, Constant.USER_STATUS.ACTIVE.getStatus());
                if (user != null) {
                    user.setStatus(Constant.USER_STATUS.INACTIVE.getStatus());
                    userService.save(user);
                }
            }
            return responseUtil.successResponse("Delete User Successfully");
        } else {
            throw new ApplicationException(APIStatus.ERR_BAD_REQUEST);
        }

    }

    @RequestMapping(path = APIName.CHANGE_PASSWORD_USER, method = RequestMethod.POST, produces = APIName.CHARSET)
    public ResponseEntity<APIResponse> changePasswordUser(
            @PathVariable Long company_id,
            @RequestBody UserChangePasswordModel user
    ) throws NoSuchAlgorithmException {

        // check user already exists
        User existedUser = userService.getUserByUserIdAndComIdAndStatus(user.getUserId(), company_id, Constant.USER_STATUS.ACTIVE.getStatus());
        if (existedUser != null) {
//            String oldHashPassword = MD5Hash.MD5Encrypt(user.getOldPassword() + existedUser.getSalt());
//            if (oldHashPassword.equals(existedUser.getPasswordHash())) {
//                if (user.getNewPassword() != null || !user.getNewPassword().isEmpty()) {
//                    existedUser.setPasswordHash(MD5Hash.MD5Encrypt(user.getNewPassword() + existedUser.getSalt()));
//                    userService.save(existedUser);
//                    return responseUtil.successResponse(existedUser);
//                } else {
//                    throw new ApplicationException(APIStatus.ERR_MISSING_PASSWORD);
//                }
//            } else {
//                throw new ApplicationException(APIStatus.ERR_UNCORRECT_PASSWORD);
//            }

        } else {
            // notify user already exists
            throw new ApplicationException(APIStatus.ERR_USER_NOT_FOUND);
        }
        return null;
    }
}
