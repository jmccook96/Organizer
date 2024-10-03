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
     * Retrieves a User from the database
     * @param id The id of the user to retrieve
     * @return The user with the given id, or null if not found.
     */
    public User findUserById(int id);

    /**
     * Adds a new user to the database
     * @param user The user to add.
     * @return If operation succeeded
     */
    boolean addUser(User user);

    /**
     * Updates an existing user in the database using their Id.
     * @param user The user with information to update.
     * @return If operation succeeded
     */
    boolean updateUser(User user);

    /**
     * Checks if a user with the passed username is contained.
     * @param username to find.
     * @return if a user with the given id is found.
     */
    boolean hasUser(String username);
    
    /**
     * Checks if a user with the passed id is contained.
     * @param userId to find.
     * @return if a user with the given id is found.
     */
    boolean hasUser(int userId);

    /**
     * Removes a user from the database.
     * @param user
     * @return If operation succeeded
     */
    boolean deleteUser(User user);
}