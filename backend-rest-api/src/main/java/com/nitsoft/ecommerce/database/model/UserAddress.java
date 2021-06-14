package com.nitsoft.ecommerce.database.model;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;


@Data
@AllArgsConstructor
public class UserAddress extends AbstractEntity {

    private static final long serialVersionUID = 1L;
    private Long userId;
    private String address;
    private String pinCode;
    private String landMark;
    private String userName;
    private String phone;
    private String city;
}
