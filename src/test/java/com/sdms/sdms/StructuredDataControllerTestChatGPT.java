package com.sdms.sdms;

import com.sdms.sdms.structuredDataDB.StructuredData;
import com.sdms.sdms.structuredDataDB.StructuredDataController;
import com.sdms.sdms.structuredDataDB.StructuredDataService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class StructuredDataControllerTestChatGPT {

    @Mock
    private StructuredDataService structuredDataService;

    @InjectMocks
    private StructuredDataController structuredDataController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testUploadData_Success() throws Exception {
        MultipartFile file = mock(MultipartFile.class);
        when(file.getOriginalFilename()).thenReturn("test.json");
        doNothing().when(structuredDataService).storeFile(file);

        ResponseEntity<String> response = structuredDataController.uploadData(file);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("File uploaded successfully!", response.getBody());
    }

    @Test
    void testUploadData_Failure() throws Exception {
        MultipartFile file = mock(MultipartFile.class);
        when(file.getOriginalFilename()).thenReturn("test.json");
        doThrow(new IOException("Test Exception")).when(structuredDataService).storeFile(file);

        ResponseEntity<String> response = structuredDataController.uploadData(file);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Failed to upload file: Test Exception", response.getBody());
    }

    @Test
    void testDisplayDataById_Success() {
        Long id = 1L;
        StructuredData data = new StructuredData(id, "{\"key\":\"value\"}", "test.json");
        when(structuredDataService.getJsonDataById(id)).thenReturn(Optional.of(data));

        String response = structuredDataController.displayData(id);

        assertEquals("{\"key\":\"value\"}", response);
    }

    @Test
    void testDisplayDataById_NotFound() {
        Long id = 1L;
        when(structuredDataService.getJsonDataById(id)).thenReturn(Optional.empty());

        try {
            structuredDataController.displayData(id);
        } catch (RuntimeException ex) {
            assertEquals("Data with id " + id + " does not exist", ex.getMessage());
        }
    }

    @Test
    void testDownloadDataAsFile_Success() throws Exception {
        Long id = 1L;
        StructuredData data = new StructuredData(id, "{\"key\":\"value\"}", "test.json");

        byte[] buf = data.getData().getBytes();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", data.getName());
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        ResponseEntity<byte[]> expectedResponse = new ResponseEntity<>(buf, headers, HttpStatus.OK);

        // Explicitly cast the service mock return type to match the expectedResponse type
        when(structuredDataService.downloadFileById(id)).thenReturn((ResponseEntity) expectedResponse);

        ResponseEntity<?> response = structuredDataController.downloadDataAsFile(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response);
    }

    @Test
    void testDownloadDataAsFile_NotFound() throws Exception {
        Long id = 1L;
        when(structuredDataService.getJsonDataById(id)).thenReturn(Optional.empty());

        ResponseEntity<?> response = structuredDataController.downloadDataAsFile(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Data not found: Data with id 1 not found", response.getBody());
    }

    ////

    @Test
    void testEditData_Success() {
        StructuredDataController.Changes changes = new StructuredDataController.Changes(Map.of("path.to.value", "newValue"), "test.json", 1L);
        doNothing().when(structuredDataService).editFile(changes);

        ResponseEntity<String> response = structuredDataController.editData(changes);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("File edited successfully!", response.getBody());
    }

    @Test
    void testEditData_Failure() {
        StructuredDataController.Changes changes = new StructuredDataController.Changes(Map.of("path.to.value", "newValue"), "test.json", 1L);
        doThrow(new RuntimeException("Test Exception")).when(structuredDataService).editFile(changes);

        ResponseEntity<String> response = structuredDataController.editData(changes);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Failed to edit file: Test Exception", response.getBody());
    }



}
