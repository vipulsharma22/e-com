package com.nitsoft.ecommerce.database.model;


import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;



@Data
public class UserChoices {
    private Long id;
    private Long userId;
    private String data;
    private String type;
    private Date createdOn;
    private Date updatedOn;
}
