package com.infocaltechnologies.exceptionhandler.exceptions;


public class ObjectNotFoundException extends RuntimeException {
    public ObjectNotFoundException(String object) {
        super(object + " não encontrado(a)(s)");
    }
}
