package com.nitsoft.ecommerce.database.model;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;


@Data

public class User extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    private Long companyId;

    private int roleId;

    private String email;
    private String firstName;
    private String middleName;
    private String lastName;

    private int status;

    private String phone;

    private String encryptedPassword;

    private String salt;
}
