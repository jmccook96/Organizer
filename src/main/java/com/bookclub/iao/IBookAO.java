package com.bookclub.iao;

import com.bookclub.model.Book;

import java.util.List;

/**
 * Interface for Books Access Object (IAO) that defines methods for managing books in the system.
 */
public interface IBookAO {

    /**
     * Retrieves a list of all books from the database
     * @return A list of all books found in the database.
     */
    List<Book> findAllBooks();

    /**
     * Retrieves a book from the database based on the title and author
     * @param title The title of the book to retrieve
     * @param author The author of the book to retrieve
     * @return The book with the given title and author, or null if not found.
     */
    Book findBookByTitleAndAuthor(String title, String author);

    /**
     * Retrieves a list of books from the database based on the title
     * @param title The title of the books to retrieve
     * @return A list of books with the given title.
     */
    List<Book> findBooksByTitle(String title);

    /**
     * Retrieves a list of books from the database based on the genre
     * @param genre The genre of the books to retrieve
     * @return A list of books with the given genre.
     */
    List<Book> findBooksByGenre(String genre);

    /**
     * Retrieves a list of books from the database based on the author
     * @param author The author of the books to retrieve
     * @return A list of books by the given author.
     */
    List<Book> findBooksByAuthor(String author);

    /**
     * Adds a new book to the database
     * @param book The book to add.
     * @return If operation succeeded
     */
    boolean addBook(Book book);

    /**
     * Updates an existing book in the database.
     * @param book The book with information to update.
     * @return If operation succeeded
     */
    boolean updateBook(Book book);

    /**
     * Removes a book from the database.
     * @param book The book to delete.
     * @return If operation succeeded
     */
    boolean deleteBook(Book book);
}
