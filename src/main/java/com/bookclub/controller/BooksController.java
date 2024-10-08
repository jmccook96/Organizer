package com.bookclub.controller;

import com.bookclub.dao.BookDAO;
import com.bookclub.iao.IBookAO;
import com.bookclub.model.Book;
import com.bookclub.service.BookService;
import com.bookclub.service.BookService.ReviewData;
import com.bookclub.util.StageFactory;
import com.bookclub.util.StageView;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.Collections;
import java.util.List;

public class BooksController {

    private IBookAO bookAO;
    private BookService bookService;

    @FXML
    private VBox booksContainer;
    @FXML
    private ListView<Book> booksList;
    @FXML
    private TextField titleField;
    @FXML
    private TextField authorField;
    @FXML
    private HBox navBar;
    @FXML
    private TextField genreField;

    public BooksController() {
        bookAO = new BookDAO();
        bookService = BookService.getInstance();
    }

    @FXML
    public void initialize() {
        // Set nav bar button color
        Button booksButton = (Button) navBar.lookup("#booksButton");
        if (booksButton != null) {
            booksButton.setStyle("-fx-background-color: lightsteelblue");
        }
        booksContainer.maxWidthProperty().bind(StageFactory.getInstance().getPrimaryStage().widthProperty().multiply(0.5));
        updateBooks();
        booksList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        // Handle book selection
        booksList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                Book selectedBook = newValue; // No need to cast to HBox
                bookService.setSelectedBook(selectedBook);
                StageFactory.getInstance().switchScene(StageView.REVIEWS);
            }
        });

        // Set custom cell factory for booksList
        booksList.setCellFactory(param -> new ListCell<Book>() {
            @Override
            protected void updateItem(Book book, boolean empty) {
                super.updateItem(book, empty);
                if (empty || book == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    HBox hbox = new HBox(10);
                    Label bookLabel = new Label(book.toString());

                    // Use the new ReviewData class to get both the average rating and number of ratings
                    ReviewData reviewData = bookService.getReviewData(book);
                    Label ratingLabel = new Label(String.format("%.1f ★ (%d)", reviewData.getAverageRating(), reviewData.getNumberOfRatings()));

                    hbox.getChildren().addAll(bookLabel, ratingLabel);
                    setGraphic(hbox);
                }
            }
        });
    }

    @FXML
    private void handleAddBook() {
        String title = titleField.getText();
        String author = authorField.getText();
//        String genre = genreField.getText();
        if (!title.isEmpty() && !author.isEmpty()) {
            Book book = new Book(title, author);
            bookAO.addBook(book);
            updateBooks();
            titleField.clear();
            authorField.clear();
            System.out.println("Book added successfully.");
        } else {
            showAlert("Failed", "Book must have a title and author.");
        }
    }

    private void updateBooks() {
        booksList.getItems().clear();
        List<Book> books = bookAO.findAllBooks();
        Collections.reverse(books);
        booksList.getItems().addAll(books);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
