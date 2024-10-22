package com.example.service;

import com.bookclub.service.BookService;
import com.bookclub.mao.BookMAO;
import com.bookclub.mao.ReviewMAO;
import com.bookclub.model.Book;
import com.bookclub.model.Review;
import com.bookclub.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BookServiceTest {

    private BookService bookService;
    private BookMAO bookMAO;
    private ReviewMAO reviewMAO;
    private Book book1;
    private Book book2;

    @BeforeEach
    public void setUp() {
        // Create instances of the MAOs
        bookMAO = new BookMAO();
        reviewMAO = new ReviewMAO();

        // Initialize BookService with the MAOs
        BookService.initialize(bookMAO, reviewMAO);
        bookService = BookService.getInstance();

        // Add some test data to BookMAO and ReviewMAO
        book1 = new Book(1, "The Shining", "Stephen King", "Horror", 447);
        book2 = new Book(2, "It", "Stephen King", "Horror", 1138);
        bookMAO.addBook(book1);
        bookMAO.addBook(book2);

        reviewMAO.addReview(new Review(new User("Username1", "Password1"), book1, 5));
        reviewMAO.addReview(new Review(new User("Username2", "Password2"), book1, 4));
        reviewMAO.addReview(new Review(new User("Username3", "Password3"), book2, 5));
    }

    // Test searchForBooks
    @Test
    public void testSearchForBooksByTitle() {
        List<Book> result = bookService.searchForBooks("Shining", "All");
        assertEquals(1, result.size());
        assertEquals("The Shining", result.get(0).getTitle());
    }

    @Test
    public void testSearchForBooksByAuthor() {
        List<Book> result = bookService.searchForBooks("Stephen King", "All");
        assertEquals(2, result.size());
    }

    @Test
    public void testSearchForBooksByGenre() {
        List<Book> result = bookService.searchForBooks("", "Horror");
        assertEquals(2, result.size());
    }

    @Test
    public void testSearchForBooksNoResult() {
        List<Book> result = bookService.searchForBooks("Nonexistent", "All");
        assertTrue(result.isEmpty());
    }

    // Test getReviewData
    @Test
    public void testGetReviewDataForBook() {
        BookService.ReviewData reviewData = bookService.getReviewData(book1);
        assertEquals(2, reviewData.getNumberOfRatings());
        assertEquals(4.5, reviewData.getAverageRating(), 0.01);
    }

    @Test
    public void testGetReviewDataForBookNoReviews() {
        Book newBook = new Book(3, "New Book", "New Author", "Genre", 300);
        bookMAO.addBook(newBook);

        BookService.ReviewData reviewData = bookService.getReviewData(newBook);
        assertEquals(0.0, reviewData.getAverageRating(), 0.01);
        assertEquals(0, reviewData.getNumberOfRatings());
    }

    // Test review cache functionality
    @Test
    public void testClearReviewCache() {
        // Ensure the cache is populated
        BookService.ReviewData initialData = bookService.getReviewData(book1);
        assertEquals(4.5, initialData.getAverageRating(), 0.01);

        // Add a new review
        reviewMAO.addReview(new Review(new User("Username4", "Password4"), book1, 3));
        bookService.clearReviewCache(book1);

        // Fetch new data and check if the cache is refreshed
        BookService.ReviewData updatedData = bookService.getReviewData(book1);
        assertEquals(4.0, updatedData.getAverageRating(), 0.01);
        assertEquals(3, updatedData.getNumberOfRatings());
    }

    // Test addOrUpdateReview
    @Test
    public void testAddOrUpdateReview() {
        Review newReview = new Review(new User("Username3", "Password3"), book2, 4);
        bookService.addOrUpdateReview(newReview, book2);

        BookService.ReviewData reviewData = bookService.getReviewData(book2);
        assertEquals(4.5, reviewData.getAverageRating(), 0.01); // (5 + 4) / 2
        assertEquals(2, reviewData.getNumberOfRatings());
    }

    // Test selectedBook property
    @Test
    public void testSelectedBookProperty() {
        bookService.setSelectedBook(book1);
        assertEquals(book1, bookService.getSelectedBook());

        bookService.selectedBookProperty().addListener((obs, oldBook, newBook) -> {
            assertEquals(book1, oldBook);
            assertEquals(book2, newBook);
        });

        bookService.setSelectedBook(book2);
        assertEquals(book2, bookService.getSelectedBook());
    }
}
