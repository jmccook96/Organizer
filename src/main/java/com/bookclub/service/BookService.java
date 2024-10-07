package com.bookclub.service;

import com.bookclub.dao.BookDAO;
import com.bookclub.dao.ReviewDAO;
import com.bookclub.iao.IBookAO;
import com.bookclub.iao.IReviewAO;
import com.bookclub.model.Book;
import com.bookclub.model.Review;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BookService {
    private IBookAO bookAO;
    private IReviewAO reviewAO;
    private ObjectProperty<Book> selectedBook;
    private Map<Book, List<Review>> reviewCache;

    // Private constructor for Singleton pattern
    private BookService() {
        bookAO = new BookDAO();
        reviewAO = new ReviewDAO();
        selectedBook = new SimpleObjectProperty<>();
        reviewCache = new ConcurrentHashMap<>();
    }

    // Bill Pugh Singleton Design for thread-safe initialization
    private static class BookServiceHolder {
        private static final BookService INSTANCE = new BookService();
    }

    public static BookService getInstance() {
        return BookServiceHolder.INSTANCE;
    }

    // Property for selectedBook
    public ObjectProperty<Book> selectedBookProperty() {
        return selectedBook;
    }

    public Book getSelectedBook() {
        return selectedBook.get();
    }

    public void setSelectedBook(Book book) {
        selectedBook.set(book);
    }

    // Retrieves review data (average rating and number of ratings) with caching
    public ReviewData getReviewData(Book book) {
        // Use cache or fetch reviews if not cached
        List<Review> reviews = reviewCache.computeIfAbsent(book, reviewAO::findReviewsByBook);

        // Streamlining the average rating calculation
        int sum = 0;
        for (Review review : reviews) {
            sum += review.getRating();
        }
        double averageRating = reviews.isEmpty() ? 0.0 : (double) sum / reviews.size();

        return new ReviewData(averageRating, reviews.size());
    }


    public static class ReviewData {
        private final double averageRating;
        private final int numberOfRatings;

        public ReviewData(double averageRating, int numberOfRatings) {
            this.averageRating = averageRating;
            this.numberOfRatings = numberOfRatings;
        }

        public double getAverageRating() {
            return averageRating;
        }

        public int getNumberOfRatings() {
            return numberOfRatings;
        }
    }
}
