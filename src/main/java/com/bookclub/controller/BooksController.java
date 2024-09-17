package com.bookclub.controller;

import com.bookclub.dao.BookDAO;
import com.bookclub.iao.IBookAO;
import com.bookclub.model.Book;
import com.bookclub.model.Review;
import com.bookclub.service.LoginService;
import com.bookclub.util.StageFactory;
import com.bookclub.util.StageView;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import org.controlsfx.control.Rating;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BooksController {

    private IBookAO bookAO;
    private static Book selectedBook;
    @FXML
    private ListView<Book> booksList;
    @FXML
    private TextField titleField;
    @FXML
    private TextField authorField;

    public BooksController() {
        bookAO = new BookDAO();
    }

    @FXML
    public void initialize() {
        updateBooks();
        booksList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        // Handle book selection
        booksList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                setSelectedBook(newValue);
                StageFactory.getInstance().switchScene(StageView.REVIEWS);
            }
        });
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
        booksList.getItems().clear(); // Clear current books
        List<Book> books = bookAO.findAllBooks();
        Collections.reverse(books); // Put latest entry on top
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
