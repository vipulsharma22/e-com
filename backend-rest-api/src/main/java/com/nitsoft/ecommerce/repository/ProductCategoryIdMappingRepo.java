package com.nitsoft.ecommerce.repository;

import com.nitsoft.ecommerce.database.model.ProductCategoryId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;


@Repository
@Transactional
public interface ProductCategoryIdMappingRepo extends AbstractRepo<ProductCategoryId, Long> {


    List<ProductCategoryId> findByProductIdAndDeletedFalse(Long productId);

    List<ProductCategoryId> findByCategoryIdAndDeletedFalse(Long categoryId);

    Page<ProductCategoryId> findByCategoryIdAndDeletedFalse(Long categoryId, Pageable pageable);

}
