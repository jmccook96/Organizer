package com.bookclub.controller;

import com.bookclub.iao.IBookAO;
import com.bookclub.mao.BookMAO;
import com.bookclub.model.Book;
import com.bookclub.util.StageFactory;
import com.bookclub.util.StageView;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;

public class BooksController {

    private IBookAO bookAO;
    private static Book selectedBook;
    @FXML
    private ListView<Book> booksList;

    public BooksController() {
        // TODO: Swap to BookDAO
        bookAO = new BookMAO();
    }

    @FXML
    public void initialize() {
        booksList.getItems().addAll(bookAO.findAllBooks());
        booksList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        // Handle book selection
        booksList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                setSelectedBook(newValue);
                StageFactory.getInstance().switchScene(StageView.REVIEWS);
            }
        });
    }

    public static Book getSelectedBook() {
        return selectedBook;
    }

    public static void setSelectedBook(Book book) {
        selectedBook = book;
    }

}
