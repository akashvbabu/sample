package com.news.sample.exception;

/**
 * Custom Exception to represent an invalid filter choice for news
 */
public class InvalidNewsFilterException extends RuntimeException {

    public InvalidNewsFilterException(String message) {
        super(message);
    }
}
