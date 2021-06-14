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
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@DynamicInsert
@DynamicUpdate
@Table(name = "order_reports")
@XmlRootElement
public class OrderReport extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    @Basic(optional = false)
    @Column(name = "report_id")
    private Long reportId;
    
    @Basic(optional = false)
    @Column(name = "order_id")
    private Long orderId;
    
    @Basic(optional = false)
    @Column(name = "status")
    private boolean status;
    
    @Column(name = "note")
    private String note;
    
}
