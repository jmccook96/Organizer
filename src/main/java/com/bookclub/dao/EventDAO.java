package com.bookclub.dao;

import com.bookclub.iao.IEventAO;
import com.bookclub.model.Event;
import com.bookclub.util.DatabaseManager;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EventDAO implements IEventAO {

    private DatabaseManager dbManager;

    public EventDAO() {
        dbManager = DatabaseManager.getInstance();
        createTable();
    }

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
