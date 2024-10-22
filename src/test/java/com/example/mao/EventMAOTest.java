package com.example.mao;

import com.bookclub.mao.EventMAO;
import com.bookclub.model.Book;
import com.bookclub.model.Event;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EventMAOTest {

    private EventMAO eventMAO;
    private Book testBook1;
    private Event testEvent1;

    @BeforeEach
    public void setUp() {
        eventMAO = new EventMAO();
        testBook1 = new Book(1, "It", "Stephen King", "Horror", 659);
        testEvent1 = new Event(testBook1.getId(), "It bookclub meeting", "Bob Jane", LocalDateTime.of(2024, 10, 2, 1, 30), "1234 library court");
    }

    // Test addEvent
    @Test
    public void testAddEvent() {
        boolean result = eventMAO.addEvent(testEvent1);
        assertTrue(result);
        assertEquals(7, eventMAO.findAllEvents().size());
    }

    // Test findAllEvents
    @Test
    public void testFindAllEvents() {
        List<Event> events = eventMAO.findAllEvents();
        assertNotNull(events);
        assertEquals(6, events.size()); // Expecting 6 events from the test data
    }

    // Test findEventByBookIdNameOrganizerDateTimeAndLocation
    @Test
    public void testFindEventByBookIdNameOrganizerDateTimeAndLocationFound() {
        Event foundEvent = eventMAO.findEventByBookIdNameOrganizerDateTimeAndLocation(
                testBook1.getId(), "It bookclub meeting", "Bob Jane", LocalDateTime.of(2024, 10, 2, 1, 30), "1234 library court");

        assertNotNull(foundEvent);
        assertEquals(testBook1.getId(), foundEvent.getBookId());
        assertEquals("It bookclub meeting", foundEvent.getName());
    }

    @Test
    public void testFindEventByBookIdNameOrganizerDateTimeAndLocationNotFound() {
        Event foundEvent = eventMAO.findEventByBookIdNameOrganizerDateTimeAndLocation(
                testBook1.getId(), "Non-existent event", "Bob Jane", LocalDateTime.of(2024, 10, 2, 1, 30), "1234 library court");

        assertNull(foundEvent);
    }

    // Test findEventsByBookId
    @Test
    public void testFindEventsByBookId() {
        List<Event> events = eventMAO.findEventsByBookId(testBook1.getId());
        assertNotNull(events);
        assertEquals(3, events.size());
    }

    @Test
    public void testFindEventsByBookIdNoResults() {
        List<Event> events = eventMAO.findEventsByBookId(99); // Non-existing book ID
        assertTrue(events.isEmpty());
    }

    // Test findEventsByName
    @Test
    public void testFindEventsByName() {
        List<Event> events = eventMAO.findEventsByName("testTitle bookclub meeting");
        assertNotNull(events);
        assertEquals(2, events.size()); // Two events have the name "testTitle bookclub meeting"
    }

    @Test
    public void testFindEventsByNameNoResults() {
        List<Event> events = eventMAO.findEventsByName("Non-existent event");
        assertTrue(events.isEmpty());
    }

    // Test findEventsByOrganizer
    @Test
    public void testFindEventsByOrganizer() {
        List<Event> events = eventMAO.findEventsByOrganizer("Bob Jane");
        assertNotNull(events);
        assertEquals(6, events.size()); // All events are organized by "Bob Jane"
    }

    // Test findEventsByLocation
    @Test
    public void testFindEventsByLocation() {
        List<Event> events = eventMAO.findEventsByLocation("1234 library court");
        assertNotNull(events);
        assertEquals(6, events.size()); // All events take place at "1234 library court"
    }

    // Test findEventsByDateTime
    @Test
    public void testFindEventsByDateTime() {
        List<Event> events = eventMAO.findEventsByDateTime(LocalDateTime.of(2024, 10, 2, 1, 30));
        assertNotNull(events);
        assertEquals(1, events.size()); // One event takes place at this exact date and time
    }

    // Test updateEvent
    @Test
    public void testUpdateEventSuccess() {
        Event existingEvent = eventMAO.findEventByBookIdNameOrganizerDateTimeAndLocation(
                testBook1.getId(), "It bookclub meeting", "Bob Jane", LocalDateTime.of(2024, 10, 2, 1, 30), "1234 library court");

        Event updatedEvent = new Event(existingEvent.getId(), testBook1.getId(), "Updated It bookclub meeting", "Bob Jane", LocalDateTime.of(2024, 10, 2, 1, 30), "1234 library court");
        boolean result = eventMAO.updateEvent(updatedEvent);

        assertTrue(result);
        // Check the event was updated by searching with new content.
        Event foundEvent = eventMAO.findEventByBookIdNameOrganizerDateTimeAndLocation(
                testBook1.getId(), "Updated It bookclub meeting", "Bob Jane", LocalDateTime.of(2024, 10, 2, 1, 30), "1234 library court");
        assertNotNull(foundEvent);
    }

    @Test
    public void testUpdateEventFailure() {
        Event nonExistentEvent = new Event(99, "Non-existent event", "Unknown", LocalDateTime.of(2024, 12, 25, 10, 0), "Unknown Location");
        boolean result = eventMAO.updateEvent(nonExistentEvent);
        assertFalse(result);
    }

    // Test deleteEvent
    @Test
    public void testDeleteEventSuccess() {
        Event existingEvent = eventMAO.findEventByBookIdNameOrganizerDateTimeAndLocation(
                testBook1.getId(), "It bookclub meeting", "Bob Jane", LocalDateTime.of(2024, 10, 2, 1, 30), "1234 library court");

        boolean result = eventMAO.deleteEvent(existingEvent);
        assertTrue(result);

        Event foundEvent = eventMAO.findEventByBookIdNameOrganizerDateTimeAndLocation(
                testBook1.getId(), "It bookclub meeting", "Bob Jane", LocalDateTime.of(2024, 10, 2, 1, 30), "1234 library court");
        assertNull(foundEvent); // The event should have been deleted
    }

    @Test
    public void testDeleteEventFailure() {
        Event nonExistentEvent = new Event(99, "Non-existent event", "Unknown", LocalDateTime.of(2024, 12, 25, 10, 0), "Unknown Location");
        boolean result = eventMAO.deleteEvent(nonExistentEvent);
        assertFalse(result);
    }
}
