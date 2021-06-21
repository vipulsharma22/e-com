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

public class Review implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer reviewId;

    private Long userId;

    private Long productId;

    private Long companyId;

    private int rank;

    private String comment;

    private Date createDate;

}
