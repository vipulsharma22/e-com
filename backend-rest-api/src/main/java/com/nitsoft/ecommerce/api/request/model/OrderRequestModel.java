/*
 * Copyright (c) NIT-Software. All Rights Reserved.
 * This software is the confidential and proprietary information of NIT-Software,
 * ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the license agreement you entered into with NIT-Software.
 */
package com.nitsoft.ecommerce.api.request.model;

import java.math.BigDecimal;
import java.util.List;

import com.nitsoft.ecommerce.database.model.entity.OrderDetail;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Trinhlbk
 */
@Data
@AllArgsConstructor
public class OrderRequestModel {
    private List<OrderDetail> productList;
    private Long addressId;
    private BigDecimal totalItemsCost;
}
