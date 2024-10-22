package com.example.mao;

import com.bookclub.mao.UserMAO;
import com.bookclub.model.User;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserMAOTest {

    private UserMAO userMAO;
    private User user1;
    private User user2;

    @BeforeEach
    public void setUp() {
        userMAO = new UserMAO();
        user1 = new User("User1", "Password1");
        user2 = new User("User2", "Password2");
        
        userMAO.addUser(user1);
        userMAO.addUser(user2);
    }

    // Test addUser
    @Test
    public void testAddUserSuccess() {
        User newUser = new User("User3", "Password3");

        boolean result = userMAO.addUser(newUser);
        assertTrue(result);
    }

    @Test
    public void testAddUserFailureDuplicateId() {
        User duplicateUser = new User("UserDuplicate", "Password1");
        duplicateUser.setId(1); // Same ID as user1

        boolean result = userMAO.addUser(duplicateUser);
        assertFalse(result); // Should not allow a duplicate ID
    }

    // Test findUserByUsername
    @Test
    public void testFindUserByUsernameSuccess() {
        User foundUser = userMAO.findUserByUsername("User1");
        assertNotNull(foundUser);
        assertEquals("User1", foundUser.getUsername());
    }

    @Test
    public void testFindUserByUsernameNotFound() {
        User foundUser = userMAO.findUserByUsername("NonExistentUser");
        assertNull(foundUser);
    }

    // Test findUserById
    @Test
    public void testFindUserByIdSuccess() {
        User foundUser = userMAO.findUserById(1);
        assertNotNull(foundUser);
        assertEquals("User1", foundUser.getUsername());
    }

    @Test
    public void testFindUserByIdNotFound() {
        User foundUser = userMAO.findUserById(99);
        assertNull(foundUser);
    }

    // Test updateUser
    @Test
    public void testUpdateUserSuccess() {
        User updatedUser = new User("UpdatedUser1", "NewPassword1");
        updatedUser.setId(1);

        boolean result = userMAO.updateUser(updatedUser);
        assertTrue(result);

        User foundUser = userMAO.findUserById(1);
        assertNotNull(foundUser);
        assertEquals("UpdatedUser1", foundUser.getUsername());
    }

    @Test
    public void testUpdateUserFailureNotFound() {
        User nonExistentUser = new User("NonExistent", "Password");
        nonExistentUser.setId(99); // ID that doesn't exist

        boolean result = userMAO.updateUser(nonExistentUser);
        assertFalse(result);
    }

    // Test hasUser by ID
    @Test
    public void testHasUserById() {
        assertTrue(userMAO.hasUser(1));
        assertFalse(userMAO.hasUser(99)); // Non-existent user ID
    }

    // Test hasUser by username
    @Test
    public void testHasUserByUsername() {
        assertTrue(userMAO.hasUser("User1"));
        assertFalse(userMAO.hasUser("NonExistentUser"));
    }

    // Test deleteUser
    @Test
    public void testDeleteUserSuccess() {
        User foundUser = userMAO.findUserById(1);
        assertNotNull(foundUser);

        boolean result = userMAO.deleteUser(foundUser);
        assertTrue(result);

        foundUser = userMAO.findUserById(1);
        assertNull(foundUser); // User should be deleted
    }

    @Test
    public void testDeleteUserFailure() {
        User nonExistentUser = new User("NonExistent", "Password");
        nonExistentUser.setId(99); // ID that doesn't exist

        boolean result = userMAO.deleteUser(nonExistentUser);
        assertFalse(result);
    }
}
