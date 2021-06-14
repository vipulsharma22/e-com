package com.nitsoft.ecommerce.database.model;

import lombok.*;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;


@Data
@AllArgsConstructor
@XmlRootElement
public class Category extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    private Long companyId;
    private Long parentId;

    private String name;

    private int status;
    private Integer position;
    private String description;
}
