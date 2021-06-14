package com.nitsoft.ecommerce.api.response.model;

import com.nitsoft.ecommerce.database.model.entity.Product;
import lombok.Data;

import java.util.List;

@Data
public class UserChoicesResponse {
    private List<Product> productList;
}
