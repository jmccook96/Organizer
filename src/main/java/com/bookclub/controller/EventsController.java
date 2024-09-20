package com.bookclub.controller;

import com.bookclub.dao.BookDAO;
import com.bookclub.dao.EventDAO;
import com.bookclub.iao.IEventAO;
import com.bookclub.model.Book;
import com.bookclub.model.Event;
import com.bookclub.service.LoginService;
import com.bookclub.util.StageFactory;
import com.bookclub.util.StageView;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.util.StringConverter;

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


    public EventsController() {
        eventAO = new EventDAO();
    }

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
        if (books.isEmpty()) {
            eventLabel.setText("Add a book to start creating events");
            eventLabel.setStyle("-fx-text-fill: red; -fx-cursor: hand; -fx-underline: true");
            eventLabel.setOnMouseClicked(event -> {
                StageFactory.getInstance().switchScene(StageView.BOOKS);
            });
            // Disable all fields if no books have been added
            bookComboBox.setDisable(true);
            eventNameField.setDisable(true);
            datePicker.setDisable(true);
            hourSpinner.setDisable(true);
            minuteSpinner.setDisable(true);
            eventLocationField.setDisable(true);
            addButton.setDisable(true);
            confirmButton.setDisable(true);
            cancelButton.setDisable(true);
            deleteButton.setDisable(true);
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
        Event newEvent = new Event(bookComboBox.getItems().get(0).getId(), "New Event", LoginService.getCurrentUser().getUsername(), LocalDateTime.now(), "New Location");
        eventAO.addEvent(newEvent);
        updateEvents();
        selectEvent(newEvent);
        bookComboBox.requestFocus();
    }

    @FXML
    private void handleCancel() {
        Event selectedEvent = eventList.getSelectionModel().getSelectedItem();
        if (selectedEvent != null) {
            selectEvent(selectedEvent);
        }
    }

    private Book getBookById(int bookId) {
        for (Book book : bookComboBox.getItems()) {
            if (book.getId() == bookId) {
                return book;
            }
        }
        return null;
    }
}
