package com.bookclub.mao;

import com.bookclub.iao.IReviewAO;
import com.bookclub.model.Book;
import com.bookclub.model.Review;
import com.bookclub.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Mock Access Object (MAO) for handling in-memory Review operations.
 * Implements the IReviewAO interface to provide methods for managing
 * reviews, including adding, updating, deleting, and retrieving reviews.
 */
public class ReviewMAO implements IReviewAO {

    private List<Review> reviews;

    /**
     * Initializes a new instance of ReviewMAO and populates it with test data.
     */
    public ReviewMAO() {
        reviews = new ArrayList<>();
        // addTestData();
    }

    /**
     * Adds test data to the reviews list for demonstration purposes.
     */
    private void addTestData() {
        reviews.add(new Review(1, 1, 1));
        reviews.add(new Review(2, 1, 3));
        reviews.add(new Review(3, 1, 5));
        reviews.add(new Review(1, 2, 2));
        reviews.add(new Review(2, 2, 4));
    }

    /**
     * Finds a review by the specified user and book.
     *
     * @param user the user who wrote the review
     * @param book the book for which the review is written
     * @return the Review object if found, otherwise null
     */
    public Review findReviewByUserAndBook(User user, Book book) {
        return findReviewByUserAndBook(user.getId(), book.getId());
    }
    
    public Review findReviewByUserAndBook(int userId, int bookId) {
        for (Review review : reviews) {
            if (review.getUserId() == userId && review.getBookId() == bookId) {
                return review;
            }
        }
        return null;
    }

    /**
     * Retrieves all reviews written by the specified user.
     *
     * @param user the user whose reviews are to be retrieved
     * @return a list of Review objects written by the user
     */
    public List<Review> findReviewsByUser(User user) {
        List<Review> reviewsByUser = new ArrayList<>();
        for (Review review : reviews) {
            if (review.getUserId() == user.getId()) {
                reviewsByUser.add(review);
            }
        }
        return reviewsByUser;
    }

    /**
     * Retrieves all reviews for the specified book.
     *
     * @param book the book for which the reviews are to be retrieved
     * @return a list of Review objects associated with the book
     */
    public List<Review> findReviewsByBook(Book book) {
        List<Review> reviewsByBook = new ArrayList<>();
        for (Review review : reviews) {
            if (review.getBookId() == book.getId()) {
                reviewsByBook.add(review);
            }
        }
        return reviewsByBook;
    }

    /**
     * Adds a new review to the list.
     *
     * @param review the Review object to be added
     * @return true if the review was added successfully
     */
    public boolean addReview(Review review) {
        return reviews.add(review);
    }

    /**
     * Updates an existing review in the list.
     *
     * @param review the Review object containing updated information
     * @return true if the review was updated successfully
     */
    public boolean updateReview(Review review) {
        int reviewIdx = reviews.indexOf(findReviewByUserAndBook(review.getUserId(), review.getBookId()));
        return reviewIdx >= 0 && reviews.set(reviewIdx, review) != null;
    }

    /**
     * Deletes a review from the list.
     *
     * @param review the Review object to be deleted
     * @return true if the review was deleted successfully
     */
    public boolean deleteReview(Review review) {
        return reviews.remove(review);
    }

    /**
     * Saves or updates a review in the list. If the review already exists, it is updated;
     * otherwise, a new review is added.
     *
     * @param review the Review object to be saved or updated
     */
    @Override
    public void saveOrUpdateReview(Review review) {
        Review existingReview = findReviewByUserAndBook(review.getUserId(), review.getBookId());
        if (existingReview != null) {
            updateReview(review);
        } else {
            addReview(review);
        }
    }
}
