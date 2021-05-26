/*
 * Copyright (c) NIT-Software. All Rights Reserved.
 * This software is the confidential and proprietary information of NIT-Software,
 * ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the license agreement you entered into with NIT-Software.
 */
package com.nitsoft.ecommerce.auth;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthUser {

    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private boolean active;
    private String role;
    private String email;
    private String phone;
}
