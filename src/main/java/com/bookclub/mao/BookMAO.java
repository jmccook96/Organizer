package com.bookclub.mao;

import com.bookclub.iao.IBookAO;
import com.bookclub.model.Book;

import java.util.*;

import java.util.List;

/**
 * The BookMAO class implements the IBookAO interface and provides methods to manage a collection of books.
 * This class operates in-memory and serves as a mock data access object (MAO).
 * It provides functionality to add, update, delete, and retrieve books based on different criteria such as title, author, and genre.
 */
public class BookMAO implements IBookAO {

    private List<Book> books;

    /**
     * Constructor for BookMAO. Initializes the list of books and adds test data.
     */
    public BookMAO() {
        books = new ArrayList<>();
        addTestData();
    }

    /**
     * Adds test data to the list of books for demonstration purposes.
     */
    private void addTestData() {
        books.add(new Book("It", "Stephen King","Horror"));
        books.add(new Book("The Shining", "Stephen King","Horror"));
        books.add(new Book("testTitle", "Stephen King","Horror"));
        books.add(new Book("1984", "George Orwell","Horror"));
        books.add(new Book("Animal Farm", "George Orwell","Horror"));
        books.add(new Book("testTitle", "George Orwell","Horror"));
    }

    /**
     * Returns a list of all books.
     *
     * @return a list of all books in the system.
     */
    @Override
    public List<Book> findAllBooks() {
        return books;
    }

    /**
     * Finds a book by its title and author.
     *
     * @param title  The title of the book.
     * @param author The author of the book.
     * @return The book matching the title and author, or null if no match is found.
     */
    @Override
    public Book findBookByTitleAndAuthor(String title, String author) {
        for (Book book : books) {
            if (book.getTitle().equals(title) && book.getAuthor().equals(author)) {
                return book;
            }
        }
        return null;
    }

    /**
     * Finds books by title.
     *
     * @param title The title of the books to find.
     * @return A list of books that match the given title.
     */
    @Override
    public List<Book> findBooksByTitle(String title) {
        List<Book> booksByTitle = new ArrayList<>();
        for (Book book : books) {
            if (book.getTitle().equals(title)) {
                booksByTitle.add(book);
            }
        }
        return booksByTitle;
    }

    /**
     * Finds books by genre.
     *
     * @param genre The genre of the books to find.
     * @return A list of books that belong to the specified genre.
     */
    @Override
    public List<Book> findBooksByGenre(String genre) {
        List<Book> booksByGenre = new ArrayList<>();
        for (Book book : books) {
            if (book.getGenre().equals(genre)) {
                booksByGenre.add(book);
            }
        }
        return booksByGenre;
    }

    /**
     * Finds books by author.
     *
     * @param author The author of the books to find.
     * @return A list of books written by the specified author.
     */
    @Override
    public List<Book> findBooksByAuthor(String author) {
        List<Book> booksByAuthor = new ArrayList<>();
        for (Book book : books) {
            if (book.getAuthor().equals(author)) {
                booksByAuthor.add(book);
            }
        }
        return booksByAuthor;
    }

    /**
     * Adds a new book to the collection.
     *
     * @param book The book to add.
     * @return true if the book was added successfully, false otherwise.
     */
    @Override
    public boolean addBook(Book book) {
        return books.add(book);
    }

    /**
     * Updates an existing book in the collection.
     *
     * @param book The book with updated information.
     * @return true if the book was updated successfully, false otherwise.
     */
    @Override
    public boolean updateBook(Book book) {
        return books.set(books.indexOf(findBookByTitleAndAuthor(book.getTitle(), book.getAuthor())), book) != null;
    }

    /**
     * Deletes a book from the collection.
     *
     * @param book The book to delete.
     * @return true if the book was removed successfully, false otherwise.
     */
    @Override
    public boolean deleteBook(Book book) {
        return books.remove(book);
    }
}
