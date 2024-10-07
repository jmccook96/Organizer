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
     * Registers a new user with the provided username and password.
     *
     * @param username the desired username of the new user
     * @param password the desired password of the new user
     * @return true if the user is successfully registered, false otherwise
     */
    public boolean register(String username, String password) {
        if (username == null || username.isEmpty() || password == null || password.isEmpty())
            return false;

        User newUser = new User(username, PasswordHasher.hashPassword(password));
        return !userAO.hasUser(newUser.getUsername()) && userAO.addUser(newUser);
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
}
