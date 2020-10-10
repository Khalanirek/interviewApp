package com.example.interviewapp.domain;

import com.example.interviewapp.controller.book.BookDto;
import com.example.interviewapp.repository.book.BookEntity;

import java.util.Objects;

public class Book {
    private final Long id;
    private final String name;
    private final String author;

    public Book(Long id, String name, String author) {
        this.id = id;
        this.name = name;
        this.author = author;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public BookDto toDto() {
        return new BookDto(this.id, this.name, this.author);
    }

    public BookEntity toEntity() {
        return new BookEntity(this.id, this.name, this.author);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(id, book.id) &&
                name.equals(book.name) &&
                author.equals(book.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, author);
    }
}
