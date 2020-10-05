package com.bsbdevelopers.javacrud.application;

import com.bsbdevelopers.javacrud.models.UserProfile;
import io.dropwizard.auth.Authorizer;

public class APIAuthorizer implements Authorizer<UserProfile> {

    @Override
    public boolean authorize(UserProfile userProfile, String role) {
        return userProfile.getUserRole().equals(role);
    }

}
