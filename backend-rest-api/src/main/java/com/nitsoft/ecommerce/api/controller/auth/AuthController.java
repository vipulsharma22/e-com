/*
 * Copyright (c) NIT-Software. All Rights Reserved.
 * This software is the confidential and proprietary information of NIT-Software,
 * ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the license agreement you entered into with NIT-Software.
 */
package com.nitsoft.ecommerce.api.controller.auth;

import com.nitsoft.ecommerce.api.APIName;
import com.nitsoft.ecommerce.api.request.model.AuthRequestModel;
import com.nitsoft.ecommerce.api.response.model.APIResponse;
import com.nitsoft.ecommerce.api.controller.AbstractBaseController;
import com.nitsoft.ecommerce.api.response.model.UserDetailResponseModel;
import com.nitsoft.ecommerce.api.response.util.APIStatus;
import com.nitsoft.ecommerce.auth.AuthUserFactory;
import com.nitsoft.ecommerce.database.model.User;
import com.nitsoft.ecommerce.database.model.UserAddress;
import com.nitsoft.ecommerce.database.model.UserToken;
import com.nitsoft.ecommerce.exception.ApplicationException;
import com.nitsoft.ecommerce.service.UserAddressService;
import com.nitsoft.ecommerce.service.auth.AuthService;
import com.nitsoft.util.Constant;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Quy Duong
 */
@RestController
@RequestMapping(APIName.AUTH_API)
public class AuthController extends AbstractBaseController {

    @Autowired
    AuthService authService;

    @Autowired
    AuthUserFactory authUserFactory;

    @Autowired
    UserAddressService userAddressService;

    @RequestMapping(path = APIName.ADMIN_LOGIN_API, method = RequestMethod.POST)
    public ResponseEntity<APIResponse> adminLogin(
            @PathVariable("company_id") Long companyId,
            @RequestBody AuthRequestModel authRequestModel
    ) {
        User adminUser = authService.getUserByEmailAndCompanyIdAndStatus(authRequestModel.getPhone(), companyId, Constant.USER_STATUS.ACTIVE.getStatus());
        if (adminUser == null) {
            throw new ApplicationException(APIStatus.ERR_USER_NOT_EXIST);
        } else {
            String passwordHash = null;

//            if (passwordHash.equals(adminUser.getPasswordHash())) {
//                // Check role
//                if (adminUser.getRoleId() == Constant.USER_ROLE.SYS_ADMIN.getRoleId()
//                        || adminUser.getRoleId() == Constant.USER_ROLE.STORE_MANAGER.getRoleId()
//                        || adminUser.getRoleId() == Constant.USER_ROLE.STORE_ADMIN.getRoleId()) {
//
//                    // TODO login
//                    UserToken userToken = authService.createUserToken(adminUser, authRequestModel.isKeepMeLogin());
//                    // Create Auth User -> Set to filter config
//                    // Perform the security
//                    return responseUtil.successResponse(userToken.getToken());
//                } else {
//                    throw new ApplicationException(APIStatus.ERR_PERMISSION_DENIED);
//                }
//            } else {
//                throw new ApplicationException(APIStatus.ERR_USER_NOT_VALID);
//            }

        }
        return null;
    }

    @RequestMapping(path = APIName.SESSION_DATA, method = RequestMethod.GET)
    public ResponseEntity<APIResponse> getSessionData(
            HttpServletRequest request,
            @PathVariable("company_id") Long companyId
    ) {
        Long userId = getAuthUserFromSession(request).getId();
        if (userId != null && !"".equals(userId)) {
            User user = authService.getUserByUserIdAndCompanyIdAndStatus(userId, companyId, Constant.USER_STATUS.ACTIVE.getStatus());
            UserAddress userAddress = userAddressService.getAddressByUserIdAndStatus(userId, Constant.STATUS.ACTIVE_STATUS.getValue());
            if (user != null) {
                UserDetailResponseModel userResponse = new UserDetailResponseModel();
                userResponse.setUserId(user.getId());
                userResponse.setCompanyId(user.getCompanyId());
                userResponse.setEmail(user.getEmail());
                userResponse.setFirstName(user.getFirstName());
                userResponse.setLastName(user.getLastName());
                userResponse.setRoleId(user.getRoleId());
                if (userAddress != null) {
                    userResponse.setAddress(userAddress.getAddress());
                    userResponse.setCity(userAddress.getCity());
                    userResponse.setPhone(userAddress.getPhone());
                }
                return responseUtil.successResponse(userResponse);
            } else {
                throw new ApplicationException(APIStatus.ERR_USER_NOT_EXIST);
            }
        } else {
            throw new ApplicationException(APIStatus.INVALID_PARAMETER);
        }
    }

    @RequestMapping(path = APIName.USERS_LOGOUT, method = RequestMethod.POST)
    public ResponseEntity<APIResponse> logout(
            @RequestHeader(value = Constant.HEADER_TOKEN) String tokenId
    ) {
        if (tokenId != null && !"".equals(tokenId)) {
            UserToken userToken = authService.getUserTokenById(tokenId);
            if (userToken != null) {
                authService.deleteUserToken(userToken);
                return responseUtil.successResponse("OK");
            } else {
                throw new ApplicationException(APIStatus.ERR_SESSION_NOT_FOUND);
            }
        } else {
            throw new ApplicationException(APIStatus.INVALID_PARAMETER);
        }
    }
}
