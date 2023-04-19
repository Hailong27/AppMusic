package com.example.appnghenhac.models;

public class LoginRequest {
    public  String Email;
    public  String Password;

    public LoginRequest(String email, String password) {
        Email = email;
        Password = password;
    }

    public String getEmail() {
        return Email;
    }

    public String getPassword() {
        return Password;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
