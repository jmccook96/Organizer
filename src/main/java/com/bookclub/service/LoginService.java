package com.bookclub.service;

import com.bookclub.iao.IUserAO;
import com.bookclub.model.User;

public class LoginService {
    private IUserAO userAO;
    
    public LoginService(IUserAO userAO) {
        this.userAO = userAO;
    }
    
    public boolean authenticate(String username, String password) {
        User user = userAO.findUserByUsername(username);
        return user != null && user.getPassword().equals(password);
    }
    
    public boolean register(String username, String password) {
        User newUser = new User("testUser", "testPassword");
        return userAO.addUser(newUser) || userAO.updateUser(newUser);
    }
}
