package com.bsbdevelopers.javacrud.application.resources;

import com.bsbdevelopers.javacrud.dao.FileDAO;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.annotation.security.PermitAll;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class FileResource {
    FileDAO fileDAO = new FileDAO();


    @Consumes({MediaType.MULTIPART_FORM_DATA,MediaType.APPLICATION_FORM_URLENCODED})
    @Path("/add-file")
    @PermitAll
    @POST
    public Response addFile(@FormDataParam("file") InputStream fileInputStream,
                            @FormDataParam("file") FormDataContentDisposition fileMetaData, @QueryParam("fileTaskId") String fileTaskId) throws IOException {
        String response = fileDAO.addFile(fileInputStream, fileMetaData, fileTaskId);
        return Response.status(Response.Status.OK).entity(response).build();
    }

    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces("application/*")
    @Path("/download-file")
    @PermitAll
    @GET
    public Response downloadFile(@QueryParam("fileId") String fileId){
        File response = fileDAO.downloadFile(fileId);
        return Response.status(Response.Status.OK).entity(response).build();
    }

    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Path("/delete-file")
    @DELETE
    @PermitAll
    public Response deleteFile(@QueryParam("fileId") String fileId){
        String response = fileDAO.deleteFile(fileId);
        return Response.status(Response.Status.OK).entity(response).build();

    }
}
