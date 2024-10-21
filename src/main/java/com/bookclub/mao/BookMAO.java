package com.bookclub.mao;

import com.bookclub.iao.IBookAO;
import com.bookclub.model.Book;

import java.util.*;

/**
 * This class is a mock implementation of the {@link IBookAO} interface, acting as a Mock Access Object (MAO)
 * for performing operations related to {@link Book} objects. It stores a list of books and provides methods to
 * perform CRUD (Create, Read, Update, Delete) operations.
 */
public class BookMAO implements IBookAO {

    private final List<Book> books;

    /**
     * Constructor to initialize the {@code BookMAO} with an empty list of books and
     * populates it with test data.
     */
    public BookMAO() {
        books = new ArrayList<>();
        addTestData();
    }

    /**
     * Adds some sample books to the list to simulate a database for testing purposes.
     */
    private void addTestData() {
        books.add(new Book("It", "Stephen King","Horror", 20));
        books.add(new Book("The Shining", "Stephen King","Horror", 30));
        books.add(new Book("testTitle", "Stephen King","Horror", 30));
        books.add(new Book("1984", "George Orwell","Horror", 30));
        books.add(new Book("Animal Farm", "George Orwell","Horror",30));
        books.add(new Book("testTitle", "George Orwell","Horror", 30));
    }

    /**
     * Retrieves all the books stored in the system.
     *
     * @return a list of all {@link Book} objects.
     */
    @Override
    public List<Book> findAllBooks() {
        return books;
    }

    /**
     * Finds a book in the system based on its title and author.
     *
     * @param title  the title of the book to find.
     * @param author the author of the book to find.
     * @return the {@link Book} object that matches the given title and author, or {@code null} if not found.
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
     * Finds all books with the given title.
     *
     * @param title the title to search for.
     * @return a list of {@link Book} objects that match the given title.
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
     * Finds all books belonging to the given genre.
     *
     * @param genre the genre to search for.
     * @return a list of {@link Book} objects that match the given genre.
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
     * Finds all books written by the given author.
     *
     * @param author the author to search for.
     * @return a list of {@link Book} objects written by the specified author.
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
     * Adds a new book to the list.
     *
     * @param book the {@link Book} object to be added.
     * @return {@code true} if the book was added successfully, {@code false} otherwise.
     */
    @Override
    public boolean addBook(Book book) {
        return books.add(book);
    }

    /**
     * Updates an existing book in the list by finding the book with the same title and author,
     * and replacing it with the provided book object.
     *
     * @param book the {@link Book} object containing updated information.
     * @return {@code true} if the book was updated successfully, {@code false} otherwise.
     */
    @Override
    public boolean updateBook(Book book) {
        return books.set(books.indexOf(findBookByTitleAndAuthor(book.getTitle(), book.getAuthor())), book) != null;
    }

    /**
     * Deletes a book from the list.
     *
     * @param book the {@link Book} object to be deleted.
     * @return {@code true} if the book was deleted successfully, {@code false} otherwise.
     */
    @Override
    public boolean deleteBook(Book book) {
        return books.remove(book);
    }
}
