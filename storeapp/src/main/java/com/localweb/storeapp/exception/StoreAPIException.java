package com.localweb.storeapp.exception;

import org.springframework.http.HttpStatus;

public class StoreAPIException extends RuntimeException{
    private HttpStatus httpStatus;
    private String message;

    public StoreAPIException(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public StoreAPIException(String message, HttpStatus httpStatus, String message1) {
        super(message);
        this.httpStatus = httpStatus;
        this.message = message1;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
