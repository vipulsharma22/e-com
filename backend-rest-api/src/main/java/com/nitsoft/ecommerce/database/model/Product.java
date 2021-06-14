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
@Table(name = "products")
@XmlRootElement
public class Product extends AbstractEntity {

    private static final long serialVersionUID = 1L;
    
    @Column(name = "company_id")
    private Long companyId;
    
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    
    @Basic(optional = false)
    @Column(name = "sale_price")
    private double salePrice;
    
    @Basic(optional = false)
    @Column(name = "list_price")
    private double listPrice;
    
    @Basic(optional = false)
    @Column(name = "default_image")
    private String defaultImage;
    
    @Basic(optional = false)
    @Column(name = "overview")
    private String overview;
    
    @Basic(optional = false)
    @Column(name = "quantity")
    private int quantity;
    
    @Column(name = "is_stock_controlled")
    private Boolean isStockControlled;
    
    @Basic(optional = false)
    @Column(name = "status")
    private int status;
    
    @Column(name = "description")
    private String description;

    @Basic(optional = false)
    @Column(name = "rank")
    private int rank;
    
    @Basic(optional = false)
    @Column(name = "sku")
    private String sku;

    @Basic(optional = false)
    @Column(name = "browsing_name")
    private String browsingName;

}
