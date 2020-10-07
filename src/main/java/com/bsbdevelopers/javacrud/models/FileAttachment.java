package com.bsbdevelopers.javacrud.models;

import java.util.UUID;

public class FileAttachment {
    private String fileId = UUID.randomUUID().toString();
    private String fileName;
    private String fileLocation;
    private String fileTaskId;

    public String getFileId() {
        return fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileLocation() {
        return fileLocation;
    }

    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    public String getFileTaskId() {
        return fileTaskId;
    }

    public void setFileTaskId(String fileTaskId) {
        this.fileTaskId = fileTaskId;
    }
}
