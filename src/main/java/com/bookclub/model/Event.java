package com.bookclub.model;

public class Event {

    private String event;
    private String organizer;
    private String date;
    private String location;
    private Integer eventid;
    private String description;

    public Event(Integer eventid, String title, String organizer, String date, String location, String description) {
        this.eventid = eventid;
        this.event = title;
        this.organizer = organizer;
        this.date = date;
        this.location = location;
        this.description = description;
    }

    public String getEvent() {
        return event;
    }

    public String getDescription() {
        return description;
    }

    public Integer getEventID() {
        return eventid;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getOrganizer() {
        return organizer;
    }

    public void setOrganizer(String author) {
        this.organizer = organizer;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return event + " created by " + organizer + " Date " + date + " at " + location;
    }
}
