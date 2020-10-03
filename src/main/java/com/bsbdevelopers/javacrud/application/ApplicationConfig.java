package com.bsbdevelopers.javacrud.application;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;

import javax.validation.constraints.NotEmpty;

public class ApplicationConfig extends Configuration {

    @JsonProperty
    @NotEmpty
    public String mongoHost;

    @JsonProperty
    public int mongoPort;

    @JsonProperty
    @NotEmpty
    public String dbName;

    public String getMongoHost() {
        return mongoHost;
    }

    public int getMongoPort() {
        return mongoPort;
    }

    public String getDbName() {
        return dbName;
    }
}
