package com.bookclub.dao;

import com.bookclub.iao.IReviewAO;
import com.bookclub.model.Book;
import com.bookclub.model.Review;
import com.bookclub.model.User;
import com.bookclub.util.DatabaseManager;
import java.util.List;

public class ReviewDAO implements IReviewAO {

    private DatabaseManager dbManager;

    public ReviewDAO() {
        dbManager = DatabaseManager.getInstance();
    }

    public Review findReviewByUserAndBook(User user, Book book) {
        // TODO: Add query
        return null;
    }

    public List<Review> findReviewsByUser(User user) {
        // TODO: Add query
        return null;
    }

    public List<Review> findReviewsByBook(Book book) {
        // TODO: Add query
        return null;
    }

    public boolean addReview(Review review) {
        // TODO: Add query
        return true;
    }

    public boolean updateReview(Review review) {
        // TODO: Add query
        return true;
    }

    public boolean deleteReview(Review review) {
        // TODO: Add query
        return true;
    }
}
