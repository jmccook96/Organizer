import com.bookclub.model.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EventTest {
    private Event event;

    @BeforeEach
    public void setUp() {
        event = new Event(1, "testName", "testOrganizer", LocalDateTime.of(2024, 10, 2, 1, 30), "testLocation");
    }

    @Test
    public void testGetBookId() {
        assertEquals(1, event.getBookId());
    }

    @Test
    public void testSetBookId() {
        event.setBookId(2);
        assertEquals(2, event.getBookId());
    }

    @Test
    public void testGetName() {
        assertEquals("testName", event.getName());
    }

    @Test
    public void testSetName() {
        event.setName("testNewName");
        assertEquals("testNewName", event.getName());
    }

    @Test
    public void testGetOrganizer() {
        assertEquals("testOrganizer", event.getOrganizer());
    }

    @Test
    public void testSetOrganizer() {
        event.setOrganizer("testNewOrganizer");
        assertEquals("testNewOrganizer", event.getOrganizer());
    }

    @Test
    public void testGetDateTime() {
        assertEquals(LocalDateTime.of(2024, 10, 2, 1, 30), event.getDateTime());
    }

    @Test
    public void testSetDateTime() {
        event.setDateTime(LocalDateTime.of(2025, 11, 3, 2, 31));
        assertEquals(LocalDateTime.of(2025, 11, 3, 2, 31), event.getDateTime());
    }

    @Test
    public void testGetLocation() {
        assertEquals("testLocation", event.getLocation());
    }

    @Test
    public void testSetLocation() {
        event.setLocation("testNewLocation");
        assertEquals("testNewLocation", event.getLocation());
    }
}
