package com.nitsoft.ecommerce.database.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartWishListData {
    private Long productId;
    private Integer quantity;
    private Product productDetails;
}
