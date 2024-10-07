package com.bookclub.service;

import com.bookclub.dao.BookDAO;
import com.bookclub.iao.IBookAO;
import com.bookclub.model.Book;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class BookService {
    private static BookService instance;
    private IBookAO bookAO;
    private ObjectProperty<Book> selectedBook;

    private BookService() {
        bookAO = new BookDAO();
        selectedBook = new SimpleObjectProperty<>();
    }

    public static BookService getInstance() {
        if (instance == null) {
            instance = new BookService();
        }
        return instance;
    }

    public ObjectProperty<Book> selectedBookProperty() {
        return selectedBook;
    }

    public Book getSelectedBook() {
        return selectedBook.get();
    }

    public void setSelectedBook(Book book) {
        selectedBook.set(book);
    }

}
