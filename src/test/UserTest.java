import com.bookclub.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    private User user;

    @BeforeEach
    public void setUp() {
        // Initialize user with name and email
        user = new User(0, "testUser", "testPassword", "Test Name", "test@example.com");
    }

    // Basic functionality
    @Test
    public void testGetId() {
        assertEquals(0, user.getId());
    }

    @Test
    public void testGetUsername() {
        assertEquals("testUser", user.getUsername());
    }

    @Test
    public void testGetPassword() {
        assertEquals("testPassword", user.getPassword());
    }

    @Test
    public void testGetName() {
        assertEquals("Test Name", user.getName());
    }

    @Test
    public void testGetEmail() {
        assertEquals("test@example.com", user.getEmail());
    }

    @Test
    public void testSetId() {
        user.setId(1);
        assertEquals(1, user.getId());
    }

    @Test
    public void testSetUsernameValid() {
        user.setUsername("newUser");
        assertEquals("newUser", user.getUsername());
    }

    @Test
    public void testSetPasswordValid() {
        user.setPassword("newPassword");
        assertEquals("newPassword", user.getPassword());
    }

    @Test
    public void testSetNameValid() {
        user.setName("New Name");
        assertEquals("New Name", user.getName());
    }

    @Test
    public void testSetEmailValid() {
        user.setEmail("new@example.com");
        assertEquals("new@example.com", user.getEmail());
    }

    // Edge Cases for Username
    @Test
    public void testGetIdWithoutSetting() {
        User userTemp = new User("TestName", "TestPassword");
        assertEquals(-1, userTemp.getId());
    }

    @Test
    public void testSetUsernameInvalidNull() {
        assertThrows(IllegalArgumentException.class, () -> user.setUsername(null));
    }

    @Test
    public void testSetUsernameInvalidEmpty() {
        assertThrows(IllegalArgumentException.class, () -> user.setUsername(""));
    }

    // Edge Cases for Password
    @Test
    public void testSetPasswordInvalidNull() {
        assertThrows(IllegalArgumentException.class, () -> user.setPassword(null));
    }

    @Test
    public void testSetPasswordInvalidEmpty() {
        assertThrows(IllegalArgumentException.class, () -> user.setPassword(""));
    }

    // Edge Cases for Name
    @Test
    public void testSetNameInvalidNull() {
        user.setName(null); // Name can be null, so this should not throw an exception
        assertNull(user.getName());
    }

    @Test
    public void testSetNameValidEmpty() {
        user.setName(""); // Empty name should be allowed
        assertEquals("", user.getName());
    }

    // Edge Cases for Email
    @Test
    public void testSetEmailInvalidNull() {
        user.setEmail(null); // Email can be null, so this should not throw an exception
        assertNull(user.getEmail());
    }

    @Test
    public void testSetEmailValidEmpty() {
        user.setEmail(""); // Empty email should be allowed
        assertEquals("", user.getEmail());
    }

    // Constructor Edge Cases
    @Test
    public void testConstructorInvalidUsernameNull() {
        assertThrows(IllegalArgumentException.class, () -> new User(null, "password"));
    }

    @Test
    public void testConstructorInvalidUsernameEmpty() {
        assertThrows(IllegalArgumentException.class, () -> new User("", "password"));
    }

    @Test
    public void testConstructorInvalidPasswordNull() {
        assertThrows(IllegalArgumentException.class, () -> new User("username", null));
    }

    @Test
    public void testConstructorInvalidPasswordEmpty() {
        assertThrows(IllegalArgumentException.class, () -> new User("username", ""));
    }

    @Test
    public void testConstructorWithNameAndEmail() {
        User newUser = new User(1, "newUser", "newPassword", "New Name", "new@example.com");
        assertEquals(1, newUser.getId());
        assertEquals("newUser", newUser.getUsername());
        assertEquals("newPassword", newUser.getPassword());
        assertEquals("New Name", newUser.getName());
        assertEquals("new@example.com", newUser.getEmail());
    }
}
