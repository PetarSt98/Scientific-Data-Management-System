package com.sdms.sdms.Library;


public class BookFormatter {
    public String formatBookDetails(Books book) {
        return String.format("Book: %s by %s, published by %s on %s",
                book.getName(), book.getAuthor(), book.getPublisher(), book.getStartDate());
    }
}
