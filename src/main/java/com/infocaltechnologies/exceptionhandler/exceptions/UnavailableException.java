package com.infocaltechnologies.exceptionhandler.exceptions;

public class UnavailableException extends RuntimeException {

    public UnavailableException() {
        super("O serviço não se encontra disponível no momento. Por favor, tente mais tarde");
    }
}
