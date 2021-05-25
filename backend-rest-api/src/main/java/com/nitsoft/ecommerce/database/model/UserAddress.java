package com.nitsoft.ecommerce.database.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@Setter
@NoArgsConstructor
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
    @Column(name = "adress")
    private String adress;
    
    @Basic(optional = false)
    @Column(name = "phone")
    private String phone;
    
    @Basic(optional = false)
    @Column(name = "fax")
    private String fax;
    
    @Basic(optional = false)
    @Column(name = "city")
    private String city;
    
    @Basic(optional = false)
    @Column(name = "country")
    private String country;
    
    @Basic(optional = false)
    @Column(name = "status")
    private int status;
}
