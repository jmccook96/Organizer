package com.bookclub.controller;

import com.bookclub.dao.EventDAO;
import com.bookclub.iao.IEventAO;
import com.bookclub.model.Event;
import com.bookclub.service.LoginService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

public class EventsController {
    private IEventAO eventAO;
    @FXML
    private ListView<Event> eventList;
    @FXML
    private TextField eventNameField;
    @FXML
    private DatePicker datePicker;
    @FXML
    private Spinner<Integer> hourSpinner;
    @FXML
    private Spinner<Integer> minuteSpinner;
    @FXML
    private TextField eventLocationField;

    public EventsController() {
        eventAO = new EventDAO();
    }

    private void selectEvent(Event event) {
        eventList.getSelectionModel().select(event);
        eventNameField.setText(event.getName());
        datePicker.setValue(event.getDateTime().toLocalDate());
        hourSpinner.getValueFactory().setValue(event.getDateTime().getHour());
        minuteSpinner.getValueFactory().setValue(event.getDateTime().getMinute());
        eventLocationField.setText(event.getLocation());
    }

    private ListCell<Event> renderCell(ListView<Event> eventList) {
        return new ListCell<>() {
            private void onEventSelected(MouseEvent mouseEvent) {
                ListCell<Event> clickedCell = (ListCell<Event>) mouseEvent.getSource();
                Event selectedEvent = clickedCell.getItem();
                if (selectedEvent != null) selectEvent(selectedEvent);
            }

            @Override
            protected void updateItem(Event event, boolean empty) {
                super.updateItem(event, empty);
                // If the cell is empty, set the text to null, otherwise set it to the event's name
                if (empty || event == null || event.getName() == null) {
                    setText(null);
                    super.setOnMouseClicked(this::onEventSelected);
                }
                else {
                    setText(event.getName());
                }
            }
        };
    }

    private void updateEvents() {
        eventList.getItems().clear();
        List<Event> events = eventAO.findAllEvents();
        if (!events.isEmpty()) {
            events.sort(Comparator.comparing(Event::getDateTime)); // Display earliest event first
            eventList.getItems().addAll(events);
        }
    }

    @FXML
    public void initialize() {
        hourSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 0)); // Default to 0 hours
        minuteSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0)); // Default to 0 minutes
        eventList.setCellFactory(this::renderCell);
        updateEvents();
        eventList.getSelectionModel().selectFirst();
        Event firstEvent = eventList.getSelectionModel().getSelectedItem();
        if (firstEvent != null) {
            selectEvent(firstEvent);
        }
        eventLocationField.textProperty().addListener((observable, oldValue, newValue) -> updateEvents());
    }

    @FXML
    private void handleConfirm() {
        Event selectedEvent = eventList.getSelectionModel().getSelectedItem();
        if (selectedEvent != null) {
            selectedEvent.setName(eventNameField.getText());
            selectedEvent.setOrganizer(LoginService.getCurrentUser().getUsername());
            LocalDate selectedDate = datePicker.getValue();
            int selectedHour = hourSpinner.getValue();
            int selectedMinute = minuteSpinner.getValue();
            selectedEvent.setDateTime(LocalDateTime.of(selectedDate, selectedDate.atTime(selectedHour, selectedMinute).toLocalTime()));
            selectedEvent.setLocation(eventLocationField.getText());
            eventAO.updateEvent(selectedEvent);
            updateEvents();
        }
    }

    @FXML
    private void handleDelete() {
        Event selectedEvent = eventList.getSelectionModel().getSelectedItem();
        if (selectedEvent != null) {
            eventAO.deleteEvent(selectedEvent);
            updateEvents();
        }
    }

    @FXML
    private void handleAdd() {
        Event newEvent = new Event("New Event", LoginService.getCurrentUser().getUsername(), LocalDateTime.now(), "New Location");
        eventAO.addEvent(newEvent);
        updateEvents();
        selectEvent(newEvent);
        eventNameField.requestFocus();
    }

    @FXML
    private void handleCancel() {
        Event selectedEvent = eventList.getSelectionModel().getSelectedItem();
        if (selectedEvent != null) {
            selectEvent(selectedEvent);
        }
    }
}
