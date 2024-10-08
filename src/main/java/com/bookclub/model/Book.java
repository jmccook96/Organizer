package com.bookclub.model;

/**
 * The {@code Book} class represents a book in the book club application.
 * Each book has a title and an author. This class provides getter and setter methods for these attributes.
 * Both title and author must be non-null and non-empty.
 */
public class Book {

    private int id;
    private String title;
    private String author;
    private String genre;

    /**
     * Constructs a {@code Book} object with the specified title and author.
     * title and author must be non-null and non-empty.
     *
     * @param title  the title of the book
     * @param author the author of the book
     * @throws IllegalArgumentException if the title or author is null or empty
     */
    public Book(String title, String author) {
        setTitle(title);
        setAuthor(author);
    }

    /**
     * Constructs a {@code Book} object with the specified title, genre and author.
     * title, genre and author must be non-null and non-empty.
     *
     * @param genre the genre of the book
     * @param title  the title of the book
     * @param author the author of the book
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
    public Book(int id, String title, String author) {
        setId(id);
        setTitle(title);
        setAuthor(author);
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
    }

    /**
     * Returns the title of the book.
     *
     * @return the title of the book
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the book.
     * The title must be non-null and non-empty.
     *
     * @param title the new title of the book
     * @throws IllegalArgumentException if the title is null or empty
     */
    public void setTitle(String title) {
        if (title == null || title.isEmpty()) {
            throw new IllegalArgumentException("Title cannot be null or empty");
        }
        this.title = title;
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
     * @throws IllegalArgumentException if the genre is null or empty
     */
    public void setGenre(String genre) {
        if (genre == null || genre.isEmpty()) {
            throw new IllegalArgumentException("Genre cannot be null or empty");
        }
        this.genre = genre;
    }

    /**
     * Returns the author of the book.
     *
     * @return the author of the book
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Sets the author of the book.
     * The author must be non-null and non-empty.
     *
     * @param author the new author of the book
     * @throws IllegalArgumentException if the author is null or empty
     */
    public void setAuthor(String author) {
        if (author == null || author.isEmpty()) {
            throw new IllegalArgumentException("Author can not be null or empty");
        }
        this.author = author;
    }

    @Override
    public String toString() {
        return title + " by " + author;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Book) {
            return ((Book) obj).getTitle().equals(this.getTitle()) && ((Book) obj).getAuthor().equals(this.getAuthor());
        }
        return false;
    }
}
