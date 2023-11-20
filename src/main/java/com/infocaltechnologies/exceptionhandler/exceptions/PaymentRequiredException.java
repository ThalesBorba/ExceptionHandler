package com.infocaltechnologies.exceptionhandler.exceptions;

public class PaymentRequiredException extends RuntimeException {

    public PaymentRequiredException() {
        super("Pagamento requerido");
    }
}
