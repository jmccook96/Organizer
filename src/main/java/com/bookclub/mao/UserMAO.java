package com.bookclub.mao;

import com.bookclub.iao.IUserAO;
import com.bookclub.model.User;

import java.util.HashMap;
import java.util.Map;

public class UserMAO implements IUserAO {
    private Map<String, User> users;
    
    public UserMAO() {
        users = new HashMap<>();
        addTestData();
    }
    
    private void addTestData() {
        users.put("testUser", new User());
    }
    
    @Override
    public User findUserByUsername(String username) {
        return users.get(username);
    }

    @Override
    public boolean addUser(User user) {
        // return users.put(user.getUsername(), user);
        return true;
    }

    @Override
    public boolean updateUser(User user) {
        User oldUser = null; // findUserByUsername(user.getUsername()));
        if (oldUser == null)
            return false;
        
        // return users.replace(user.getUsername(), oldUser, user);
        return true;
    }

    @Override
    public boolean deleteUser(User user) {
        return users.remove(user) != null;
    }
}
