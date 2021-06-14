package com.nitsoft.ecommerce.repository;

import com.nitsoft.ecommerce.database.model.entity.UserAddress;
import org.springframework.data.repository.CrudRepository;

public interface UserAddressRepository extends CrudRepository<UserAddress, String> {
    UserAddress findByUserId(Long userId);
    
    UserAddress findByIdAndUserId(Long addressId, Long userId);
}
