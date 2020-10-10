package com.example.interviewapp.integration.book;

import com.example.interviewapp.InterviewappApplication;
import com.example.interviewapp.controller.ApiError;
import com.example.interviewapp.controller.book.BookDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = InterviewappApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReplaceNotExistingBookIT {

    @LocalServerPort
    private int port;

    private TestRestTemplate restTemplate = new TestRestTemplate();
    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void shouldReceiveErrorWhenReplaceNotExistingBook() {
        // Prepare error and book to replace
        BookDto notExistingBookDto = new BookDto(0L, "REPLACED_NAME", "REPLACED_AUTHOR");
        ApiError expectedError = new ApiError(HttpStatus.NOT_FOUND, "Book with id 0 not found.");

        // Replace not existing book
        RequestEntity<BookDto> requestEntity = RequestEntity.put(createURI("/book/0")).body(notExistingBookDto);
        ResponseEntity<Object> notValidEntity = this.restTemplate.exchange(requestEntity, Object.class);

        // Check error
        assertEquals(HttpStatus.NOT_FOUND, notValidEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, notValidEntity.getHeaders().getContentType());
        assertEquals(expectedError, this.objectMapper.convertValue(notValidEntity.getBody(), ApiError.class));
    }

    private URI createURI(String path) {
        return URI.create("http://localhost:" + port + path);
    }
}
