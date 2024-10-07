package com.example;

import com.bookclub.mao.UserMAO;
import com.bookclub.model.User;
import com.bookclub.service.LoginService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LoginServiceTests { 
    
    private User mockUser;
    
    @BeforeEach
    public void setUp() {
        UserMAO mao = new UserMAO();
        LoginService.initialize(mao);
        
        mockUser = new User("testUser", "password123");
    }

    @Test
    void testRegister_Successful() {
        boolean registerSuccess = LoginService.getInstance().register(mockUser.getUsername(), mockUser.getPassword());
        assertTrue(registerSuccess);
    }
    
    @Test 
    void testRegister_UniqueUsername() {
        LoginService.getInstance().register(mockUser.getUsername(), mockUser.getPassword());
        assertTrue(LoginService.getInstance().register(mockUser.getUsername() + "A", mockUser.getPassword()));
    }
    
    @Test
    void testRegister_FailureSameDetails() {
        LoginService.getInstance().register(mockUser.getUsername(), mockUser.getPassword());
        assertFalse(LoginService.getInstance().register(mockUser.getUsername(), mockUser.getPassword()));
    }
    
    @Test
    void testRegister_FailureDifferentPassword() {
        LoginService.getInstance().register(mockUser.getUsername(), mockUser.getPassword());
        assertFalse(LoginService.getInstance().register(mockUser.getUsername(), mockUser.getPassword() + "UNIQUE"));
    }
    
    @Test
    void testRegister_FailedDueToBadUsernameOrPassword() {
        assertFalse(LoginService.getInstance().register(null, "password123"));
        assertFalse(LoginService.getInstance().register("", "password123"));
        assertFalse(LoginService.getInstance().register("user", null));
        assertFalse(LoginService.getInstance().register("user", ""));
    }
    
    @Test
    void testAttemptLogin_SuccessfulLogin() {
        LoginService.getInstance().register(mockUser.getUsername(), mockUser.getPassword());
        
        assertTrue(LoginService.getInstance().attemptLogin(mockUser.getUsername(), mockUser.getPassword()));
        assertEquals(mockUser.getUsername(), LoginService.getCurrentUser().getUsername()); // Current user should be set after successful login
    }
    
    @Test
    void testAttemptLogin_FailedWrongPassword() {
        LoginService.getInstance().register(mockUser.getUsername(), mockUser.getPassword());
        assertFalse(LoginService.getInstance().attemptLogin(mockUser.getUsername(), mockUser.getPassword() + "UNIQUE"));
    }
    
    @Test
    void testAttemptLogin_FailedUserNotFound() {
        assertFalse(LoginService.getInstance().attemptLogin(mockUser.getUsername(), mockUser.getPassword()));
    }
    
    @Test
    void testDropCurrentUser() {
        LoginService.getInstance().register(mockUser.getUsername(), mockUser.getPassword());
        LoginService.getInstance().attemptLogin(mockUser.getUsername(), mockUser.getPassword());
        
        // Act
        LoginService.getInstance().dropCurrentUser();
        
        assertNull(LoginService.getCurrentUser());
    }
}
