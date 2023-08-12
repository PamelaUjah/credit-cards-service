package com.clearscore.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "The request contained invalid parameters")
public class InvalidParametersException extends RuntimeException {
 //todo: custom exception handling
    public InvalidParametersException(String message) {
        super(message);
    }

}

