package com.nsrp.challenge.exceptionhandler;

public class ApiError {

    private String message;

    public ApiError() {
        // serialization
    }

    public ApiError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}