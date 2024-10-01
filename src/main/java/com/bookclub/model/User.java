package com.bookclub.model;

/**
 * The {@code User} class represents a user of the book club application.
 * Each user has a username and a password.
 * This class provides basic getter methods for accessing these attributes.
 */
public class User {

    // IF FIELDS GROW ENSURE USERDAO IS UPDATED TO MATCH.
    private int id = -1;
    private String username;
    private String password;

    /**
     * Constructs a {@code User} object with the specified username and password.
     *
     * @param id the id of the user
     * @param username the username of the user
     * @param password the password of the user
     * @throws IllegalArgumentException if the username or password is null or empty
     */
    public User(int id, String username, String password) {
        this.id = id;
        setUsername(username);
        setPassword(password);
    }

    /**
     * Constructs a {@code User} object with the specified username and password.
     * Both username and password must be non-null and non-empty.
     *
     * @param username the username of the user
     * @param password the password of the user
     * @throws IllegalArgumentException if the username or password is null or empty
     */
    public User(String username, String password) {
        setUsername(username);
        setPassword(password);
    }

    /**
     * Returns the id of the user.
     * 
     * @return the id of the user
     */
    public int getId() { return id; }

    /**
     * Sets the id of the user.
     * 
     * @param id the new id of the user
     */
    public void setId(int id) { this.id = id; }

    /**
     * Returns the username of the user.
     *
     * @return the username of the user
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the user.
     * The username must be non-null and non-empty.
     *
     * @param username the new username of the user
     * @throws IllegalArgumentException if the username is null or empty
     */
    public void setUsername(String username) {
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username can not be null or empty!");
        }
        this.username = username;
    }

    /**
     * Returns the password of the user.
     *
     * @return the password of the user
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the user.
     * The password must be non-null and non-empty.
     *
     * @param password the new password of the user
     * @throws IllegalArgumentException if the password is null or empty
     */
    public void setPassword(String password) {
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Password can not be null or empty");
        }
        this.password = password;
    }
}
