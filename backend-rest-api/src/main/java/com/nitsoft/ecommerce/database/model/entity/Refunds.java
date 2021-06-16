package com.nitsoft.ecommerce.database.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@Table(name = "refunds")
public class Refunds extends AbstractEntity{

    @Column(
            name = "payment_id",
            nullable = false
    )
    private Long paymentId;
    @Column(
            name = "amount",
            nullable = false
    )
    private BigDecimal amount;
    @Column(
            name = "refund_id",
            nullable = false,
            columnDefinition = "varchar"
    )
    private String refundId;

    @Column(
            name = "status",
            nullable = false,
            columnDefinition = "varchar"
    )
    private String status;

    @Column(
            name = "gateway_refund_response",
            nullable = false,
            columnDefinition = "varchar"
    )
    private String gatewayRefundResponse;
}
