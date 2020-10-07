package com.bsbdevelopers.javacrud.application.db;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public class MongoDBConnection {
    private static MongoDBConnection instance = null;
    private String mongoHost = "localhost";
    private int mongoPort = 27017;
    private String dbName = "projectmanagement";
    private MongoClient client;
    private MongoDatabase database;
    private MongoDBConnection(){
        this.client = new MongoClient(mongoHost,mongoPort);
        this.database = this.client.getDatabase(dbName);
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
