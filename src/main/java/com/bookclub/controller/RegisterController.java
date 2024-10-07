package com.bookclub.controller;

import com.bookclub.service.LoginService;
import com.bookclub.util.StageFactory;
import com.bookclub.util.StageView;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class RegisterController {
    @FXML
    private BorderPane registerBorderPane;
    @FXML
    private VBox logoVBox;
    @FXML
    private ImageView logoImageView;
    @FXML
    private VBox loginVBox;
    @FXML
    private Label registerLabel;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField emailField;
    @FXML
    private Button registerButton;
    @FXML
    private Button backToLoginButton;
    @FXML
    private Label messageLabel;

    @FXML
    private void initialize() {
        Platform.runLater(() -> registerLabel.requestFocus());
        logoVBox.prefWidthProperty().bind(registerBorderPane.widthProperty().multiply(0.45));
        loginVBox.prefWidthProperty().bind(logoVBox.widthProperty());
        logoImageView.fitWidthProperty().bind(logoVBox.prefWidthProperty().multiply(0.6));
        usernameField.maxWidthProperty().bind(loginVBox.widthProperty().multiply(0.8));
        passwordField.maxWidthProperty().bind(usernameField.widthProperty());
        confirmPasswordField.maxWidthProperty().bind(usernameField.widthProperty());
        usernameField.prefHeightProperty().bind(registerBorderPane.heightProperty().multiply(0.05));
        passwordField.prefHeightProperty().bind(usernameField.heightProperty());
        confirmPasswordField.prefHeightProperty().bind(usernameField.prefHeightProperty());
        nameField.maxWidthProperty().bind(usernameField.widthProperty());
        emailField.maxWidthProperty().bind(usernameField.widthProperty());
        nameField.prefHeightProperty().bind(usernameField.prefHeightProperty());
        emailField.prefHeightProperty().bind(usernameField.prefHeightProperty());
        registerButton.prefHeightProperty().bind(usernameField.heightProperty());
        registerButton.prefWidthProperty().bind(usernameField.widthProperty());
        backToLoginButton.prefHeightProperty().bind(usernameField.heightProperty());
        backToLoginButton.prefWidthProperty().bind(usernameField.widthProperty());
        messageLabel.prefWidthProperty().bind(usernameField.widthProperty());
    }

    @FXML
    protected void handleRegister() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        String name = nameField.getText();
        String email = emailField.getText();

        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            messageLabel.setText("Username and password are required.");
            return;
        }

        if (!password.equals(confirmPassword)) {
            messageLabel.setText("Passwords do not match.");
            return;
        }

        if (LoginService.getInstance().register(username, password, name.isEmpty() ? null : name, email.isEmpty() ? null : email)) {
            messageLabel.setText("Registration successful. You can now log in.");
            clearFields();
        } else {
            messageLabel.setText("Registration failed. Username may already exist.");
        }
    }


    private void clearFields() {
        usernameField.clear();
        passwordField.clear();
        confirmPasswordField.clear();
        nameField.clear();
        emailField.clear();
    }

    @FXML
    protected void handleBackToLogin() {
        StageFactory.getInstance().switchScene(StageView.LOGIN);
    }
}