package com.test.mc;

import org.springframework.http.HttpStatus;

public class ApiError extends Throwable {

    private HttpStatus status;
    private String message;


    public ApiError(HttpStatus status, String message) {
        super();
        this.status = status;
        this.message = message;

    }

}