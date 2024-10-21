package com.bookclub.controller;

import com.bookclub.dao.RSVPDAO;
import com.bookclub.dao.UserDAO;
import com.bookclub.model.Event;
import com.bookclub.model.RSVP;
import com.bookclub.service.EventService;
import com.bookclub.service.LoginService;
import com.bookclub.service.RSVPService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

/**
 * The RSVPController class manages the RSVP functionality in the JavaFX application.
 * It interacts with the RSVP service to allow users to respond to events and view
 * the list of attendees.
 */
public class RSVPController {
    @FXML
    private Label promptLabel;
    @FXML
    private ComboBox<RSVP.RSVPStatus> rsvpStatusComboBox;
    @FXML
    private Button rsvpButton;
    @FXML
    private ListView<String> attendeesList;

    /**
     * Initializes the RSVPController.
     * This method is called automatically after the FXML fields have been injected.
     * It sets up the RSVP status options, displays the current RSVP information for
     * the selected event, and sets up a listener for changes in the selected event.
     */
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

    /**
     * Handles the selection of an RSVP status and saves it for the current user.
     * It updates the list of attendees to reflect the current RSVP status.
     */
    @FXML
    public void handleRSVPSelect() {
        RSVP.RSVPStatus status = rsvpStatusComboBox.getValue();
        RSVPService.getInstance().saveRSVP(EventService.getInstance().getSelectedEvent(), LoginService.getCurrentUser(), status);
        updateAttendees(EventService.getInstance().getSelectedEvent());
        attendeesList.setVisible(true);
    }

    /**
     * Toggles the visibility of the RSVP components based on the given parameter.
     *
     * @param visible If true, the RSVP components are shown; otherwise, they are hidden.
     */
    private void toggleRSVPVisibility(boolean visible) {
        promptLabel.setVisible(visible);
        rsvpStatusComboBox.setVisible(visible);
        rsvpButton.setVisible(visible);
    }

    /**
     * Updates the attendees list based on the current event.
     *
     * @param event The event for which to update the attendees list.
     */
    private void updateAttendees(Event event) {
        attendeesList.getItems().clear();
        attendeesList.getItems().addAll(RSVPService.getInstance().getEventRSVPUsernames(event));
    }

    /**
     * Displays the RSVP information for the given event.
     *
     * @param event The event for which to display RSVP information.
     */
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
