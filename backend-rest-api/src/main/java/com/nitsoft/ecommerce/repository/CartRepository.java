package com.nitsoft.ecommerce.repository;

import com.nitsoft.ecommerce.database.model.UserChoices;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;

public interface CartRepository extends JpaSpecificationExecutor<UserChoices> {
    UserChoices findByUserIdAndType(@Param("userId") Long userId,String type);
}
