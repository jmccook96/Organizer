package com.bookclub.service;

import com.bookclub.dao.BookDAO;
import com.bookclub.model.Book;

import java.util.List;
import java.util.stream.Collectors;

public class BookServiceManager {

    private BookDAO bookDAO;

    public BookServiceManager(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    public List<Book> searchForBooks(String searchQuery, String selectedGenre) {
        System.out.println("Search query: " + searchQuery);
        System.out.println("Selected genre: " + selectedGenre);

        List<Book> filteredBooks = bookDAO.findAllBooks();
        System.out.println("Books before filtering: " + filteredBooks.size());

        // Filter based on the search query (title or author)
        if (!searchQuery.isEmpty()) {
            filteredBooks = filteredBooks.stream()
                    .filter(book -> book.getTitle().toLowerCase().contains(searchQuery) ||
                            book.getAuthor().toLowerCase().contains(searchQuery))
                    .collect(Collectors.toList());
        }

        System.out.println("Books after title/author filtering: " + filteredBooks.size());

        // If a specific genre is selected, filter by genre
        if (!selectedGenre.equalsIgnoreCase("All")) {
            filteredBooks = filteredBooks.stream()
                    .filter(book -> book.getGenre().equalsIgnoreCase(selectedGenre))
                    .collect(Collectors.toList());
        }

        System.out.println("Books after genre filtering: " + filteredBooks.size());
        return filteredBooks;
    }
}

