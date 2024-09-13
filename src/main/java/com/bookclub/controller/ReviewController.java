package com.bookclub.controller;

import com.bookclub.iao.IReviewAO;
import com.bookclub.mao.ReviewMAO;
import com.bookclub.model.Book;
import com.bookclub.model.Review;
import com.bookclub.service.LoginService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import org.controlsfx.control.Rating;

import java.util.List;

public class ReviewController {

    private IReviewAO reviewAO;
    @FXML
    private Rating ratingControl;
    @FXML
    private ListView<Rating> ratingsList;

    public ReviewController() {
        // TODO: Swap to ReviewDAO
        reviewAO = new ReviewMAO();
    }

    @FXML
    public void initialize() {
        List<Review> reviews = reviewAO.findReviewsByUser(LoginService.getCurrentUser());
        if (reviews != null) {
            for (Review review : reviews) {
                ratingsList.getItems().add(new Rating(5, review.getRating()));
            }
        }
    }

    @FXML
    private void handleSubmitReview() {
        int rating = (int)ratingControl.getRating();
        if (rating == 0) {
            showAlert("Review submit failed", "A rating must be selected!");
        }
        else {
            showAlert("Review submitted", "You rated: " + rating + " stars");
            // TODO: Change to non hard coded Book object
            Review review = new Review(LoginService.getCurrentUser(), new Book("testTitle", "testAuthor"), rating);
            reviewAO.addReview(review);
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
