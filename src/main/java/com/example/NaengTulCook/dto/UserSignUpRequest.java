package com.example.NaengTulCook.dto;

public class UserSignUpRequest {

    private String userIdentifier;
    private String password;

    // Getters and Setters
    public String getUserIdentifier() {
        return userIdentifier;
    }

    public void setUserIdentifier(String userIdentifier) {
        this.userIdentifier = userIdentifier;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
