package com.bsbdevelopers.javacrud.dao;

import com.bsbdevelopers.javacrud.application.db.MongoDBConnection;
import com.bsbdevelopers.javacrud.models.User;
import com.google.gson.Gson;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Projections;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    MongoDatabase database = MongoDBConnection.getInstance().getDatabase();
    MongoCollection<Document> userCollection = database.getCollection("User");
    public String addUser(User user){
        Gson gson = new Gson();
        Document document = Document.parse(gson.toJson(user));
        userCollection.insertOne(document);
        return "The user has been added";
    }

    public List<Document> listUsers(){
        List<Document> allUsers = new ArrayList<>();
        allUsers = userCollection.find().projection(Projections.exclude("_id","password")).into(allUsers);
        return allUsers;

    }
}
