package com.sdms.sdms.unstructuredDataDB;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/unstructured-data")
public class UnstructuredDataController {
    @Autowired
    private UnstructuredDataService unstructuredDataService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("image") MultipartFile file) {
        try {
            return ResponseEntity.ok(unstructuredDataService.storeImage(file));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload image: " + e.getMessage());
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<UnstructuredData>> listAllImages() {
        List<UnstructuredData> images = unstructuredDataService.getAllImages();
        return ResponseEntity.ok(images);
    }

    @GetMapping("/download-by-id/{id}")
    public ResponseEntity<byte[]> downloadImageId(@PathVariable String id) {
        return unstructuredDataService.getImageById(id)
                .map(image -> ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + image.getTitle() + "\"")
                        .contentType(MediaType.IMAGE_JPEG) // Change this based on the actual image content type
                        .body(image.getImage()))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/download-by-title/{title}")
    public ResponseEntity<byte[]> downloadImageTitle(@PathVariable String title) {
        return unstructuredDataService.getImageByTitle(title)
                .map(image -> ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + image.getTitle() + "\"")
                        .contentType(MediaType.IMAGE_JPEG) // Change this based on the actual image content type
                        .body(image.getImage()))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
