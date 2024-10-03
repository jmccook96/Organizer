package com.bookclub.dao;

import com.bookclub.iao.IUserAO;
import com.bookclub.model.User;
import com.bookclub.util.DatabaseManager;
import com.bookclub.util.PasswordHasher;

import javax.xml.transform.Result;
import java.sql.*;

public class UserDAO implements IUserAO {
    public UserDAO() {
        tryCreateTable();
    }
    
    @Override
    public User findUserByUsername(String username) {
        String sqlQuery = "SELECT * FROM USERS WHERE USERNAME = ?";
        try {
            PreparedStatement stmt = getConnection().prepareStatement(sqlQuery);
            stmt.setString(1, username);
            return findUserFromStatement(stmt);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Exception met for Finding User By Username.");
        }

        return null;
    }
    
    @Override 
    public User findUserById(int id) {
        String sqlQuery = "SELECT * FROM USERS WHERE Id = ?";
        try {
            PreparedStatement stmt = getConnection().prepareStatement(sqlQuery);
            stmt.setInt(1, id);
            return findUserFromStatement(stmt);
        }  catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Exception met for Finding User By Id.");
        }
        return null;
    }

    @Override
    public boolean addUser(User user) {
        String sql = "INSERT INTO Users (Username, Password) VALUES (?, ?)";
        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());

            if (stmt.executeUpdate() > 0) {
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int newUserId = generatedKeys.getInt(1);
                    user.setId(newUserId);
                }
                System.out.println("Added user to db \"" + user.getUsername() + "\"");
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateUser(User user) { /**TODO: , Name = ?, Email = ?, Settings = ? */
        String sql = "UPDATE Users SET Password = ? WHERE Username = ?";
        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setString(1, user.getPassword());
            //stmt.setString(2, user.getName());
            //stmt.setString(3, user.getEmail());
            //stmt.setString(4, user.getSettings());  // ASSUMING STORED AS JSON STRING
            stmt.setString(5, user.getUsername());

            // Make sure row count from execution is 1 (or more) to verify it executed.
            if (stmt.executeUpdate() > 0) {
                System.out.println("Updated user in db: \"" + user.getUsername() + "\"");
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean hasUser(String username) {
        // SQL trick, will return a column containing a number if present.
        String sql = "SELECT 1 FROM Users WHERE USERNAME = ?";
        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setString(1, username);
            return stmt.executeQuery().next();  // True if user exists, false otherwise

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean hasUser(int userId) {
        // SQL trick, will return a column containing a number if present.
        String sql = "SELECT 1 FROM Users WHERE Id = ?";
        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setInt(1, userId);
            return stmt.executeQuery().next();  // True if user exists, false otherwise

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteUser(User user) {
        String sql = "DELETE FROM Users WHERE Username = ?";
        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());

            if (stmt.executeUpdate() > 0) {
                System.out.println("Deleted user from db \"" + user.getUsername() + "\"");
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void tryCreateTable() {
        String createTableSQL = """
            CREATE TABLE IF NOT EXISTS Users (
                Id INTEGER PRIMARY KEY AUTOINCREMENT,
                Username TEXT UNIQUE NOT NULL,
                Password TEXT NOT NULL,
                Name TEXT,
                Email TEXT,
                Settings TEXT
            );
            """;

        try (Statement stmt = getConnection().createStatement()) {
            stmt.execute(createTableSQL);
            System.out.println("Successfully created Users table.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Attempts to find user from the created prepared statement.
     * @param query The query to create a user from.
     * @return Either the user found with the query, or null if failed.
     * @throws SQLException if SQLException encountered.
     */
    private User findUserFromStatement(PreparedStatement query) throws SQLException {
        ResultSet resultSet = query.executeQuery();
        if (resultSet.next()) {
            return mapResultSetToUser(resultSet);
        }
        System.err.println("Failed to find User.");
        return null;
    }
    
    private User mapResultSetToUser(ResultSet resultSet) throws SQLException { // TODO: POPULATE AS USER MODEL GROWS.
        return new User(resultSet.getInt("id"), 
                resultSet.getString("Username"), 
                resultSet.getString("Password"));
    }
    
    private Connection getConnection() {
        return DatabaseManager.getInstance().getConnection();
    }
}
