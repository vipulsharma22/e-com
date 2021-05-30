
package com.nitsoft.ecommerce.repository;

import com.nitsoft.ecommerce.database.model.OrderAddress;


public interface OrderAddressRepository extends AbstractRepo<OrderAddress, Long> {
    OrderAddress findOneByIdAndDeletedFalse(Long orderId);
}
