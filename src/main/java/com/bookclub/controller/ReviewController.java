package com.bookclub.controller;

import com.bookclub.dao.ReviewDAO;
import com.bookclub.dao.UserDAO;
import com.bookclub.iao.IReviewAO;
import com.bookclub.iao.IUserAO;
import com.bookclub.model.Book;
import com.bookclub.model.Review;
import com.bookclub.model.User;
import com.bookclub.service.BookService;
import com.bookclub.service.LoginService;
import com.bookclub.util.StageFactory;
import com.bookclub.util.StageView;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
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

    // TODO: Migrate out of controller into a service.
    private IReviewAO reviewAO;
    private IUserAO userAO;
    private Book selectedBook;
    private User currentUser;
    private BookService bookService;

    @FXML
    private VBox reviewContainer;
    @FXML
    private VBox newReviewContainer;
    @FXML
    private ImageView backIcon;
    @FXML
    private Rating ratingControl;
    @FXML
    private ListView<HBox> ratingsList;
    @FXML
    private Button backButton;
    @FXML
    private Label bookLabel;
    @FXML
    private TextField reviewTopicField;
    @FXML
    private TextArea reviewDescriptionField;

    /**
     * Initializes a new instance of ReviewController and sets up the review access object.
     * Also retrieves the current user and selected book.
     */
    public ReviewController() {
        reviewAO = new ReviewDAO();
        userAO = new UserDAO();
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
        backButton.prefHeightProperty().bind(StageFactory.getInstance().getPrimaryStage().heightProperty());
        backIcon.fitWidthProperty().bind(StageFactory.getInstance().getPrimaryStage().widthProperty().multiply(0.1));
        selectedBook = bookService.getSelectedBook();
        if (selectedBook != null) {
            bookLabel.setText(selectedBook.toString());
        }
        updateRatings();
        ratingsList.maxHeightProperty().bind(reviewContainer.heightProperty());
        ratingControl.setUpdateOnHover(false);
    }

    /**
     * Handles the submission of a review by the current user for the selected book.
     * Updates existing reviews or adds new reviews as necessary.
     */
    @FXML
    private void handleSubmitReview() {
        int rating = getSelectedRating();
        String topic = reviewTopicField.getText();
        String description = reviewDescriptionField.getText();

        Book book = bookService.getSelectedBook();
        Review existingReview = reviewAO.findReviewByUserAndBook(currentUser, book);

        if (existingReview != null) {
            existingReview.setRating(rating);
            existingReview.setTopic(topic);
            existingReview.setDescription(description);
            bookService.addOrUpdateReview(existingReview, book);
        } else {
            Review newReview = new Review(currentUser, book, rating, topic, description);
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
     * Handles the action when the back button is clicked,
     * switching the scene back to the book list view.
     */
    @FXML
    private void handleBackButton() {
        // Switch back to the book list view
        StageFactory.getInstance().switchScene(StageView.BOOKS);
    }

    /**
     * Updates the displayed ratings for the selected book by retrieving reviews
     * from the review access object and populating the ratings list.
     */
    private void updateRatings() {
        if (selectedBook != null) {
            List<Review> reviews = reviewAO.findReviewsByBook(selectedBook);
            ratingsList.getItems().clear(); // Clear current ratings

            if (!reviews.isEmpty()) {
                Collections.reverse(reviews); // Show latest rating at the top

                for (Review review : reviews) {
                    // Horizontal box for layout (spacing of 10 between elements)
                    HBox hBox = new HBox(10);

                    // Vertical box for stacking user's details, review topic, and description
                    VBox reviewDetails = new VBox(5);

                    // Add the reviewer's username and rating in a horizontal layout
                    HBox userRatingBox = new HBox(10); // New HBox for user and rating

                    hBox.getChildren().add(new Label(getUsername(review)));
                    userLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14;");
                    userRatingBox.getChildren().add(userLabel); // Add username to the HBox
                    // Push the rating to the right
                    Region spacer = new Region();
                    HBox.setHgrow(spacer, Priority.ALWAYS); // Allow the spacer to grow horizontally
                    userRatingBox.getChildren().add(spacer); // Add spacer between username and rating

                    Rating userRating = new Rating(5, review.getRating());
                    userRating.setDisable(true); // Disable interaction
                    userRatingBox.getChildren().add(userRating); // Add rating to the right of the spacer
                    reviewDetails.getChildren().add(userRatingBox); // Add user and rating to the details VBox

                    // Conditionally add the review topic and description if they are not empty
                    if (review.getTopic() != null && !review.getTopic().isEmpty()) {
                        Label topicLabel = new Label("Topic: " + review.getTopic());
                        topicLabel.setStyle("-fx-font-weight: bold;");
                        reviewDetails.getChildren().add(topicLabel);
                    }

                    if (review.getDescription() != null && !review.getDescription().isEmpty()) {
                        Label descriptionLabel = new Label("Description: " + review.getDescription());
                        descriptionLabel.setWrapText(true); // Enable wrapping for long descriptions
                        reviewDetails.getChildren().add(descriptionLabel);
                    }
                    // Set VBox for the review details to expand to fill available space
                    HBox.setHgrow(reviewDetails, Priority.ALWAYS);
                    // Add the review details to the main HBox
                    hBox.getChildren().add(reviewDetails);
                    hBox.setAlignment(Pos.CENTER_LEFT); // Align elements to the left
                    // Add the constructed HBox to the ListView
                    ratingsList.getItems().add(hBox);
                }
            } else {
                // Show "No reviews available" message in an HBox
                HBox noReviewsBox = new HBox();
                Label noReviewsLabel = new Label("No reviews available for this book.");
                noReviewsBox.getChildren().add(noReviewsLabel);
                noReviewsBox.setAlignment(Pos.CENTER); // Center the message
                ratingsList.getItems().add(noReviewsBox); // Add the HBox to the ListView
            }
        } else {
            showAlert("No Book Selected", "No book was selected to show reviews for.");
        }
    }

    /**
     * Fetches the username from the given review.
     * @param review The review to fetch the username from
     * @return The username
     */
    private String getUsername(Review review) {
        User user = userAO.findUserById(review.getUserId());
        if (user != null) {
            return user.getUsername();
        }
        else {
            return "Unknown";
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
