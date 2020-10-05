package com.bsbdevelopers.javacrud.application.db;

import com.bsbdevelopers.javacrud.application.ApplicationConfig;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public class MongoDBConnection {
    ApplicationConfig obj = new ApplicationConfig();
    private static MongoDBConnection instance = null;
    private MongoClient client;
    private MongoDatabase database;
    private MongoDBConnection(){
        this.client = new MongoClient(obj.getMongoHost(), obj.mongoPort);
        this.database = this.client.getDatabase(obj.dbName);
    }

    public static synchronized MongoDBConnection getInstance(){
        if (instance == null){
            instance = new MongoDBConnection();
        }
        return instance;
    }

    public MongoClient getClient() {
        return client;
    }

    public MongoDatabase getDatabase() {
        return database;
    }
}
