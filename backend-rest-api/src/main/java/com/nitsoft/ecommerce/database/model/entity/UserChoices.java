package com.nitsoft.ecommerce.database.model.entity;


import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;


@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
@Table(name = "user_choices")
public class UserChoices extends AbstractEntity{

    @Basic(optional = false)
    @Column(name = "user_id")
    private Long userId;

    @Basic(optional = false)
    @Column(name = "data")
    private String data;

    @Basic(optional = false)
    @Column(name = "type")
    private String type;

}
