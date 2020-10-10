package com.example.interviewapp.controller;

import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ApiError {

    private String appCode = "InterviewApp";
    private HttpStatus statusCode;
    private Map<String, String> errorMessages = new HashMap<>();

    public ApiError() {
    }

    public ApiError(HttpStatus statusCode, String errorMessage) {
        this.statusCode = statusCode;
        this.errorMessages.put("error", errorMessage);
    }

    public ApiError(HttpStatus statusCode, Map<String, String> errorMessages) {
        this.statusCode = statusCode;
        this.errorMessages = errorMessages;
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(HttpStatus statusCode) {
        this.statusCode = statusCode;
    }

    public Map<String, String> getErrorMessages() {
        return errorMessages;
    }

    public void setErrorMessages(Map<String, String> errorMessages) {
        this.errorMessages = errorMessages;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApiError apiError = (ApiError) o;
        return Objects.equals(appCode, apiError.appCode) &&
                statusCode == apiError.statusCode &&
                Objects.equals(errorMessages, apiError.errorMessages);
    }

    @Override
    public int hashCode() {
        return Objects.hash(appCode, statusCode, errorMessages);
    }
}
