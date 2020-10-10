package com.example.interviewapp.unit.repository;

import com.example.interviewapp.controller.book.BookDto;
import com.example.interviewapp.domain.Book;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookEntityTest {

    private BookDto bookDto = new BookDto(1L, "TEST_NAME", "TEST_AUTHOR");
    private Book book = new Book(1L, "TEST_NAME", "TEST_AUTHOR");

    @Test
    void shouldReturnBookWithSameIdWhenToBook() {
        Book transformedBook = this.bookDto.toBook();

        assertEquals(this.book.getId(), transformedBook.getId());
    }

    @Test
    void shouldReturnBookWithSameNameWhenToBook() {
        Book transformedBook = this.bookDto.toBook();

        assertEquals(this.book.getName(), transformedBook.getName());
    }

    @Test
    void shouldReturnBookWithSameAuthorWhenToBook() {
        Book transformedBook = this.bookDto.toBook();

        assertEquals(this.book.getAuthor(), transformedBook.getAuthor());
    }
}
