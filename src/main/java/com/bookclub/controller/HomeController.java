package com.bookclub.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class HomeController {

    @FXML
    private HBox navBar;

    @FXML
    public void initialize() {
        // Set nav bar button colour
        Button booksButton = (Button)navBar.lookup("#homeButton");
        if (booksButton != null) {
            booksButton.setStyle("-fx-background-color: lightsteelblue");
        }
    }
}
