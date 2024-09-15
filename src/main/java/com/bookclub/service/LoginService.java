package com.bookclub.service;

import com.bookclub.iao.IUserAO;
import com.bookclub.model.User;

public class LoginService {
    private IUserAO userAO;
    private static User currentUser;
    
    public LoginService(IUserAO userAO) {
        this.userAO = userAO;
    }
    
    public boolean authenticate(String username, String password) {
        User user = userAO.findUserByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            currentUser = user;
            return true;
        }
        return false;
    }

    public boolean register(String username, String password, String email) {
        if (userAO.findUserByUsername(username) != null) {
            return false; // Username already exists
        }
        User newUser = new User(username, password, email);  // Pass email
        return userAO.addUser(newUser);
    }


    public static User getCurrentUser() {
        return currentUser;
    }
}
