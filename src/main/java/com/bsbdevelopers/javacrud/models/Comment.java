package com.bsbdevelopers.javacrud.models;

import java.util.UUID;

public class Comment {
    private String commentID = UUID.randomUUID().toString();
    private String commentedPersonID;
    private String message;
    private String lastUpdated;

    public String getCommentID() {
        return commentID;
    }

    public String getCommentedPersonID() {
        return commentedPersonID;
    }

    public void setCommentedPersonID(String commentedPersonID) {
        this.commentedPersonID = commentedPersonID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

}
