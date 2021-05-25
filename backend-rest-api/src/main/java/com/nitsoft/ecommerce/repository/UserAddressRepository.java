package com.nitsoft.ecommerce.repository;

import com.nitsoft.ecommerce.database.model.UserAddress;
import org.springframework.data.repository.CrudRepository;

public interface UserAddressRepository extends CrudRepository<UserAddress, String> {
    UserAddress findByUserIdAndStatus(Long userId, int status);
    
    UserAddress findByIdAndStatus(Long adressId, int status);

    UserAddress findByAddressIdAndUserId(Long addressId, Long userId);
}
