package com.bookclub.model;

import java.time.LocalDateTime;


/**
 * The {@code Event} class represents an event in the book club application.
 * It contains information such as the event id, book id, name,
 * organizer, date and time, and location of the event.
 */
public class Event {

    private int id;
    private int bookId;
    private String name;
    private String organizer;
    private LocalDateTime dateTime;
    private String location;

    /**
     * Constructs an {@code Event} object with the specified event id, bookId, name, organizer, dateTime and location.
     *
     * @param id        the ID of the event
     * @param bookId    the ID of the book associated with the event
     * @param name      the name of the event
     * @param organizer the organizer of the event
     * @param dateTime  the date and time of the event
     * @param location  the location of the event
     */
    public Event(int id, int bookId, String name, String organizer, LocalDateTime dateTime, String location) {
        this.id = id;
        this.bookId = bookId;
        this.name = name;
        this.organizer = organizer;
        this.dateTime = dateTime;
        this.location = location;
    }

    /**
     * Constructs an {@code Event} object with the specified bookId, name, organizer, dateTime and location.
     *
     * @param bookId    the ID of the book associated with the event
     * @param name      the name of the event
     * @param organizer the organizer of the event
     * @param dateTime  the date and time of the event
     * @param location  the location of the event
     */
    public Event(int bookId, String name, String organizer, LocalDateTime dateTime, String location) {
        this.name = name;
        this.bookId = bookId;
        this.organizer = organizer;
        this.dateTime = dateTime;
        this.location = location;
    }

    /**
     * Retrieves the ID of the event.
     *
     * @return The ID of the event.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the event.
     *
     * @param id the ID of the event
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retrieves the ID of the book associated with this event.
     *
     * @return The ID of the book.
     */
    public int getBookId() {
        return bookId;
    }

    /**
     * Sets the book ID associated with the event.
     *
     * @param bookId the ID of the book to be associated with the event
     */
    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    /**
     * Retrieves the name of the event.
     *
     * @return The name of the event.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the event.
     *
     * @param name the name of the event
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retrieves the organizer of the event.
     *
     * @return The organizer of the event.
     */
    public String getOrganizer() {
        return organizer;
    }

    /**
     * Sets the organizer of the event.
     *
     * @param organizer the organizer of the event
     */
    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }

    /**
     * Retrieves the date and time of the event.
     *
     * @return The date and time of the event.
     */
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    /**
     * Sets the date and time of the event.
     *
     * @param dateTime the date and time of the event
     */
    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    /**
     * Retrieves the location of the event.
     *
     * @return The location of the event.
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the location of the event.
     *
     * @param location the new location of the event
     */
    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return name + " created by " + organizer + " Date " + dateTime + " at " + location;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Event) {
            return ((Event) obj).name.equals(this.name) && ((Event) obj).organizer.equals(this.organizer) && ((Event) obj).dateTime.equals(this.dateTime) && ((Event) obj).location.equals(this.location);
        }
        return false;
    }
}
