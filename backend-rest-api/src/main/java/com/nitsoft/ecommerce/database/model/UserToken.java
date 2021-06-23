package com.nitsoft.ecommerce.database.model;

import lombok.*;
import java.io.Serializable;
import java.util.Date;


@Data

public class UserToken implements Serializable {

    private static final long serialVersionUID = 1L;

    private String token;

    private Long companyId;

    private Long userId;

    private Date loginDate;

    private Date expirationDate;

    private String sessionData;

}
