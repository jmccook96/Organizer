package com.bookclub.controller;

import com.bookclub.dao.ReviewDAO;
import com.bookclub.iao.IReviewAO;
import com.bookclub.model.Book;
import com.bookclub.model.Review;
import com.bookclub.service.BookService;
import com.bookclub.service.LoginService;
import com.bookclub.util.StageFactory;
import com.bookclub.util.StageView;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import org.controlsfx.control.Rating;

import java.util.Collections;
import java.util.List;

public class ReviewController {

    private IReviewAO reviewAO;
    private Book selectedBook;
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

    public ReviewController() {
        reviewAO = new ReviewDAO();
    }

    @FXML
    public void initialize() {
        reviewContainer.maxWidthProperty().bind(StageFactory.getInstance().getPrimaryStage().widthProperty().multiply(0.5));
        newReviewContainer.maxWidthProperty().bind(reviewContainer.maxWidthProperty().multiply(0.5));
        backButton.prefHeightProperty().bind(StageFactory.getInstance().getPrimaryStage().heightProperty());
        backIcon.fitWidthProperty().bind(StageFactory.getInstance().getPrimaryStage().widthProperty().multiply(0.1));
        selectedBook = BookService.getInstance().getSelectedBook();
        if (selectedBook != null) {
            bookLabel.setText(selectedBook.toString());
        }
        updateRatings();
        ratingsList.maxHeightProperty().bind(reviewContainer.heightProperty());
        ratingControl.setUpdateOnHover(false);
    }

    @FXML
    private void handleSubmitReview() {
        int rating = (int)ratingControl.getRating();
        if (rating == 0) {
            showAlert("Review submit failed", "A rating must be selected!");
        }
        else {
            showAlert("Review submitted", "You rated " + selectedBook + ": " + rating + " stars");
            Review review = new Review(LoginService.getCurrentUser(), selectedBook, rating);
            reviewAO.addReview(review);
            updateRatings();
        }
    }

    @FXML
    private void handleBackButton() {
        // Switch back to the book list view
        StageFactory.getInstance().switchScene(StageView.BOOKS);
    }

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
                    hBox.getChildren().add(new Rating(5, review.getRating()));
                    hBox.setAlignment(Pos.CENTER);
                    ratingsList.getItems().add(hBox);
                }
            }
        }
        else {
            showAlert("No Book Selected", "No book was selected to show reviews for.");
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
