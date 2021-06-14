package com.nitsoft.ecommerce.database.model;

import lombok.Data;


@Data
public class ProductAttributeDetail extends AbstractEntity {

    private static final long serialVersionUID = 1L;
    private Long productId;
    private Long attributeId;
    private String valueString;
    private Double valueNumberic;

}
