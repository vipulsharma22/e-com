package com.nitsoft.ecommerce.database.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product_category")
public class ProductCategoryId extends AbstractEntity{
    @Basic(optional = false)
    @Column(name = "product_id")
    private Long productId;

    @Basic(optional = false)
    @Column(name = "category_id")
    private Long categoryId;
}
