package com.nitsoft.ecommerce.repository;

import com.nitsoft.ecommerce.database.model.entity.ProductCategory;
import java.util.List;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface ProductCategoryRepository extends AbstractRepo<ProductCategory, Long> {
    
//    @Query("SELECT p FROM ProductCategory p WHERE p.id.productId = :productId")
    List<ProductCategory> findByIdAndDeletedFalse(Long productId);
}
