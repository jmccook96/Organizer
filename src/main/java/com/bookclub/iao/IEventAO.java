package com.bookclub.iao;

import com.bookclub.model.Event;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Interface for Events Access Object (IAO) that defines methods for managing events in the system.
 */
public interface IEventAO {
    /**
     * Retrieves a list of all events from the database
     * @return All events found in the database, or null if not found.
     */
    List<Event> findAllEvents();

    /**
     * Retrieves an event from the database based on the bookId, name, organizer, date and time, and location
     * @param bookId The bookId of the event to retrieve
     * @param name The name of the event to retrieve
     * @param organizer The organizer of the event to retrieve
     * @param dateTime The date and time of the event to retrieve
     * @param location The location of the event to retrieve
     * @return The event with the given bookId, name, organiser, date and time, and location, or null if not found.
     */
    Event findEventByBookIdNameOrganizerDateTimeAndLocation(int bookId, String name, String organizer, LocalDateTime dateTime, String location);

    /**
     * Retrieves an event based on the events ID
     * @param id The eventID to retrieve
     * @return The event with the given eventID, or null if not found.
     */
    Event findEventById(int id);    
    
    /**
     * Retrieves a list of events from the database based on the bookId
     * @param bookId The bookId of the events to retrieve
     * @return A list of events with the given bookId
     */
    List<Event> findEventsByBookId(int bookId);

    /**
     * Retrieves a list of events from the database based on the name
     * @param name The name of the events to retrieve
     * @return A list of events with the given name
     */
    List<Event> findEventsByName(String name);

    /**
     * Retrieves a list of events from the database based on the organizer
     * @param organizer The organizer of the events to retrieve
     * @return A list of events created by the given organizer
     */
    List<Event> findEventsByOrganizer(String organizer);

    /**
     * Retrieves a list of events from the database based on the location
     * @param location The location of the events to retrieve
     * @return A list of events at the given location
     */
    List<Event> findEventsByLocation(String location);

    /**
     * Retrieves a list of events from the database based on the date and time
     * @param dateTime The date and time of the events to retrieve
     * @return A list of events on the given date and time
     */
    List<Event> findEventsByDateTime(LocalDateTime dateTime);

    /**
     * Adds a new event to the database
     * @param event The event to add.
     * @return If operation succeeded
     */
    boolean addEvent(Event event);

    /**
     * Updates an existing event in the database.
     * @param event The event with information to update.
     * @return If operation succeeded
     */
    boolean updateEvent(Event event);

    /**
     * Removes an event from the database.
     * @param event The event to delete.
     * @return If operation succeeded
     */
    boolean deleteEvent(Event event);

}