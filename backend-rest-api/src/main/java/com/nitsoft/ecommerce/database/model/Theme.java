package com.nitsoft.ecommerce.database.model;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;


@Data
@AllArgsConstructor
public class Theme implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "theme_id")
    private Integer themeId;
    
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
