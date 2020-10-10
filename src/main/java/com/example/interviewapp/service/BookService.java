package com.example.interviewapp.service;

import com.example.interviewapp.domain.Book;
import com.example.interviewapp.repository.book.BookEntity;
import com.example.interviewapp.repository.book.BookNotFoundException;
import com.example.interviewapp.repository.book.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book addBook(Book newBook) {
        BookEntity newBookEntity = newBook.toEntity();
        newBookEntity.setId(null);
        return this.bookRepository.save(newBookEntity).toBook();
    }

    public Book getBook(Long id) throws BookNotFoundException {
        Optional<BookEntity> loadedBookEntity = bookRepository.findById(id);
        return  loadedBookEntity.map(BookEntity::toBook).orElseThrow(() -> new BookNotFoundException(id));
    }

    public Book replaceBook(Book newBook) throws BookNotFoundException {
        Optional<BookEntity> loadedBookEntity = bookRepository.findById(newBook.getId());
        if (loadedBookEntity.isPresent()) {
            return this.bookRepository.save(newBook.toEntity()).toBook();
        } else {
            throw new BookNotFoundException(newBook.getId());
        }
    }

    public void deleteBook(Long id) {
        Optional<BookEntity> loadedBookEntity = bookRepository.findById(id);
        if (loadedBookEntity.isPresent()) {
            this.bookRepository.deleteById(id);
        }
    }
}
