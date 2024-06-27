package com.sdms.sdms;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.*;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StructuredDataControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private HttpHeaders headers;

    @BeforeEach
    public void setUp() {
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    @Test
    public void testUploadData_Success() {
        ClassPathResource resource = new ClassPathResource("test.json");
        MockMultipartFile file = null;

        try {
            file = new MockMultipartFile("file", "test.json", "application/json", resource.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        HttpEntity<MultipartFile> entity = new HttpEntity<>(file, headers);
        ResponseEntity<String> response = restTemplate.postForEntity("/api/v1/structured-data/upload", entity, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("File uploaded successfully!", response.getBody());
    }

    @Test
    @WithMockUser(roles = "client_admin")
    public void testDisplayDataById_Success() {
        Long id = 1L;
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange("/api/v1/structured-data/display-by-id/" + id, HttpMethod.GET, entity, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    @WithMockUser(roles = "client_user")
    public void testDisplayDataByName_Success() {
        String name = "test.json";
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange("/api/v1/structured-data/display-by-name/" + name, HttpMethod.GET, entity, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testDownloadDataAsFile_Success() {
        Long id = 1L;
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<byte[]> response = restTemplate.exchange("/api/v1/structured-data/download-by-id/" + id, HttpMethod.GET, entity, byte[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }
}
