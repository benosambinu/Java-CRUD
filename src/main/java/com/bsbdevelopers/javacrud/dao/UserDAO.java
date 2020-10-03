package com.bsbdevelopers.javacrud.dao;

import com.bsbdevelopers.javacrud.models.User;
import com.google.gson.Gson;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Projections;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    public String addUser(User user, MongoCollection<Document> userCollection){

        Gson gson = new Gson();
        Document document = Document.parse(gson.toJson(user));
        userCollection.insertOne(document);
        return "The user has been added";
    }

    public List<Document> listUsers(MongoCollection<Document> userCollection){
        List<Document> allUsers = new ArrayList<>();
        allUsers = userCollection.find().projection(Projections.exclude("_id","password")).into(allUsers);
        return allUsers;

    }
}
