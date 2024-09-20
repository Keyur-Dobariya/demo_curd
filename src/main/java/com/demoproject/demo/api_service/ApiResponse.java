package com.demoproject.demo.api_service;

public class ApiResponse<T> {
    private boolean status;
    private String message;
    private String jwtToken;
    private T data;

    public ApiResponse(boolean status, String message, String jwtToken, T data) {
        this.status = status;
        this.message = message;
        this.jwtToken = jwtToken;
        this.data = data;
    }

    public ApiResponse(boolean status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    // Getters and Setters
    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}

