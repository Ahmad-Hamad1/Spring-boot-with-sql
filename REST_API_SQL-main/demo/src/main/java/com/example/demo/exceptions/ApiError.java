package com.example.demo.exceptions;

import java.util.Date;

public class ApiError {
    int status;
    String message;
    long timestamp;
    String path;

    public ApiError(int status, String message, String path) {
        this.status = status;
        this.message = message;
        timestamp = new Date().getTime();
        this.path = path;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
