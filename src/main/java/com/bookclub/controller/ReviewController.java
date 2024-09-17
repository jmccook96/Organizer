package com.bookclub.controller;

import com.bookclub.dao.ReviewDAO;
import com.bookclub.iao.IReviewAO;
import com.bookclub.model.Book;
import com.bookclub.model.Review;
import com.bookclub.service.LoginService;
import com.bookclub.util.StageFactory;
import com.bookclub.util.StageView;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import org.controlsfx.control.Rating;

import java.util.List;

public class ReviewController {

    private IReviewAO reviewAO;
    private Book selectedBook;
    @FXML
    private Rating ratingControl;
    @FXML
    private ListView<Rating> ratingsList;

    public ReviewController() {
        // TODO: Swap to ReviewDAO
        reviewAO = new ReviewDAO();
    }

    @FXML
    public void initialize() {
        selectedBook = BooksController.getSelectedBook();
        updateRatings();
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
                for (Review review : reviews) {
                    ratingsList.getItems().add(new Rating(5, review.getRating()));
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
