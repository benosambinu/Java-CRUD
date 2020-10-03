package com.bsbdevelopers.javacrud.models;

import java.util.UUID;

public class User {
    private String userID = UUID.randomUUID().toString();
    private String name;
    private String email;
    private String password;
    private enum UserRole{
        Super_Admin, Project_Manager, User;
    }
    private UserRole userRole;

    public String getUserID() {
        return userID;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
