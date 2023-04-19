package com.example.appnghenhac.models;

public class LoginResponse {
    public String token;
    public String expiration;

    public LoginResponse(String token, String expiration) {
        this.token = token;
        this.expiration = expiration;
    }

    public String getToken() {
        return token;
    }

    public String getExpiration() {
        return expiration;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }
}
