package com.example;

import com.bookclub.model.Book;
import com.bookclub.model.Review;
import com.bookclub.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ReviewTest {

    private User user;
    private Book book;
    private Review review;

    @BeforeEach
    public void setUp() {
        user = new User("testUser", "testPassword");
        book = new Book(1, "testTitle", "testAuthor", "testGenre");
        review = new Review(user, book, 5);
    }

    // Basic functionality
    @Test
    public void testGetUser() {
        assertEquals(user, review.getUser());
    }

    @Test
    public void testGetBook() {
        assertEquals(book, review.getBook());
    }

    @Test
    public void testGetRating() {
        assertEquals(5, review.getRating());
    }

    @Test
    public void testSetRating() {
        review.setRating(1);
        assertEquals(1, review.getRating());
    }

    // Edge cases
    @Test
    public void testSetRatingInvalidLowValue() {
        assertThrows(IllegalArgumentException.class, () -> review.setRating(-1));
    }

    @Test
    public void testSetRatingInvalidHighValue() {
        assertThrows(IllegalArgumentException.class, () -> review.setRating(11));
    }

    @Test
    public void testNullUser() {
        Review nullUserReview = new Review(null, book, 3);
        assertNull(nullUserReview.getUser());
    }

    @Test
    public void testNullBook() {
        Review nullBookReview = new Review(user, null, 3);
        assertNull(nullBookReview.getBook());
    }

    @Test
    public void testZeroRating() {
        review.setRating(0);
        assertEquals(0, review.getRating());
    }

    @Test
    public void testMaxRating() {
        review.setRating(5);
        assertEquals(5, review.getRating());
    }
}
