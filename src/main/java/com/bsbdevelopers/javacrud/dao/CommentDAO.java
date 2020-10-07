package com.bsbdevelopers.javacrud.dao;

import com.bsbdevelopers.javacrud.application.db.MongoDBConnection;
import com.bsbdevelopers.javacrud.models.Comment;
import com.bsbdevelopers.javacrud.models.UserProfile;
import com.google.gson.Gson;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import java.util.ArrayList;
import java.util.List;

public class CommentDAO {

    MongoDatabase database = MongoDBConnection.getInstance().getDatabase();
    MongoCollection<Document> commentCollection = database.getCollection("Comment");
    MongoCollection<Document> taskCollection = database.getCollection("Task");
    MongoCollection<Document> allowedUsers = database.getCollection("AllowedUsers");
    Gson gson = new Gson();


    public String addComment(String authHeader, String taskId, Comment comment){
        String sessionId = authHeader.split("Bearer ")[1];
        Document taskDocument = taskCollection.find(new Document("taskId", taskId)).projection(Projections.exclude("_id")).first();
        if (taskDocument != null){
            Document dbUser = allowedUsers.find(new Document("sessionId", sessionId)).projection(Projections.exclude("_id")).first();
            String JsonUser = dbUser.toJson();
            UserProfile userProfile = gson.fromJson(JsonUser, UserProfile.class);
            comment.setCommentedPersonID(userProfile.getUserEmail());
            Document document = Document.parse(gson.toJson(comment));
            commentCollection.insertOne(document);
            Document updatedTask = new Document();
            updatedTask.put("$push",new Document().append("taskComments",comment.getCommentID()));
            taskCollection.updateOne(taskDocument,updatedTask);
            return "The Comment has been created with ID " + comment.getCommentID();
        }
        return "The given Task id is not present in the database";
    }

    public String deleteComment(String authHeader, String commentID){

        Document commentDocument = commentCollection.find(new Document("commentID",commentID)).projection(Projections.exclude("_id")).first();
        if (commentDocument != null){
            String jsonComment = commentDocument.toJson();
            Comment commentToDelete = gson.fromJson(jsonComment,Comment.class);
            Document taskDocument = taskCollection.find(new Document("taskId", commentToDelete.getCommentedTaskId())).projection(Projections.exclude("_id")).first();
            if (taskDocument != null){
                Document updatedTask = new Document();
                updatedTask.put("$pull",new Document().append("taskComments",commentToDelete.getCommentID()));
                taskCollection.updateOne(taskDocument,updatedTask);
            }
            else {
                return "The given task id does not exist";
            }
            commentCollection.deleteOne(new Document("commentID",commentID));
            return "The comment has been deleted";
        }
        return "The given comment does not exist";
    }

    public String updateComment(String authHeader, Comment comment){
        Document updatedComment = Document.parse(gson.toJson(comment));
        Document originalComment = commentCollection.find(new Document("commentID", comment.getCommentID())).projection(Projections.exclude("_id")).first();
        if(originalComment != null){
            for (String name : updatedComment.keySet()) {
                commentCollection.updateOne(Filters.eq("commentID", comment.getCommentID()), Updates.set(name,updatedComment.get(name)));
            }
            return "The Comment "+ comment.getCommentID()+" has been updated";
        }
        return "The given comment id does not exist";
    }

    public List<Document> viewComment(String taskId){
        List<Document> taskComments = new ArrayList<Document>();
        taskComments = commentCollection.find(new Document("commentedTaskId", taskId)).projection(Projections.exclude("_id")).into(taskComments);
        return taskComments;
    }

}
