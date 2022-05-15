package com.javabeer.usecase.exception;

public class BeerValidationException extends RuntimeException {

    public BeerValidationException(String message) {
        super(message);
    }
}
