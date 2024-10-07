package com.bookclub.service;

import com.bookclub.model.Event;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class EventService {
    private static EventService instance;
    private ObjectProperty<Event> selectedEvent;

    private EventService() {
        selectedEvent = new SimpleObjectProperty<>();
    }

    public static EventService getInstance() {
        if (instance == null) {
            instance = new EventService();
        }
        return instance;
    }

    public ObjectProperty<Event> selectedEventProperty() {
        return selectedEvent;
    }

    public Event getSelectedEvent() {
        return selectedEvent.get();
    }

    public void setSelectedEvent(Event event) {
        selectedEvent.set(event);
    }
}
