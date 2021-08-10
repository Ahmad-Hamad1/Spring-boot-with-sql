package com.example.demo.exceptions;

import org.hibernate.PropertyValueException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class ExceptionAdvice {
    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleNoSuchElementException(NoSuchElementException noSuchElementException,
                                                 HttpServletRequest httpServletRequest) {
        return new ApiError(404, noSuchElementException.getMessage(),
                httpServletRequest.getServletPath());
    }

    @ExceptionHandler(PropertyValueException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handlePropertyValueException(PropertyValueException propertyValueException,
                                                 HttpServletRequest httpServletRequest) {
            return new ApiError(400, propertyValueException.getMessage(),
                httpServletRequest.getServletPath());
    }

}
