package com.example.interviewapp.integration.book;

import com.example.interviewapp.InterviewappApplication;
import com.example.interviewapp.controller.book.BookDto;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = InterviewappApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DeleteBookIT {

    @LocalServerPort
    private int port;

    private TestRestTemplate restTemplate = new TestRestTemplate();

    @Test
    void shouldDeleteBook() {
        // Prepare book
        BookDto bookDto = new BookDto(null, "TEST_NAME", "TEST_AUTHOR");
        restTemplate.postForEntity(createURI("/book"), bookDto, BookDto.class);

        // Delete book
        RequestEntity<Void> requestEntity = RequestEntity.delete(createURI("/book/1")).build();
        ResponseEntity<Void> responseEntity = restTemplate.exchange(requestEntity, Void.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    private URI createURI(String path) {
        return URI.create("http://localhost:" + port + path);
    }
}
