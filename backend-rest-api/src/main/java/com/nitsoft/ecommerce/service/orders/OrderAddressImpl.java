
package com.nitsoft.ecommerce.service.orders;

import com.nitsoft.ecommerce.database.model.entity.OrderAddress;
import com.nitsoft.ecommerce.repository.OrderAddressRepository;
import com.nitsoft.ecommerce.service.AbstractBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderAddressImpl extends AbstractBaseService implements OrderAddressService {

    @Autowired
    OrderAddressRepository orderAddressRepository;
    
    
    @Override
    public OrderAddress saveOrUpdate(OrderAddress orderAddress) {
        return orderAddressRepository.save(orderAddress);
    }
    
    
    
    @Override
    public OrderAddress getOrderAddressByOrderId(Long orderId) {
        return orderAddressRepository.findOneByIdAndDeletedFalse(orderId);
    }
    
    
}
