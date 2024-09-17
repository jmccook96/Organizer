package com.bookclub.dao;

import com.bookclub.iao.IBookAO;
import com.bookclub.model.Book;
import com.bookclub.util.DatabaseManager;
import java.util.List;

public class BookDAO implements IBookAO {

    private DatabaseManager dbManager;

    public BookDAO() {
        dbManager = DatabaseManager.getInstance();
    }

    public List<Book> findAllBooks() {
        // TODO: Add query
        return null;
    }

    public Book findBookByTitleAndAuthor(String title, String author) {
        // TODO: Add query
        return null;
    }

    public List<Book> findBooksByBookID(Integer bookid){
        // TODO: Add query
        return null;
    }

    public List<Book> findBooksByGenre(String genre){
        // TODO: Add query
        return null;
    }

    public List<Book> findBooksByTitle(String title) {
        // TODO: Add query
        return null;
    }

    public List<Book> findBooksByAuthor(String author) {
        // TODO: Add query
        return null;
    }

    public boolean addBook(Book book) {
        // TODO: Add query
        return true;
    }

    public boolean updateBook(Book book) {
        // TODO: Add query
        return true;
    }

    public boolean deleteBook(Book book) {
        // TODO: Add query
        return true;
    }
}
