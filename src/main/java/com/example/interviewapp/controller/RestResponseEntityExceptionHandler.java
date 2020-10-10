package com.example.interviewapp.controller;

import com.example.interviewapp.repository.EntityNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger logger = LogManager.getLogger(RestResponseEntityExceptionHandler.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @ExceptionHandler(value = EntityNotFoundException.class)
    protected ResponseEntity<ApiError> handleEntityNotFoundException(EntityNotFoundException ex, WebRequest request) {
        logger.error(ex.getStackTrace(), ex);
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, ex.getMessage());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
        //return handleExceptionInternal(ex, apiError, httpHeaders, HttpStatus.NOT_FOUND, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        logger.error(ex.getStackTrace(), ex);
        Map<String, String> errorMessages = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach((error) -> errorMessages.put(error.getField(), error.getDefaultMessage()));
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, errorMessages);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
        //return handleExceptionInternal(ex, apiError, httpHeaders, HttpStatus.BAD_REQUEST, request);
    }
}
