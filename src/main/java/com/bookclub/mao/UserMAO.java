package com.bookclub.mao;

import com.bookclub.iao.IUserAO;
import com.bookclub.model.User;

import java.util.HashMap;
import java.util.Map;

public class UserMAO implements IUserAO {
    private final Map<Integer, User> users;
    private int idIndex;
    
    public UserMAO() {
        idIndex = 0;
        users = new HashMap<>();
    }
    
    @Override
    public User findUserByUsername(String username) {
        for (User user : users.values())
            if (user.getUsername().equals(username))
                return user;
        return null;
    }

    @Override
    public User findUserById(int id) {
        return users.get(id);
    }
    
    @Override
    public boolean addUser(User user) {
        if (user == null) {
            return false;
        }
        
        // If we get passed an id in user, need to ensure it isn't occupied.
        if (findUserById(user.getId()) != null) {
            return false;
        } else {
            // We want to imbue our own ID before proceeding.
            user.setId(++idIndex);
        }
        
        return users.put(user.getId(), user) == null;
    }

    @Override
    public boolean updateUser(User user) {
        User oldUser = findUserById(user.getId());
        if (oldUser == null)
            return false;
        
        return users.replace(user.getId(), oldUser, user);
    }
    
    @Override
    public boolean hasUser(int userId) { return users.containsKey(userId); }

    @Override
    public boolean hasUser(String username) {
        for (User user : users.values())
            if (user.getUsername().equals(username))
                return true;
        
        return false;
    }

    @Override
    public boolean deleteUser(User user) {
        return user != null && users.remove(user.getId()) != null;
    }
}
