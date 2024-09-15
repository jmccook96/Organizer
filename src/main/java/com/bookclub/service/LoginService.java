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
    
    public boolean register(String username, String password) {
        User newUser = new User(username, password);
        return userAO.addUser(newUser);
    }

    public static User getCurrentUser() {
        return currentUser;
    }
}
