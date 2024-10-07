package com.bookclub.controller;

import com.bookclub.dao.RSVPDAO;
import com.bookclub.dao.UserDAO;
import com.bookclub.model.Event;
import com.bookclub.model.RSVP;
import com.bookclub.service.LoginService;
import com.bookclub.service.EventService;
import com.bookclub.service.RSVPService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class RSVPController {
    @FXML
    private Label promptLabel;
    @FXML
    private ComboBox<RSVP.RSVPStatus> rsvpStatusComboBox;
    @FXML
    private Button rsvpButton;
    @FXML
    private ListView<String> attendeesList;

    @FXML
    public void initialize() {
        RSVPService.initialize(new RSVPDAO(), new UserDAO());
        rsvpStatusComboBox.getItems().addAll(RSVP.RSVPStatus.values());

        // Displays correct RSVP info for the selected event
        Event currentEvent = EventService.getInstance().getSelectedEvent();
        if (currentEvent != null) {
            displayRSVP(EventService.getInstance().getSelectedEvent());
        }
        else {
            promptLabel.setText("RSVP");
            toggleRSVPVisibility(false);
            attendeesList.setVisible(false);
        }

        // Listener for when the selected event changes
        EventService.getInstance().selectedEventProperty().addListener((observable, oldEvent, newEvent) -> {
            if (newEvent != null) {
                displayRSVP(newEvent);
            }
        });
    }

    @FXML
    public void handleRSVPSelect() {
        RSVP.RSVPStatus status = rsvpStatusComboBox.getValue();
        RSVPService.getInstance().saveRSVP(EventService.getInstance().getSelectedEvent(), LoginService.getCurrentUser(), status);
        updateAttendees(EventService.getInstance().getSelectedEvent());
        attendeesList.setVisible(true);
    }

    private void toggleRSVPVisibility(boolean visible) {
        promptLabel.setVisible(visible);
        rsvpStatusComboBox.setVisible(visible);
        rsvpButton.setVisible(visible);
    }

    private void updateAttendees(Event event) {
        attendeesList.getItems().clear();
        attendeesList.getItems().addAll(RSVPService.getInstance().getEventRSVPUsernames(event));
    }

    private void displayRSVP(Event event) {
        promptLabel.setText("RSVP to " + event.getName());
        RSVP rsvp = RSVPService.getInstance().getRSVP(event, LoginService.getCurrentUser());
        rsvpStatusComboBox.setValue(rsvp == null ? RSVP.RSVPStatus.PENDING : rsvp.getStatus());
        boolean isUserOrganizer = event.getOrganizer().equals(LoginService.getCurrentUser().getUsername());
        toggleRSVPVisibility(!isUserOrganizer);
        updateAttendees(event);
        attendeesList.setVisible(isUserOrganizer || rsvp != null);
    }
}
