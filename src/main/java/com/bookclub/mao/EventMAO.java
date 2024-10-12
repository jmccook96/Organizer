package com.bookclub.mao;

import com.bookclub.iao.IEventAO;
import com.bookclub.model.Book;
import com.bookclub.model.Event;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EventMAO  implements IEventAO {
    private List<Event> events;

    public EventMAO() {
        events = new ArrayList<>();
        addTestData();
    }

    private void addTestData() {
        Book book1 = new Book("It", "Stephen King");
        Book book2 = new Book("The Shining", "Stephen King");
        events.add(new Event(book1.getId(), "It bookclub meeting", "Bob Jane", LocalDateTime.of(2024, 10, 2, 1, 30),"1234 library court"));
        events.add(new Event(book2.getId(), "The Shining bookclub meeting", "Bob Jane", LocalDateTime.of(2024, 10, 3, 2, 30),"1234 library court"));
        events.add(new Event(book1.getId(),"testTitle bookclub meeting", "Bob Jane", LocalDateTime.of(2024, 10, 4, 3, 30),"1234 library court"));
        events.add(new Event(book1.getId(),"1994 bookclub meeting", "Bob Jane", LocalDateTime.of(2024, 10, 2, 13, 30),"1234 library court"));
        events.add(new Event(book2.getId(),"Animal Farm bookclub meeting", "Bob Jane", LocalDateTime.of(2024, 10, 6, 14, 30),"1234 library court"));
        events.add(new Event(book2.getId(), "testTitle bookclub meeting", "Bob Jane", LocalDateTime.of(2024, 10, 7, 15, 30),"1234 library court"));
    }

    @Override
    public List<Event> findAllEvents() {
        return events;
    }

    @Override
    public Event findEventByBookIdNameOrganizerDateTimeAndLocation(int bookId, String name, String organizer, LocalDateTime dateTime, String location) {
        for (Event event : events) {
            if (event.getBookId() == bookId && event.getName().equals(name) && event.getOrganizer().equals(organizer) && event.getDateTime().equals(dateTime) && event.getLocation().equals(location)) {
                return event;
            }
        }
        return null;
    }

    @Override
    public List<Event> findEventsByBookId(int bookId) {
        List<Event> eventsByBookId = new ArrayList<>();
        for (Event event : events) {
            if (event.getBookId() == bookId) {
                eventsByBookId.add(event);
            }
        }
        return eventsByBookId;
    }

    @Override
    public List<Event> findEventsByName(String name) {
        List<Event> eventsByName = new ArrayList<>();
        for (Event event : events) {
            if (event.getName().equals(name)) {
                eventsByName.add(event);
            }
        }
        return eventsByName;
    }

    @Override
    public List<Event> findEventsByOrganizer(String organizer) {
        List<Event> eventsByOrganizer = new ArrayList<>();
        for (Event event : events) {
            if (event.getOrganizer().equals(organizer)) {
                eventsByOrganizer.add(event);
            }
        }
        return eventsByOrganizer;
    }

    @Override
    public List<Event> findEventsByLocation(String location) {
        List<Event> eventsByLocation = new ArrayList<>();
        for (Event event : events) {
            if (event.getLocation().equals(location)) {
                eventsByLocation.add(event);
            }
        }
        return eventsByLocation;
    }

    @Override
    public List<Event> findEventsByDateTime(LocalDateTime dateTime) {
        List<Event> eventsByDateTime = new ArrayList<>();
        for (Event event : events) {
            if (event.getDateTime().equals(dateTime)) {
                eventsByDateTime.add(event);
            }
        }
        return eventsByDateTime;
    }

    @Override
    public boolean addEvent(Event event) {
        return events.add(event);
    }

    @Override
    public boolean updateEvent(Event event) {
        return events.set(events.indexOf(findEventByBookIdNameOrganizerDateTimeAndLocation(event.getBookId(), event.getName(), event.getOrganizer(), event.getDateTime(), event.getLocation())), event) != null;
    }

    @Override
    public boolean deleteEvent(Event event) {
        return events.remove(event);
    }
}
