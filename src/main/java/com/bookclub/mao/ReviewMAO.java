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
        addTestData();
    }

    /**
     * Adds test data to the reviews list for demonstration purposes.
     */
    private void addTestData() {
        User user1 = new User("User1", "Password1");
        User user2 = new User("User2", "Password2");
        User user3 = new User("User3", "Password3");
        Book book1 = new Book("It", "Stephen King","Horror");
        Book book2 = new Book("The Shining", "Stephen King","Horror");

        reviews.add(new Review(user1, book1, 1));
        reviews.add(new Review(user2, book1, 3));
        reviews.add(new Review(user3, book1, 5));
        reviews.add(new Review(user1, book2, 2));
        reviews.add(new Review(user2, book2, 4));
    }

    /**
     * Finds a review by the specified user and book.
     *
     * @param user the user who wrote the review
     * @param book the book for which the review is written
     * @return the Review object if found, otherwise null
     */
    public Review findReviewByUserAndBook(User user, Book book) {
        for (Review review : reviews) {
            if (review.getUser().equals(user) && review.getBook().equals(book)) {
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
            if (review.getUser().equals(user)) {
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
            if (review.getBook().equals(book)) {
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
        return reviews.set(reviews.indexOf(findReviewByUserAndBook(review.getUser(), review.getBook())), review) != null;
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
        Review existingReview = findReviewByUserAndBook(review.getUser(), review.getBook());
        if (existingReview != null) {
            updateReview(review);
        } else {
            addReview(review);
        }
    }
}
