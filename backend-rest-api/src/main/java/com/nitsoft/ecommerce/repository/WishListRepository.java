package com.nitsoft.ecommerce.repository;

import com.nitsoft.ecommerce.database.model.WishList;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;

public interface WishListRepository extends JpaSpecificationExecutor<WishList> {
    WishList findByUserId(@Param("userId") Long userId);
}
