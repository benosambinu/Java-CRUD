package com.bsbdevelopers.javacrud.application.resources;


import com.bsbdevelopers.javacrud.dao.LoginDAO;

import javax.annotation.security.PermitAll;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
public class LoginResource {
    LoginDAO loginDAO = new LoginDAO();


    @Path("/login")
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response userLogin(@QueryParam("email") String userEmail, @QueryParam("password") String userPassword){
        String response = loginDAO.userLogin(userEmail,userPassword);
        return Response.status(Response.Status.OK).entity(response).build();
    }

    @Path("/logout")
    @GET
    @PermitAll
    public Response userLogOut(@HeaderParam("Authorization") String authHeader){
        String response = loginDAO.userLogout(authHeader);
        return Response.status(Response.Status.OK).entity(response).build();
    }


}
