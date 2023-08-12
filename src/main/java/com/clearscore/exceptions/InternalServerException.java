package com.clearscore.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Internal Server Error")
public class InternalServerException extends Exception {

    public InternalServerException(String message) {
        super(message);
    }

    public InternalServerException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
