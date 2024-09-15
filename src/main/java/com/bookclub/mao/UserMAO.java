package com.bookclub.mao;

import com.bookclub.iao.IUserAO;
import com.bookclub.model.User;

import java.util.HashMap;
import java.util.Map;

public class UserMAO implements IUserAO {
    private final Map<String, User> users;
    
    public UserMAO() {
        users = new HashMap<>();
        // addTestData();
    }
    
    private void addTestData() {
        users.put("testUser", new User("testUser", "testPassword"));
    }
    
    @Override
    public User findUserByUsername(String username) {
        return users.get(username);
    }

    @Override
    public boolean addUser(User user) {
        return users.put(user.getUsername(), user) != null;
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
