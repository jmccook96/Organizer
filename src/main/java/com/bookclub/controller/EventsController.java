package com.bookclub.controller;

import com.bookclub.dao.BookDAO;
import com.bookclub.dao.EventDAO;
import com.bookclub.iao.IEventAO;
import com.bookclub.model.Book;
import com.bookclub.model.Event;
import com.bookclub.service.EventService;
import com.bookclub.service.LoginService;
import com.bookclub.util.StageFactory;
import com.bookclub.util.StageView;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

/**
 * Controller class responsible for managing the events in the Book Club application.
 * It handles user interactions for adding, updating, and deleting events,
 * and binds these interactions to the data model.
 */
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
    @FXML
    private HBox navBar;
    @FXML
    private ComboBox<Book> bookComboBox;
    @FXML
    private Label eventLabel;
    @FXML
    private Button addButton;
    @FXML
    private Button confirmButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Button deleteButton;
    @FXML
    private VBox rsvp;

    /**
     * Constructor initializes the Event Data Access Object (DAO).
     */
    public EventsController() {
        eventAO = new EventDAO();
    }

    /**
     * Populates the form fields with the selected event's data and enables or disables fields 
     * based on the organizer of the event.
     *
     * @param event The selected event.
     */
    private void selectEvent(Event event) {
        eventList.getSelectionModel().select(event);
        Book book = getBookById(event.getBookId());
        if (book != null) {
            bookComboBox.setValue(book);
        }
        eventNameField.setText(event.getName());
        datePicker.setValue(event.getDateTime().toLocalDate());
        hourSpinner.getValueFactory().setValue(event.getDateTime().getHour());
        minuteSpinner.getValueFactory().setValue(event.getDateTime().getMinute());
        eventLocationField.setText(event.getLocation());
        toggleFieldsVisibility(true);
        // TODO: Migrate to using ids
        toggleFieldsDisable(!event.getOrganizer().equals(LoginService.getCurrentUser().getUsername()));
    }

    /**
     * Renders each event in the ListView as a selectable cell, and sets up the click event 
     * to populate the form fields when an event is selected.
     *
     * @param eventList The ListView displaying the events.
     * @return A ListCell containing the event name.
     */
    private ListCell<Event> renderCell(ListView<Event> eventList) {
        return new ListCell<>() {
            private void onEventSelected(MouseEvent mouseEvent) {
                ListCell<Event> clickedCell = (ListCell<Event>) mouseEvent.getSource();
                Event selectedEvent = clickedCell.getItem();
                if (selectedEvent != null) selectEvent(selectedEvent);
                EventService.getInstance().setSelectedEvent(selectedEvent);
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

    /**
     * Updates the ListView with the latest events from the database, sorting them 
     * by the event date (earliest event first).
     */
    private void updateEvents() {
        eventList.getItems().clear();
        List<Event> events = eventAO.findAllEvents();
        if (!events.isEmpty()) {
            events.sort(Comparator.comparing(Event::getDateTime)); // Display earliest event first
            eventList.getItems().addAll(events);
        }
    }

    /**
     * Initializes the UI components of the Events view. Sets up the default values 
     * for the time spinners and loads the list of books into the ComboBox. 
     * Also checks if books exist, and disables event creation if none are currently available.
     */
    @FXML
    public void initialize() {
        // Set nav bar button colour
        Button booksButton = (Button)navBar.lookup("#eventsButton");
        if (booksButton != null) {
            booksButton.setStyle("-fx-background-color: lightsteelblue");
        }
        hourSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 0)); // Default to 0 hours
        minuteSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0)); // Default to 0 minutes
        eventList.setCellFactory(this::renderCell);
        updateEvents();
        BookDAO bookAO = new BookDAO();
        List<Book> books = bookAO.findAllBooks();
        toggleFieldsVisibility(false);
        if (books.isEmpty()) {
            eventLabel.setText("Add a book to start creating events");
            eventLabel.setStyle("-fx-text-fill: red; -fx-cursor: hand; -fx-underline: true");
            eventLabel.setOnMouseClicked(event -> {
                StageFactory.getInstance().switchScene(StageView.BOOKS);
            });
            addButton.setDisable(true);
        }
        else {
            bookComboBox.getItems().addAll(books);
            bookComboBox.setConverter(new StringConverter<>() {
                @Override
                public String toString(Book book) {
                    return book == null ? null : book.toString();
                }

                @Override
                public Book fromString(String string) {
                    for (Book book : books) {
                        if (book.toString().equals(string)) {
                            return book;
                        }
                    }
                    return null;
                }
            });
            eventLabel.setText("Select a book event to view or edit");
        }
    }

    /**
     * Handles the confirmation of event editing. Updates the selected event's details 
     *  and persists the changes to the database.
     */
    @FXML
    private void handleConfirm() {
        Event selectedEvent = eventList.getSelectionModel().getSelectedItem();
        if (selectedEvent != null) {
            selectedEvent.setBookId(bookComboBox.getValue().getId());
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

    /**
     * Handles the deletion of the selected event, removing it from the database and 
     * updating the ListView to reflect the change.
     */
    @FXML
    private void handleDelete() {
        Event selectedEvent = eventList.getSelectionModel().getSelectedItem();
        if (selectedEvent != null) {
            eventAO.deleteEvent(selectedEvent);
            updateEvents();
        }
    }

    /**
     * Handles the creation of a new default event. Adds the new event to the database and selects it in the UI 
     * for further editing.
     */
    @FXML
    private void handleAdd() {
        Event newEvent = new Event(bookComboBox.getItems().get(0).getId(), "New Event", LoginService.getCurrentUser().getUsername(), LocalDateTime.now(), "New Location");
        eventAO.addEvent(newEvent);
        updateEvents();
        selectEvent(newEvent);
        bookComboBox.requestFocus();
    }

    /**
     * Cancels any unsaved changes made to the selected event, restoring the form fields to the event's
     * previously saved state.
     */
    @FXML
    private void handleCancel() {
        Event selectedEvent = eventList.getSelectionModel().getSelectedItem();
        if (selectedEvent != null) {
            selectEvent(selectedEvent);
        }
    }

    /**
     * Retrieves a book from the ComboBox by its ID.
     *
     * @param bookId The ID of the book to retrieve.
     * @return The matching Book object, or null if not found.
     */
    private Book getBookById(int bookId) {
        for (Book book : bookComboBox.getItems()) {
            if (book.getId() == bookId) {
                return book;
            }
        }
        return null;
    }

    /**
     * Enables or disables form fields.
     *
     * @param disable True to disable the fields, false to enable them.
     */
    private void toggleFieldsDisable(boolean disable) {
        bookComboBox.setDisable(disable);
        eventNameField.setDisable(disable);
        datePicker.setDisable(disable);
        hourSpinner.setDisable(disable);
        minuteSpinner.setDisable(disable);
        eventLocationField.setDisable(disable);
        confirmButton.setDisable(disable);
        cancelButton.setDisable(disable);
        deleteButton.setDisable(disable);
    }

    /**
     * Toggles visibility of form fields.
     *
     * @param visible True to enable field visibility, false to disable them.
     */
    private void toggleFieldsVisibility(boolean visible) {
        bookComboBox.setVisible(visible);
        eventNameField.setVisible(visible);
        datePicker.setVisible(visible);
        hourSpinner.setVisible(visible);
        minuteSpinner.setVisible(visible);
        eventLocationField.setVisible(visible);
        confirmButton.setVisible(visible);
        cancelButton.setVisible(visible);
        deleteButton.setVisible(visible);
        rsvp.setVisible(visible);
    }
}
