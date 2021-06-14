package com.nitsoft.ecommerce.database.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


@Data
public class Orders implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long userId;

    private Long orderAddressId;
    private Long paymentId;

    private Long companyId;

    private String status;
    private Integer itemsCount;
    private Integer itemsQuantity;

    private String deliveryId;

    private String couponId;
    private BigDecimal totalItemsCost;
    private BigDecimal deliveryCharge;
    private BigDecimal couponDiscount;
    private BigDecimal finalAmount;
    private String customerEmail;
    private String paymentPartnerOrderId;

    private String previous_status;

    private String platform;
    private Date createdAt;
    private Date updatedAt;
}
