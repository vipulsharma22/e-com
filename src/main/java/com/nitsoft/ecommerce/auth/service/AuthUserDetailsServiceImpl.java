/*
 * Copyright (c) NIT-Software. All Rights Reserved.
 * This software is the confidential and proprietary information of NIT-Software,
 * ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the license agreement you entered into with NIT-Software.
 */
package com.nitsoft.ecommerce.auth.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nitsoft.ecommerce.api.response.util.APIStatus;
import com.nitsoft.ecommerce.auth.AuthUser;
import com.nitsoft.ecommerce.database.model.UserToken;
import com.nitsoft.ecommerce.exception.ApplicationException;
import com.nitsoft.ecommerce.repository.UserTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 * 
 * @author Quy Duong
 */
@Service
public class AuthUserDetailsServiceImpl implements CustomUserAuthService {
    
    @Autowired
    private UserTokenRepository userTokenRepository;

    ObjectMapper map = new ObjectMapper();
    
    @Override
    public UserDetails loadUserByUsername(String userId) {
        // Not implement
        return null;
    }
    
    
    @Override
    public AuthUser loadUserByAccessToken(String token) {
        UserToken session = userTokenRepository.findById(token).get();
        if (session != null){
            if (session.getSessionData() != null && !"".equals(session.getSessionData())){
                try {
                    AuthUser authUser = map.readValue(session.getSessionData(), AuthUser.class);
                    return authUser;
                }catch (Exception ex){
                    return null;
                }
            }else{
                throw new ApplicationException(APIStatus.ERR_SESSION_DATA_INVALID);
            }
        }else{
            throw new ApplicationException(APIStatus.ERR_SESSION_NOT_FOUND);
        }
    }
}
