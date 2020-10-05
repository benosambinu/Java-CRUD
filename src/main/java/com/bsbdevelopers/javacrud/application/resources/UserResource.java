package com.bsbdevelopers.javacrud.application.resources;

import com.bsbdevelopers.javacrud.dao.UserDAO;
import com.bsbdevelopers.javacrud.models.User;
import org.bson.Document;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;


@Path("user")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    public UserDAO userDAO = new UserDAO();

    //end point to add user
    @Path("/add-user")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed("Super_Admin")
    public Response addUser(User user){
        String message = userDAO.addUser(user);
        return Response.status(Response.Status.OK).entity(message).build();
    }

    //end point to list all users
    @Path("/list-users")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    @RolesAllowed("Super_Admin")
    public Response listUsers(){
        List<Document> allUsers;
        allUsers = userDAO.listUsers();
        return Response.status(Response.Status.OK).entity(allUsers).build();
    }


}
