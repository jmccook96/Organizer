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
import java.util.stream.Collectors;

public class BookService {
    private IBookAO bookAO;
    private IReviewAO reviewAO;
    private ObjectProperty<Book> selectedBook;
    private Map<Book, List<Review>> reviewCache;

    private BookService() {
        bookAO = new BookDAO();
        reviewAO = new ReviewDAO();
        selectedBook = new SimpleObjectProperty<>();
        reviewCache = new ConcurrentHashMap<>();
    }

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

    public List<Book> searchForBooks(String searchQuery, String selectedGenre) {
        List<Book> filteredBooks = bookAO.findAllBooks();

        // Filter based on the search query (title or author)
        if (!searchQuery.isEmpty()) {
            filteredBooks = filteredBooks.stream()
                    .filter(book -> book.getTitle().toLowerCase().contains(searchQuery.toLowerCase()) ||
                            book.getAuthor().toLowerCase().contains(searchQuery.toLowerCase()))
                    .collect(Collectors.toList());
        }

        // If a specific genre is selected, filter by genre
        if (!selectedGenre.equalsIgnoreCase("All")) {
            filteredBooks = filteredBooks.stream()
                    .filter(book -> book.getGenre().equalsIgnoreCase(selectedGenre))
                    .collect(Collectors.toList());
        }
        return filteredBooks;
    }


    public ReviewData getReviewData(Book book) {
        List<Review> reviews = reviewCache.computeIfAbsent(book, reviewAO::findReviewsByBook);
        int totalRating = reviews.stream().mapToInt(Review::getRating).sum();
        double averageRating = reviews.isEmpty() ? 0.0 : (double) totalRating / reviews.size();
        return new ReviewData(averageRating, reviews.size());
    }

    // Method to clear cache for a specific book
    public void clearReviewCache(Book book) {
        reviewCache.remove(book);
    }

    // Add or update review and invalidate the cache
    public void addOrUpdateReview(Review review, Book book) {
        reviewAO.saveOrUpdateReview(review);
        clearReviewCache(book);
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
