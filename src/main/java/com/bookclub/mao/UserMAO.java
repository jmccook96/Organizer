package com.bookclub.mao;

import com.bookclub.iao.IUserAO;
import com.bookclub.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.bookclub.util.DatabaseManager;


import java.util.HashMap;
import java.util.Map;

public class UserMAO implements IUserAO {
    private final Map<String, User> users;
    
    public UserMAO() {
        users = new HashMap<>();
    }
    
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
        return users.put(user.getUsername(), user) == null;
    }

    @Override
    public boolean updateUser(User user) {
        User oldUser = findUserByUsername(user.getUsername());
        if (oldUser == null)
            return false;
        
        return users.replace(user.getUsername(), oldUser, user);
    }
    
    @Override
    public boolean hasUser(User user) { return users.containsKey(user.getUsername()); }

    @Override
    public boolean deleteUser(User user) {
        return user != null && users.remove(user) != null;
    }
}
