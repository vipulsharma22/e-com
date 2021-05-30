package com.nitsoft.ecommerce.api.response.model;

import com.nitsoft.ecommerce.database.model.OrderDetail;
import com.nitsoft.ecommerce.database.model.Orders;
import com.nitsoft.ecommerce.database.model.UserAddress;
import lombok.Data;

import java.util.List;

@Data
public class OrderResponseModel {
    private List<OrderDetail> productList;
    private UserAddress userAddress;
    private Orders orders;
}
