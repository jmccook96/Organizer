package com.bookclub.dao;

import com.bookclub.iao.IBookAO;
import com.bookclub.model.Book;
import com.bookclub.util.DatabaseManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BookDAO implements IBookAO {

    private DatabaseManager dbManager;
    private static final Logger LOGGER = Logger.getLogger(BookDAO.class.getName());

    public BookDAO() {
        dbManager = DatabaseManager.getInstance();
        createTable();
    }

    // Helper method to construct a Book object from the ResultSet
    private Book buildBookFromResultSet(ResultSet resultSet) throws SQLException {
        int bookId = resultSet.getInt("bookId");
        String bookTitle = resultSet.getString("bookTitle");
        String bookAuthor = resultSet.getString("bookAuthor");
        String bookGenre = resultSet.getString("bookGenre");
        int totalPages = resultSet.getInt("totalPages");
        return new Book(bookId, bookTitle, bookAuthor, bookGenre, totalPages);
    }

    // Generalized method to fetch a list of books with different queries
    private List<Book> getBooksFromQuery(String query, String param) {
        List<Book> books = new ArrayList<>();
        try (PreparedStatement statement = dbManager.getConnection().prepareStatement(query)) {
            if (param != null) {
                statement.setString(1, param);
            }
            books = getListFromStatement(statement);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching books", e);
        }
        return books;
    }

    // Fetch the list of books based on a prepared statement
    private List<Book> getListFromStatement(PreparedStatement statement) throws SQLException {
        List<Book> books = new ArrayList<>();
        try (ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                books.add(buildBookFromResultSet(resultSet));
            }
        }
        return books;
    }

    // Create table if it doesn't exist
    private void createTable() {
        String createTableQuery = """
                CREATE TABLE IF NOT EXISTS Books (
                bookID INTEGER PRIMARY KEY AUTOINCREMENT,
                bookTitle VARCHAR NOT NULL,
                bookAuthor VARCHAR NOT NULL,
                bookGenre VARCHAR NOT NULL,
                totalPages INTEGER NOT NULL)""";
        try (Statement statement = dbManager.getConnection().createStatement()) {
            statement.executeUpdate(createTableQuery);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error creating table", e);
        }
    }


    @Override
    public List<Book> findAllBooks() {
        return getBooksFromQuery("SELECT * FROM Books", null);
    }

    @Override
    public Book findBookByTitleAndAuthor(String title, String author) {
        String query = "SELECT * FROM Books WHERE bookTitle = ? AND bookAuthor = ?";
        try (PreparedStatement statement = dbManager.getConnection().prepareStatement(query)) {
            statement.setString(1, title);
            statement.setString(2, author);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return buildBookFromResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error finding book by title and author", e);
        }
        return null;
    }

    @Override
    public List<Book> findBooksByTitle(String title) {
        return getBooksFromQuery("SELECT * FROM Books WHERE bookTitle = ?", title);
    }

    @Override
    public List<Book> findBooksByGenre(String genre) {
        return getBooksFromQuery("SELECT * FROM Books WHERE bookGenre = ?", genre);
    }

    @Override
    public List<Book> findBooksByAuthor(String author) {
        return getBooksFromQuery("SELECT * FROM Books WHERE bookAuthor = ?", author);
    }

    @Override
    public boolean addBook(Book book) {
        String query = "INSERT INTO Books (bookTitle, bookAuthor, bookGenre, totalPages) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = dbManager.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setString(2, book.getAuthor());
            statement.setString(3, book.getGenre());
            statement.setInt(4, book.getTotalPages());
            statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    book.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error adding book", e);
            return false;
        }
        return true;
    }

    @Override
    public boolean updateBook(Book book) {
        String query = "UPDATE Books SET bookTitle = ?, bookAuthor = ?, bookGenre = ?, totalPages = ? WHERE bookID = ?";
        try (PreparedStatement statement = dbManager.getConnection().prepareStatement(query)) {
            statement.setString(1, book.getTitle());
            statement.setString(2, book.getAuthor());
            statement.setString(3, book.getGenre());
            statement.setInt(4, book.getTotalPages());
            statement.setInt(5, book.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error updating book", e);
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteBook(Book book) {
        String query = "DELETE FROM Books WHERE bookID = ?";
        try (PreparedStatement statement = dbManager.getConnection().prepareStatement(query)) {
            statement.setInt(1, book.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error deleting book", e);
            return false;
        }
        return true;
    }
}
