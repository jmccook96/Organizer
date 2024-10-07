package com.bookclub.dao;

import com.bookclub.iao.IBookProgressAO;
import com.bookclub.model.BookProgress;
import com.bookclub.util.DatabaseManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BookProgressDAO implements IBookProgressAO {

    private DatabaseManager dbManager;

    public BookProgressDAO() {
        dbManager = DatabaseManager.getInstance();
        createTable();
    }

    @Override
    public BookProgress findBookProgressByBookAndUser(int bookId, int userId) {
        try {
            PreparedStatement statement = dbManager.getConnection().prepareStatement("SELECT * FROM BookProgress WHERE bookId = ? AND userId = ?");
            statement.setInt(1, bookId);
            statement.setInt(2, userId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return mapResultSetToBookProgress(resultSet);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<BookProgress> findBookProgressesByBook(int bookId) {
        List<BookProgress> bookProgresses = new ArrayList<>();
        try {
            PreparedStatement statement = dbManager.getConnection().prepareStatement("SELECT * FROM BookProgress WHERE bookId = ?");
            statement.setInt(1, bookId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                bookProgresses.add(mapResultSetToBookProgress(resultSet));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return bookProgresses;
    }

    @Override
    public List<BookProgress> findBookProgressesByUser(int userId) {
        List<BookProgress> bookProgresses = new ArrayList<>();
        try {
            PreparedStatement statement = dbManager.getConnection().prepareStatement("SELECT * FROM BookProgress WHERE userId = ?");
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                bookProgresses.add(mapResultSetToBookProgress(resultSet));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return bookProgresses;
    }

    @Override
    public boolean addBookProgress(BookProgress bookProgress) {
        try {
            PreparedStatement statement = dbManager.getConnection().prepareStatement("INSERT INTO BookProgress (bookId, userId, pageNumber) VALUES (?, ?, ?)");
            statement.setInt(1, bookProgress.getBookId());
            statement.setInt(2, bookProgress.getUserId());
            statement.setInt(3, bookProgress.getPageNumber());
            statement.executeUpdate();

            // Set the id of the new book
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                bookProgress.setId(generatedKeys.getInt(1));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean updateBookProgress(BookProgress bookProgress) {
        try {
            PreparedStatement statement = dbManager.getConnection().prepareStatement("UPDATE BookProgress SET bookId = ?, userId = ?, pageNumber = ? WHERE id = ?");
            statement.setInt(1, bookProgress.getBookId());
            statement.setInt(2, bookProgress.getUserId());
            statement.setInt(3, bookProgress.getPageNumber());
            statement.setInt(4, bookProgress.getId());
            statement.executeUpdate();
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteBookProgress(BookProgress bookProgress) {
        try {
            PreparedStatement statement = dbManager.getConnection().prepareStatement("DELETE FROM BookProgress WHERE id = ?");
            statement.setInt(1, bookProgress.getId());
            statement.executeUpdate();
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private void createTable() {
        // Create table if not exists
        try {
            Statement statement = dbManager.getConnection().createStatement();
            String query = "CREATE TABLE IF NOT EXISTS BookProgress ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "bookId INTEGER NOT NULL,"
                    + "userId INTEGER NOT NULL,"
                    + "pageNumber INTEGER NOT NULL,"
                    + "FOREIGN KEY (bookId) REFERENCES Books(bookID),"
                    + "FOREIGN KEY (userId) REFERENCES Users(Id)"
                    + ")";
            statement.execute(query);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private BookProgress mapResultSetToBookProgress(ResultSet resultSet) throws SQLException {
        return new BookProgress(resultSet.getInt("id"),
                                resultSet.getInt("bookId"),
                                resultSet.getInt("userId"),
                                resultSet.getInt("pageNumber"));
    }
}
