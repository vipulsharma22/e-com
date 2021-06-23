package com.nitsoft.ecommerce.database.model;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;


@Data

public class OrderReport extends AbstractEntity {

    private static final long serialVersionUID = 1L;
    
    @Column(name = "order_id")
    private Long orderId;
    

    @Column(name = "status")
    private boolean status;
    
    @Column(name = "note")
    private String note;
    
}
