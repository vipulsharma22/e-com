package com.nitsoft.ecommerce.service;

import com.nitsoft.ecommerce.database.model.entity.User;
import com.nitsoft.ecommerce.database.model.entity.UserToken;
import com.nitsoft.ecommerce.api.request.model.AuthRequestModel;
import com.nitsoft.ecommerce.api.response.model.UserLogInResponse;
import com.nitsoft.ecommerce.api.response.util.APIStatus;
import com.nitsoft.ecommerce.exception.ApplicationException;
import com.nitsoft.ecommerce.service.auth.AuthService;
import com.nitsoft.ecommerce.validators.UserValidator;
import com.nitsoft.util.Constant;
import com.nitsoft.util.EmailUtil;
import com.nitsoft.util.PasswordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nitsoft.ecommerce.repository.UserRepository;
import com.nitsoft.ecommerce.repository.UserTokenRepository;
import com.nitsoft.ecommerce.repository.specification.UserSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserTokenRepository userTokenRepository;
    @Autowired
    private OtpService otpService;
    @Autowired
    private AuthService authService;

    public UserLogInResponse login(AuthRequestModel model) {
        UserValidator.login(model);
        String userName;
        userName = model.getPhone() == null ? model.getUserName() : model.getPhone();
        User existedUser = getUserByEmailOrNumber(userName, Constant.USER_STATUS.ACTIVE.getStatus());
        if (existedUser == null) {
            throw new ApplicationException(APIStatus.ERR_USER_NOT_FOUND);
        }
        if (model.getOtp() != null) {
            if (!otpService.verifyOtp(model.getPhone(), model.getOtp())) {
                throw new ApplicationException(APIStatus.INVALID_OTP);
            }
        } else if (model.getPassword() != null) {
            if (!PasswordUtils.verifyUserPassword(model.getPassword(), existedUser.getEncryptedPassword(), existedUser.getSalt())) {
                throw new ApplicationException(APIStatus.INVALID_PASSWORD);
            }
        } else {
            throw new ApplicationException(APIStatus.INVALID_PARAMETER);
        }
        UserToken token = authService.getOrCreateUserTokenByUserId(existedUser);
        return UserLogInResponse.builder().token(token.getToken()).userName(existedUser.getEmail()).build();
    }

    public User getUserByEmail(String email, int status) {
        return userRepository.findByEmailIgnoreCaseAndStatus(email, status);
    }

    public User getUserByPhoneNumber(String phone, int status) {
        return userRepository.findByPhoneAndStatus(phone, status);
    }

    public User getUserByEmailOrNumber(String userName, int status) {
        if (EmailUtil.isEmailFormat(userName)) {
            return getUserByEmail(userName, status);
        } else {
            return userRepository.findByPhoneAndStatus(userName, status);

        }
    }

    public User save(User users) {
        return userRepository.save(users);
    }

    public User getUserByUserIdAndComIdAndStatus(Long userId, Long companyId, int status) {
        return userRepository.findByIdAndStatus(userId, status);
    }

    public User getUserByActivationCode(String token) {
        UserToken userToken = userTokenRepository.findByToken(token);

        if (userToken != null) {
            return userRepository.findById(userToken.getUserId()).get();
        } else {
            return null;
        }
    }
    
    public Page<User> doFilterSearchSortPagingUser(Long userId,long companyId, String searchKey, int sortKey, boolean isAscSort, int pSize, int pNumber) {
        return userRepository.findAll(new UserSpecification(userId, companyId, searchKey, sortKey, isAscSort), PageRequest.of(pNumber, pSize));
    }
}
