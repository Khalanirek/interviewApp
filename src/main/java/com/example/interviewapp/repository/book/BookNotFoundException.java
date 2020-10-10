package com.example.interviewapp.repository.book;

import com.example.interviewapp.repository.EntityNotFoundException;

public class BookNotFoundException extends EntityNotFoundException {
    public BookNotFoundException(Long id) { super(String.format("Book with id %d not found.", id));
    }
}
