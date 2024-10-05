package com.bookclub.controller;

import com.bookclub.dao.BookDAO;
import com.bookclub.iao.IBookAO;
import com.bookclub.model.Book;
import com.bookclub.util.StageFactory;
import com.bookclub.util.StageView;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.util.List;

public class BooksController {

    private IBookAO bookAO;
    private static Book selectedBook;

    @FXML
    private VBox booksContainer;
    @FXML
    private ListView<Book> bookListView;
    @FXML
    private TextField titleField;
    @FXML
    private TextField authorField;
    @FXML
    private HBox navBar;

    public BooksController() {
        bookAO = new BookDAO();
    }

    @FXML
    public void initialize() {
        // Set nav bar button colour
        Button booksButton = (Button)navBar.lookup("#booksButton");
        if (booksButton != null) {
            booksButton.setStyle("-fx-background-color: lightsteelblue");
        }

        booksContainer.maxWidthProperty().bind(StageFactory.getInstance().getPrimaryStage().widthProperty().multiply(0.5));

        bookListView.setCellFactory(param -> new ListCell<Book>() {
            @Override
            protected void updateItem(Book book, boolean empty) {
                super.updateItem(book, empty);
                if (empty || book == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    VBox container = new VBox(5);
                    container.setStyle("-fx-background-color: white; -fx-padding: 10; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 5, 0, 0, 0);");

                    Text title = new Text(book.getTitle());
                    title.setStyle("-fx-font-weight: bold; -fx-font-size: 16;");

                    Text author = new Text("by " + book.getAuthor());
                    author.setStyle("-fx-font-style: italic;");

                    Text rating = new Text(String.format("Average Rating: %.1f/5.0", book.getAverageRating()));

                    container.getChildren().addAll(title, author, rating);
                    setGraphic(container);
                }
            }
        });

        bookListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                setSelectedBook(newValue);
                StageFactory.getInstance().switchScene(StageView.REVIEWS);
            }
        });

        updateBooks();
    }

    @FXML
    private void handleAddBook() {
        String title = titleField.getText();
        String author = authorField.getText();
        if (!title.isEmpty() && !author.isEmpty()) {
            Book book = new Book(title, author);
            bookAO.addBook(book);
            updateBooks();
            titleField.clear();
            authorField.clear();
            System.out.println("Book added successfully.");
        }
        else {
            showAlert("Failed", "Book must have a title and author.");
        }
    }

    public static Book getSelectedBook() {
        return selectedBook;
    }

    public static void setSelectedBook(Book book) {
        selectedBook = book;
    }

    private void updateBooks() {
        bookListView.getItems().clear(); // Clear current books
        List<Book> books = bookAO.findAllBooks();
        bookListView.getItems().addAll(books);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}