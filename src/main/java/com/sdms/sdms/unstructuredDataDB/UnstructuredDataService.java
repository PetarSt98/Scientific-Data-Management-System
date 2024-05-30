package com.sdms.sdms.unstructuredDataDB;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class UnstructuredDataService {
    @Autowired
    private UnstructuredDataRepository imageRepository;

    public String storeImage(MultipartFile file) throws IOException {
        try {


            UnstructuredData newImage = new UnstructuredData();
            newImage.setTitle(file.getOriginalFilename());
            newImage.setImage(file.getBytes());

            imageRepository.save(newImage);
            return "File uploaded successfully with ID: " + newImage.getId();
        }
        catch (Exception e) {
            throw new IOException(e.getMessage());
        }
    }

    public List<UnstructuredData> getAllImages() {
        return imageRepository.findAll();
    }

    public Optional<UnstructuredData> getImageById(String id) {
        return imageRepository.findById(id);
    }

    public Optional<UnstructuredData> getImageByTitle(String title) {
        return imageRepository.findByTitle(title);
    }
}
