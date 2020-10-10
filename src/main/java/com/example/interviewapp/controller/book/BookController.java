package com.example.interviewapp.controller.book;

import com.example.interviewapp.repository.book.BookNotFoundException;
import com.example.interviewapp.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/book")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping()
    public ResponseEntity<BookDto> addBook(@RequestBody @Valid BookDto newBookDTO) {
        BookDto addedBookDto = this.bookService.addBook(newBookDTO.toBook()).toDto();

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path(addedBookDto.getId().toString())
                .build().toUri();
        return ResponseEntity
                .created(location)
                .body(addedBookDto);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<BookDto> getBook(@PathVariable Long id) throws BookNotFoundException {
        BookDto loadedBookDto = this.bookService.getBook(id).toDto();
        return ResponseEntity
                .ok()
                .body(loadedBookDto);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<BookDto> replaceBook(@RequestBody @Valid BookDto newBookDTO) throws BookNotFoundException {
        BookDto replacedBookDto = this.bookService.replaceBook(newBookDTO.toBook()).toDto();
        return ResponseEntity
                .ok()
                .body(replacedBookDto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        this.bookService.deleteBook(id);
        return ResponseEntity.ok(null);
    }
}
