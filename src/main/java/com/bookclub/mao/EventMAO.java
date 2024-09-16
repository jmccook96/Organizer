package com.bookclub.mao;

import com.bookclub.model.Event;

import java.util.ArrayList;
import java.util.List;

public class EventMAO {
    private List<Event> events;

    public EventMAO() {
        events = new ArrayList<>();
        addTestData();
    }

    private void addTestData() {
        events.add(new Event("It bookclub meeting", "Bob Jane", "02/10/2024","1234 library court"));
        events.add(new Event("The Shining bookclub meeting", "Bob Jane", "03/10/2024","1234 library court"));
        events.add(new Event("testTitle bookclub meeting", "Bob Jane", "04/10/2024","1234 library court"));
        events.add(new Event("1994 bookclub meeting", "Bob Jane", "05/10/2024","1234 library court"));
        events.add(new Event("Animal Farm bookclub meeting", "Bob Jane", "06/010/2024","1234 library court"));
        events.add(new Event("testTitle bookclub meeting", "Bob Jane", "07/10/2024","1234 library court"));
    }

    public List<Event> findAllEvents() {
        return events;
    }

    public Event findEventByTitleAndOrganizer(String title, String organizer) {
        for (Event event : events) {
            if (event.getEvent().equals(title) && event.getOrganizer().equals(organizer)) {
                return event;
            }
        }
        return null;
    }

    public List<Event> findEventsByTitle(String title) {
        List<Event> eventsByTitle = new ArrayList<>();
        for (Event event : events) {
            if (event.getEvent().equals(title)) {
                eventsByTitle.add(event);
            }
        }
        return eventsByTitle.isEmpty() ? null : eventsByTitle;
    }

    public List<Event> findEventsByOrganizer(String organizer) {
        List<Event> eventsByOrganizer = new ArrayList<>();
        for (Event event : events) {
            if (event.getOrganizer().equals(organizer)) {
                eventsByOrganizer.add(event);
            }
        }
        return eventsByOrganizer.isEmpty() ? null : eventsByOrganizer;
    }

    public List<Event> findEventsByLocation(String location) {
        List<Event> eventsByLocation = new ArrayList<>();
        for (Event event : events) {
            if (event.getLocation().equals(location)) {
                eventsByLocation.add(event);
            }
        }
        return eventsByLocation.isEmpty() ? null : eventsByLocation;
    }

    public List<Event> findEventsByDate(String date) {
        List<Event> eventsByDate = new ArrayList<>();
        for (Event event : events) {
            if (event.getDate().equals(date)) {
                eventsByDate.add(event);
            }
        }
        return eventsByDate.isEmpty() ? null : eventsByDate;
    }

    public boolean addEvent(Event event) {
        return events.add(event);
    }

    public boolean updateBook(Event event) {
        return events.set(events.indexOf(findEventByTitleAndOrganizer(event.getEvent(), event.getOrganizer())), event) != null;
    }

    public boolean deleteEvent(Event event) {
        return events.remove(event);
    }
}
