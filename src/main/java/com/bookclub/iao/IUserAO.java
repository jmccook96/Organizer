package com.bookclub.iao;

import com.bookclub.model.User;

public interface IUserAO {
    User findUserByUsername(String username);
    boolean addUser(User user);
    boolean updateUser(User user);
    boolean deleteUser(User user);
    boolean hasUser(User user);
}
