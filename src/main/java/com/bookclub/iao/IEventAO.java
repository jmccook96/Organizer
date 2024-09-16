package com.bookclub.iao;

import com.bookclub.model.Event;

import java.util.List;

public interface IEventAO {
    /**
     * Retrieves a list of all events from the database
     * @return All events found in the database, or null if not found.
     */
    public List<Event> findAllEvents();

    /**
     * Retrieves a event from the database based on the title and organiser
     * @param title The title of the event to retrieve
     * @param organizer The organizer of the event to retrieve
     * @return The event with the given title and organiser, or null if not found.
     */
    public Event findEventByTitleAndOrganizer(String title, String organizer);

    /**
     * Retrieves a event from the database based on the title
     * @param title The title of the event to retrieve
     * @return The event with the given title, or null if not found.
     */
    public List<Event> findEventsByTitle(String title);

    /**
     * Retrieves a event from the database based on the organizer
     * @param organizer The organizer of the event to retrieve
     * @return The event with the given organizer, or null if not found.
     */
    public List<Event> findEventsByOrganizer(String organizer);

    /**
     * Retrieves a event from the database based on the location
     * @param location The location of the event to retrieve
     * @return The event with the given location, or null if not found.
     */
    public List<Event> findEventsByLocation(String location);

    /**
     * Retrieves a event from the database based on the date
     * @param date The date of the event to retrieve
     * @return The event with the given date, or null if not found.
     */
    public List<Event> findEventsByDate(String date);

    /**
     * Adds a new event to the database
     * @param event The event to add.
     * @return If operation succeeded
     */
    public boolean addEvent(Event event);

    /**
     * Updates an existing event in the database.
     * @param event The event with information to update.
     * @return If operation succeeded
     */
    public boolean updateBook(Event event);

    /**
     * Removes a event from the database.
     * @param event The event to delete.
     * @return If operation succeeded
     */
    public boolean deleteEvent(Event event);


}