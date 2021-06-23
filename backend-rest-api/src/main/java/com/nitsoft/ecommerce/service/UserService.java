package com.nitsoft.ecommerce.service;

import com.nitsoft.ecommerce.database.model.entity.User;
import com.nitsoft.ecommerce.database.model.entity.UserToken;
import com.nitsoft.util.EmailUtil;
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
    
    public User getUserByEmail(String email, int status) {
        return userRepository.findByEmailAndStatus(email, status);
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
