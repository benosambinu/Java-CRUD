package com.bsbdevelopers.javacrud.application.resources;

import com.bsbdevelopers.javacrud.dao.TaskDAO;
import com.bsbdevelopers.javacrud.dao.UserDAO;
import com.bsbdevelopers.javacrud.models.User;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;


@Path("user")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    public UserDAO userDAO = new UserDAO();
    private final MongoCollection<Document> userCollection;

    public UserResource(MongoDatabase database) {
        this.userCollection  = database.getCollection("User");

    }

    //end point to add user
    @Path("/add-user")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addUser(User user){
        String message = userDAO.addUser(user,userCollection);
        return Response.status(Response.Status.OK).entity(message).build();
    }

    //end point to list all users
    @Path("/list-users")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public Response listUsers(){
        List<Document> allUsers;
        allUsers = userDAO.listUsers(userCollection);
        return Response.status(Response.Status.OK).entity(allUsers).build();
    }


}
