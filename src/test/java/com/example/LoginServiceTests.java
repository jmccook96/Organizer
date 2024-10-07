package com.example;

import com.bookclub.mao.UserMAO;
import com.bookclub.model.User;
import com.bookclub.service.LoginService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LoginServiceTests {

    private User mockUser;
    private LoginService loginService;

    @BeforeEach
    public void setUp() {
        UserMAO mao = new UserMAO();
        LoginService.initialize(mao);
        loginService = LoginService.getInstance();

        mockUser = new User("testUser", "password123");
    }

    @Test
    void testRegister_Successful() {
        boolean registerSuccess = loginService.register(mockUser.getUsername(), mockUser.getPassword(), "Test User", "test@example.com");
        assertTrue(registerSuccess);
    }

    @Test
    void testRegister_SuccessfulWithNullNameAndEmail() {
        boolean registerSuccess = loginService.register(mockUser.getUsername(), mockUser.getPassword(), null, null);
        assertTrue(registerSuccess);
    }

    @Test
    void testRegister_UniqueUsername() {
        loginService.register(mockUser.getUsername(), mockUser.getPassword(), null, null);
        assertTrue(loginService.register(mockUser.getUsername() + "A", mockUser.getPassword(), null, null));
    }

    @Test
    void testRegister_FailureSameDetails() {
        loginService.register(mockUser.getUsername(), mockUser.getPassword(), null, null);
        assertFalse(loginService.register(mockUser.getUsername(), mockUser.getPassword(), null, null));
    }

    @Test
    void testRegister_FailureDifferentPassword() {
        loginService.register(mockUser.getUsername(), mockUser.getPassword(), null, null);
        assertFalse(loginService.register(mockUser.getUsername(), mockUser.getPassword() + "UNIQUE", null, null));
    }

    @Test
    void testRegister_FailedDueToBadUsernameOrPassword() {
        assertFalse(loginService.register(null, "password123", null, null));
        assertFalse(loginService.register("", "password123", null, null));
        assertFalse(loginService.register("user", null, null, null));
        assertFalse(loginService.register("user", "", null, null));
    }

    @Test
    void testAttemptLogin_SuccessfulLogin() {
        loginService.register(mockUser.getUsername(), mockUser.getPassword(), "Test User", "test@example.com");

        assertTrue(loginService.attemptLogin(mockUser.getUsername(), mockUser.getPassword()));
        User currentUser = loginService.getCurrentUser();
        assertNotNull(currentUser);
        assertEquals(mockUser.getUsername(), currentUser.getUsername());
        assertEquals("Test User", currentUser.getName());
        assertEquals("test@example.com", currentUser.getEmail());
    }

    @Test
    void testAttemptLogin_FailedWrongPassword() {
        loginService.register(mockUser.getUsername(), mockUser.getPassword(), null, null);
        assertFalse(loginService.attemptLogin(mockUser.getUsername(), mockUser.getPassword() + "UNIQUE"));
    }

    @Test
    void testAttemptLogin_FailedUserNotFound() {
        assertFalse(loginService.attemptLogin(mockUser.getUsername(), mockUser.getPassword()));
    }

    @Test
    void testDropCurrentUser() {
        loginService.register(mockUser.getUsername(), mockUser.getPassword(), null, null);
        loginService.attemptLogin(mockUser.getUsername(), mockUser.getPassword());

        loginService.dropCurrentUser();

        assertNull(loginService.getCurrentUser());
    }

    @Test
    void testGetCurrentUser_ReturnsNullWhenNoUserLoggedIn() {
        assertNull(loginService.getCurrentUser());
    }
}