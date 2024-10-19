package com.bookclub.controller;

import com.bookclub.dao.BookProgressDAO;
import com.bookclub.model.BookProgress;
import com.bookclub.service.BookProgressService;
import com.bookclub.service.BookService;
import com.bookclub.service.LoginService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

/**
 * The {@code BookProgressController} class handles the user interface for managing a user's progress in a book.
 * It allows users to start, save, or finish tracking their book progress and provides visual feedback for current progress.
 */
public class BookProgressController {
    @FXML
    private Spinner<Integer> pageNumberInput;
    @FXML
    private Button startFinishButton;
    @FXML
    private HBox progressHBox;
    @FXML
    private ListView<Integer> progressListView;

    /**
     * Initializes the controller and sets up the UI components.
     * Sets a range for the page number input and populates UI based on current book progress.
     */
    @FXML
    public void initialize() {
        BookProgressService.initialize(new BookProgressDAO());
        // TODO: Update max value to book total pages when it gets implemented
        pageNumberInput.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1000, 0)); // Min: 0, Max: 1000, Initial: 0
        updateUI();
    }

    /**
     * Handles the "Start" or "Finish" button click.
     * Starts tracking the progress for the selected book if not already started.
     * If progress exists, marks the book as finished.
     */
    @FXML
    private void handleStartFinishButton() {
        if (BookProgressService.getInstance().hasBookProgress(BookService.getInstance().getSelectedBook(), LoginService.getCurrentUser())) {
            BookProgressService.getInstance().finishBookProgress(BookService.getInstance().getSelectedBook(), LoginService.getCurrentUser());
        }
        else {
            BookProgressService.getInstance().startBookProgress(BookService.getInstance().getSelectedBook(), LoginService.getCurrentUser());
        }
        updateUI();
    }

    /**
     * Handles the save progress action.
     * Saves the current page number entered by the user. If the entered page number is invalid, it shows an alert.
     */
    @FXML
    private void handleSaveProgress() {
        int pageNumber = pageNumberInput.getValue();
        boolean isProgressSaved = BookProgressService.getInstance().saveBookProgress(BookService.getInstance().getSelectedBook(), LoginService.getCurrentUser(), pageNumber);
        if (!isProgressSaved) {
            showAlert("Failed", "Invalid page number was entered.");
        }
        else {
            updateProgressList();
        }
    }

    /**
     * Displays an alert dialog to the user.
     *
     * @param title the title of the alert
     * @param message the message content for the alert
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Updates the UI components based on whether progress is being tracked for the current book.
     * Adjusts the visibility of input fields and buttons.
     */
    private void updateUI() {
        boolean isStarted = BookProgressService.getInstance().hasBookProgress(BookService.getInstance().getSelectedBook(), LoginService.getCurrentUser());
        pageNumberInput.getValueFactory().setValue(isStarted ? BookProgressService.getInstance().getBookProgress(BookService.getInstance().getSelectedBook(), LoginService.getCurrentUser()).getPageNumber() : 0);
        startFinishButton.setText(isStarted ? "Finish" : "Start");
        progressHBox.setVisible(isStarted);
        updateProgressList();
        progressListView.setVisible(isStarted);
    }

    /**
     * Updates the progress list view by populating it with page numbers recorded for the selected book.
     */
    private void updateProgressList() {
        progressListView.getItems().clear();
        progressListView.getItems().addAll(BookProgressService.getInstance().getBookProgressListForBook(BookService.getInstance().getSelectedBook()).stream().map(progress -> progress.getPageNumber()).toList());
    }
}
