package com.bookclub.controller;

import com.bookclub.service.LoginService;
import com.bookclub.util.StageFactory;
import com.bookclub.util.StageView;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegisterController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private Label messageLabel;

    @FXML
    protected void handleRegister() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            messageLabel.setText("All fields are required.");
            return;
        }

        if (!password.equals(confirmPassword)) {
            messageLabel.setText("Passwords do not match.");
            return;
        }

        if (LoginService.getInstance().register(username, password)) {
            messageLabel.setText("Registration successful. You can now log in.");
            // Clear the fields after successful registration
            usernameField.clear();
            passwordField.clear();
            confirmPasswordField.clear();
        } else {
            messageLabel.setText("Registration failed. Username may already exist.");
        }
    }

    @FXML
    protected void handleBackToLogin() {
        StageFactory.getInstance().switchScene(StageView.LOGIN);
    }
}