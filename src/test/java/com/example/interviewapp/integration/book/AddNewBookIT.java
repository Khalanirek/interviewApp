package com.example.interviewapp.integration.book;

import com.example.interviewapp.InterviewappApplication;
import com.example.interviewapp.controller.book.BookDto;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = InterviewappApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AddNewBookIT {

    @LocalServerPort
    private int port;

    private TestRestTemplate restTemplate = new TestRestTemplate();

    @Test
    public void shouldAddBook() {
        // Prepare new book;
        BookDto bookDto = new BookDto(null, "TEST_NAME", "TEST_AUTHOR");

        ResponseEntity<BookDto> responseEntity = restTemplate
                .postForEntity(createURI("/book"), bookDto, BookDto.class);

        // Check if returned book same
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
        assertNotNull(responseEntity.getBody().getId());
        assertEquals(bookDto.getName(), responseEntity.getBody().getName());
        assertEquals(bookDto.getAuthor(), responseEntity.getBody().getAuthor());

        // Load saved book
        ResponseEntity<BookDto> loadedBookResponseEntity = restTemplate
                .getForEntity(createURI("/book/" + responseEntity.getBody().getId()), BookDto.class);

        // Check if saved book same
        assertEquals(HttpStatus.OK, loadedBookResponseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, loadedBookResponseEntity.getHeaders().getContentType());
        assertNotNull(loadedBookResponseEntity.getBody().getId());
        assertEquals(bookDto.getName(), loadedBookResponseEntity.getBody().getName());
        assertEquals(bookDto.getAuthor(), loadedBookResponseEntity.getBody().getAuthor());
    }


    private URI createURI(String path) {
        return URI.create("http://localhost:" + port + path);
    }
}
