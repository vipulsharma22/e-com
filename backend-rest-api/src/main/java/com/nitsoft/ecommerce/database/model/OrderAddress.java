package com.nitsoft.ecommerce.database.model;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;


@Data

public class OrderAddress extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    private String address;

    private String pinCode;

    private String landMark;
    private String customerName;

    private String phone;

    private String city;
}
