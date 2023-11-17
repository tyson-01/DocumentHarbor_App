package com.example.documentharbor.servercommunication;

public class ApiResponse {
    private String status;
    private String message;
    private Object data;

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }

    //TODO: with ApiInterface, not yet used, for the upload image
}
