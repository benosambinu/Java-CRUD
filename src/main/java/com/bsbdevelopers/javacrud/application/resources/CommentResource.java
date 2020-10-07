package com.bsbdevelopers.javacrud.application.resources;


import com.bsbdevelopers.javacrud.dao.CommentDAO;
import com.bsbdevelopers.javacrud.models.Comment;
import org.bson.Document;
import javax.annotation.security.PermitAll;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

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

    @Path("/update-comment")
    @PermitAll
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateComment(@HeaderParam("Authorization") String authHeader, Comment comment){
        String response = commentDAO.updateComment(authHeader,comment);
        return Response.status(Response.Status.OK).entity(response).build();
    }

    @Path("/view-comment")
    @PermitAll
    @GET
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response viewComment(@QueryParam("taskId") String taskId){
        List<Document> response = commentDAO.viewComment(taskId);
        return Response.status(Response.Status.OK).entity(response).build();
    }

}
