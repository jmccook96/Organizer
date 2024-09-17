package com.bookclub.model;

/**
 * The {@code Book} class represents a book in the book club application.
 * Each book has a title and an author. This class provides getter and setter methods for these attributes.
 * Both title and author must be non-null and non-empty.
 */
public class Book {

    private String title;
    private String author;
    private Integer bookid;
    private String genre;

 Event-Class
    public Book(Integer bookid, String title, String author, String genre) {
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

    /**
     * Constructs a {@code Book} object with the specified title and author.
     * Both title and author must be non-null and non-empty.
     *
     * @param title  the title of the book
     * @param author the author of the book
     * @throws IllegalArgumentException if the title or author is null or empty
     */
    public Book(String title, String author) {
        setTitle(title);
        setAuthor(author);
 main
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
