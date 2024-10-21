package com.bookclub.controller;

import com.bookclub.dao.BookProgressDAO;
import com.bookclub.service.BookProgressService;
import com.bookclub.service.BookService;
import com.bookclub.service.LoginService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

public class BookProgressController {
    @FXML
    private Spinner<Integer> pageNumberInput;
    @FXML
    private Button startFinishButton;
    @FXML
    private HBox progressHBox;
    @FXML
    private ListView<Integer> progressListView;

    @FXML
    public void initialize() {
        BookProgressService.initialize(new BookProgressDAO());
        // TODO: Update max value to book total pages when it gets implemented
        pageNumberInput.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1000, 0)); // Min: 0, Max: 1000, Initial: 0
        updateUI();
    }

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

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void updateUI() {
        boolean isStarted = BookProgressService.getInstance().hasBookProgress(BookService.getInstance().getSelectedBook(), LoginService.getCurrentUser());
        pageNumberInput.getValueFactory().setValue(isStarted ? BookProgressService.getInstance().getBookProgress(BookService.getInstance().getSelectedBook(), LoginService.getCurrentUser()).getPageNumber() : 0);
        startFinishButton.setText(isStarted ? "Finish" : "Start");
        progressHBox.setVisible(isStarted);
        updateProgressList();
        progressListView.setVisible(isStarted);
    }

    private void updateProgressList() {
        progressListView.getItems().clear();
        progressListView.getItems().addAll(BookProgressService.getInstance().getBookProgressListForBook(BookService.getInstance().getSelectedBook()).stream().map(progress -> progress.getPageNumber()).toList());
    }
}
