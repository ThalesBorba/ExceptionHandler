package com.infocaltechnologies.exceptionhandler.exceptions;

public class ForbiddenException extends RuntimeException {

    public ForbiddenException() {
        super("Permissões insuficientes para acessar esse recurso");
    }
}
