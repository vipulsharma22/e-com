package com.nitsoft.ecommerce.repository;

import com.nitsoft.ecommerce.database.model.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface OrdersRepository extends AbstractRepo<Orders, Long>, JpaSpecificationExecutor<Orders> {

    Page<Orders> findAllByCompanyId(long companyId, Pageable pageable);

    Page<Orders> findAllByUserId(long userId, Pageable pageable);

    Orders findOneByIdAndCompanyId(Long orderId, Long companyId);
    
}
