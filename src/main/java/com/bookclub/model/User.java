package com.bookclub.model;

public class User {

    private String username;
    private String password;
    private String name;    // Optional field
    private String email;   // Optional field
    private String settings; // JSON or CLOB field

    // Constructor with all fields
    public User(String username, String password, String name, String email, String settings) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.settings = settings;
    }

    // Minimal constructor (username and password only)
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Constructor for username, password, and email
    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    // Getters and setters for all fields
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
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

    public String getSettings() {
        return settings;
    }

    public void setSettings(String settings) {
        this.settings = settings;
    }
}
