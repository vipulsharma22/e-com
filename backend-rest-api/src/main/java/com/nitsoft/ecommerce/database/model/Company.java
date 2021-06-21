package com.nitsoft.ecommerce.database.model;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;


@Data

@XmlRootElement
public class Company extends AbstractEntity {

    private static final long serialVersionUID = 1L;
    

    @Column(name = "name")
    private String name;
    

    @Column(name = "status")
    private int status;
    

    @Column(name = "create_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

}
