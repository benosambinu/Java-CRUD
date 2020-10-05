package com.bsbdevelopers.javacrud.application.resources;


import com.bsbdevelopers.javacrud.dao.CommentDAO;
import com.bsbdevelopers.javacrud.models.Comment;
import javax.annotation.security.PermitAll;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/comment")
public class CommentResource {
    CommentDAO commentDAO = new CommentDAO();


    @Path("/add-comment")
    @PermitAll
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addComment(@HeaderParam("Authorization") String authHeader, @QueryParam("taskId") String taskId, Comment comment){
        String response = commentDAO.addComment(authHeader,taskId, comment);
        return Response.status(Response.Status.OK).entity(response).build();
    }

    @Path("/delete-comment")
    @PermitAll
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteComment(@HeaderParam("Authorization") String authHeader, @QueryParam("commentID") String commentID){
        String response = commentDAO.deleteComment(authHeader, commentID);
        return Response.status(Response.Status.OK).entity(response).build();
    }

}
