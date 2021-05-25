/*
 * Copyright (c) NIT-Software. All Rights Reserved.
 * This software is the confidential and proprietary information of NIT-Software,
 * ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the license agreement you entered into with NIT-Software.
 */
package com.nitsoft.ecommerce.auth;


import lombok.Data;

@Data
public class AuthUser {

    private final Long id;
    private final String username;
    private final String password;
    private String firstName;
    private String lastName;
    private final boolean enabled;
    private String role;
    
    public AuthUser(
            Long id,
            String username,
            String password, String role, String firstName, String lastName,
            boolean enabled
    ) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return "AuthUser{" + "id=" + id + ", username=" + username + ", password=" + password + ", role=" + role + ", enabled=" + enabled + '}';
    }

}
