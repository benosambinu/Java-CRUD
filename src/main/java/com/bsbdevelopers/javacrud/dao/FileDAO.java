package com.bsbdevelopers.javacrud.dao;

import com.bsbdevelopers.javacrud.application.db.MongoDBConnection;
import com.bsbdevelopers.javacrud.models.FileAttachment;
import com.google.gson.Gson;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Projections;
import org.bson.Document;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import java.io.*;

public class FileDAO {
    MongoDatabase database = MongoDBConnection.getInstance().getDatabase();
    MongoCollection<Document> fileCollection = database.getCollection("File");
    MongoCollection<Document> taskCollection = database.getCollection("Task");
    Gson gson = new Gson();

    public String addFile(InputStream fileInputStream, FormDataContentDisposition fileMetaData, String fileTaskId) throws IOException {
        String UPLOAD_PATH = "/home/dxuser/IdeaProjects/JavaCRUD/src/main/java/com/dexlock/javacrud/files/";
        FileAttachment fileAttachment = new FileAttachment();
        fileAttachment.setFileTaskId(fileTaskId);
        fileAttachment.setFileName(fileMetaData.getFileName());

        int read = 0;
        byte[] bytes = new byte[1024];

        OutputStream out = new FileOutputStream(new File(UPLOAD_PATH + fileMetaData.getFileName()+fileAttachment.getFileId()));
        while ((read = fileInputStream.read(bytes)) != -1)
        {
            out.write(bytes, 0, read);
        }
        out.flush();
        out.close();
        fileAttachment.setFileLocation(UPLOAD_PATH + fileMetaData.getFileName()+fileAttachment.getFileId());

        Document taskDocument = taskCollection.find(new Document("taskId", fileAttachment.getFileTaskId())).projection(Projections.exclude("_id")).first();
        if (taskDocument != null){
            Document updatedTask = new Document();
            updatedTask.put("$push",new Document().append("taskFiles",fileAttachment.getFileId()));
            Document fileDocument = Document.parse(gson.toJson(fileAttachment));
            fileCollection.insertOne(fileDocument);
            taskCollection.updateOne(taskDocument,updatedTask);
            return "The File has been attached with ID " + fileAttachment.getFileId();
        }
        return "The task id specified is not found";
    }

    public File downloadFile(String fileId){
        Document fileDocument = fileCollection.find(new Document("fileId",fileId)).projection(Projections.exclude("_id")).first();
        if(fileDocument != null) {
            String fileJson = fileDocument.toJson();
            FileAttachment fileAttachment = gson.fromJson(fileJson, FileAttachment.class);
            File fileDownload = new File(fileAttachment.getFileLocation());
            if(fileDownload.exists()) {
                return fileDownload;
            }
        }
        return null;
    }

    public String deleteFile(String fileId){
        Document fileDocument = fileCollection.find(new Document("fileId",fileId)).projection(Projections.exclude("_id")).first();
        if(fileDocument != null){
            String fileJson = fileDocument.toJson();
            FileAttachment fileAttachment = gson.fromJson(fileJson, FileAttachment.class);
            Document taskDocument = taskCollection.find(new Document("taskId", fileAttachment.getFileTaskId())).projection(Projections.exclude("_id")).first();
            if (taskDocument != null){
                Document updatedTask = new Document();
                updatedTask.put("$pull",new Document().append("taskComments",fileAttachment.getFileTaskId()));
                taskCollection.updateOne(taskDocument,updatedTask);
                fileCollection.deleteOne(new Document("fileId", fileId));
                File fileToBeDeleted = new File(fileAttachment.getFileLocation());
                if (fileToBeDeleted != null)
                    fileToBeDeleted.delete();
                return "The file has been deleted";
            }
            else
                return "The Task id does not exist";
        }
        else
            return "The file does not exist";
    }
}
