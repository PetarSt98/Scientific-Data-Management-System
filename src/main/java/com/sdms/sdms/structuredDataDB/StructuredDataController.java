package com.sdms.sdms.structuredDataDB;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(path = "api/v1/structured-data")
public class StructuredDataController {

    private final StructuredDataService structuredDataService;
    private final StructuredDataRepository structuredDataRepository;

    @Autowired
    public StructuredDataController(StructuredDataService structuredDataService, StructuredDataRepository structuredDataRepository) {
        this.structuredDataService = structuredDataService;
        this.structuredDataRepository = structuredDataRepository;
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
    public String displayData(@PathVariable("id") Long id) {
        StructuredData data = structuredDataService.getJsonDataById(id)
                .orElseThrow(() -> new RuntimeException("Data with id " + id + " does not exist"));

        return data.getData();
    }

    @GetMapping("/display-by-name/{name}")
    public String displayData(@PathVariable("name") String name) {
        StructuredData data = structuredDataService.getJsonDataByName(name)
                .orElseThrow(() -> new RuntimeException("Data with name " + name + " does not exist"));

        return data.getData();
    }

    @GetMapping("/download-by-id/{id}")
    public ResponseEntity<byte[]> downloadDataAsFile(@PathVariable("id") Long id) {
        StructuredData data = structuredDataService.getJsonDataById(id)
                .orElseThrow(() -> new RuntimeException("Data with id " + id + " not found"));

        byte[] buf = data.getData().getBytes();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", data.getName());
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        return new ResponseEntity<>(buf, headers, HttpStatus.OK);
    }
}
