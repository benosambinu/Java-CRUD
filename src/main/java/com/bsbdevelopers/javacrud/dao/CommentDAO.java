package com.bsbdevelopers.javacrud.dao;

import com.bsbdevelopers.javacrud.application.db.MongoDBConnection;
import com.bsbdevelopers.javacrud.models.Comment;
import com.bsbdevelopers.javacrud.models.UserProfile;
import com.google.gson.Gson;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Projections;
import org.bson.Document;

public class CommentDAO {

    MongoDatabase database = MongoDBConnection.getInstance().getDatabase();
    MongoCollection<Document> commentCollection = database.getCollection("Comment");
    MongoCollection<Document> taskCollection = database.getCollection("Task");
    MongoCollection<Document> allowedUsers = database.getCollection("AllowedUsers");

    public String addComment(String authHeader, String taskId, Comment comment){
        Gson gson = new Gson();
        String sessionId = authHeader.split("Bearer ")[1];
        Document dbUser = allowedUsers.find(new Document("sessionId", sessionId)).projection(Projections.exclude("_id")).first();
        String JsonUser = dbUser.toJson();
        UserProfile userProfile = gson.fromJson(JsonUser, UserProfile.class);
        comment.setCommentedPersonID(userProfile.getUserEmail());
        Document document = Document.parse(gson.toJson(comment));
        commentCollection.insertOne(document);
        Document taskDocument = taskCollection.find(new Document("taskId", taskId)).projection(Projections.exclude("_id")).first();
        if (taskDocument != null){
            //TODO add the comment id to the comments string list
        }
        return "The Comment has been created " + comment.getCommentID();
    }

}
