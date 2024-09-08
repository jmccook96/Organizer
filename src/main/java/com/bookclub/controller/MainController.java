package com.bookclub.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class MainController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    private BorderPane rootLayout; // Reference to the root BorderPane, if needed

    @FXML
    private Label contentLabel;

    // Handler for Home Button
    @FXML
    private void handleHome() {
        contentLabel.setText("Home Screen");
        // Additional logic to load Home screen content can be added here
    }
}