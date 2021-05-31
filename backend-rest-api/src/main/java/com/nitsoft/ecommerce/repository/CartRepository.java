package com.nitsoft.ecommerce.repository;

import com.nitsoft.ecommerce.database.model.Cart;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;

public interface CartRepository extends JpaSpecificationExecutor<Cart> {
    Cart findByUserId(@Param("userId") Long userId);
}
