package com.nitsoft.ecommerce.api.response.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserLogInResponse {
    private String userName;
    private String token;
}
