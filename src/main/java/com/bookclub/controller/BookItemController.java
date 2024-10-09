package com.bookclub.controller;

import com.bookclub.service.BookService;
import com.bookclub.util.StageFactory;
import com.bookclub.util.StageView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class BookItemController {
    private BookService bookService;
    @FXML
    private Button backButton;
    @FXML
    private ImageView backIcon;
    @FXML
    private Label bookLabel;

    @FXML
    public void initialize() {
        bookService = BookService.getInstance();
        backButton.prefHeightProperty().bind(StageFactory.getInstance().getPrimaryStage().heightProperty());
        backIcon.fitWidthProperty().bind(StageFactory.getInstance().getPrimaryStage().widthProperty().multiply(0.1));
        bookLabel.setText(bookService.getSelectedBook() == null ? "Select a book" : bookService.getSelectedBook().toString());
    }

    @FXML
    private void handleBackButton() {
        // Switch back to the book list view
        StageFactory.getInstance().switchScene(StageView.BOOKS);
    }
}
