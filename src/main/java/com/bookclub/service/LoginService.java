package com.bookclub.service;

import com.bookclub.iao.IUserAO;
import com.bookclub.model.User;
import com.sun.source.tree.InstanceOfTree;

import java.awt.*;

public class LoginService {
    public static LoginService instance;
    private IUserAO userAO;
    private User currentUser;

    public static LoginService getInstance() {
        if (instance == null) {
            throw new IllegalStateException("LoginService is not initialized. Call initialize() first.");
        }
        return instance;
    }

    public static void initialize(IUserAO userAO) {
        if (instance == null) {
            instance = new LoginService();
        }
        instance.userAO = userAO;
    }
    
    public boolean attemptLogin(String username, String password) {
        User user = userAO.findUserByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            currentUser = user;
            return true;
        }
        return false;
    }
    
    public boolean register(String username, String password) {
        if (username == null || username.isEmpty() || password == null || password.isEmpty())
            return false;
        
        User newUser = new User(username, password);
        return userAO.addUser(newUser);
    }

    public static User getCurrentUser() {
        return instance != null ? instance.currentUser : null;
    }
    
    public void dropCurrentUser() {
        currentUser = null;
        System.out.println("Current User dropped by LoginService.");
    }
}
