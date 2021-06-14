package com.nitsoft.ecommerce.api.request.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
public class AuthRequestModel {
    public String userName;
    public String phone;
    public String otp;
    public String password;
    public boolean keepMeLogin;
}
