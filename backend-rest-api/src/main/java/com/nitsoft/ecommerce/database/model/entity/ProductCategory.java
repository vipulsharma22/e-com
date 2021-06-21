package com.nitsoft.ecommerce.database.model.entity;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.nitsoft.ecommerce.enums.CategoryType;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Data

@EntityListeners(AuditingEntityListener.class)
@DynamicInsert
@DynamicUpdate
@Table(name = "product_categories")
@XmlRootElement
public class ProductCategory extends AbstractEntity {
    
    private String name;

    private String description;

    private CategoryType type;

}
