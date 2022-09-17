package com.drk.tools.exceptions;

import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

public class InvalidJsonException extends ResponseEntityExceptionHandler {

    private  String success;
    private String error;
    private String message;

    public InvalidJsonException(String success, String error, String message){
        this.success = success;
        this.error = error;
        this.message = message;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
