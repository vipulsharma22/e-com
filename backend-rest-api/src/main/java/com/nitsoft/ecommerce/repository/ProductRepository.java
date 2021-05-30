package com.nitsoft.ecommerce.repository;

import com.nitsoft.ecommerce.database.model.Product;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends AbstractRepo<Product, Long> {

    Page<Product> findByCompanyId(@Param("companyId") long companyId, Pageable pageable);

    Page<Product> findAll(Pageable pageable);

    List<Product> findByIdInAndDeletedFalse(List<Long> productIds);


}
