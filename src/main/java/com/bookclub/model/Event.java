package com.bookclub.model;

import java.time.LocalDateTime;

public class Event {

    private int id;
    private String name;
    private String organizer;
    private LocalDateTime dateTime;
    private String location;

    public Event(int id, String name, String organizer, LocalDateTime dateTime, String location) {
        this.id = id;
        this.name = name;
        this.organizer = organizer;
        this.dateTime = dateTime;
        this.location = location;
    }

    public Event(String name, String organizer, LocalDateTime dateTime, String location) {
        this.name = name;
        this.organizer = organizer;
        this.dateTime = dateTime;
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrganizer() {
        return organizer;
    }

    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getLocation() {
        return location;
    }

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
