package com.example.mao;

import com.bookclub.mao.ReviewMAO;
import com.bookclub.model.Book;
import com.bookclub.model.Review;
import com.bookclub.model.User;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ReviewMAOTest {
    private ReviewMAO reviewMAO;
    private User user1;
    private User user2;
    private Book book1;

    @BeforeEach
    public void setUp() {
        reviewMAO = new ReviewMAO();
        user1 = new User("User1", "Password1");
        user2 = new User("User2", "Password2");
        book1 = new Book("It", "Stephen King", "Horror", 659);
    }

    // Test addReview
    @Test
    public void testAddReview() {
        Review newReview = new Review(user1, book1, 4);
        boolean result = reviewMAO.addReview(newReview);
        assertTrue(result);

        Review foundReview = reviewMAO.findReviewByUserAndBook(user1, book1);
        assertNotNull(foundReview);
        assertEquals(4, foundReview.getRating());
    }

    // Test findReviewByUserAndBook
    @Test
    public void testFindReviewByUserAndBookFound() {
        reviewMAO.addReview(new Review(user1, book1, 4));
        Review foundReview = reviewMAO.findReviewByUserAndBook(user1.getId(), book1.getId());
        assertNotNull(foundReview);
        assertEquals(4, foundReview.getRating());
    }

    @Test
    public void testFindReviewByUserAndBookNotFound() {
        Review foundReview = reviewMAO.findReviewByUserAndBook(user2, new Book("Non-existent Book", "Unknown Author", "Unknown Genre", 100));
        assertNull(foundReview);
    }

    // Test findReviewsByUser
    @Test
    public void testFindReviewsByUser() {
        reviewMAO.addReview(new Review(user1, book1, 4));
        reviewMAO.addReview(new Review(user1.getId(), 6, 5));
        
        List<Review> reviews = reviewMAO.findReviewsByUser(user1);
        assertNotNull(reviews);
        assertEquals(2, reviews.size()); // user1 has reviews for 2 books
    }

    @Test
    public void testFindReviewsByUserNoResults() {
        User userWithoutReviews = new User("NewUser", "NewPassword");
        List<Review> reviews = reviewMAO.findReviewsByUser(userWithoutReviews);
        assertTrue(reviews.isEmpty());
    }

    // Test findReviewsByBook
    @Test
    public void testFindReviewsByBook() {
        reviewMAO.addReview(new Review(user1, book1, 4));
        reviewMAO.addReview(new Review(3, book1.getId(), 8));
        reviewMAO.addReview(new Review(user1.getId(), 6, 5));

        List<Review> reviews = reviewMAO.findReviewsByBook(book1);
        assertNotNull(reviews);
        assertEquals(2, reviews.size()); // book1 has 3 reviews
    }

    @Test
    public void testFindReviewsByBookNoResults() {
        Book bookWithoutReviews = new Book("Unknown Book", "Unknown Author", "Unknown Genre", 200);
        List<Review> reviews = reviewMAO.findReviewsByBook(bookWithoutReviews);
        assertTrue(reviews.isEmpty());
    }

    // Test updateReview
    @Test
    public void testUpdateReviewSuccess() {
        reviewMAO.addReview(new Review(user1, book1, 4));
        Review existingReview = reviewMAO.findReviewByUserAndBook(user1, book1);
        Review updatedReview = new Review(user1, book1, 5); // Change rating to 5

        boolean result = reviewMAO.updateReview(updatedReview);
        assertTrue(result);

        Review foundReview = reviewMAO.findReviewByUserAndBook(user1, book1);
        assertNotNull(foundReview);
        assertEquals(5, foundReview.getRating()); // Rating should now be 5
    }

    @Test
    public void testUpdateReviewFailure() {
        Review nonExistentReview = new Review(user1, new Book("Unknown Book", "Unknown Author", "Unknown Genre", 200), 3);
        boolean result = reviewMAO.updateReview(nonExistentReview);
        assertFalse(result);
    }

    // Test deleteReview
    @Test
    public void testDeleteReviewSuccess() {
        reviewMAO.addReview(new Review(user1, book1, 4));
        Review existingReview = reviewMAO.findReviewByUserAndBook(user1, book1);

        boolean result = reviewMAO.deleteReview(existingReview);
        assertTrue(result);

        Review foundReview = reviewMAO.findReviewByUserAndBook(user1, book1);
        assertNull(foundReview); // Review should be deleted
    }

    @Test
    public void testDeleteReviewFailure() {
        Review nonExistentReview = new Review(user1, new Book("Unknown Book", "Unknown Author", "Unknown Genre", 200), 3);
        boolean result = reviewMAO.deleteReview(nonExistentReview);
        assertFalse(result);
    }

    // Test saveOrUpdateReview
    @Test
    public void testSaveOrUpdateReviewUpdateExisting() {
        Review existingReview = reviewMAO.findReviewByUserAndBook(user1, book1);
        Review updatedReview = new Review(user1, book1, 4);

        reviewMAO.saveOrUpdateReview(updatedReview);

        Review foundReview = reviewMAO.findReviewByUserAndBook(user1, book1);
        assertNotNull(foundReview);
        assertEquals(4, foundReview.getRating()); // Rating should now be 4
    }

    @Test
    public void testSaveOrUpdateReviewAddNew() {
        Review newReview = new Review(user1, new Book("New Book", "New Author", "New Genre", 300), 4);
        reviewMAO.saveOrUpdateReview(newReview);

        Review foundReview = reviewMAO.findReviewByUserAndBook(user1.getId(), newReview.getBookId());
        assertNotNull(foundReview);
        assertEquals(4, foundReview.getRating());
    }
}
