package com.example.demo.exception.handler;

import com.example.demo.exception.EntityAlreadyExistsException;
import com.example.demo.exception.ResponseError;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseError handleValidationExceptions(MethodArgumentNotValidException e) {
        BindingResult result = e.getBindingResult();

        StringBuilder errorMessage = new StringBuilder();

        for (FieldError error : result.getFieldErrors()) {
            errorMessage.append(error.getDefaultMessage()).append("; ");
        }

        return new ResponseError(errorMessage.toString(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseError handleEntityAlreadyExistsException(EntityAlreadyExistsException e) {
        return new ResponseError(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseError handleEntityNotFoundException(EntityNotFoundException e) {
        return new ResponseError(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnsupportedOperationException.class)
    @ResponseStatus(HttpStatus.PAYMENT_REQUIRED)
    public ResponseError handleUnsupportedOperationException(UnsupportedOperationException e) {
        return new ResponseError(e.getMessage(), HttpStatus.PAYMENT_REQUIRED);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseError handleIllegalArgumentException(IllegalArgumentException e) {
        return new ResponseError(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
