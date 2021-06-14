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
@Table(name = "user_addresses")
@XmlRootElement
public class UserAddress extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    @Basic(optional = false)
    @Column(name = "user_id")
    private Long userId;
    
    @Basic(optional = false)
    @Column(name = "address")
    private String address;

    @Basic(optional = false)
    @Column(name = "pin_code")
    private String pinCode;

    @Basic(optional = false)
    @Column(name = "land_mark")
    private String landMark;

    @Basic(optional = false)
    @Column(name = "user_name")
    private String userName;

    @Basic(optional = false)
    @Column(name = "phone")
    private String phone;
    
    @Basic(optional = false)
    @Column(name = "city")
    private String city;
}
