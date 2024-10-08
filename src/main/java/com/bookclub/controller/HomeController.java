package com.bookclub.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

/**
 * Controller for the Home view, responsible for managing the home interface elements and interactions.
 */
public class HomeController {

    @FXML
    private HBox navBar;

    /**
     * Initializes the HomeController.
     * This method is automatically called after the FXML elements have been loaded.
     */
    @FXML
    public void initialize() {
        // Set nav bar button colour
        Button booksButton = (Button)navBar.lookup("#homeButton");
        if (booksButton != null) {
            booksButton.setStyle("-fx-background-color: lightsteelblue");
        }
    }
}
