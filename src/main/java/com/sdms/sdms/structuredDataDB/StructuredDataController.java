package com.sdms.sdms.structuredDataDB;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(path = "api/v1/structured-data")
public class StructuredDataController {

    private final StructuredDataService structuredDataService;

    @Autowired // bolje je ovo sa kontruktorom i final nego bezkao u unstructured
    public StructuredDataController(StructuredDataService structuredDataService, StructuredDataRepository structuredDataRepository) {
        this.structuredDataService = structuredDataService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadData(@RequestParam("file") MultipartFile file) {
        try {
            structuredDataService.storeFile(file);
            return ResponseEntity.ok("File uploaded successfully!");
        } catch (Exception ex) {
            return ResponseEntity.status(
                    HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file: " + ex.getMessage()
            );
        }
    }

    @GetMapping("/display-by-id/{id}")
    @PreAuthorize("hasRole('client_admin')")
    public String displayData(@PathVariable("id") Long id) {
        StructuredData data = structuredDataService.getJsonDataById(id)
                .orElseThrow(() -> new RuntimeException("Data with id " + id + " does not exist"));

        return data.getData();
    }

    @GetMapping("/display-by-name/{name}")
    @PreAuthorize("hasRole('client_user')")
    public String displayData(@PathVariable("name") String name) {
        StructuredData data = structuredDataService.getJsonDataByName(name)
                .orElseThrow(() -> new RuntimeException("Data with name " + name + " does not exist"));

        return data.getData();
    }

    @GetMapping("/download-by-id/{id}")
    public ResponseEntity<?> downloadDataAsFile(@PathVariable("id") Long id) {
        try {
            return structuredDataService.downloadFileById(id);
        } catch (RuntimeException ex) {  // Catching RuntimeException for not found data.
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data not found: " + ex.getMessage());
        } catch (IOException ex) {  // Catching IOException for any input/output error during the process.
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to download file: " + ex.getMessage());
        } catch (Exception ex) {  // General Exception handler.
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + ex.getMessage());
        }
    }
}