package com.nitsoft.ecommerce.database.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@DynamicInsert
@DynamicUpdate
@Table(name = "categories")
@XmlRootElement
public class Category extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    @Basic(optional = false)
    @Column(name = "company_id")
    private Long companyId;

    @Column(name = "parent_id")
    private Long parentId;

    @Basic(optional = false)
    @Column(name = "name")
    private String name;

    @Basic(optional = false)
    @Column(name = "status")
    private int status;

    @Column(name = "position")
    private Integer position;

    @Column(name = "description")
    private String description;
}
