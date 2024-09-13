package com.bookclub.mao;

import com.bookclub.iao.IReviewAO;
import com.bookclub.model.Book;
import com.bookclub.model.Review;
import com.bookclub.model.User;
import com.bookclub.service.LoginService;

import java.util.ArrayList;
import java.util.List;

public class ReviewMAO implements IReviewAO {

    private List<Review> reviews;

    public ReviewMAO() {
        reviews = new ArrayList<>();
        addTestData();
    }

    private void addTestData() {
        User user1 = new User("User1", "Password1");
        User user2 = new User("User2", "Password2");
        User user3 = new User("User3", "Password3");
        Book book1 = new Book("Book1", "Author1");
        Book book2 = new Book("Book2", "Author2");

        reviews.add(new Review(user1, book1, 1));
        reviews.add(new Review(user2, book1, 3));
        reviews.add(new Review(user3, book1, 5));
        reviews.add(new Review(user1, book2, 2));
        reviews.add(new Review(user2, book2, 4));
    }

    public Review findReviewByUserAndBook(User user, Book book) {
        for (Review review : reviews) {
            if (review.getUser().equals(user) && review.getBook().equals(book)) {
                return review;
            }
        }
        return null;
    }

    public List<Review> findReviewsByUser(User user) {
        List<Review> reviewsByUser = new ArrayList<>();
        for (Review review : reviews) {
            if (review.getUser().equals(user)) {
                reviewsByUser.add(review);
            }
        }
        return reviewsByUser.isEmpty() ? null : reviewsByUser;
    }

    public List<Review> findReviewsByBook(Book book) {
        List<Review> reviewsByBook = new ArrayList<>();
        for (Review review : reviews) {
            if (review.getBook().equals(book)) {
                reviewsByBook.add(review);
            }
        }
        return reviewsByBook.isEmpty() ? null : reviewsByBook;
    }

    public boolean addReview(Review review) {
        return reviews.add(review);
    }

    public boolean updateReview(Review review) {
        return reviews.set(reviews.indexOf(findReviewByUserAndBook(review.getUser(), review.getBook())), review) != null;
    }

    public boolean deleteReview(Review review) {
        return reviews.remove(review);
    }
}
