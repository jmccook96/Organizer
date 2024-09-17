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

public class ReviewDAO implements IReviewAO {

    private DatabaseManager dbManager;

    public ReviewDAO() {
        dbManager = DatabaseManager.getInstance();
        createTable();
    }

    @Override
    public Review findReviewByUserAndBook(User user, Book book) {
        try {
            PreparedStatement statement = dbManager.getConnection().prepareStatement("SELECT * FROM Reviews WHERE bookId = ? AND username = ?");
            statement.setInt(1, book.getId());
            statement.setString(2, user.getUsername());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int rating = resultSet.getInt("rating");
                Review review = new Review(user, book, rating);
                return review;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

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
                int rating = resultSet.getInt("rating");
                Book book = new Book(bookId, bookTitle, bookAuthor);
                Review review = new Review(user, book, rating);
                reviews.add(review);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return reviews;
    }

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
                System.out.println(rating);
                // TODO: Change Review implementation so we don't have to do this
                User user = new User(username, "somePassword");
                Review review = new Review(user, book, rating);
                reviews.add(review);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return reviews;
    }

    @Override
    public boolean addReview(Review review) {
        try {
            PreparedStatement statement = dbManager.getConnection().prepareStatement("INSERT INTO Reviews (bookId, username, rating) VALUES (?, ?, ?)");
            statement.setInt(1, review.getBook().getId());
            statement.setString(2, review.getUser().getUsername());
            statement.setInt(3, review.getRating());
            statement.executeUpdate();
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean updateReview(Review review) {
        try {
            PreparedStatement statement = dbManager.getConnection().prepareStatement("UPDATE Reviews SET bookId = ?, username = ?, rating = ? WHERE bookID = ? AND username = ?");
            statement.setInt(1, review.getBook().getId());
            statement.setString(2, review.getUser().getUsername());
            statement.setInt(3, review.getRating());
            statement.setInt(4, review.getBook().getId());
            statement.setString(5, review.getUser().getUsername());
            statement.executeUpdate();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

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

    private void createTable() {
        // Create table if not exists
        try {
            Statement statement = dbManager.getConnection().createStatement();
            String query = "CREATE TABLE IF NOT EXISTS Reviews ("
                    + "reviewId INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "bookId INTEGER NOT NULL,"
                    + "username VARCHAR NOT NULL,"
                    + "rating INTEGER NOT NULL" // Required rating for now
                    + ")";
            statement.execute(query);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
