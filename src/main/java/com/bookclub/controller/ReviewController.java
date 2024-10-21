package com.bookclub.controller;

import com.bookclub.dao.ReviewDAO;
import com.bookclub.iao.IReviewAO;
import com.bookclub.model.Book;
import com.bookclub.model.Review;
import com.bookclub.model.User;
import com.bookclub.service.BookService;
import com.bookclub.service.LoginService;
import com.bookclub.util.StageFactory;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import org.controlsfx.control.Rating;

import java.util.Collections;
import java.util.List;

/**
 * Controller for managing the review functionality of books in the application.
 * This class handles user interactions related to submitting and displaying book reviews.
 */
public class ReviewController {

    private IReviewAO reviewAO;
    private Book selectedBook;
    private User currentUser;
    private BookService bookService;

    @FXML
    private VBox reviewContainer;
    @FXML
    private VBox newReviewContainer;
    @FXML
    private Rating ratingControl;
    @FXML
    private ListView<HBox> ratingsList;

    /**
     * Initializes a new instance of ReviewController and sets up the review access object.
     * Also retrieves the current user and selected book.
     */
    public ReviewController() {
        reviewAO = new ReviewDAO();
        bookService = BookService.getInstance();
        currentUser = LoginService.getInstance().getCurrentUser(); // TODO: Remove this, is dangerous
    }

    /**
     * Initializes the controller after its root element has been processed.
     * Sets up UI bindings and updates the view with current book ratings.
     */
    @FXML
    public void initialize() {
        reviewContainer.maxWidthProperty().bind(StageFactory.getInstance().getPrimaryStage().widthProperty().multiply(0.5));
        newReviewContainer.maxWidthProperty().bind(reviewContainer.maxWidthProperty().multiply(0.5));
        selectedBook = bookService.getSelectedBook();
        updateRatings();
        ratingControl.setUpdateOnHover(false);
    }

    /**
     * Handles the submission of a review by the current user for the selected book.
     * Updates existing reviews or adds new reviews as necessary.
     */
    @FXML
    private void handleSubmitReview() {
        int rating = getSelectedRating();
        Book book = bookService.getSelectedBook();
        Review existingReview = reviewAO.findReviewByUserAndBook(currentUser, book);

        if (existingReview != null) {
            existingReview.setRating(rating);
            bookService.addOrUpdateReview(existingReview, book);
        } else {
            Review newReview = new Review(currentUser, book, rating);
            bookService.addOrUpdateReview(newReview, book);
        }

        updateRatings();  // Refresh the view to reflect the updated reviews
    }

    /**
     * Retrieves the currently selected rating from the rating control.
     *
     * @return the selected rating as an integer
     */
    private int getSelectedRating() {
        return (int) ratingControl.getRating();
    }

    /**
     * Updates the displayed ratings for the selected book by retrieving reviews
     * from the review access object and populating the ratings list.
     */
    private void updateRatings() {
        if (selectedBook != null) {
            List<Review> reviews = reviewAO.findReviewsByBook(selectedBook);
            if (!reviews.isEmpty()) {
                ratingsList.getItems().clear(); // Clear current ratings
                Collections.reverse(reviews); // Latest rating at the top
                for (Review review : reviews) {
                    HBox hBox = new HBox();
                    hBox.getChildren().add(new Label(review.getUser().getUsername()));
                    Region spacer = new Region();
                    HBox.setHgrow(spacer, Priority.ALWAYS);
                    hBox.getChildren().add(spacer);
                    Rating rating = new Rating(5, review.getRating());
                    rating.addEventFilter(MouseEvent.ANY, event -> event.consume());
                    hBox.getChildren().add(rating);
                    hBox.setAlignment(Pos.CENTER);
                    ratingsList.getItems().add(hBox);
                }
            }
        }
        else {
            showAlert("No Book Selected", "No book was selected to show reviews for.");
        }
    }

    /**
     * Displays an alert dialog with the specified title and message.
     *
     * @param title   the title of the alert
     * @param message the message to be displayed in the alert
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
