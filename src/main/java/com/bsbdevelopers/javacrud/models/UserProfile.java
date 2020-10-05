package com.bsbdevelopers.javacrud.models;

import javax.security.auth.Subject;
import java.security.Principal;

public class UserProfile implements Principal {
    private String userEmail;
    private String sessionId;
    private String userRole;

    public UserProfile(String userEmail, String sessionId, String userRole) {
        this.userEmail = userEmail;
        this.sessionId = sessionId;
        this.userRole = userRole;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public boolean implies(Subject subject) {
        return false;
    }
}
