package com.bsbdevelopers.javacrud.application;

import com.bsbdevelopers.javacrud.application.resources.TaskResource;
import com.bsbdevelopers.javacrud.application.resources.UserResource;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.bson.Document;

public class CRUDApplication extends Application<ApplicationConfig> {
    public void run(ApplicationConfig applicationConfig, Environment environment) throws Exception {
        MongoClient mongo = new MongoClient(applicationConfig.getMongoHost(), applicationConfig.getMongoPort());
        MongoDatabase database = mongo.getDatabase(applicationConfig.getDbName());
        environment.jersey().register(new UserResource(database));
        environment.jersey().register(new TaskResource(database));
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
