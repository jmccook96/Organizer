package com.bookclub.controller;

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

import java.util.List;

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
    private ComboBox<String> genreComboBox, searchGenreComboBox;
    @FXML
    private TextField searchAuthorField; // New text field for searching by author

    @FXML
    private HBox navBar;
    @FXML
    private Button searchButton;

    public BooksController() {
        bookAO = new BookDAO();
        bookService = BookService.getInstance();
    }

    @FXML
    public void initialize() {
        // Initialize genre ComboBox options

        genreComboBox.getItems().addAll("Fiction", "Non-fiction", "Sci-Fi", "Fantasy", "Romance", "Horror");


        searchGenreComboBox.getItems().addAll("All", "Fiction", "Non-fiction", "Sci-Fi", "Fantasy", "Romance", "Horror");
        searchGenreComboBox.setValue("All");  // Default to "All" for searching

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
                Book selectedBook = newValue;
                bookService.setSelectedBook(selectedBook);
                StageFactory.getInstance().switchScene(StageView.REVIEWS);
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



                    ReviewData reviewData = bookService.getReviewData(book);
                    Label ratingLabel = new Label(String.format("%.1f ★ (%d)", reviewData.getAverageRating(), reviewData.getNumberOfRatings()));

                    // Create a remove button
                    Button removeButton = new Button("Remove");
                    removeButton.setOnAction(event -> {
                        bookAO.removeBook(book); // Call your method to remove the book
                        updateBooks(); // Refresh the book list
                    });

                    Region spacer = new Region();
                    HBox.setHgrow(spacer, Priority.ALWAYS); // Allow the spacer to grow and fill available space



                    hbox.getChildren().addAll(bookLabel, ratingLabel, spacer, removeButton);
                    setGraphic(hbox);
                }
            }
        });
    }

    @FXML
    private void handleAddBook() {
        String title = titleField.getText();
        String author = authorField.getText();
        String genre = genreComboBox.getValue();

        System.out.println("Title: " + title + ", Author: " + author + ", Genre: " + genre);

        if (!title.isEmpty() && !author.isEmpty() && genre !=null ) {
            Book book = new Book(title, author, genre);
            bookAO.addBook(book);
            updateBooks();
            titleField.clear();
            authorField.clear();
            genreComboBox.setValue(null);

            System.out.println("Book added successfully.");
        } else {
            showAlert("Failed", "Book must have a title, author, and genre.");
        }
    }

    private void updateBooks() {
        booksList.getItems().clear();
        List<Book> books = bookAO.findAllBooks();
        if (books != null && !books.isEmpty()) {
            booksList.getItems().addAll(books);
        } else {
            System.out.println("No books to display.");
        }
    }

    @FXML
    public void handleSearchBooks() {
        String searchQuery = searchAuthorField.getText().trim().toLowerCase(); // Get the search query and convert it to lower case
        String selectedGenre = searchGenreComboBox.getValue();

        List<Book> filteredBooks;

        // Search by title and/or author
        if (selectedGenre.equals("All") && searchQuery.isEmpty()) {
            // No filters, show all books
            filteredBooks = bookAO.findAllBooks();
        } else {
            // Filter by genre
            filteredBooks = bookAO.findAllBooks(); // Get all books first

            // Filter based on the search query
            filteredBooks.removeIf(book ->
                    !book.getTitle().toLowerCase().contains(searchQuery) && // Check title
                    !book.getAuthor().toLowerCase().contains(searchQuery)); // Check author

            // If a specific genre is selected, filter by genre
            if (!selectedGenre.equals("All")) {
                filteredBooks.removeIf(book -> !book.getGenre().equalsIgnoreCase(selectedGenre));
            }
        }

        // Update the book list with filtered results
        booksList.getItems().clear();
        if (filteredBooks != null && !filteredBooks.isEmpty()) {
            booksList.getItems().addAll(filteredBooks);
        } else {
            System.out.println("No books found.");
            showAlert("No Results", "No books found for the given search search.");
        }
    }


    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
