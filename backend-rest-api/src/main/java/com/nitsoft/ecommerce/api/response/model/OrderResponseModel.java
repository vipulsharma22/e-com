package com.nitsoft.ecommerce.api.response.model;

import com.nitsoft.ecommerce.database.model.entity.OrderDetail;
import com.nitsoft.ecommerce.database.model.entity.Orders;
import com.nitsoft.ecommerce.database.model.entity.UserAddress;
import lombok.Data;

import java.util.List;

@Data
public class OrderResponseModel {
    private List<OrderDetail> productList;
    private UserAddress userAddress;
    private Orders orders;
}
