/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nitsoft.ecommerce.api.response.model;

import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.*;

/**
 *
 * @author Trinhlbk
 */
@Data

public class UserDetailResponseModel {
    private Long userId;
    private Long companyId;
    private int roleId;
    private String email;
    private String firstName;
    private String middleName;
    private String lastName;
    private Date createDate;
    private String salt;
    private String address;
    private String phone;
    private String city;
    private String country;
}
