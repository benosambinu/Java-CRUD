package com.bsbdevelopers.javacrud.application.resources;


import com.bsbdevelopers.javacrud.dao.TaskDAO;
import com.bsbdevelopers.javacrud.models.Task;
import org.bson.Document;
import javax.annotation.security.PermitAll;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("task")
@Produces(MediaType.APPLICATION_JSON)

public class TaskResource {
    public TaskDAO taskDAO = new TaskDAO();

    //end point to add task
    @Path("/add-task")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @PermitAll
    public Response addTask(@HeaderParam("Authorization") String authHeader, Task task){
        String message = taskDAO.addTask(authHeader, task);
        return Response.status(Response.Status.OK).entity(message).build();
    }

    //end point to list all tasks
    @Path("/list-tasks")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    @PermitAll
    public Response listTasks(){
        List<Document> allTasks;
        allTasks = taskDAO.listTasks();
        return Response.status(Response.Status.OK).entity(allTasks).build();
    }

    //end point to update task
    @Path("/update-task")
    @Consumes(MediaType.APPLICATION_JSON)
    @PUT
    @PermitAll
    public Response updateTask(@HeaderParam("Authorization") String authHeader,Task task){
        String message = taskDAO.updateTask(authHeader, task);
        return Response.status(Response.Status.OK).entity(message).build();
    }

}
