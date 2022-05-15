package com.javabeer.usecase.exception;

public class InvalidBeerCategoryException extends RuntimeException {

    public InvalidBeerCategoryException(String message) {
        super(message);
    }
}
