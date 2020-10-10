package com.example.interviewapp.unit.domain;

import com.example.interviewapp.controller.book.BookDto;
import com.example.interviewapp.domain.Book;
import com.example.interviewapp.repository.book.BookEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookTest {

    private BookDto bookDto = new BookDto(1L, "TEST_NAME", "TEST_AUTHOR");
    private Book book = new Book(1L, "TEST_NAME", "TEST_AUTHOR");
    private BookEntity bookEntity = new BookEntity(1L, "TEST_NAME", "TEST_AUTHOR");
    private BookDto transformedBookDto = this.book.toDto();
    private BookEntity transformedBookEntity = this.book.toEntity();

    @Test
    void shouldReturnBookWithSameIdWhenToDto() {
        assertEquals(this.bookDto.getId(), transformedBookDto.getId());
    }

    @Test
    void shouldReturnBookWithSameNameWhenToDto() {
        assertEquals(this.bookDto.getName(), transformedBookDto.getName());
    }

    @Test
    void shouldReturnBookWithSameAuthorWhenToDto() {
        assertEquals(this.bookDto.getAuthor(), transformedBookDto.getAuthor());
    }

    @Test
    void shouldReturnBookWithSameIdWhenToEntity() {
        assertEquals(this.book.getId(), transformedBookEntity.getId());
    }

    @Test
    void shouldReturnBookWithSameNameWhenToEntity() {
        assertEquals(this.book.getName(), transformedBookEntity.getName());
    }

    @Test
    void shouldReturnBookWithSameAuthorWhenToEntity() {
        assertEquals(this.book.getAuthor(), transformedBookEntity.getAuthor());
    }
}
