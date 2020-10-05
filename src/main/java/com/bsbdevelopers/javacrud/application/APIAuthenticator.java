package com.bsbdevelopers.javacrud.application;

import com.bsbdevelopers.javacrud.application.db.MongoDBConnection;
import com.bsbdevelopers.javacrud.models.UserProfile;
import com.google.gson.Gson;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Projections;
import io.dropwizard.auth.Authenticator;
import org.bson.Document;
import java.util.Optional;

public class APIAuthenticator implements Authenticator<String, UserProfile> {
    MongoDatabase database = MongoDBConnection.getInstance().getDatabase();
    MongoCollection<Document> allowedUsers = database.getCollection("AllowedUsers");
    Gson gson = new Gson();


    @Override
    public Optional<UserProfile> authenticate(String authToken) {

        Document query = new Document("sessionId", authToken);
        Document dbUser = allowedUsers.find(query).projection(Projections.exclude("_id")).first();
        if (dbUser != null) {
            String JsonUser = dbUser.toJson();
            UserProfile userProfile = gson.fromJson(JsonUser, UserProfile.class);
            return Optional.of(userProfile);
        }
        return Optional.empty();
    }
}

