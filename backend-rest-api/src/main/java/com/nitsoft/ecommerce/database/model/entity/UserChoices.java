package com.nitsoft.ecommerce.database.model.entity;


import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;


@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
@Table(name = "user_choices")
public class UserChoices {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;

    @Basic(optional = false)
    @Column(name = "user_id")
    private Long userId;

    @Basic(optional = false)
    @Column(name = "data")
    private String data;

    @Basic(optional = false)
    @Column(name = "type")
    private String type;

    @Basic(optional = false)
    @Column(name = "created_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;

    @Basic(optional = false)
    @Column(name = "updated_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedOn;
}
