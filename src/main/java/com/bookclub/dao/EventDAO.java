package com.bookclub.dao;

import com.bookclub.iao.IEventAO;
import com.bookclub.model.Event;
import com.bookclub.util.DatabaseManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code EventDAO} class is responsible for interacting with the
 * {@code Events} table in the database. 
 * It provides CRUD operations for events in the Book Club application.
 */
public class EventDAO implements IEventAO {

    private DatabaseManager dbManager;

    /**
     * Constructs an {@code EventDAO} object and initializes the database connection.
     * If the events table doesn't exist, it creates the table.
     */
    public EventDAO() {
        dbManager = DatabaseManager.getInstance();
        createTable();
    }

    /**
     * Retrieves all events from the database.
     *
     * @return a list of all {@link Event} objects stored in the database.
     */
    @Override
    public List<Event> findAllEvents() {
        List<Event> events = new ArrayList<>();
        try {
            Statement statement = dbManager.getConnection().createStatement();
            String query = "SELECT * FROM Events";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int eventId = resultSet.getInt("eventId");
                int bookId = resultSet.getInt("bookId");
                String eventName = resultSet.getString("eventName");
                String eventOrganizer = resultSet.getString("eventOrganizer");
                LocalDateTime eventDateTime = resultSet.getTimestamp("eventDateTime").toLocalDateTime();
                String eventLocation = resultSet.getString("eventLocation");
                Event event = new Event(eventId, bookId, eventName, eventOrganizer, eventDateTime, eventLocation);
                events.add(event);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return events;
    }

    /**
     * Finds a specific event based on the book ID, name, organizer, date-time, and location.
     *
     * @param bookId    the ID of the book associated with the event.
     * @param name      the name of the event.
     * @param organizer the organizer of the event.
     * @param dateTime  the date and time of the event.
     * @param location  the location of the event.
     * @return the {@link Event} object that matches the given criteria, or {@code null} if no match is found.
     */
    @Override
    public Event findEventByBookIdNameOrganizerDateTimeAndLocation(int bookId, String name, String organizer, LocalDateTime dateTime, String location) {
        try {
            PreparedStatement statement = dbManager.getConnection().prepareStatement("SELECT * FROM Events WHERE bookId = ? AND eventName = ? AND eventOrganizer = ? AND eventDateTime = ? AND eventLocation = ?");
            statement.setInt(1, bookId);
            statement.setString(2, name);
            statement.setString(3, organizer);
            statement.setTimestamp(4, Timestamp.valueOf(dateTime));
            statement.setString(5, location);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int eventId = resultSet.getInt("eventId");
                int eventBookId = resultSet.getInt("bookId");
                String eventName = resultSet.getString("eventName");
                String eventOrganizer = resultSet.getString("eventOrganizer");
                LocalDateTime eventDateTime = resultSet.getTimestamp("eventDateTime").toLocalDateTime();
                String eventLocation = resultSet.getString("eventLocation");
                Event event = new Event(eventId, eventBookId, eventName, eventOrganizer, eventDateTime, eventLocation);
                return event;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Retrieves a list of events associated with a specific book ID.
     *
     * @param bookId the ID of the book.
     * @return a list of {@link Event} objects associated with the given book ID.
     */
    @Override
    public List<Event> findEventsByBookId(int bookId) {
        List<Event> events = new ArrayList<>();
        try {
            PreparedStatement statement = dbManager.getConnection().prepareStatement("SELECT * FROM Events WHERE bookId = ?");
            statement.setInt(1, bookId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int eventId = resultSet.getInt("eventId");
                int eventBookId = resultSet.getInt("bookId");
                String eventName = resultSet.getString("eventName");
                String eventOrganizer = resultSet.getString("eventOrganizer");
                LocalDateTime eventDateTime = resultSet.getTimestamp("eventDateTime").toLocalDateTime();
                String eventLocation = resultSet.getString("eventLocation");
                Event event = new Event(eventId, eventBookId, eventName, eventOrganizer, eventDateTime, eventLocation);
                events.add(event);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return events;
    }

    /**
     * Retrieves a list of events associated with a specific book name.
     *
     * @param name the name of the book.
     * @return a list of {@link Event} objects associated with the given book name.
     */
    @Override
    public List<Event> findEventsByName(String name) {
        List<Event> events = new ArrayList<>();
        try {
            PreparedStatement statement = dbManager.getConnection().prepareStatement("SELECT * FROM Events WHERE eventName = ?");
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int eventId = resultSet.getInt("eventId");
                int eventBookId = resultSet.getInt("bookId");
                String eventName = resultSet.getString("eventName");
                String eventOrganizer = resultSet.getString("eventOrganizer");
                LocalDateTime eventDateTime = resultSet.getTimestamp("eventDateTime").toLocalDateTime();
                String eventLocation = resultSet.getString("eventLocation");
                Event event = new Event(eventId, eventBookId, eventName, eventOrganizer, eventDateTime, eventLocation);
                events.add(event);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return events;
    }

    /**
     * Retrieves a list of events associated with a specific organizer.
     *
     * @param organizer the organizer of the event(s).
     * @return a list of {@link Event} objects associated with the given organizer.
     */
    @Override
    public List<Event> findEventsByOrganizer(String organizer) {
        List<Event> events = new ArrayList<>();
        try {
            PreparedStatement statement = dbManager.getConnection().prepareStatement("SELECT * FROM Events WHERE eventOrganizer = ?");
            statement.setString(1, organizer);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int eventId = resultSet.getInt("eventId");
                int eventBookId = resultSet.getInt("bookId");
                String eventName = resultSet.getString("eventName");
                String eventOrganizer = resultSet.getString("eventOrganizer");
                LocalDateTime eventDateTime = resultSet.getTimestamp("eventDateTime").toLocalDateTime();
                String eventLocation = resultSet.getString("eventLocation");
                Event event = new Event(eventId, eventBookId, eventName, eventOrganizer, eventDateTime, eventLocation);
                events.add(event);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return events;
    }

    /**
     * Retrieves a list of events with a specific location.
     *
     * @param location the location of the events.
     * @return a list of {@link Event} objects associated with the given location.
     */
    @Override
    public List<Event> findEventsByLocation(String location) {
        List<Event> events = new ArrayList<>();
        try {
            PreparedStatement statement = dbManager.getConnection().prepareStatement("SELECT * FROM Events WHERE eventLocation = ?");
            statement.setString(1, location);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int eventId = resultSet.getInt("eventId");
                int eventBookId = resultSet.getInt("bookId");
                String eventName = resultSet.getString("eventName");
                String eventOrganizer = resultSet.getString("eventOrganizer");
                LocalDateTime eventDateTime = resultSet.getTimestamp("eventDateTime").toLocalDateTime();
                String eventLocation = resultSet.getString("eventLocation");
                Event event = new Event(eventId, eventBookId, eventName, eventOrganizer, eventDateTime, eventLocation);
                events.add(event);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return events;
    }

    /**
     * Retrieves a list of events associated with a specific date/time.
     *
     * @param dateTime the date/time of the event(s).
     * @return a list of {@link Event} objects associated with the given date/time.
     */
    @Override
    public List<Event> findEventsByDateTime(LocalDateTime dateTime) {
        List<Event> events = new ArrayList<>();
        try {
            PreparedStatement statement = dbManager.getConnection().prepareStatement("SELECT * FROM Events WHERE eventDateTime = ?");
            statement.setTimestamp(1, Timestamp.valueOf(dateTime));
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int eventId = resultSet.getInt("eventId");
                int eventBookId = resultSet.getInt("bookId");
                String eventName = resultSet.getString("eventName");
                String eventOrganizer = resultSet.getString("eventOrganizer");
                LocalDateTime eventDateTime = resultSet.getTimestamp("eventDateTime").toLocalDateTime();
                String eventLocation = resultSet.getString("eventLocation");
                Event event = new Event(eventId, eventBookId, eventName, eventOrganizer, eventDateTime, eventLocation);
                events.add(event);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return events;
    }

    /**
     * Adds a new event to the database.
     *
     * @param event the {@link Event} object to be added.
     * @return {@code true} if the event was added successfully, {@code false} otherwise.
     */
    @Override
    public boolean addEvent(Event event) {
        try {
            PreparedStatement statement = dbManager.getConnection().prepareStatement("INSERT INTO Events (bookId, eventName, eventOrganizer, eventDateTime, eventLocation) VALUES (?, ?, ?, ?, ?)");
            statement.setInt(1, event.getBookId());
            statement.setString(2, event.getName());
            statement.setString(3, event.getOrganizer());
            statement.setTimestamp(4, Timestamp.valueOf(event.getDateTime()));
            statement.setString(5, event.getLocation());
            statement.executeUpdate();

            // Set the id of the new event
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                event.setId(generatedKeys.getInt(1));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Updates an existing event in the database.
     *
     * @param event the {@link Event} object containing updated event data.
     * @return {@code true} if the event was updated successfully, {@code false} otherwise.
     */
    @Override
    public boolean updateEvent(Event event) {
        try {
            PreparedStatement statement = dbManager.getConnection().prepareStatement("UPDATE Events SET bookId = ?, eventName = ?, eventOrganizer = ?, eventDateTime = ?, eventLocation = ? WHERE eventId = ?");
            statement.setInt(1, event.getBookId());
            statement.setString(2, event.getName());
            statement.setString(3, event.getOrganizer());
            statement.setTimestamp(4, Timestamp.valueOf(event.getDateTime()));
            statement.setString(5, event.getLocation());
            statement.setInt(6, event.getId());
            statement.executeUpdate();
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Deletes an event from the database.
     *
     * @param event the {@link Event} object to be deleted.
     * @return {@code true} if the event was deleted successfully, {@code false} otherwise.
     */
    @Override
    public boolean deleteEvent(Event event) {
        try {
            PreparedStatement statement = dbManager.getConnection().prepareStatement("DELETE FROM Events WHERE eventId = ?");
            statement.setInt(1, event.getId());
            statement.executeUpdate();
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Creates the {@code Events} table in the database if it does not already exist.
     */
    private void createTable() {
        // Create table if not exists
        try {
            Statement statement = dbManager.getConnection().createStatement();
            String query = "CREATE TABLE IF NOT EXISTS Events ("
                    + "eventId INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "bookId INTEGER NOT NULL,"
                    + "eventName VARCHAR NOT NULL,"
                    + "eventOrganizer VARCHAR NOT NULL,"
                    + "eventDateTime TIMESTAMP NOT NULL,"
                    + "eventLocation VARCHAR NOT NULL"
                    + ")";
            statement.execute(query);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
