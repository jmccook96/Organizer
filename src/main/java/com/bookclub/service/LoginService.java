package com.bookclub.service;

import com.bookclub.iao.IUserAO;
import com.bookclub.model.User;
import com.bookclub.util.PasswordHasher;


/**
 * Service class responsible for managing user login, registration, 
 * and maintaining the current user session.
 */
public class LoginService {
    /** Singleton instance of the LoginService */
    public static LoginService instance;
    /** Data Access Object for user operations */
    private IUserAO userAO;
    /** The current user who is logged in */
    private User currentUser;

    /**
     * Retrieves the singleton instance of the LoginService.
     *
     * @return the singleton instance of LoginService
     * @throws IllegalStateException if the service has not been initialized
     */
    public static LoginService getInstance() {
        if (instance == null) {
            throw new IllegalStateException("LoginService is not initialized. Call initialize() first.");
        }
        return instance;
    }

    /**
     * Initializes the LoginService singleton with the provided IUserAO instance.
     *
     * @param userAO the IUserAO instance used for user data operations
     */
    public static void initialize(IUserAO userAO) {
        if (instance == null) {
            instance = new LoginService();
        }
        instance.userAO = userAO;
    }

    /**
     * Attempts to log in a user with the provided username and password.
     *
     * @param username the username entered by the user
     * @param password the password entered by the user
     * @return true if the login is successful, false otherwise
     */
    public boolean attemptLogin(String username, String password) {
        User user = userAO.findUserByUsername(username);
        if (user != null) {
            String hashedInputPassword = PasswordHasher.hashPassword(password);
            if (user.getPassword().equals(hashedInputPassword)) {
                currentUser = user;
                return true;
            }
        }
        return false;
    }

    /**
     * Registers a new user with the provided username, password, name, and email.
     *
     * @param username the desired username of the new user
     * @param password the desired password of the new user
     * @param name     the name of the new user (can be null)
     * @param email    the email of the new user (can be null)
     * @return true if the user is successfully registered, false otherwise
     */
    public boolean register(String username, String password, String name, String email) {
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            return false;
        }

        String hashedPassword = PasswordHasher.hashPassword(password);
        User newUser = new User(username, hashedPassword);
        newUser.setName(name);
        newUser.setEmail(email);

        if (!userAO.hasUser(newUser.getUsername()) && userAO.addUser(newUser)) {
            currentUser = newUser;
            return true;
        }
        return false;
    }

    /**
     * Returns the currently logged-in user, if any.
     *
     * @return the current User object, or null if no user is logged in
     */
    public static User getCurrentUser() {
        return instance != null ? instance.currentUser : null;
    }

    /**
     * Logs out the current user and clears the session.
     */
    public void dropCurrentUser() {
        currentUser = null;
        System.out.println("Current User dropped by LoginService.");
    }
    /**
     * Updates the current user information with the provided {@code User} object.
     * The method validates that the {@code User} object is not null and that the username is non-null and non-empty.
     * If the validation succeeds, it attempts to update the user using the {@code userAO.updateUser} method.
     * If the update is successful, the current user session is updated.
     *
     * @param user the {@code User} object containing the updated user information
     * @return {@code true} if the update is successful, {@code false} otherwise
     * @throws IllegalArgumentException if the {@code user} or {@code username} is null or empty
     */
    public boolean updateUser(User user) {
        if (user == null || user.getUsername() == null || user.getUsername().isEmpty()) {
            return false;
        }

        if (userAO.updateUser(user)) {
            currentUser = user;  // Update the current user in the session
            return true;
        }
        return false;
    }

}
