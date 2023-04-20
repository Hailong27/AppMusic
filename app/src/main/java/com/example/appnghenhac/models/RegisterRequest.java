package com.example.appnghenhac.models;

public class RegisterRequest {
    public  String Email;
    public  String Password;
    public  String Confirm;

    public RegisterRequest(String email, String password, String confirm) {
        Email = email;
        Password = password;
        Confirm = confirm;
    }

    public String getEmail() {
        return Email;
    }

    public String getPassword() {
        return Password;
    }

    public String getConfirm() {
        return Confirm;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public void setConfirm(String confirm) {
        Confirm = confirm;
    }
}
