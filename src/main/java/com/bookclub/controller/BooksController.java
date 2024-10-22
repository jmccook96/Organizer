package com.bookclub.controller;

import com.bookclub.dao.ReviewDAO;
import com.bookclub.model.Genre;
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
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.util.Collections;
import java.util.List;

/**
 * The {@code BooksController} class serves as the controller for managing the
 * books in the book club application. It interacts with the data layer to display,
 * add, remove, and search for books. It also navigates to the book's review stage
 * when a book is selected from the list.
 */
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
    private TextField totalChaptersField;

    @FXML
    private HBox navBar;

    @FXML
    private ComboBox<String> genreComboBox, searchGenreComboBox;
    @FXML
    private TextField searchTitleAuthorField;

    /**
     * Initializes a new instance of the {@code BooksController}.
     * This constructor sets up the DAO and service instances.
     */
    public BooksController() {
        bookAO = new BookDAO();
        BookService.initialize(bookAO, new ReviewDAO());
        bookService = BookService.getInstance();
    }

    /**
     * Initializes the controller after its root element has been fully processed.
     * This method sets up event listeners, binds the width of the book container to the stage,
     * and populates the book list. It also configures how books are displayed in the list,
     * including adding a remove button and displaying average ratings.
     */
    @FXML
    public void initialize() {
        // Populate the genre combo boxes
        searchGenreComboBox.getItems().add("All"); // Add "All" as the first option
        for (Genre genre : Genre.values()) {
            genreComboBox.getItems().add(genre.getDisplayName());
            searchGenreComboBox.getItems().add(genre.getDisplayName());
        }

        // Set navigation bar button color
        Button booksButton = (Button) navBar.lookup("#booksButton");
        if (booksButton != null) {
            booksButton.setStyle("-fx-background-color: lightsteelblue");
        }
        booksContainer.maxWidthProperty().bind(StageFactory.getInstance().getPrimaryStage().widthProperty().multiply(0.5));

        // Update the list of books and set up book selection listener
        updateBooks();
        booksList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        // Handle book selection to navigate to the review stage
        booksList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                bookService.setSelectedBook(newValue);
                StageFactory.getInstance().switchScene(StageView.REVIEWS);
            }
        });

        // Set custom cell factory to display books with a remove button and ratings
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

                    // Display book rating
                    ReviewData reviewData = bookService.getReviewData(book);
                    Label ratingLabel = new Label(String.format("%.1f ★ (%d)", reviewData.getAverageRating(), reviewData.getNumberOfRatings()));

                    // Add remove button
                    Button removeButton = new Button("Remove");
                    removeButton.setOnAction(event -> {
                        bookAO.deleteBook(book);
                        updateBooks();
                    });

                    // Create a spacer between labels and button
                    Region spacer = new Region();
                    HBox.setHgrow(spacer, Priority.ALWAYS);

                    hbox.getChildren().addAll(bookLabel, ratingLabel, spacer, removeButton);
                    setGraphic(hbox);
                }
            }
        });
    }

    /**
     * Handles the action of adding a new book to the system.
     * The book is added only if the title, author, and genre fields are filled
     * and the total chapters is a valid number.
     */
    @FXML
    private void handleAddBook() {
        String title = titleField.getText();
        String author = authorField.getText();
        String genre = genreComboBox.getValue();
        String totalChaptersText = totalChaptersField.getText().trim();

        if (!title.isEmpty() && !author.isEmpty() && genre != null) {
            try {
                int totalChapters = Integer.parseInt(totalChaptersText);
                Book book = new Book(title, author, genre, totalChapters);
                bookAO.addBook(book);
                updateBooks();
                clearFields();
            } catch (NumberFormatException e) {
                showAlert("Error", "Total chapters must be a valid number.");
            }
        } else {
            showAlert("Failed", "Book must have a Title, Author, and Genre.");
        }
    }

    /**
     * Updates the book list by retrieving all books from the data source
     * and displaying them in reverse order in the {@code booksList}.
     */
    private void updateBooks() {
        booksList.getItems().clear();
        List<Book> books = bookAO.findAllBooks();
        Collections.reverse(books); // Reverse the order for recent books
        booksList.getItems().addAll(books);
    }

    /**
     * Handles searching for books based on the search query (title/author) and selected genre.
     * It filters the books and displays the matching results in the {@code booksList}.
     */
    @FXML
    public void handleSearchBooks() {
        String searchQuery = searchTitleAuthorField.getText().trim().toLowerCase(); // Get the search query
        String selectedGenre = searchGenreComboBox.getValue();

        // Use the service to search for books based on query and genre
        List<Book> filteredBooks = bookService.searchForBooks(searchQuery, selectedGenre);

        displaySearchedBooks(filteredBooks);
    }

    /**
     * Displays the search results by updating the book list with the filtered books.
     * If no books match the search, an alert is shown to the user.
     *
     * @param filteredBooks the list of books that match the search query.
     */
    private void displaySearchedBooks(List<Book> filteredBooks) {
        booksList.getItems().clear();
        if (filteredBooks != null && !filteredBooks.isEmpty()) {
            booksList.getItems().addAll(filteredBooks);
        } else {
            showAlert("No results", "No books found for the given search");
        }
    }

    /**
     * Clears the input fields for adding a new book.
     */
    private void clearFields() {
        titleField.clear();
        authorField.clear();
        totalChaptersField.clear();
        genreComboBox.setValue(null);
    }

    /**
     * Displays an alert dialog with the provided title and message.
     * Used to notify the user of various events or errors.
     *
     * @param title   the title of the alert dialog.
     * @param message the content message of the alert dialog.
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
