package com.bookclub.model;

/**
 * The {@code Book} class represents a book in the book club application.
 * Each book has an id, title, author, genre, and total number of chapters.
 * This class provides getter and setter methods for these attributes.
 * Title, author, and genre must be non-null and non-empty. The id must be greater than or equal to 1.
 */
public class Book {

    private int id;
    private String title;
    private String author;
    private String genre;
    private int totalChapters;

    /**
     * Constructs a {@code Book} object with the specified title and author.
     * Title and author must be non-null and non-empty.
     *
     * @param title  the title of the book.
     * @param author the author of the book.
     * @throws IllegalArgumentException if the title or author is null or empty.
     */
    public Book(String title, String author) {
        setTitle(title);
        setAuthor(author);
    }

    /**
     * Constructs a {@code Book} object with the specified title, author, genre, and total number of chapters.
     * Title, author, and genre must be non-null and non-empty, and total chapters must be greater than 0.
     *
     * @param title      the title of the book.
     * @param author     the author of the book.
     * @param genre      the genre of the book.
     * @param totalChapters the total number of chapters in the book.
     * @throws IllegalArgumentException if the title, author, or genre is null or empty, or if total chapters is less than or equal to 0.
     */
    public Book(String title, String author, String genre, int totalChapters) {
        setTitle(title);
        setAuthor(author);
        setGenre(genre);
        setTotalChapters(totalChapters);
    }

    /**
     * Constructs a {@code Book} object with the specified id, title, author, genre, and total number of chapters.
     * Id must be greater than or equal to 1, and title, author, and genre must be non-null and non-empty.
     *
     * @param id         the id of the book.
     * @param title      the title of the book.
     * @param author     the author of the book.
     * @param genre      the genre of the book.
     * @param totalChapters the total number of chapters in the book.
     * @throws IllegalArgumentException if the id is less than 1, or if the title, author, or genre is null or empty, or if total chapters is less than or equal to 0.
     */
    public Book(int id, String title, String author, String genre, int totalChapters) {
        setId(id);
        setTitle(title);
        setAuthor(author);
        setGenre(genre);
        setTotalChapters(totalChapters);
    }

    /**
     * Returns the id of the book.
     *
     * @return the id of the book.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the id of the book.
     * The id must be greater than or equal to 1.
     *
     * @param id the new id of the book.
     * @throws IllegalArgumentException if the id is less than 1.
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
     * @return the title of the book.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the book.
     * The title must be non-null and non-empty.
     *
     * @param title the new title of the book.
     * @throws IllegalArgumentException if the title is null or empty.
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
     * @return the genre of the book.
     */
    public String getGenre() {
        return genre;
    }

    /**
     * Sets the genre of the book.
     * The genre must be non-null and non-empty.
     *
     * @param genre the new genre of the book.
     * @throws IllegalArgumentException if the genre is null or empty.
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
     * @return the author of the book.
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Sets the author of the book.
     * The author must be non-null and non-empty.
     *
     * @param author the new author of the book.
     * @throws IllegalArgumentException if the author is null or empty.
     */
    public void setAuthor(String author) {
        if (author == null || author.isEmpty()) {
            throw new IllegalArgumentException("Author cannot be null or empty");
        }
        this.author = author;
    }

    /**
     * Returns the total number of chapters in the book.
     *
     * @return the total number of chapters in the book.
     */
    public int getTotalChapters() {
        return totalChapters;
    }

    /**
     * Sets the total number of chapters in the book.
     * The total chapters must be greater than 0.
     *
     * @param totalChapters the new total number of chapters in the book.
     * @throws IllegalArgumentException if the total number of chapters is less than or equal to 0.
     */
    public void setTotalChapters(int totalChapters) {
        if (totalChapters > 0) {
            this.totalChapters = totalChapters;
        } else {
            throw new IllegalArgumentException("Total chapters must be greater than 0");
        }
    }

    /**
     * Returns a string representation of the book, showing the title, author, and genre.
     *
     * @return a string representing the book.
     */
    @Override
    public String toString() {
        return title + " by " + author + " ( " + genre + " ) ";
    }

    /**
     * Compares this book with another object. Returns {@code true} if the object is a {@code Book}
     * and has the same title, author, and genre.
     *
     * @param obj the object to compare.
     * @return {@code true} if the books are equal, {@code false} otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Book) {
            return ((Book) obj).getTitle().equals(this.getTitle()) &&
                    ((Book) obj).getAuthor().equals(this.getAuthor()) &&
                    ((Book) obj).getGenre().equals(this.getGenre());
        }
        return false;
    }
}
