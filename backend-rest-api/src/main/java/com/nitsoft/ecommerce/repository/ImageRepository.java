package com.nitsoft.ecommerce.repository;


import com.nitsoft.ecommerce.database.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ImageRepository extends JpaRepository<Image, Long> {
    Image findByProductIdAndImageType(long companyId,String imageType);
}
