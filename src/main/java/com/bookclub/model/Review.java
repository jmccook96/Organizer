package com.bookclub.model;

/**
 * The {@code Review} class represents a user's review of a book.
 * It contains the user who made the review, the book being reviewed, 
 * and the rating given to the book.
 */
public class Review {

    private static int MAX_RATING = 5;
    private static int MIN_RATING = 0;
    
    private User user;
    private Book book;
    private int rating;

    /**
     * Constructs a {@code Review} object with the specified user, book, and rating.
     *
     * @param user   the user who wrote the review
     * @param book   the book being reviewed
     * @param rating the rating given to the book
     */
    public Review(User user, Book book, int rating) {
        this.user = user;
        this.book = book;
        this.rating = rating;
    }

    /**
     * Returns the user who wrote the review.
     *
     * @return the user who wrote the review
     */
    public User getUser() {
        return user;
    }

    /**
     * Returns the book being reviewed.
     *
     * @return the book being reviewed
     */
    public Book getBook() {
        return book;
    }

    /**
     * Returns the rating given to the book.
     *
     * @return the rating given to the book
     */
    public int getRating() {
        return rating;
    }

    /**
     * Sets a new rating for the book.
     * The rating must be between 0 and 5, otherwise, an {@code IllegalArgumentException} is thrown.
     *
     * @param rating the new rating to be set for the book
     * @throws IllegalArgumentException if the rating is outside the range 0 to 5
     */
    public void setRating(int rating) {
        if (rating < MIN_RATING || rating > MAX_RATING) {
            throw new IllegalArgumentException("Rating must be between " + MIN_RATING + " and " + MAX_RATING);
        }
        this.rating = rating;
    }
}
