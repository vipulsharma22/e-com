package com.nitsoft.ecommerce.auth;


public class AuthenticationException extends RuntimeException {

    public AuthenticationException() {
        super("User Not Valid.");
    }
}

