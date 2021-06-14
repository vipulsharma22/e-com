package com.nitsoft.ecommerce.database.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Data
public class Payments extends AbstractEntity {

    @Id
    @Column(
            name = "id",
            unique = true,
            nullable = false
    )
    private Long id;
    @Column(
            name = "user_id",
            nullable = false
    )
    private String userId;
    @Column(
            name = "amount",
            nullable = false
    )
    private BigDecimal amount;
    @Column(
            name = "pg_provider",
            nullable = false,
            columnDefinition = "varchar"
    )
    @Enumerated(EnumType.STRING)
    private String pgProvider;

    @Column(
            name = "platform",
            nullable = false,
            columnDefinition = "varchar"
    )
    @Enumerated(EnumType.STRING)
    private String platform;

    @Column(
            name = "pg_trans_id"
    )
    private String pgTransactionId;

    @Column(
            name = "bank_trans_id",
            nullable = true
    )
    @Enumerated(EnumType.STRING)
    private String status;
    @Column(
            name = "created_date",
            nullable = false,
            updatable = false
    )
    private ZonedDateTime createdDate;
    @Column(
            name = "updated_date",
            nullable = false
    )
    private ZonedDateTime updatedDate;
}
