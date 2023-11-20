package com.infocaltechnologies.exceptionhandler.exceptions;

public class BadGatewayException extends RuntimeException {

    public BadGatewayException() {
        super("Erro na comunicação com a GerenciaNet");
    }
}
