package com.sdms.sdms.Library;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/library")
public class LibraryController {
    private final LibraryService libraryService;

    @Autowired
    public LibraryController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @GetMapping("all-books")
    public List<Books> getAllBooks() {
        return libraryService.getAllBooks();
    }

    @PostMapping("add-new-book")
    public ResponseEntity<String> addNewBook(@RequestBody Books book) {
        try {
            libraryService.addBook(book);
            return ResponseEntity.ok("New book successfully added.");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add book: " + ex.getMessage());
        }
    }
}
