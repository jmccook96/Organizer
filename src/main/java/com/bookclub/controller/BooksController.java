package com.bookclub.controller;

import com.bookclub.iao.IBookAO;
import com.bookclub.mao.BookMAO;
import com.bookclub.model.Book;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;

public class BooksController {

    private IBookAO bookAO;
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
    }

}
