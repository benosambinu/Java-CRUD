package com.bsbdevelopers.javacrud.application.resources;


import com.bsbdevelopers.javacrud.dao.TaskDAO;
import com.bsbdevelopers.javacrud.models.Task;
import com.bsbdevelopers.javacrud.models.User;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("task")
@Produces(MediaType.APPLICATION_JSON)

public class TaskResource {
    public TaskDAO taskDAO = new TaskDAO();
    private final MongoCollection<Document> taskCollection;

    public TaskResource(MongoDatabase database) {
        this.taskCollection = database.getCollection("Task");
    }

    //end point to add task
    @Path("/add-task")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addTask(Task task){
        String message = taskDAO.addTask(task,taskCollection);
        return Response.status(Response.Status.OK).entity(message).build();
    }

    //end point to list all tasks
    @Path("/list-tasks")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public Response listTasks(){
        List<Document> allTasks;
        allTasks = taskDAO.listTasks(taskCollection);
        return Response.status(Response.Status.OK).entity(allTasks).build();
    }

    //end point to update task
    @Path("/update-task")
    @Consumes(MediaType.APPLICATION_JSON)
    @PUT
    public Response updateTask(Task task){
        String message = taskDAO.updateTask(task, taskCollection);
        return Response.status(Response.Status.OK).entity(message).build();
    }

}
