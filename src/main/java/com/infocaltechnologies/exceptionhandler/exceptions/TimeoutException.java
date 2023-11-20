package com.infocaltechnologies.exceptionhandler.exceptions;

public class TimeoutException extends RuntimeException {

    public TimeoutException(String message) {
        super(message);
    }
}
