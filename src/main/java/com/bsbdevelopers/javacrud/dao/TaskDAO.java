package com.bsbdevelopers.javacrud.dao;

import com.bsbdevelopers.javacrud.application.db.MongoDBConnection;
import com.bsbdevelopers.javacrud.models.Task;
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


public class TaskDAO {
    MongoDatabase database = MongoDBConnection.getInstance().getDatabase();
    MongoCollection<Document> taskCollection = database.getCollection("Task");
    MongoCollection<Document> allowedUsers = database.getCollection("AllowedUsers");

    public String addTask(String authHeader, Task task) {
        Gson gson = new Gson();
        String sessionId = authHeader.split("Bearer ")[1];
        Document query = new Document("sessionId", sessionId);
        Document dbUser = allowedUsers.find(query).projection(Projections.exclude("_id")).first();
        String JsonUser = dbUser.toJson();
        UserProfile userProfile = gson.fromJson(JsonUser, UserProfile.class);
        Document document = Document.parse(gson.toJson(task));
        document.put("reporter", userProfile.getUserEmail());
        taskCollection.insertOne(document);
        return "The task has been created " + task.getTaskId();

    }

    public String updateTask(String authHeader, Task task) {
        Gson gson = new Gson();
        Document taskToBeUpdated = Document.parse(gson.toJson(task));
        String sessionId = authHeader.split("Bearer ")[1];
        Document query = new Document("sessionId", sessionId);
        Document dbUser = allowedUsers.find(query).projection(Projections.exclude("_id")).first();
        String JsonUser = dbUser.toJson();
        UserProfile userProfile = gson.fromJson(JsonUser, UserProfile.class);
        Document taskQuery = new Document("taskId", task.getTaskId());
        Document document = taskCollection.find(taskQuery).projection(Projections.exclude("_id")).first();
        if(document != null) {
            String JsonTask = document.toJson();
            Task objTask = gson.fromJson(JsonTask, Task.class);
            if(objTask.getReporter().equals(userProfile.getUserEmail())){
                for (String name : taskToBeUpdated.keySet()) {
                    taskCollection.updateOne(Filters.eq("taskId", task.getTaskId()), Updates.set(name,taskToBeUpdated.get(name)));
                }
                return "The task "+ objTask.getTaskId()+" has been updated";
            }
            else{
                return "This user did not create the task";
            }
        }
        else{
            return "There is no task with this id";
        }


    }



    public List<Document> listTasks(){
        List<Document> allTasks = new ArrayList<>();
        allTasks = taskCollection.find().projection(Projections.exclude("_id")).into(allTasks);
        return allTasks;

    }




}
