package com.bookclub.iao;

import com.bookclub.model.Review;
import com.bookclub.model.Book;
import com.bookclub.model.User;
import java.util.List;

public interface IReviewAO {

    /**
     * Retrieves a review from the database based on the user and book
     * @param user The user to retrieve the review for
     * @param book The book to retrieve the review for
     * @return The review by the given user for the given book, or null if not found.
     */
    Review findReviewByUserAndBook(User user, Book book);

    /**
     * Retrieves a list of reviews from the database based on the user
     * @param user The user to retrieve reviews for
     * @return The reviews by the given user, or null if not found.
     */
    List<Review> findReviewsByUser(User user);

    /**
     * Retrieves a list of reviews from the database based on the book
     * @param book The book to retrieve reviews for
     * @return The reviews for the given book, or null if not found.
     */
    List<Review> findReviewsByBook(Book book);

    /**
     * Adds a new review to the database
     * @param review The review to add.
     * @return If operation succeeded
     */
    boolean addReview(Review review);

    /**
     * Updates an existing review in the database.
     * @param review The review with information to update.
     * @return If operation succeeded
     */
    boolean updateReview(Review review);

    /**
     * Removes a review from the database.
     * @param review The review to remove.
     * @return If operation succeeded
     */
    boolean deleteReview(Review review);
}