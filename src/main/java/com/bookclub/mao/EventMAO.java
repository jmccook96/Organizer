package com.bookclub.mao;

import com.bookclub.iao.IEventAO;
import com.bookclub.model.Book;
import com.bookclub.model.Event;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code EventMAO} class implements the {@link IEventAO} interface and serves as a Mock Access Object (MAO)
 * for managing {@link Event} objects in the book club application.
 * It provides methods to perform CRUD (Create, Read, Update, Delete) operations on events.
 */
public class EventMAO implements IEventAO {
    private List<Event> events;
    private int eventId = 1;

    /**
     * Constructs a new {@code EventMAO} instance with a predefined list of test events.
     */
    public EventMAO() {
        events = new ArrayList<>();
        addTestData();
    }

    /**
     * Adds some sample events to the internal list to simulate a data store for testing purposes.
     */
    private void addTestData() {
        Book book1 = new Book(1, "It", "Stephen King", "Horror", 659);
        Book book2 = new Book(2, "The Shining", "Stephen King", "Horror", 1120);
        events.add(new Event(eventId++, book1.getId(), "It bookclub meeting", "Bob Jane", LocalDateTime.of(2024, 10, 2, 1, 30), "1234 library court"));
        events.add(new Event(eventId++, book2.getId(), "The Shining bookclub meeting", "Bob Jane", LocalDateTime.of(2024, 10, 3, 2, 30), "1234 library court"));
        events.add(new Event(eventId++, book1.getId(), "testTitle bookclub meeting", "Bob Jane", LocalDateTime.of(2024, 10, 4, 3, 30), "1234 library court"));
        events.add(new Event(eventId++, book1.getId(), "1994 bookclub meeting", "Bob Jane", LocalDateTime.of(2024, 10, 2, 13, 30), "1234 library court"));
        events.add(new Event(eventId++, book2.getId(), "Animal Farm bookclub meeting", "Bob Jane", LocalDateTime.of(2024, 10, 6, 14, 30), "1234 library court"));
        events.add(new Event(eventId++, book2.getId(), "testTitle bookclub meeting", "Bob Jane", LocalDateTime.of(2024, 10, 7, 15, 30), "1234 library court"));
    }

    /**
     * Retrieves all events in the system.
     *
     * @return a list of {@link Event} objects.
     */
    @Override
    public List<Event> findAllEvents() {
        return events;
    }

    /**
     * Finds an event in the system based on the book ID, event name, organizer, date/time, and location.
     *
     * @param bookId    the ID of the book associated with the event.
     * @param name      the name of the event.
     * @param organizer the organizer of the event.
     * @param dateTime  the date and time of the event.
     * @param location  the location of the event.
     * @return the {@link Event} object matching the specified criteria, or {@code null} if not found.
     */
    @Override
    public Event findEventByBookIdNameOrganizerDateTimeAndLocation(int bookId, String name, String organizer, LocalDateTime dateTime, String location) {
        for (Event event : events) {
            if (event.getBookId() == bookId && event.getName().equals(name) && event.getOrganizer().equals(organizer)
                    && event.getDateTime().equals(dateTime) && event.getLocation().equals(location)) {
                return event;
            }
        }
        return null;
    }

    /**
     * Retrieves an event based on the events ID
     * @param id The eventID to retrieve
     * @return The event with the given eventID, or null if not found.
     */
    @Override
    public Event findEventById(int id) {
        for (Event event : events) {
            if (event.getId() == id) {
                return event;
            }
        }
        return null;
    }

    /**
     * Finds all events associated with the specified book ID.
     *
     * @param bookId the ID of the book to search for.
     * @return a list of {@link Event} objects that match the specified book ID.
     */
    @Override
    public List<Event> findEventsByBookId(int bookId) {
        List<Event> eventsByBookId = new ArrayList<>();
        for (Event event : events) {
            if (event.getBookId() == bookId) {
                eventsByBookId.add(event);
            }
        }
        return eventsByBookId;
    }

    /**
     * Finds all events with the specified name.
     *
     * @param name the name of the event to search for.
     * @return a list of {@link Event} objects that match the specified name.
     */
    @Override
    public List<Event> findEventsByName(String name) {
        List<Event> eventsByName = new ArrayList<>();
        for (Event event : events) {
            if (event.getName().equals(name)) {
                eventsByName.add(event);
            }
        }
        return eventsByName;
    }

    /**
     * Finds all events organized by the specified organizer.
     *
     * @param organizer the name of the organizer to search for.
     * @return a list of {@link Event} objects that match the specified organizer.
     */
    @Override
    public List<Event> findEventsByOrganizer(String organizer) {
        List<Event> eventsByOrganizer = new ArrayList<>();
        for (Event event : events) {
            if (event.getOrganizer().equals(organizer)) {
                eventsByOrganizer.add(event);
            }
        }
        return eventsByOrganizer;
    }

    /**
     * Finds all events happening at the specified location.
     *
     * @param location the location to search for.
     * @return a list of {@link Event} objects that match the specified location.
     */
    @Override
    public List<Event> findEventsByLocation(String location) {
        List<Event> eventsByLocation = new ArrayList<>();
        for (Event event : events) {
            if (event.getLocation().equals(location)) {
                eventsByLocation.add(event);
            }
        }
        return eventsByLocation;
    }

    /**
     * Finds all events happening at the specified date and time.
     *
     * @param dateTime the date and time to search for.
     * @return a list of {@link Event} objects that match the specified date and time.
     */
    @Override
    public List<Event> findEventsByDateTime(LocalDateTime dateTime) {
        List<Event> eventsByDateTime = new ArrayList<>();
        for (Event event : events) {
            if (event.getDateTime().equals(dateTime)) {
                eventsByDateTime.add(event);
            }
        }
        return eventsByDateTime;
    }

    /**
     * Adds a new event to the system.
     *
     * @param event the {@link Event} object to be added.
     * @return {@code true} if the event was added successfully, {@code false} otherwise.
     */
    @Override
    public boolean addEvent(Event event) {
        return events.add(event);
    }

    /**
     * Updates an existing event in the system.
     * The event is located based on its ID.
     *
     * @param event the {@link Event} object containing updated information.
     * @return {@code true} if the event was updated successfully, {@code false} otherwise.
     */
    @Override
    public boolean updateEvent(Event event) {
        int eventIdx = events.indexOf(findEventById(event.getId()));
        if (eventIdx == -1) {
            return false;
        }
        return events.set(eventIdx, event) != null;
    }

    /**
     * Deletes an event from the system.
     *
     * @param event the {@link Event} object to be deleted.
     * @return {@code true} if the event was deleted successfully, {@code false} otherwise.
     */
    @Override
    public boolean deleteEvent(Event event) {
        return events.remove(event);
    }
}
