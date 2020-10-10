package com.example.interviewapp.repository;

public abstract class EntityNotFoundException extends Exception {
    public EntityNotFoundException(String message) {
        super(message);
    }
}
