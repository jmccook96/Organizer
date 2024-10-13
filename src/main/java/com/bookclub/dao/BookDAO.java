package com.bookclub.dao;

import com.bookclub.iao.IBookAO;
import com.bookclub.model.Book;
import com.bookclub.util.DatabaseManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BookDAO implements IBookAO {

    private Book buildBookFromResultSet(ResultSet resultSet) throws Exception {
        int bookId = resultSet.getInt("bookId");
        String bookTitle = resultSet.getString("bookTitle");
        String bookAuthor = resultSet.getString("bookAuthor");
        String bookGenre = resultSet.getString("bookGenre");
        return new Book(bookId, bookTitle, bookAuthor, bookGenre);
    }

    private DatabaseManager dbManager;

    public BookDAO() {
        dbManager = DatabaseManager.getInstance();
        createTable();
    }

    @Override
    public List<Book> findAllBooks() {
        List<Book> books = new ArrayList<>();
        String query = "SELECT * FROM Books";
        try {
            Statement statement = dbManager.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {

                books.add(buildBookFromResultSet(resultSet));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Books in the system: " + books.size());
        return books;
    }

    @Override
    public Book findBookByTitleAndAuthor(String title, String author) {
        try {
            PreparedStatement statement = dbManager.getConnection().prepareStatement("SELECT * FROM Books WHERE bookTitle = ? AND bookAuthor = ?");
            statement.setString(1, title);
            statement.setString(2, author);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {

                return buildBookFromResultSet(resultSet);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Book> findBooksByTitle(String title) {
        List<Book> books = new ArrayList<>();
        try {
            PreparedStatement statement = dbManager.getConnection().prepareStatement("SELECT * FROM Books WHERE bookTitle = ?");
            statement.setString(1, title);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {

                books.add(buildBookFromResultSet(resultSet));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return books;
    }

    @Override
    public List<Book> findBooksByGenre(String genre) {
        List<Book> books = new ArrayList<>();
        try {
            PreparedStatement statement = dbManager.getConnection().prepareStatement("SELECT * FROM Books WHERE bookGenre = ?");
            statement.setString(1, genre);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {

                books.add(buildBookFromResultSet(resultSet));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return books;
    }

    @Override
    public List<Book> findBooksByAuthor(String author) {
        List<Book> books = new ArrayList<>();
        try {
            PreparedStatement statement = dbManager.getConnection().prepareStatement("SELECT * FROM Books WHERE bookAuthor = ?");
            statement.setString(1, author);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {

                books.add(buildBookFromResultSet(resultSet));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return books;
    }

    @Override
    public boolean addBook(Book book) {
        try {
            PreparedStatement statement = dbManager.getConnection().prepareStatement("INSERT INTO Books (bookTitle, bookAuthor, bookGenre) VALUES (?, ?, ?)");
            statement.setString(1, book.getTitle());
            statement.setString(2, book.getAuthor());
            statement.setString(3, book.getGenre());
            statement.executeUpdate();

            // Set the id of the new book

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                book.setId(generatedKeys.getInt(1));
            }

        }
        catch (Exception e) {
            e.printStackTrace();

        }
        return true;
    }


    @Override
    public boolean updateBook(Book book) {
        try {
            PreparedStatement statement = dbManager.getConnection().prepareStatement("UPDATE Books SET bookTitle = ?, bookAuthor = ? WHERE bookID = ?");
            statement.setString(1, book.getTitle());
            statement.setString(2, book.getAuthor());
            statement.setInt(3, book.getId());
            statement.executeUpdate();
        }
        catch (Exception e) {
            e.printStackTrace();

        }
        return true;
    }

    @Override
    public boolean deleteBook(Book book) {
        try {
            PreparedStatement statement = dbManager.getConnection().prepareStatement("DELETE FROM Books WHERE bookID = ?");
            statement.setInt(1, book.getId());
            statement.executeUpdate();
        }
        catch (Exception e) {
            e.printStackTrace();

        }
        return true;
    }


    private void createTable() {
        // Create table if not exists
        try {
            Statement statement = dbManager.getConnection().createStatement();
            String createTableQuery = "CREATE TABLE IF NOT EXISTS Books ("
                    + "bookID INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "bookTitle VARCHAR NOT NULL,"
                    + "bookAuthor VARCHAR NOT NULL,"
                    + "bookGenre VARCHAR NOT NULL"
                    + ")";
            statement.executeUpdate(createTableQuery);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}