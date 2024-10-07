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

    @FXML
    protected void onSignOutButton() {
        LoginService.getInstance().dropCurrentUser();
        StageFactory.getInstance().switchScene(StageView.LOGIN);
    }
}