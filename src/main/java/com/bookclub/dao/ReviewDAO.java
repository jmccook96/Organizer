package com.bookclub.dao;

import com.bookclub.iao.IReviewAO;
import com.bookclub.model.Book;
import com.bookclub.model.Review;
import com.bookclub.model.User;
import com.bookclub.util.DatabaseManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) for handling Review-related database operations.
 * Implements the IReviewAO interface to provide methods for adding, updating,
 * deleting, and retrieving reviews from the database.
 */
public class ReviewDAO implements IReviewAO {

    private DatabaseManager dbManager;

    /**
     * Initializes a new instance of ReviewDAO and creates the Reviews table 
     * if it does not already exist.
     */
    public ReviewDAO() {
        dbManager = DatabaseManager.getInstance();
        createTable();
    }

    /**
     * Finds a review by the specified user and book.
     *
     * @param user the user who wrote the review
     * @param book the book for which the review is written
     * @return the Review object if found, otherwise null
     */
    @Override
    public Review findReviewByUserAndBook(User user, Book book) {
        try {
            PreparedStatement statement = dbManager.getConnection().prepareStatement("SELECT * FROM Reviews WHERE bookId = ? AND username = ?");
            statement.setInt(1, book.getId());
            statement.setString(2, user.getUsername());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int rating = resultSet.getInt("rating");
                String topic = resultSet.getString("topic");
                String description = resultSet.getString("description");
                Review review = new Review(user, book, rating, topic, description);
                return review;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Retrieves all reviews written by the specified user.
     *
     * @param user the user whose reviews are to be retrieved
     * @return a list of Review objects written by the user
     */
    @Override
    public List<Review> findReviewsByUser(User user) {
        List<Review> reviews = new ArrayList<>();
        try {
            PreparedStatement statement = dbManager.getConnection().prepareStatement("SELECT * FROM Reviews WHERE username = ?");
            statement.setString(1, user.getUsername());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int bookId = resultSet.getInt("bookId");
                String bookTitle = resultSet.getString("bookTitle");
                String bookAuthor = resultSet.getString("bookAuthor");
                String bookGenre = resultSet.getString("bookGenre");
                int totalChapters = resultSet.getInt("totalChapters");
                int rating = resultSet.getInt("rating");
                String topic = resultSet.getString("topic");
                String description = resultSet.getString("description");
                Book book = new Book(bookId, bookTitle, bookAuthor, bookGenre, totalChapters);
                Review review = new Review(user, book, rating, topic, description);
                reviews.add(review);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return reviews;
    }

    /**
     * Retrieves all reviews for the specified book.
     *
     * @param book the book for which the reviews are to be retrieved
     * @return a list of Review objects associated with the book
     */
    @Override
    public List<Review> findReviewsByBook(Book book) {
        List<Review> reviews = new ArrayList<>();
        try {
            PreparedStatement statement = dbManager.getConnection().prepareStatement("SELECT * FROM Reviews WHERE bookId = ?");
            statement.setInt(1, book.getId());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String username = resultSet.getString("username");
                int rating = resultSet.getInt("rating");
                String topic = resultSet.getString("topic");
                String description = resultSet.getString("description");
                // TODO: Change Review implementation so we don't have to do this
                User user = new User(username, "somePassword");
                Review review = new Review(user, book, rating, topic, description);
                reviews.add(review);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return reviews;
    }

    /**
     * Adds a new review to the database.
     *
     * @param review the Review object to be added
     * @return true if the review was added successfully, false otherwise
     */
    @Override
    public boolean addReview(Review review) {
        try {
            if (findReviewByUserAndBook(review.getUser(), review.getBook()) != null) {
                // Review already exists for this user and book
                return false;
            }
            // Proceed to add the review
            PreparedStatement statement = dbManager.getConnection().prepareStatement("INSERT INTO Reviews (bookId, username, rating, topic, description) VALUES (?, ?, ?, ?, ?)");
            statement.setInt(1, review.getBook().getId());
            statement.setString(2, review.getUser().getUsername());
            statement.setInt(3, review.getRating());
            statement.setString(4, review.getTopic());
            statement.setString(5, review.getDescription());
            statement.executeUpdate();

        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Updates an existing review in the database.
     *
     * @param review the Review object containing updated information
     * @return true if the review was updated successfully, false otherwise
     */
    @Override
    public boolean updateReview(Review review) {
        try {
            PreparedStatement statement = dbManager.getConnection().prepareStatement("UPDATE Reviews SET bookId = ?, username = ?, rating = ?, topic = ?, description = ? WHERE bookID = ? AND username = ?");
            statement.setInt(1, review.getBook().getId());
            statement.setString(2, review.getUser().getUsername());
            statement.setInt(3, review.getRating());
            statement.setString(4, review.getTopic());
            statement.setString(5, review.getDescription());
            statement.setInt(6, review.getBook().getId());
            statement.setString(7, review.getUser().getUsername());
            statement.executeUpdate();
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Deletes a review from the database.
     *
     * @param review the Review object to be deleted
     * @return true if the review was deleted successfully, false otherwise
     */
    @Override
    public boolean deleteReview(Review review) {
        try {
            PreparedStatement statement = dbManager.getConnection().prepareStatement("DELETE FROM Reviews WHERE bookId = ? AND username = ? AND rating = ?");
            statement.setInt(1, review.getBook().getId());
            statement.setString(2, review.getUser().getUsername());
            statement.setInt(3, review.getRating());
            statement.executeUpdate();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * Saves or updates a review in the database. If the review already exists, it is updated;
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

    /**
     * Creates the Reviews table in the database if it does not already exist.
     */
    private void createTable() {
        // Create table if not exists
        try {
            Statement statement = dbManager.getConnection().createStatement();
            String query = "CREATE TABLE IF NOT EXISTS Reviews ("
                    + "reviewId INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "bookId INTEGER NOT NULL,"
                    + "username VARCHAR NOT NULL,"
                    + "rating INTEGER NOT NULL,"
                    + "topic VARCHAR,"
                    + "description TEXT"
                    + ")";
            statement.execute(query);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
