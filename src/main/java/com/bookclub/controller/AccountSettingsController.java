package com.bookclub.controller;

import com.bookclub.model.User;
import com.bookclub.service.LoginService;
import com.bookclub.util.StageFactory;
import com.bookclub.util.StageView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

/**
 * The {@code AccountSettingsController} class is the controller for the Account Settings view.
 * It manages the display and update of user account information such as username, name, and email.
 * It also handles signing out the user and updating account settings.
 */
public class AccountSettingsController {

    @FXML
    private Label usernameLabel;
    @FXML
    private TextField nameField;
    @FXML
    private TextField emailField;
    @FXML
    private Label messageLabel;
    @FXML
    private HBox navBar;

    /**
     * Initializes the controller after the view has been loaded.
     * It sets the user's account information in the appropriate fields and highlights the account button in the navigation bar.
     */
    public void initialize() {
        // Highlight the account button in the navigation bar
        Button accountButton = (Button) navBar.lookup("#accountButton");
        if (accountButton != null) {
            accountButton.setStyle("-fx-background-color: lightsteelblue");
        }

        // Set the user's account details
        User currentUser = LoginService.getInstance().getCurrentUser();
        usernameLabel.setText(currentUser.getUsername());
        nameField.setText(currentUser.getName());
        emailField.setText(currentUser.getEmail());
    }

    /**
     * Handles the event when the user clicks the "Update Settings" button.
     * It updates the current user's name and email based on the input fields, and displays a message indicating success or failure.
     */
    @FXML
    protected void onUpdateSettings() {
        User currentUser = LoginService.getInstance().getCurrentUser();
        currentUser.setName(nameField.getText());
        currentUser.setEmail(emailField.getText());

        // Update the user's account information and display appropriate message
        if (LoginService.getInstance().updateUser(currentUser)) {
            messageLabel.setText("Settings updated successfully!");
            messageLabel.setStyle("-fx-text-fill: green;");
        } else {
            messageLabel.setText("Failed to update settings. Please try again.");
            messageLabel.setStyle("-fx-text-fill: red;");
        }
    }

    /**
     * Handles the event when the user clicks the "Sign Out" button.
     * It signs out the current user and switches the scene to the login view.
     */
    @FXML
    protected void onSignOutButton() {
        // Sign out the current user
        LoginService.getInstance().dropCurrentUser();

        // Switch to the login scene
        StageFactory.getInstance().switchScene(StageView.LOGIN);
    }
}
