package com.nitsoft.ecommerce.database.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "orders")
public class Orders implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    
    @Basic(optional = false)
    @Column(name = "user_id")
    private Long userId;
    
    @Basic(optional = false)
    @Column(name = "order_address_id")
    private Long orderAddressId;
    
    @Basic(optional = false)
    @Column(name = "payment_id")
    private Long paymentId;
    
    @Basic(optional = false)
    @Column(name = "company_id")
    private Long companyId;

    @Basic(optional = false)
    @Column(name = "status")
    private String status;

    @Column(name = "items_count")
    private Integer itemsCount;
    
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "items_quantity")
    private Integer itemsQuantity;

    @Basic(optional = false)
    @Column(name = "delivery_id")
    private String deliveryId;

    @Basic(optional = false)
    @Column(name = "coupon_id")
    private String couponId;
    
    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    @Column(name = "delivery_charge")
    private BigDecimal deliveryCharge;

    @Column(name = "coupon_discount")
    private BigDecimal couponDiscount;

    @Column(name = "final_amount")
    private BigDecimal finalAmount;
    
    @Column(name = "checkout_comment")
    private String checkoutComment;
    
    @Column(name = "customer_email")
    private String customerEmail;

    @Basic(optional = false)
    @Column(name = "previous_status")
    private String previous_status;

    @Basic(optional = false)
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
}
