package com.bookclub.iao;

import com.bookclub.model.User;

public interface IUserAO {

    /**
     * Retrieves a User from the database
     * @param username The username of the user to retrieve
     * @return The user with the given username, or null if not found.
     */
    User findUserByUsername(String username);

    /**
     * Adds a new user to the database
     * @param user The user to add.
     * @return If operation succeeded
     */
    boolean addUser(User user);

    /**
     * Updates an existing user in the database.
     * @param user The user with information to update.
     * @return If operation succeeded
     */
    boolean updateUser(User user);

    /**
     * Removes a user from the database.
     * @param user
     * @return If operation succeeded
     */
    boolean deleteUser(User user);
}