package com.infocaltechnologies.exceptionhandler.exceptions;

public class TooManyRequestsException extends RuntimeException {

    public TooManyRequestsException() {
        super("Excesso de requisições. Por favor aguarde");
    }
}
