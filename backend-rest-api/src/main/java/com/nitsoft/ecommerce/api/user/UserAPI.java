package com.nitsoft.ecommerce.api.user;

import com.nitsoft.ecommerce.api.APIName;
import com.nitsoft.ecommerce.api.controller.AbstractBaseController;
import com.nitsoft.ecommerce.api.request.model.AuthRequestModel;
import com.nitsoft.ecommerce.api.request.model.UserListRequestModel;
import com.nitsoft.ecommerce.api.request.model.UserRequestModel;
import com.nitsoft.ecommerce.api.response.model.APIResponse;
import com.nitsoft.ecommerce.api.response.model.PagingResponseModel;
import com.nitsoft.ecommerce.api.response.model.UserDetailResponseModel;
import com.nitsoft.ecommerce.api.response.model.UserLogInResponse;
import com.nitsoft.ecommerce.api.response.util.APIStatus;
import com.nitsoft.ecommerce.auth.service.Authenticated;
import com.nitsoft.ecommerce.database.model.User;
import com.nitsoft.ecommerce.database.model.UserAddress;
import com.nitsoft.ecommerce.database.model.UserToken;
import com.nitsoft.ecommerce.enums.PermissionEnum;
import com.nitsoft.ecommerce.exception.ApplicationException;
import com.nitsoft.ecommerce.notification.email.EmailService;
import com.nitsoft.ecommerce.service.*;
import com.nitsoft.ecommerce.service.auth.AuthService;
import com.nitsoft.ecommerce.validators.UserValidator;
import com.nitsoft.util.Constant;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

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
    @Autowired
    private EmailService emailService;

    @RequestMapping(value = APIName.USERS_LOGIN, method = RequestMethod.POST, produces = APIName.CHARSET)
    public ResponseEntity<APIResponse> login(@PathVariable(value = "company_id") Long companyId, @RequestBody AuthRequestModel authRequestModel) {
        UserValidator.login(authRequestModel);
        if (!otpService.verifyOtp(authRequestModel.getPhone(), authRequestModel.getOtp())) {
            throw new ApplicationException(APIStatus.INVALID_OTP);
        }
        User existedUser = userService.getUserByPhoneNumber(authRequestModel.getPhone(), Constant.USER_STATUS.ACTIVE.getStatus());
        if (existedUser == null) {
            throw new ApplicationException(APIStatus.ERR_USER_NOT_FOUND);
        }
        UserToken token = authService.getOrCreateUserTokenByUserId(existedUser);
        UserLogInResponse response = UserLogInResponse.builder().token(token.getToken()).userName(existedUser.getEmail()).build();
        return responseUtil.successResponse(response);
    }

    @RequestMapping(value = APIName.USERS_LOGOUT, method = RequestMethod.POST, produces = APIName.CHARSET)
    public ResponseEntity<APIResponse> logout(@PathVariable Long companyId, HttpServletRequest request) {
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
    public ResponseEntity<APIResponse> register(@PathVariable(value = "company_id") Long companyId, @RequestBody UserRequestModel user) {
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
            userSignUp.setCompanyId(companyId);
            userService.save(userSignUp);
            UserToken token = authService.createUserToken(userSignUp, true);
            UserLogInResponse response = UserLogInResponse.builder().userName(user.getEmail()).
                    token(token.getToken()).build();
            return responseUtil.successResponse(response);
        } else {
            throw new ApplicationException(APIStatus.USER_ALREADY_EXIST);
        }
    }

    @RequestMapping(value = APIName.USER_LIST, method = RequestMethod.POST, produces = APIName.CHARSET)
    public ResponseEntity<APIResponse> getUserList(
            HttpServletRequest httpRequest,
            @PathVariable Long companyId,
            @RequestBody UserListRequestModel request) {

        try {
            Long userId = getAuthUserFromSession(httpRequest).getId();
            Page<User> users = userService.doFilterSearchSortPagingUser(userId, companyId, request.getSearchKey(), request.getSortCase(), request.getAscSort(), request.getPageSize(), request.getPageNumber());
            PagingResponseModel finalRes = new PagingResponseModel(users.getContent(), users.getTotalElements(), users.getTotalPages(), users.getNumber());
            return responseUtil.successResponse(finalRes);
        } catch (Exception ex) {
            throw new ApplicationException(APIStatus.ERR_GET_LIST_USERS);
        }

    }

    @RequestMapping(path = APIName.USER_DETAILS, method = RequestMethod.GET, produces = APIName.CHARSET)
    public ResponseEntity<APIResponse> getUserDetails(
            @PathVariable Long companyId,
            @PathVariable Long userId
    ) {

        // check user already exists
        User existedUser = userService.getUserByUserIdAndComIdAndStatus(userId, companyId, Constant.USER_STATUS.ACTIVE.getStatus());
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
            @PathVariable Long companyId,
            @RequestBody List<Long> userIds
    ) {
        if (!CollectionUtils.isEmpty(userIds)) {

            for (Long userId : userIds) {
                User user = userService.getUserByUserIdAndComIdAndStatus(userId, companyId, Constant.USER_STATUS.ACTIVE.getStatus());
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

    @Authenticated(permissions = PermissionEnum.PRODUCT_CRUD)
    @RequestMapping(value = APIName.SEND_OTP, method = RequestMethod.POST, produces = APIName.CHARSET)
    public ResponseEntity<APIResponse> sentOTP(@RequestParam String phone) {
        otpService.sendOtp(phone);
        return responseUtil.successResponse("OTP sent successfully");
    }

    @RequestMapping(value = "/test", produces = APIName.CHARSET)
    public ResponseEntity<APIResponse> test(@RequestParam String phone) {
        emailService.sendSimpleMessage("vipul13103557@gmail.com","Test","Testing mail");
        return responseUtil.successResponse("OTP sent successfully");
    }
}
