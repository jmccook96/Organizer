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

/**
 * The {@code BookService} class provides services related to managing books and their associated reviews.
 * It serves as the central component for handling book data, searching for books, and managing reviews.
 * The class follows a singleton design pattern to ensure that only one instance of the service exists.
 */
public class BookService {

    private IBookAO bookAO;
    private IReviewAO reviewAO;
    private ObjectProperty<Book> selectedBook;
    private Map<Book, List<Review>> reviewCache;

    /**
     * Private constructor to initialize the {@code BookService}.
     * This constructor initializes the DAO objects and sets up a cache for storing reviews.
     */
    private BookService() {
        bookAO = new BookDAO();
        reviewAO = new ReviewDAO();
        selectedBook = new SimpleObjectProperty<>();
        reviewCache = new ConcurrentHashMap<>();
    }

    /**
     * Nested static class to hold the singleton instance of {@code BookService}.
     */
    private static class BookServiceHolder {
        private static final BookService INSTANCE = new BookService();
    }

    /**
     * Returns the singleton instance of {@code BookService}.
     *
     * @return the singleton instance of {@code BookService}.
     */
    public static BookService getInstance() {
        return BookServiceHolder.INSTANCE;
    }

    /**
     * Provides a property for observing changes to the selected book.
     *
     * @return an {@link ObjectProperty} representing the selected book.
     */
    public ObjectProperty<Book> selectedBookProperty() {
        return selectedBook;
    }

    /**
     * Gets the currently selected book.
     *
     * @return the currently selected {@link Book}.
     */
    public Book getSelectedBook() {
        return selectedBook.get();
    }

    /**
     * Sets the selected book.
     *
     * @param book the {@link Book} to set as selected.
     */
    public void setSelectedBook(Book book) {
        selectedBook.set(book);
    }

    /**
     * Searches for books based on a search query and selected genre.
     * If the search query is not empty, it filters books by title or author.
     * If a specific genre is selected, it filters books by genre.
     *
     * @param searchQuery   the search query to filter books by title or author.
     * @param selectedGenre the genre to filter books by. If "All" is selected, no genre filtering is applied.
     * @return a list of {@link Book} objects that match the search criteria.
     */
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

    /**
     * Retrieves the review data for a specific book, including the average rating and the number of reviews.
     * The reviews are cached to improve performance, and the cache is updated as necessary.
     *
     * @param book the {@link Book} for which review data is being requested.
     * @return a {@link ReviewData} object containing the average rating and the number of reviews.
     */
    public ReviewData getReviewData(Book book) {
        List<Review> reviews = reviewCache.computeIfAbsent(book, reviewAO::findReviewsByBook);
        int totalRating = reviews.stream().mapToInt(Review::getRating).sum();
        double averageRating = reviews.isEmpty() ? 0.0 : (double) totalRating / reviews.size();
        return new ReviewData(averageRating, reviews.size());
    }

    /**
     * Clears the review cache for a specific book.
     * This forces the cache to be refreshed the next time review data is requested for the book.
     *
     * @param book the {@link Book} for which the cache should be cleared.
     */
    public void clearReviewCache(Book book) {
        reviewCache.remove(book);
    }

    /**
     * Adds or updates a review for a book and invalidates the cache for the book's reviews.
     * This ensures that the next request for review data retrieves fresh data.
     *
     * @param review the {@link Review} to be added or updated.
     * @param book   the {@link Book} for which the review is being added or updated.
     */
    public void addOrUpdateReview(Review review, Book book) {
        reviewAO.saveOrUpdateReview(review);
        clearReviewCache(book);
    }

    /**
     * The {@code ReviewData} class is a data container for review information,
     * including the average rating and the number of reviews for a book.
     */
    public static class ReviewData {
        private final double averageRating;
        private final int numberOfRatings;

        /**
         * Constructs a {@code ReviewData} object with the specified average rating and number of ratings.
         *
         * @param averageRating   the average rating of the reviews.
         * @param numberOfRatings the total number of reviews.
         */
        public ReviewData(double averageRating, int numberOfRatings) {
            this.averageRating = averageRating;
            this.numberOfRatings = numberOfRatings;
        }

        /**
         * Returns the average rating of the reviews.
         *
         * @return the average rating of the reviews.
         */
        public double getAverageRating() {
            return averageRating;
        }

        /**
         * Returns the total number of reviews.
         *
         * @return the total number of reviews.
         */
        public int getNumberOfRatings() {
            return numberOfRatings;
        }
    }

}
