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

public class UserToken implements Serializable {

    private static final long serialVersionUID = 1L;

    private String token;

    private Long companyId;

    private Long userId;

    private Date loginDate;

    private Date expirationDate;

    private String sessionData;
    
}
