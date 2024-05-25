package com.sdms.sdms.structuredDataDB;

import org.springframework.beans.factory.annotation.Autowired;
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
}
