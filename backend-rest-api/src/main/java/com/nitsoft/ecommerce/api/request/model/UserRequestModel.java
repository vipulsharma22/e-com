package com.nitsoft.ecommerce.api.request.model;

import lombok.*;

@Data
public class UserRequestModel {
    private Long userId;
    private String firstName;
    private String lastName;
    private String middleName;
    private String email;
    private String phone;
    private String address;
    private String city;
    private String country;
    private String otp;
    private String password;
    
}
