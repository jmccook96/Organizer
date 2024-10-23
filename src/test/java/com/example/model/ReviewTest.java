package com.example.model;

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
    private Review review1;
    private Review review2;

    @BeforeEach
    public void setUp() {
        user = new User("testUser", "testPassword");
        book = new Book(1, "testTitle", "testAuthor", "testGenre", 100);
        review1 = new Review(user, book, 5);
        
        review2 = new Review(2, 2, 2);
    }

    // Basic functionality
    @Test
    public void testGetUserId() {
        assertEquals(user.getId(), review1.getUserId());
    }

    @Test
    public void testGetBookId() {
        assertEquals(2, review2.getBookId());
    }

    @Test
    public void testGetRating() {
        assertEquals(5, review1.getRating());
    }

    @Test
    public void testSetRating() {
        review1.setRating(1);
        assertEquals(1, review1.getRating());
    }

    // Edge cases
    @Test
    public void testSetRatingInvalidLowValue() {
        assertThrows(IllegalArgumentException.class, () -> review2.setRating(-1));
    }

    @Test
    public void testSetRatingInvalidHighValue() {
        assertThrows(IllegalArgumentException.class, () -> review1.setRating(11));
    }

    @Test
    public void testNullUser() {
        assertThrows(IllegalArgumentException.class, () -> new Review(null, book, 3));
    }

    @Test
    public void testNullBook() {
        assertThrows(IllegalArgumentException.class, () -> new Review(user, null, 3));
    }

    @Test
    public void testZeroRating() {
        review1.setRating(0);
        assertEquals(0, review1.getRating());
    }

    @Test
    public void testMaxRating() {
        review1.setRating(5);
        assertEquals(5, review1.getRating());
    }
}
