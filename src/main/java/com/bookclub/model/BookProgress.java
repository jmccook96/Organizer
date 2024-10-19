package com.bookclub.model;

/**
 * The {@code BookProgress} class represents the reading progress of a user for a particular book.
 * It stores information such as the user's current page in the book, the book's ID, and the user's ID.
 */
public class BookProgress {
    private int id;
    private int bookId;
    private int userId;
    private int pageNumber;

    /**
     * Constructs a new {@code BookProgress} object with the given id, bookId, userId, and pageNumber.
     *
     * @param id the unique identifier for this progress record
     * @param bookId the unique identifier for the book
     * @param userId the unique identifier for the user
     * @param pageNumber the page number that the user has reached
     */
    public BookProgress(int id, int bookId, int userId, int pageNumber) {
        setId(id);
        setBookId(bookId);
        setUserId(userId);
        setPageNumber(pageNumber);
    }
    
    /**
     * Constructs a new {@code BookProgress} object without an id, typically for new entries that do not yet have an assigned id.
     *
     * @param bookId the unique identifier for the book
     * @param userId the unique identifier for the user
     * @param pageNumber the page number that the user has reached
     */
    public BookProgress(int bookId, int userId, int pageNumber) {
        setBookId(bookId);
        setUserId(userId);
        setPageNumber(pageNumber);
    }

    /**
     * Returns the unique identifier for this progress record.
     *
     * @return the id of this progress
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique identifier for this progress record.
     *
     * @param id the id to set for this progress
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the unique identifier for the book.
     *
     * @return the id of the book
     */
    public int getBookId() {
        return bookId;
    }

    /**
     * Sets the unique identifier for the book.
     *
     * @param bookId the book id to set
     */
    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    /**
     * Returns the unique identifier for the user.
     *
     * @return the id of the user
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets the unique identifier for the user.
     *
     * @param userId the user id to set
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Returns the current page number the user has reached in the book.
     *
     * @return the current page number
     */
    public int getPageNumber() {
        return pageNumber;
    }

    /**
     * Sets the current page number the user has reached in the book.
     *
     * @param pageNumber the page number to set
     */
    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }
}
