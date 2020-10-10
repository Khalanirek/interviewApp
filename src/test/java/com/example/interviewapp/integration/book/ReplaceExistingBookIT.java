package com.example.interviewapp.integration.book;

import com.example.interviewapp.InterviewappApplication;
import com.example.interviewapp.controller.book.BookDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = InterviewappApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReplaceExistingBookIT {

    @LocalServerPort
    private int port;

    private TestRestTemplate restTemplate = new TestRestTemplate();
    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void shouldReplaceBook() {
        // Add book
        BookDto bookDto = new BookDto(null, "TEST_NAME", "TEST_AUTHOR");
        ResponseEntity<BookDto> savedBookResponse = restTemplate.postForEntity(createURI("/book"), bookDto, BookDto.class);

        // Replace data in saved book
        BookDto newBookDto = this.objectMapper.convertValue(savedBookResponse.getBody(), BookDto.class);
        newBookDto.setName("REPLACED_NAME");
        newBookDto.setAuthor("REPLACED_AUTHOR");
        RequestEntity<BookDto> requestEntity = RequestEntity
                .put(createURI("/book/" + newBookDto.getId()))
                .body(newBookDto);
        ResponseEntity<BookDto> responseEntity = restTemplate.exchange(requestEntity, BookDto.class);

        // Check if replaced data in returned book
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
        assertNotNull(responseEntity.getBody().getId());
        assertEquals(newBookDto.getName(), responseEntity.getBody().getName());
        assertEquals(newBookDto.getAuthor(), responseEntity.getBody().getAuthor());

        // Load replaced book
        ResponseEntity<BookDto> loadedBookResponseEntity = restTemplate
                .getForEntity(createURI("/book/" + newBookDto.getId()), BookDto.class);

        // Check if loaded book is same
        assertEquals(HttpStatus.OK, loadedBookResponseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, loadedBookResponseEntity.getHeaders().getContentType());
        assertNotNull(loadedBookResponseEntity.getBody().getId());
        assertEquals(newBookDto.getName(), loadedBookResponseEntity.getBody().getName());
        assertEquals(newBookDto.getAuthor(), loadedBookResponseEntity.getBody().getAuthor());
    }

    private URI createURI(String path) {
        return URI.create("http://localhost:" + port + path);
    }
}
