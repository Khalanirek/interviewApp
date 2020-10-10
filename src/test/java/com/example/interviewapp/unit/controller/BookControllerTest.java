package com.example.interviewapp.unit.controller;

import com.example.interviewapp.controller.ApiError;
import com.example.interviewapp.controller.book.BookController;
import com.example.interviewapp.domain.Book;
import com.example.interviewapp.repository.book.BookNotFoundException;
import com.example.interviewapp.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    BookService bookService;

    private ObjectMapper objectMapper = new ObjectMapper();


    @Test
    void shouldReturnStatusCreated_ContentTypeApplicationJson_BooDtoJsonInContent_WhenAddBook() throws Exception {
        Book book = new Book(null, "TEST_NAME", "TEST_AUTHOR");
        Book addedBook = new Book(1L, "TEST_NAME", "TEST_AUTHOR");
        String inputContent = this.objectMapper.writeValueAsString(book.toDto());
        String expectedContent = this.objectMapper.writeValueAsString(addedBook.toDto());

        when(this.bookService.addBook(book)).thenReturn(addedBook);

        MvcResult mvcResult = this.mockMvc.
                perform(MockMvcRequestBuilders.post("http://localhost:8080/book/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inputContent))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedContent))
                .andReturn();
    }

    @Test
    void shouldReturnStatusBadRequest_ContentTypeApplicationJson_ErrorMessageJsonInContent_WhenAddNotValidBook()
            throws Exception {
        Book book = new Book(null, null, "");
        String inputContent = this.objectMapper.writeValueAsString(book.toDto());

        MvcResult mvcResult = this.mockMvc.
                perform(MockMvcRequestBuilders.post("http://localhost:8080/book/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inputContent))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errorMessages.name", Is.is("Name of the book can't be blank")))
                .andExpect(jsonPath("$.errorMessages.author", Is.is("Author of the book can't be blank")))
                .andReturn();
    }

    @Test
    void shouldReturnStatusOk_ContentTypeApplicationJson_BookDtoJsonInContent_WhenGetExistingBook() throws Exception {
        Book book = new Book(1L, "TEST_NAME", "TEST_AUTHOR");
        String expectedContent = this.objectMapper.writeValueAsString(book.toDto());

        when(this.bookService.getBook(1L)).thenReturn(book);

        MvcResult mvcResult = this.mockMvc
                .perform(MockMvcRequestBuilders.get("http://localhost:8080/book/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedContent))
                .andReturn();
    }

    @Test
    void shouldReturnStatusNotFound_ContentTypeTextPlain_ErrorMessageInContent_WhenGetNotExistingBook()
            throws Exception {
        Book book = new Book(1L, "TEST_NAME", "TEST_AUTHOR");
        ApiError expectedError = new ApiError(HttpStatus.NOT_FOUND, "Book with id 1 not found.");
        String expectedErrorJson = this.objectMapper.writeValueAsString(expectedError);
        BookNotFoundException bookNotFoundException = new BookNotFoundException(1L);

        when(this.bookService.getBook(1L)).thenThrow(bookNotFoundException);

        MvcResult mvcResult = this.mockMvc
                .perform(MockMvcRequestBuilders.get("http://localhost:8080/book/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(expectedErrorJson))
                .andReturn();
    }

    @Test
    void shouldReturnStatusOk_ContentTypeApplicationJson_BooDtoJsonInContent_WhenReplaceExistingBook() throws Exception {
        Book book = new Book(1L, "TEST_NAME", "TEST_AUTHOR");
        String inputContent = this.objectMapper.writeValueAsString(book.toDto());
        String expectedContent = inputContent;

        when(this.bookService.replaceBook(book)).thenReturn(book);

        MvcResult mvcResult = this.mockMvc
                .perform(MockMvcRequestBuilders
                        .put("http://localhost:8080/book/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inputContent))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedContent))
                .andReturn();
    }

    @Test
    void shouldReturnStatusNotFound_ContentTypeTextPlain_ErrorMessageInContent_WhenReplaceNotExistingBook() throws Exception {
        Book book = new Book(1L, "TEST_NAME", "TEST_AUTHOR");
        String inputContent = this.objectMapper.writeValueAsString(book.toDto());
        ApiError expectedError = new ApiError(HttpStatus.NOT_FOUND, "Book with id 1 not found.");
        String expectedErrorJson = this.objectMapper.writeValueAsString(expectedError);
        BookNotFoundException bookNotFoundException = new BookNotFoundException(1L);

        when(this.bookService.replaceBook(book)).thenThrow(bookNotFoundException);

        MvcResult mvcResult = this.mockMvc
                .perform(MockMvcRequestBuilders
                        .put("http://localhost:8080/book/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inputContent))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedErrorJson))
                .andReturn();
    }

    @Test
    void shouldDeleteBook() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("http://localhost:8080/book/1"))
                .andExpect(status().isOk())
                .andReturn();
    }
}
