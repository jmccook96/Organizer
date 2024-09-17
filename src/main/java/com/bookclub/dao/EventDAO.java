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
        // TODO: Add query
        return null;
    }

    public List<Event> findEventsByEventID(Integer eventid){
        // TODO: Add query
        return null;
    }

    public List<Event> findEventsByDescription(String description){
        // TODO: Add query
        return null;
    }


    public List<Event> findEventsByTitle(String title) {
        // TODO: Add query
        return null;
    }


    public List<Event> findEventsByOrganizer(String organizer){
        // TODO: Add query
        return null;
    }


    public List<Event> findEventsByLocation(String location){
        // TODO: Add query
        return null;
    }


    public List<Event> findEventsByDate(String date){
        // TODO: Add query
        return null;
    }


    public boolean addEvent(Event event){
        // TODO: Add query
        return true;
    }

   public boolean updateEvent(Event event) {
        // TODO: Add query
        return true;
   }

    public boolean deleteEvent(Event event) {
        // TODO: Add query
        return true;
    }
}
