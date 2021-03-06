package com.nitsoft.ecommerce.database.model;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;


@Data

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
