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
public class GetExistingBookIT {

    @LocalServerPort
    private int port;

    private TestRestTemplate restTemplate = new TestRestTemplate();

    @Test
    public void shouldGetBook() {
        // Add book
        BookDto bookDto = new BookDto(null, "TEST_NAME", "TEST_AUTHOR");
        ResponseEntity<BookDto> savedBookResponse = restTemplate
                .postForEntity(createURI("/book"), bookDto, BookDto.class);

        // Load added book
        ResponseEntity<BookDto> responseEntity = restTemplate
                .getForEntity(createURI("/book/" + savedBookResponse.getBody().getId()), BookDto.class);

        // Check if same as added
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
        assertNotNull(responseEntity.getBody().getId());
        assertEquals(bookDto.getName(), responseEntity.getBody().getName());
        assertEquals(bookDto.getAuthor(), responseEntity.getBody().getAuthor());
    }

    private URI createURI(String path) {
        return URI.create("http://localhost:" + port + path);
    }

}
