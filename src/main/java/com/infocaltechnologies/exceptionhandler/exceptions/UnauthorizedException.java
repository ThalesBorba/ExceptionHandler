package com.infocaltechnologies.exceptionhandler.exceptions;

public class UnauthorizedException extends RuntimeException{

    public UnauthorizedException() {
        super("Recurso indisponível para usuários não autenticados");
    }
}
