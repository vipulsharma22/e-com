package com.nitsoft.ecommerce.database.model.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Data

@EntityListeners(AuditingEntityListener.class)
@DynamicInsert
@DynamicUpdate
@Table(name = "themes")
@XmlRootElement
public class Theme extends AbstractEntity {

    private static final long serialVersionUID = 1L;
    
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    
    @Basic(optional = false)
    @Column(name = "version")
    private String version;
    
    @Basic(optional = false)
    @Column(name = "thumbnail")
    private String thumbnail;
    
    @Basic(optional = false)
    @Column(name = "source_path")
    private String sourcePath;
    
}
