package com.news.sample.controller;

import com.news.sample.exception.InvalidNewsFilterException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * Class to handle http response handling for exceptions from the service
 */
@RestControllerAdvice
public class NewsControllerAdvice {

    @ExceptionHandler(InvalidNewsFilterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public InvalidNewsFilterException ExceptionResponseHandleInvalidParameter(InvalidNewsFilterException exception) {
        return exception;
    }
}
