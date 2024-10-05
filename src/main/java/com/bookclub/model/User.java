package com.bookclub.model;

/**
 * The {@code User} class represents a user of the book club application.
 * Each user has a username, password, name, and email.
 * This class provides getter and setter methods for accessing these attributes.
 */
public class User {
    private int id = -1;
    private String username;
    private String password;
    private String name;
    private String email;

    /**
     * Constructs a {@code User} object with the specified username, password, name, and email.
     *
     * @param username the username of the user
     * @param password the password of the user
     * @param name     the name of the user
     * @param email    the email of the user
     * @throws IllegalArgumentException if the username or password is null or empty
     */
    public User(int id, String username, String password, String name, String email) {
        this.id = id;
        setUsername(username);
        setPassword(password);
        this.name = name;
        this.email = email;
    }

    /**
     * Constructs a {@code User} object with the specified username and password.
     * Name and email are initialized as empty strings.
     *
     * @param username the username of the user
     * @param password the password of the user
     * @throws IllegalArgumentException if the username or password is null or empty
     */
    public User(String username, String password) {
        this(-1,username, password, "", "");
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username can not be null or empty!");
        }
        this.username = username;
    }
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Password can not be null or empty");
        }
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}