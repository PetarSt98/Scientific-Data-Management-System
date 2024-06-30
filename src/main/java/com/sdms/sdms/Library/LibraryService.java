package com.sdms.sdms.Library;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.util.List;

@Service
public class LibraryService {
    private final  LibraryRepository libraryRepository;
    private final BookFormatter bookFormatter;

    @Autowired
    public LibraryService(LibraryRepository libraryRepository, BookFormatter bookFormatter) {
        this.libraryRepository = libraryRepository;
        this.bookFormatter = bookFormatter;
    }
    public List<Books> getAllBooks() {
        return libraryRepository.findAll();
    }

    public void addBook(Books book) {
        libraryRepository.save(book);
        System.out.println(bookFormatter.formatBookDetails(book));
    }
}
