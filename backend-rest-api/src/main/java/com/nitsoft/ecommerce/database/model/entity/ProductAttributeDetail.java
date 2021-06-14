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
@Table(name = "product_attribute_details")
@XmlRootElement
public class ProductAttributeDetail extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    @Basic(optional = false)
    @Column(name = "product_id")
    private Long productId;

    @Basic(optional = false)
    @Column(name = "attribute_id")
    private Long attributeId;

    @Column(name = "value_string")
    private String valueString;
    
    @Column(name = "value_numberic")
    private Double valueNumberic;

}
