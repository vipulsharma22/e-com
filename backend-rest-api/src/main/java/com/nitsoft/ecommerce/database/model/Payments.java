package com.nitsoft.ecommerce.database.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Data
public class Payments extends AbstractEntity {
    private Long id;
    private String userId;
    private BigDecimal amount;
    private String pgProvider;
    private String platform;
    String pgTransactionId;
    private String status;
    private ZonedDateTime createdDate;
    private ZonedDateTime updatedDate;
}
