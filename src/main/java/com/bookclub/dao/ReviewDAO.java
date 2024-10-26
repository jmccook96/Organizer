package com.bookclub.dao;

import com.bookclub.iao.IReviewAO;
import com.bookclub.model.Book;
import com.bookclub.model.Review;
import com.bookclub.model.User;
import com.bookclub.util.DatabaseManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        return findReviewByUserAndBook(user.getId(), book.getId());
    }

    @Override
    public Review findReviewByUserAndBook(int userId, int bookId) {
        try {
            PreparedStatement statement = dbManager.getConnection().prepareStatement("SELECT * FROM Reviews WHERE bookId = ? AND userId = ?");
            statement.setInt(1, bookId);
            statement.setInt(2, userId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return createReview(resultSet);
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
                reviews.add(createReview(resultSet));
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
                reviews.add(createReview(resultSet));
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
            PreparedStatement statement = dbManager.getConnection().prepareStatement(
                    "INSERT INTO Reviews (bookId, username, rating, topic, description) VALUES (?, ?, ?, ?, ?)"
            );
            statement.setInt(1, review.getBookId());
            statement.setInt(2, review.getUserId());
            statement.setInt(3, review.getRating());

            // Check for optional topic and description
            if (review.getTopic() != null) {
                statement.setString(4, review.getTopic());
            } else {
                statement.setNull(4, java.sql.Types.VARCHAR);
            }

            if (review.getDescription() != null) {
                statement.setString(5, review.getDescription());
            } else {
                statement.setNull(5, java.sql.Types.VARCHAR);
            }

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
            statement.setInt(1, review.getBookId());
            statement.setInt(2, review.getUserId());
            statement.setInt(3, review.getRating());
            statement.setString(4, review.getTopic());
            statement.setString(5, review.getDescription());
            statement.setInt(4, review.getBookId());
            statement.setInt(5, review.getUserId());
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
            PreparedStatement statement = dbManager.getConnection().prepareStatement("DELETE FROM Reviews WHERE bookId = ? AND userId = ? AND rating = ?");
            statement.setInt(1, review.getBookId());
            statement.setInt(2, review.getUserId());
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
        Review existingReview = findReviewByUserAndBook(review.getUserId(), review.getBookId());

        if (existingReview != null) {
            updateReview(review);
        } else {
            addReview(review);
        }
    }
    
    private Review createReview(ResultSet resultSet) {
        try {
            return new Review(
                    resultSet.getInt("userId"),
                    resultSet.getInt("bookId"),
                    resultSet.getInt("rating"),
                    resultSet.getString("topic"),
                    resultSet.getString("description")
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return null;
    }

    /**
     * Creates the Reviews table in the database if it does not already exist.
     */
    private void createTable() {
        // Create table if not exists
        String query = "CREATE TABLE IF NOT EXISTS Reviews ("
                + "reviewId INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "bookId INTEGER NOT NULL,"
                + "userId INTEGER NOT NULL,"
                + "rating INTEGER NOT NULL,"
                + "topic VARCHAR,"
                + "description TEXT"
                + ")";
        try {
            Statement statement = dbManager.getConnection().createStatement();
            statement.execute(query);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
