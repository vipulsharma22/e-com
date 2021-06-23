package com.nitsoft.ecommerce.database.model.entity;

import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
@DynamicInsert
@DynamicUpdate
@Data
@Table(name = "user_tokens")
@XmlRootElement
public class UserToken extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    @Basic(optional = false)
    @Column(name = "token")
    private String token;

    @Basic(optional = false)
    @Column(name = "company_id")
    private Long companyId;

    @Basic(optional = false)
    @Column(name = "user_id")
    private Long userId;

    @Basic(optional = false)
    @Column(name = "login_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date loginDate;

    @Basic(optional = false)
    @Column(name = "expiration_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expirationDate;

    @Basic(optional = false)
    @Column(name = "session_data")
    private String sessionData;

}
