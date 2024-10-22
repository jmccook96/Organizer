package com.bookclub.controller;

import com.bookclub.dao.BookProgressDAO;
import com.bookclub.dao.UserDAO;
import com.bookclub.model.Book;
import com.bookclub.model.BookProgress;
import com.bookclub.model.User;
import com.bookclub.service.BookProgressService;
import com.bookclub.service.BookService;
import com.bookclub.service.LoginService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

import java.util.List;

/**
 * The {@code BookProgressController} class handles the user interactions for managing book progress.
 * It allows users to start, finish, and update their progress for a selected book.
 * The class interacts with the {@link BookProgressService} to update progress data and the UI.
 */
public class BookProgressController {

    @FXML
    private Spinner<Integer> ChapterInput;
    @FXML
    private Button startFinishButton;
    @FXML
    private HBox progressHBox;
    @FXML
    private Label currentChapterLabel;
    @FXML
    private Label totalChaptersLabel;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private ListView<String> progressListView;

    /**
     * Initializes the controller after the view has been loaded.
     * It sets up the book progress spinner and updates the UI based on the selected book and user's progress.
     */
    @FXML
    public void initialize() {
        BookProgressService.initialize(new BookProgressDAO(), new UserDAO());

        Book selectedBook = BookService.getInstance().getSelectedBook();
        int chapterNumber = selectedBook != null ? selectedBook.getTotalChapters() : 0;

        ChapterInput.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, chapterNumber > 0 ? chapterNumber : 1000, 0));

        updateUI();
    }

    /**
     * Handles the event when the "Start" or "Finish" button is clicked.
     * It starts or finishes the user's progress for the selected book based on the current state.
     */
    @FXML
    private void handleStartFinishButton() {
        Book selectedBook = BookService.getInstance().getSelectedBook();
        User currentUser = LoginService.getCurrentUser();

        if (BookProgressService.getInstance().hasBookProgress(selectedBook, currentUser)) {
            BookProgressService.getInstance().finishBookProgress(selectedBook, currentUser);
        } else {
            BookProgressService.getInstance().startBookProgress(selectedBook, currentUser);
        }
        updateUI();
    }

    /**
     * Handles the event when the "Save Progress" button is clicked.
     * It saves the user's progress for the selected book based on the input chapter number.
     * If the input chapter number is invalid, an alert is shown.
     */
    @FXML
    private void handleSaveProgress() {
        Book selectedBook = BookService.getInstance().getSelectedBook();
        User currentUser = LoginService.getCurrentUser();
        int chapterNumber = ChapterInput.getValue();

        if (!BookProgressService.getInstance().saveBookProgress(selectedBook, currentUser, chapterNumber)) {
            showAlert("Failed", "Invalid chapter number was entered.");
        } else {
            updateProgressList();
        }
    }

    /**
     * Displays an alert with a specified title and message.
     *
     * @param title   the title of the alert.
     * @param message the content message of the alert.
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Updates the UI elements based on the selected book and user's current progress.
     * It updates the spinner, progress bar, labels, and the list of other users' progress.
     */
    private void updateUI() {
        Book selectedBook = BookService.getInstance().getSelectedBook();
        User currentUser = LoginService.getCurrentUser();

        boolean isStarted = BookProgressService.getInstance().hasBookProgress(selectedBook, currentUser);
        BookProgress bookProgress = BookProgressService.getInstance().getBookProgress(selectedBook, currentUser).orElse(null);

        int currentChapter = bookProgress != null ? bookProgress.getChapterNumber() : 0;

        ChapterInput.getValueFactory().setValue(currentChapter);
        startFinishButton.setText(isStarted ? "Finish" : "Start");
        progressHBox.setVisible(isStarted);

        updateProgressList();
        progressListView.setVisible(isStarted);

        // Update labels and progress bar
        int totalChapters = selectedBook != null ? selectedBook.getTotalChapters() : 0;
        currentChapterLabel.setText(String.valueOf(currentChapter));
        totalChaptersLabel.setText(String.valueOf(totalChapters));

        double progress = totalChapters > 0 ? (double) currentChapter / totalChapters : 0;
        progressBar.setProgress(progress);
    }

    /**
     * Updates the progress list that shows the progress of other users for the selected book.
     * It retrieves the formatted progress data from the {@link BookProgressService}.
     */
    private void updateProgressList() {
        progressListView.getItems().clear();

        List<String> formattedProgressList = BookProgressService.getInstance()
                .getFormattedProgressForBook(BookService.getInstance().getSelectedBook());

        if (formattedProgressList != null && !formattedProgressList.isEmpty()) {
            progressListView.getItems().addAll(formattedProgressList);
        } else {
            progressListView.getItems().add("No progress available.");
        }
    }
}
