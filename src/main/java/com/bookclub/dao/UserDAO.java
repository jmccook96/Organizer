package com.bookclub.dao;

import com.bookclub.iao.IUserAO;
import com.bookclub.model.User;
import com.bookclub.util.DatabaseManager;
import com.bookclub.util.PasswordHasher;

import java.sql.*;

public class UserDAO implements IUserAO {
    public UserDAO() {
        tryCreateTable();
    }
    
    @Override
    public User findUserByUsername(String username) {
        String sqlQuery = "SELECT Id, Username, Password, Name, Email FROM USERS WHERE USERNAME = ?";
        try (PreparedStatement stmt = getConnection().prepareStatement(sqlQuery)) {
            stmt.setString(1, username);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                return mapResultSetToUser(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Exception met for Finding User By Username.");
        }
        return null;
    }
    /*
    @Override
    public boolean addUser(User user) { // TODO: , Name, Email, Settings
        String sql = "INSERT INTO Users (Username, Password) VALUES (?, ?)";
        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            //stmt.setString(3, user.getName());
            //stmt.setString(4, user.getEmail());
            //stmt.setString(5, user.getSettings());  // ASSUMING STORED AS JSON STRING
            
            // Make sure row count from execution is 1 (or more) to verify it executed.
            if (stmt.executeUpdate() > 0) {
                System.out.println("Added user to db \"" + user.getUsername() + "\"");
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    */
    @Override
    public boolean addUser(User user) {
        String sql = "INSERT INTO Users (Username, Password, Name, Email) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getName());
            stmt.setString(4, user.getEmail());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        user.setId(generatedKeys.getInt(1));
                    }
                }
                System.out.println("Added user to db \"" + user.getUsername() + "\" with id " + user.getId());
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    @Override
    public boolean updateUser(User user) {
        String sql = "UPDATE Users SET Password = ?, Name = ?, Email = ? WHERE Id = ?";
        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setString(1, user.getPassword());
            stmt.setString(2, user.getName());
            stmt.setString(3, user.getEmail());
            stmt.setInt(4, user.getId());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Updated user in db: \"" + user.getUsername() + "\" with id " + user.getId());
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    @Override
    public boolean hasUser(User user) {
        // SQL trick, will return a column containing a number if present.
        String sql = "SELECT 1 FROM Users WHERE Username = ?";
        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            return stmt.executeQuery().next();  // True if user exists, false otherwise

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteUser(User user) {
        String sql = "DELETE FROM Users WHERE Id = ?";
        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setInt(1, user.getId());

            if (stmt.executeUpdate() > 0) {
                System.out.println("Deleted user from db \"" + user.getUsername() + "\" with id " + user.getId());
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

    private User mapResultSetToUser(ResultSet resultSet) throws SQLException {
        return new User(
                resultSet.getInt("Id"),
                resultSet.getString("Username"),
                resultSet.getString("Password"),
                resultSet.getString("Name"),
                resultSet.getString("Email")
        );
    }
    
    private Connection getConnection() {
        return DatabaseManager.getInstance().getConnection();
    }
}
