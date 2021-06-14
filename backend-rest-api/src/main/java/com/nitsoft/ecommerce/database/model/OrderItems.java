package com.nitsoft.ecommerce.database.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;


@Data
@NoArgsConstructor
public class OrderItems {
    private Long id;

    private Long orderId;

    private Long productId;
    private BigDecimal displayAmount;
    private BigDecimal finalAmount;
}
