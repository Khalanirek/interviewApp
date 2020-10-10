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
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = InterviewappApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReplaceNotValidBookIT {

    @LocalServerPort
    private int port;

    private TestRestTemplate restTemplate = new TestRestTemplate();
    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void shouldReturnErrorWhenReplaceNotValidBook() {
        // Add Book
        BookDto bookDto = new BookDto(null, "TEST_NAME", "TEST_AUTHOR");
        ResponseEntity<BookDto> savedBookResponse = restTemplate.postForEntity(createURI("/book"), bookDto, BookDto.class);
        BookDto newBookDto = this.objectMapper.convertValue(savedBookResponse.getBody(), BookDto.class);

        // Prepare error
        HashMap<String, String> errorMessages = new HashMap<>();
        errorMessages.put("name", "Name of the book can't be blank");
        errorMessages.put("author", "Author of the book can't be blank");
        ApiError expectedError = new ApiError(HttpStatus.BAD_REQUEST, errorMessages);

        // Replace book with not valid data
        newBookDto.setName("");
        newBookDto.setAuthor(null);
        RequestEntity<BookDto> requestEntity = RequestEntity
                .put(createURI("/book/" + newBookDto.getId()))
                .body(newBookDto);
        ResponseEntity<Object> responseEntity = restTemplate.exchange(requestEntity, Object.class);

        // Check error - not valid
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
        assertEquals(expectedError, this.objectMapper.convertValue(responseEntity.getBody(), ApiError.class));

        // Load book which should be replaced
        ResponseEntity<BookDto> loadedResponseEntity = restTemplate
                .getForEntity(createURI("/book/" + savedBookResponse.getBody().getId()), BookDto.class);

        // Check if book in untouched
        assertEquals(HttpStatus.OK, loadedResponseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, loadedResponseEntity.getHeaders().getContentType());
        assertNotNull(loadedResponseEntity.getBody().getId());
        assertEquals(bookDto.getName(), loadedResponseEntity.getBody().getName());
        assertEquals(bookDto.getAuthor(), loadedResponseEntity.getBody().getAuthor());
    }


    private URI createURI(String path) {
        return URI.create("http://localhost:" + port + path);
    }
}
