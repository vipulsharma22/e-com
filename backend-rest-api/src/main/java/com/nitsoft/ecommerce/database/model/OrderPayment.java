package com.nitsoft.ecommerce.database.model;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;


@Data
@AllArgsConstructor
public class OrderPayment implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long orderId;
    private Long paymentId;

    private Long transactionId;

    private int status;

}
