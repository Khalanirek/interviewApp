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
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = InterviewappApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AddNotValidBookIT {

    @LocalServerPort
    private int port;

    private TestRestTemplate restTemplate = new TestRestTemplate();
    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void shouldReturnErrorWhenAddNotValidBook() {
        // Prepare book
        BookDto bookDto = new BookDto(null, null, "");
        HashMap<String, String> errorMessages = new HashMap<>();

        // Prepare error
        errorMessages.put("name", "Name of the book can't be blank");
        errorMessages.put("author", "Author of the book can't be blank");
        ApiError expectedError = new ApiError(HttpStatus.BAD_REQUEST, errorMessages);

        // Add not valid book
        ResponseEntity<Object> responseEntity = restTemplate
                .postForEntity(createURI("/book"), bookDto, Object.class);

        // Check error
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
        assertEquals(expectedError, this.objectMapper.convertValue(responseEntity.getBody(), ApiError.class));
    }


    private URI createURI(String path) {
        return URI.create("http://localhost:" + port + path);
    }
}
