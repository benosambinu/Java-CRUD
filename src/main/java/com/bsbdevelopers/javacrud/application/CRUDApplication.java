package com.bsbdevelopers.javacrud.application;

import com.bsbdevelopers.javacrud.application.resources.LoginResource;
import com.bsbdevelopers.javacrud.application.resources.TaskResource;
import com.bsbdevelopers.javacrud.application.resources.UserResource;
import com.bsbdevelopers.javacrud.models.UserProfile;
import io.dropwizard.Application;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.oauth.OAuthCredentialAuthFilter;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

public class CRUDApplication extends Application<ApplicationConfig> {
    public void run(ApplicationConfig applicationConfig, Environment environment) {
        environment.jersey().register(new TaskResource());
        environment.jersey().register(new UserResource());
        environment.jersey().register(new LoginResource());
        environment.jersey().register(new AuthDynamicFeature(
                new OAuthCredentialAuthFilter.Builder<UserProfile>()
                        .setAuthenticator(new APIAuthenticator())
                        .setAuthorizer(new APIAuthorizer())
                        .setPrefix("Bearer")
                        .buildAuthFilter()));
        environment.jersey().register(RolesAllowedDynamicFeature.class);
        environment.jersey().register(new AuthValueFactoryProvider.Binder<>(UserProfile.class));


    }

    @Override
    public String getName() {
        return "Java CRUD Application with MongoDB";
    }

    @Override
    public void initialize(Bootstrap<ApplicationConfig> bootstrap) {
        super.initialize(bootstrap);
    }

    public static void main(String[] args) throws Exception {
        new CRUDApplication().run(args);
    }
}
