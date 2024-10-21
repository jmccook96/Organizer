package com.bookclub.controller;
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
 * books in the book club application. It interacts with the data layer and handles
 * the book list, adding books, and navigating between different stages of the application.
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
        bookService = BookService.getInstance();
    }

    /**
     * Initializes the controller after its root element has been completely processed.
     * This method sets up event listeners, bindings, and populates the list of books.
     */
    @FXML
    public void initialize() {

        searchGenreComboBox.getItems().add("All"); // Add "All" as the first option
        for (Genre genre : Genre.values()) {
            genreComboBox.getItems().add(genre.getDisplayName());
            searchGenreComboBox.getItems().add(genre.getDisplayName());
        }

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
                StageFactory.getInstance().switchScene(StageView.BOOK_ITEM);
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

                    // Create a remove button
                    Button removeButton = new Button("Remove");
                    removeButton.setOnAction(event -> {
                        bookAO.deleteBook(book);
                        updateBooks(); // Refresh the book list
                    });

                    Region spacer = new Region();
                    HBox.setHgrow(spacer, Priority.ALWAYS); // Allow the spacer to grow and fill available space and move the remove button to the right end of the list

                    hbox.getChildren().addAll(bookLabel, ratingLabel, spacer, removeButton);
                    setGraphic(hbox);
                }
            }
        });
    }

    /**
     * Handles the action of adding a new book to the system.
     * The book is added only if both the title and author fields are filled.
     */
    @FXML
    private void handleAddBook() {
        String title = titleField.getText();
        String author = authorField.getText();
        String genre = genreComboBox.getValue();

        if (!title.isEmpty() && !author.isEmpty() && genre !=null) {
            Book book = new Book(title, author, genre);
            bookAO.addBook(book);
            updateBooks();
            titleField.clear();
            authorField.clear();
            genreComboBox.setValue("General");
        } else {
            showAlert("Failed", "Book must have a Title, Author and Genre.");
        }
    }

    /**
     * Updates the book list by retrieving all books from the data source,
     * displaying them in reverse order in the {@code booksList}.
     */
    private void updateBooks() {
        booksList.getItems().clear();
        List<Book> books = bookAO.findAllBooks();
        Collections.reverse(books); //
        booksList.getItems().addAll(books);
    }

    @FXML
    public void handleSearchBooks() {
        String searchQuery = searchTitleAuthorField.getText().trim().toLowerCase(); // Get the search query
        String selectedGenre = searchGenreComboBox.getValue();

        // Use the service manager to perform the search
        List<Book> filteredBooks = bookService.searchForBooks(searchQuery, selectedGenre);

        displaySearchedBooks(filteredBooks);
    }

    private void displaySearchedBooks(List<Book> filteredBooks) {
        booksList.getItems().clear();
        if (filteredBooks != null && !filteredBooks.isEmpty()) {
            booksList.getItems().addAll(filteredBooks);

        } else {
            showAlert("No results", "No books found for the given search");
        }
    }

    /**
     * Displays an alert dialog with the provided title and message.
     * Used to display informal alerts to the user.
     *
     * @param title   The title of the alert dialog.
     * @param message The message content of the alert dialog.
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
