package com.nitsoft.ecommerce.database.model;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;


@Data

public class OrderDetail implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private Long orderId;
    private Long productId;
    private Date createdAt;
    private Date updatedAt;

    private String name;

    private String description;

    private int quantity;

    private double salePrice;

    private double listPrice;

    private String productType;

}
