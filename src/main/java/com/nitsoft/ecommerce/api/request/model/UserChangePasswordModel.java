/*
 * Copyright (c) NIT-Software. All Rights Reserved.
 * This software is the confidential and proprietary information of NIT-Software,
 * ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the license agreement you entered into with NIT-Software.
 */
package com.nitsoft.ecommerce.api.request.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Trinhlbk
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserChangePasswordModel {
    public String userId;
    public String oldPassword;
    public String newPassword;
}
