package com.bookclub.dao;

import com.bookclub.iao.IUserAO;
import com.bookclub.model.User;
import com.bookclub.util.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO implements IUserAO {

    @Override
    public User findUserByUsername(String username) {
        String sql = "SELECT * FROM Users WHERE Username = ?";
        try (Connection conn = DatabaseManager.getInstance().getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new User(
                        rs.getString("Username"),
                        rs.getString("Password"),
                        rs.getString("Name"),
                        rs.getString("Email"),
                        rs.getString("Settings")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean addUser(User user) {
        String sql = "INSERT INTO Users (username, email, password, age, profile_picture, social_media_links, settings) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getPassword());
            //pstmt.setInt(4, user.getAge());  // Assuming age is an int field in the User model
            //pstmt.setString(5, user.getProfilePicture());
            //pstmt.setString(6, user.getSocialMediaLinks());
            pstmt.setString(7, user.getSettings());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateUser(User user) {
        String sql = "UPDATE Users SET Password = ?, Name = ?, Email = ?, Settings = ? WHERE Username = ?";
        try (Connection conn = DatabaseManager.getInstance().getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getPassword());
            pstmt.setString(2, user.getName());
            pstmt.setString(3, user.getEmail());
            pstmt.setString(4, user.getSettings());
            pstmt.setString(5, user.getUsername());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteUser(User user) {
        String sql = "DELETE FROM Users WHERE Username = ?";
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getUsername());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean hasUser(User user) {
        return findUserByUsername(user.getUsername()) != null;
    }
}
