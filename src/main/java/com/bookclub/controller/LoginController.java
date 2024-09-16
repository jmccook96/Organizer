package com.bookclub.controller;

import com.bookclub.dao.UserDAO; // Use UserDAO instead of UserMAO
import com.bookclub.service.LoginService;
import com.bookclub.util.StageFactory;
import com.bookclub.util.StageView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
    private LoginService loginService;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField emailField;

    public LoginController() {

        loginService = new LoginService(new UserDAO());
    }

    @FXML
    public void handleLogin(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (loginService.authenticate(username, password)) {
            // Successful login
            showAlert("Login Successful", "Welcome " + username + "!");
            StageFactory.getInstance().switchScene(StageView.MAIN);
        } else {
            showAlert("Login Failed", "Incorrect username or password.");
            // TODO: Offer password reset or additional actions
        }
    }

    @FXML
    public void handleRegister() {
        // TODO: Transition to a register screen.
        String username = usernameField.getText();
        String password = passwordField.getText();
        String email = emailField.getText();  // Capture email
        // TODO: Additional data required for sign-up

        if (loginService.register(username, password, email)) {
            showAlert("Registration Successful", "User registered: " + username + ". Please login.");
        } else {
            showAlert("Registration Failed", "Registration failed. Please try again.");
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
