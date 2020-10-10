package com.example.interviewapp.integration.book;

import com.example.interviewapp.InterviewappApplication;
import com.example.interviewapp.controller.ApiError;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = InterviewappApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GetNotExistingBookIT {

    @LocalServerPort
    private int port;

    private TestRestTemplate restTemplate = new TestRestTemplate();
    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void shouldReceiveErrorWhenGetNotExistingBook () {
        // Prepare error
        ApiError expectedError = new ApiError(HttpStatus.NOT_FOUND, "Book with id 0 not found.");

        // Get not existing book
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(createURI("/book/0"), Object.class);

        // Check error
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
        assertEquals(expectedError, this.objectMapper.convertValue(responseEntity.getBody(), ApiError.class));
    }

    private URI createURI(String path) {
        return URI.create("http://localhost:" + port + path);
    }

}
