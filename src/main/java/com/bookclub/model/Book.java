package com.bookclub.model;


/**
 * The {@code Book} class represents a book in the book club application.
 * Each book has a title, a genre and an author. This class provides getter and setter methods for these attributes.
 * Both title and author must be non-null and non-empty.
 */

public class Book {

    private int id;
    private String title;
    private String author;

    private String genre;

    /**
     * Constructs a {@code Book} object with the specified title and author.
     * Both title and author must be non-null and non-empty.
     *
     * @param title  the title of the book
     * @param author the author of the book
     * @param genre the genre of the book
     * @throws IllegalArgumentException if the title or author is null or empty
     */
    public Book(String title, String author, String genre) {
        setTitle(title);
        setAuthor(author);
        setGenre(genre);
    }

    /**
     * Constructs a {@code Book} object with the specified id, title and author.
     * id must be greater than or equal to 1, and title and author must be non-null and non-empty.
     *
     * @param id the id of the book
     * @param title  the title of the book
     * @param author the author of the book
     * @throws IllegalArgumentException if the id is less than 1, or title or author is null or empty
     */
    public Book(int id, String title, String author, String genre) {
        setId(id);
        setTitle(title);
        setAuthor(author);
        setGenre(genre);
    }

    /**
     * Returns the id of the book.
     *
     * @return the id of the book
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the id of the book.
     * The id must be greater than or equal to 1.
     *
     * @param id the new id of the book
     * @throws IllegalArgumentException if the id is less than 1
     */
    public void setId(int id) {
        if (id < 1) {
            throw new IllegalArgumentException("Id cannot be less than 1");
        }
        this.id = id;

    private Integer bookid;
    private String genre;

    public Book(String title, String author) {
        this.bookid = bookid;
        this.title = title;
        this.author = author;
        this.genre = genre;
    }
    public Integer getBookID() {
        return bookid;
    }

    public String getGenre() {
        return genre;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * Returns the genre of the book.
     *
     * @return the genre of the book
     */
    public String getGenre() {
        return genre;
    }

    /**
     * Sets the genre of the book.
     * The genre must be non-null and non-empty.
     *
     * @param genre the new genre of the book
     * @throws IllegalArgumentException if the author is null or empty
     */
    public void setGenre(String genre) {
        if (genre == null || genre.isEmpty()) {
            throw new IllegalArgumentException("Genre can not be null or empty");
        }
        this.genre = genre;
    }

    @Override
    public String toString() {
        return title + " by " + author;
    }
}
