package com.nitsoft.ecommerce.repository;

import com.nitsoft.ecommerce.database.model.entity.UserChoices;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserChoicesRepository extends CrudRepository<UserChoices,Long> {
    UserChoices findByUserIdAndType(@Param("userId") Long userId,@Param("type") String type);
}
