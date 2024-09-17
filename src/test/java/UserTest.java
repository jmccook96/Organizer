import com.bookclub.model.User;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserTest {

    private User user;

    @BeforeEach
    public void setUp() {
        user = new User("testUser", "testPassword");
    }

    // Basic functionality
    @Test
    public void testGetUsername() {
        assertEquals("testUser", user.getUsername());
    }

    @Test
    public void testGetPassword() {
        assertEquals("testPassword", user.getPassword());
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

    // Edge Cases
    @Test
    public void testSetUsernameInvalidNull() {
        assertThrows(IllegalArgumentException.class, () -> user.setUsername(null));
    }

    @Test
    public void testSetUsernameInvalidEmpty() {
        assertThrows(IllegalArgumentException.class, () -> user.setUsername(""));
    }

    @Test
    public void testSetPasswordInvalidNull() {
        assertThrows(IllegalArgumentException.class, () -> user.setPassword(null));
    }

    @Test
    public void testSetPasswordInvalidEmpty() {
        assertThrows(IllegalArgumentException.class, () -> user.setPassword(""));
    }

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
}
