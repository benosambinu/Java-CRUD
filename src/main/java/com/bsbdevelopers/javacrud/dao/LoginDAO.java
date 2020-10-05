package com.bsbdevelopers.javacrud.dao;

import com.bsbdevelopers.javacrud.application.db.MongoDBConnection;
import com.bsbdevelopers.javacrud.models.User;
import com.google.gson.Gson;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Projections;
import org.bson.Document;

import java.util.UUID;

public class LoginDAO {
    Gson gson = new Gson();
    MongoDatabase database = MongoDBConnection.getInstance().getDatabase();
    MongoCollection<Document> allowedUsers = database.getCollection("AllowedUsers");
    MongoCollection<Document> userCollection = database.getCollection("User");


    public String userLogin(String userEmail, String userPassword) {
        Document dbUser = userCollection.find(new Document("email", userEmail).append("password", userPassword)).projection(Projections.exclude("_id")).first();
        if (dbUser != null) {
            String JsonUser = dbUser.toJson();
            User user = gson.fromJson(JsonUser, User.class);
            String sessionId = UUID.randomUUID().toString();
            String email = user.getEmail();
            String role = user.getUserRole();
            Document userSession = new Document("sessionId", sessionId)
                    .append("userEmail", email)
                    .append("userRole", role);
            allowedUsers.insertOne(userSession);
            return sessionId;
        } else {
            return "Invalid Credentials";
        }
    }

    public String userLogout(String authHeader) {
        String sessionId = authHeader.split("Bearer ")[1];
        Document query = new Document("sessionId", sessionId);
        allowedUsers.deleteOne(new Document(query));
        return "The user is logged out";
    }

}
