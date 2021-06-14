package com.nitsoft.ecommerce.database.model;

import lombok.*;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
public class ProductCategoryId extends AbstractEntity {
    private Long productId;
    private Long categoryId;
}
