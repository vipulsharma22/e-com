package com.nitsoft.ecommerce.database.model;

import com.nitsoft.ecommerce.enums.CategoryType;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;


@Data
public class ProductCategory extends AbstractEntity {
    
    private String name;

    private String description;

    private CategoryType type;

}
