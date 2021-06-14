package com.nitsoft.ecommerce.repository;

import com.nitsoft.ecommerce.database.model.entity.Payments;
import org.springframework.data.repository.CrudRepository;

public interface PaymentRepository extends CrudRepository<Payments, Long> {
}
