package com.nitsoft.ecommerce.repository;

import com.nitsoft.ecommerce.database.model.entity.UserToken;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;

public interface UserTokenRepository extends CrudRepository<UserToken, String> {

    UserToken findByUserIdAndExpirationDateGreaterThan(Long userId, Date currentDate);

    UserToken findByToken(String token);
}
