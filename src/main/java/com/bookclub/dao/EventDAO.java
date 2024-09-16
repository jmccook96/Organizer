package com.bookclub.dao;

import com.bookclub.iao.IEventAO;
import com.bookclub.model.Event;
import com.bookclub.util.DatabaseManager;

import java.util.List;

public class EventDAO implements IEventAO {

    private DatabaseManager dbManager;

    public EventDAO() {
        dbManager = DatabaseManager.getInstance();
    }

    public List<Event> findAllEvents() {
        // TODO: Add query
        return null;
    }


    public Event findEventByTitleAndOrganizer(String title, String organizer) {
        return null;
    }


    public List<Event> findEventsByTitle(String title) {
        return List.of();
    }


    public List<Event> findEventsByOrganizer(String organizer) {
        return List.of();
    }


    public List<Event> findEventsByLocation(String location) {
        return List.of();
    }


    public List<Event> findEventsByDate(String date) {
        return List.of();
    }


    public boolean addEvent(Event event) {
        return false;
    }


    public boolean updateBook(Event event) {
        return false;
    }

    public Event findEventByTitleAndOrganizer() {
        // TODO: Add query
        return null;
    }

    public List<Event> findEventsByTitle() {
        // TODO: Add query
        return null;
    }

    public List<Event> findEventsByOrganizer() {
        // TODO: Add query
        return null;
    }

    public List<Event> findEventsByLocation() {
        // TODO: Add query
        return null;
    }

    public List<Event> findEventsByDate() {
        // TODO: Add query
        return null;
    }

    public boolean addEvent() {
        // TODO: Add query
        return true;
    }

    public boolean updateBook() {
        // TODO: Add query
        return true;
    }

    public boolean deleteEvent(Event event) {
        // TODO: Add query
        return true;
    }
}
