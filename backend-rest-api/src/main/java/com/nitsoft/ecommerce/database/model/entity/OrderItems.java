package com.nitsoft.ecommerce.database.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@Table(name = "order_items")
public class OrderItems extends AbstractEntity {
    @Basic(optional = false)
    @Column(name = "order_id")
    private Long orderId;

    @Basic(optional = false)
    @Column(name = "product_id")
    private Long productId;

    @Column(name = "display_amount")
    private BigDecimal displayAmount;

    @Column(name = "final_amount")
    private BigDecimal finalAmount;
}
