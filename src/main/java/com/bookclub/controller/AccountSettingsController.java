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
 * The AccountSettingsController class is responsible for managing the user's account settings.
 * It allows users to view and update their account details, such as their name and email.
 * Additionally, it provides functionality for signing out.
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
     * Initializes the controller by setting the user's account information in the fields.
     * It also highlights the account button in the navigation bar to indicate the current view.
     */
    public void initialize() {
        Button accountButton = (Button)navBar.lookup("#accountButton");
        if (accountButton != null) {
            accountButton.setStyle("-fx-background-color: lightsteelblue");
        }

        User currentUser = LoginService.getInstance().getCurrentUser();
        usernameLabel.setText(currentUser.getUsername());
        nameField.setText(currentUser.getName());
        emailField.setText(currentUser.getEmail());
    }

    /**
     * Handles the action when the user clicks the "Update Settings" button.
     * It updates the user's name and email with the values entered in the text fields.
     * If the update is successful, a success message is displayed; otherwise, an error message is shown.
     */
    @FXML
    protected void onUpdateSettings() {
        User currentUser = LoginService.getInstance().getCurrentUser();
        currentUser.setName(nameField.getText());
        currentUser.setEmail(emailField.getText());

        if (LoginService.getInstance().updateUser(currentUser)) {
            messageLabel.setText("Settings updated successfully!");
            messageLabel.setStyle("-fx-text-fill: green;");
        } else {
            messageLabel.setText("Failed to update settings. Please try again.");
            messageLabel.setStyle("-fx-text-fill: red;");
        }
    }

    /**
     * Handles the action when the user clicks the "Sign Out" button.
     * It logs the user out by clearing the current user session and switches the scene to the login view.
     */
    @FXML
    protected void onSignOutButton() {
        LoginService.getInstance().dropCurrentUser();
        StageFactory.getInstance().switchScene(StageView.LOGIN);
    }
}