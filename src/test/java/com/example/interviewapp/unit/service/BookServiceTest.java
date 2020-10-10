package com.example.interviewapp.unit.service;

import com.example.interviewapp.domain.Book;
import com.example.interviewapp.repository.book.BookEntity;
import com.example.interviewapp.repository.book.BookNotFoundException;
import com.example.interviewapp.repository.book.BookRepository;
import com.example.interviewapp.service.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @Test
    void shouldSaveBookWhenAddBook() {

        Book book = new Book(1L, "TEST_NAME", "TEST_AUTHOR");
        BookEntity bookEntity = book.toEntity();

        when(this.bookRepository.save(any(BookEntity.class))).thenReturn(bookEntity);
        this.bookService.addBook(book);

        verify(this.bookRepository).save(any(BookEntity.class));
    }

    @Test
    void shouldSetIdToNullWhenAddBook() {
        Book book = mock(Book.class);
        BookEntity bookEntity = mock(BookEntity.class);

        when(book.toEntity()).thenReturn(bookEntity);
        when(this.bookRepository.save(any(BookEntity.class))).thenReturn(bookEntity);

        this.bookService.addBook(book);

        verify(bookEntity).setId(null);
    }

    @Test
    void shouldReturnBookCreatedFromEntityWhenAddBook() {
        Book book = new Book(1L, "TEST_NAME", "TEST_AUTHOR");
        BookEntity bookEntity = spy(book.toEntity());

        when(this.bookRepository.save(any(BookEntity.class))).thenReturn(bookEntity);

        assertEquals(Book.class, this.bookService.addBook(book).getClass());
        verify(bookEntity).toBook();
    }

    @Test
    void shouldGetBookWhenGetBookAndBookExists() throws BookNotFoundException {
        Book book = new Book(1L, "TEST_NAME", "TEST_AUTHOR");
        BookEntity bookEntity = book.toEntity();

        when(this.bookRepository.findById(1L)).thenReturn(Optional.of(bookEntity));

        assertEquals(book, this.bookService.getBook(1L));

    }

    @Test
    void shouldThrowBookNotFoundExceptionWhenGetNotExistingBook() throws BookNotFoundException {
        when(this.bookRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> this.bookService.getBook(1L));
    }

    @Test
    void shouldReplaceBookWhenBookExists() throws BookNotFoundException {
        Book book = new Book(1L, "TEST_NAME", "TEST_AUTHOR");
        BookEntity bookEntity = book.toEntity();

        when(this.bookRepository.findById(1L)).thenReturn(Optional.of(bookEntity));
        when(this.bookRepository.save(any(BookEntity.class))).thenReturn(bookEntity);

        this.bookService.replaceBook(book);

        verify(this.bookRepository).save(any(BookEntity.class));
    }

    @Test
    void shouldThrowBookNotFoundExceptionWhenReplaceNotExistingBook() {
        Book book = new Book(1L, "TEST_NAME", "TEST_AUTHOR");

        when(this.bookRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> this.bookService.replaceBook(book));
    }

    @Test
    void shouldRepositoryDeleteBook() {
        Book book = new Book(1L, "TEST_NAME", "TEST_AUTHOR");
        BookEntity bookEntity = book.toEntity();

        when(this.bookRepository.findById(1L)).thenReturn(Optional.of(bookEntity));

        this.bookService.deleteBook(1L);

        verify(this.bookRepository).deleteById(1L);
    }
}
