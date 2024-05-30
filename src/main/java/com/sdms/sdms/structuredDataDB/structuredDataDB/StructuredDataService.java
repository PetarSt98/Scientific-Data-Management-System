package com.sdms.sdms.structuredDataDB.structuredDataDB;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class StructuredDataService {

    private final StructuredDataRepository structuredDataRepository;

    @Autowired
    public StructuredDataService(StructuredDataRepository structuredDataRepository) {
        this.structuredDataRepository = structuredDataRepository;
    }

    public void storeFile(MultipartFile file) throws IOException {
        String content = new String(file.getBytes());
        String fileName = file.getOriginalFilename();  // Retrieves the original file name

        Optional<StructuredData> fileExistenceCheck = structuredDataRepository.findByName(fileName);
        if (fileExistenceCheck.isPresent()){
            throw new IllegalStateException("File with name " + fileName + " already exist!");
        }

        structuredDataRepository.insertData(content, fileName);
    }

    public Optional<StructuredData> getJsonDataById(Long id)
    {
        return structuredDataRepository.findById(id);
    }

    public Optional<StructuredData> getJsonDataByName(String name)
    {
        return structuredDataRepository.findByName(name);
    }

    public ResponseEntity<?> downloadFileById(Long id) throws IOException {
        StructuredData data = getJsonDataById(id)
                .orElseThrow(() -> new RuntimeException("Data with id " + id + " not found"));
        byte[] buf = data.getData().getBytes();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", data.getName());
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        return new ResponseEntity<>(buf, headers, HttpStatus.OK);
    }
}
