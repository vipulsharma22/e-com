package com.nitsoft.ecommerce.service.auth;

import com.nitsoft.ecommerce.database.model.User;
import com.nitsoft.ecommerce.database.model.UserToken;


public interface AuthService {
    
    public User getUserByEmailAndCompanyIdAndStatus (String email, Long companyId, int status);

    public UserToken createUserToken(User adminUser, boolean keepMeLogin);
    
    public User getUserByUserIdAndStatus(Long userId);
    
    public UserToken getUserTokenById (String id);
    
    public void deleteUserToken (UserToken userToken);

    UserToken getOrCreateUserTokenByUserId(User user);

    UserToken getByToken(String token);
}
