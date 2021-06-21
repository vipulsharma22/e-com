package com.nitsoft.ecommerce.database.model;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;


@Data

public class Product extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    private Long companyId;

    private String name;

    private double salePrice;

    private double listPrice;

    private String defaultImage;

    private String overview;

    private int quantity;
    private Boolean isStockControlled;

    private int status;
    private String description;

    private int rank;

    private String sku;

    private String browsingName;

}
