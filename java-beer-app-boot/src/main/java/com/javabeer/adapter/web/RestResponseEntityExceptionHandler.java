package com.javabeer.adapter.web;

import com.javabeer.usecase.exception.BeerNotFoundException;
import com.javabeer.usecase.exception.BeerValidationException;
import com.javabeer.usecase.exception.InvalidBeerCategoryException;
import com.javabeer.usecase.exception.InvalidBeerIdException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {
            BeerValidationException.class,
            InvalidBeerCategoryException.class,
            InvalidBeerIdException.class
    })
    protected ResponseEntity<Object> handleValidationException(
            RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(),
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = {
            BeerNotFoundException.class
    })
    protected ResponseEntity<Object> handleBeerNotFound(
            RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(),
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }
}
