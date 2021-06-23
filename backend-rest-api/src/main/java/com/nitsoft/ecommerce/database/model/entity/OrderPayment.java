package com.nitsoft.ecommerce.database.model.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Data

@EntityListeners(AuditingEntityListener.class)
@DynamicInsert
@DynamicUpdate
@Table(name = "order_payments")
@XmlRootElement
public class OrderPayment extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    @Basic(optional = false)
    @Column(name = "order_id")
    private Long orderId;

    @Basic(optional = false)
    @Column(name = "payment_id")
    private Long paymentId;

    @Basic(optional = false)
    @Column(name = "transaction_id")
    private Long transactionId;
    
    @Basic(optional = false)
    @Column(name = "status")
    private int status;

}
