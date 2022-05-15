package com.javabeer.usecase.exception;

public class InvalidBeerIdException extends RuntimeException {

    public InvalidBeerIdException(String message) {
        super(message);
    }

}
