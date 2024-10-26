package com.example.service;

import com.bookclub.service.EventService;
import com.bookclub.model.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class EventServiceTest {

    private EventService eventService;

    @BeforeEach
    public void setUp() {
        eventService = EventService.getInstance();
        eventService.setSelectedEvent(null); // Reset to ensure clean state
    }

    // Test getInstance
    @Test
    public void testGetInstance() {
        EventService instance1 = EventService.getInstance();
        EventService instance2 = EventService.getInstance();
        assertSame(instance1, instance2); // Singleton, should be the same instance
    }

    // Test setSelectedEvent and getSelectedEvent
    @Test
    public void testSetAndGetSelectedEvent() {
        Event event = new Event(1, "Test Event", "Organizer", LocalDateTime.now(), "Test Location");
        eventService.setSelectedEvent(event);

        Event selectedEvent = eventService.getSelectedEvent();
        assertNotNull(selectedEvent);
        assertEquals("Test Event", selectedEvent.getName());
        assertEquals("Organizer", selectedEvent.getOrganizer());
    }

    @Test
    public void testSetSelectedEventNull() {
        eventService.setSelectedEvent(null);

        Event selectedEvent = eventService.getSelectedEvent();
        assertNull(selectedEvent);
    }
    
    // TODO: Add listener property tests.
}
