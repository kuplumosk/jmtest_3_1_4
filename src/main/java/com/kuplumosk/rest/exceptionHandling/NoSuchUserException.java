package com.kuplumosk.rest.exceptionHandling;

public class NoSuchUserException extends RuntimeException{

    public NoSuchUserException(String message) {
        super(message);
    }
}
