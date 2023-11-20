package com.infocaltechnologies.exceptionhandler.exceptions;

public class GatewayTimeoutException extends RuntimeException {

    public GatewayTimeoutException() {
        super("O gateway não respondeu a tempo. Por favor tente de novo mais tarde");
    }
}
