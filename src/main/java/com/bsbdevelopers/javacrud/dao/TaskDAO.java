package com.bsbdevelopers.javacrud.dao;

import com.bsbdevelopers.javacrud.models.Task;
import com.google.gson.Gson;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Projections;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;


public class TaskDAO {
    public String addTask(Task task, MongoCollection<Document> taskCollection){

        Gson gson = new Gson();
        Document document = Document.parse(gson.toJson(task));
        taskCollection.insertOne(document);
        return "The task has been added";
    }

    public List<Document> listTasks(MongoCollection<Document> taskCollection){
        List<Document> allTasks = new ArrayList<>();
        allTasks = taskCollection.find().projection(Projections.exclude("_id")).into(allTasks);
        return allTasks;

    }

    public String updateTask(Task task, MongoCollection<Document> taskCollection){

        return "Task updating is success";
    }


}
