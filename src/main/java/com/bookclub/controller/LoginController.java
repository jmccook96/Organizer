package com.bookclub.controller;

import com.bookclub.dao.UserDAO;
import com.bookclub.service.LoginService;
import com.bookclub.util.StageFactory;
import com.bookclub.util.StageView;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class LoginController {
    @FXML
    private BorderPane loginBorderPane;
    @FXML
    private VBox logoVBox;
    @FXML
    private ImageView logoImageView;
    @FXML
    private VBox loginVBox;
    @FXML
    private Label loginLabel;
    @FXML 
    private PasswordField passwordField;
    @FXML
    private TextField usernameField;
    @FXML
    private Button loginButton;
    @FXML
    private Button registerButton;
    
    public LoginController() {
        LoginService.initialize(new UserDAO());
    }

    @FXML
    private void initialize() {
        Platform.runLater(() -> loginLabel.requestFocus());
        logoVBox.prefWidthProperty().bind(loginBorderPane.widthProperty().multiply(0.45));
        loginVBox.prefWidthProperty().bind(logoVBox.widthProperty());
        logoImageView.fitWidthProperty().bind(logoVBox.prefWidthProperty().multiply(0.6));
        usernameField.maxWidthProperty().bind(loginVBox.widthProperty().multiply(0.8));
        passwordField.maxWidthProperty().bind(usernameField.widthProperty());
        usernameField.prefHeightProperty().bind(loginBorderPane.heightProperty().multiply(0.05));
        passwordField.prefHeightProperty().bind(usernameField.heightProperty());
        loginButton.prefHeightProperty().bind(usernameField.heightProperty());
        loginButton.prefWidthProperty().bind(usernameField.widthProperty());
        registerButton.prefHeightProperty().bind(usernameField.heightProperty());
        registerButton.prefWidthProperty().bind(usernameField.widthProperty());
    }
    
    @FXML
    public void handleLogin(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (LoginService.getInstance().attemptLogin(username, password)) {
            showAlert("Login successful.", "Welcome " + username + "!");
            StageFactory.getInstance().switchScene(StageView.HOME);
        } else {
            showAlert("Login Failed.", "Incorrect username or password.");
           // TODO: Offer password reset or something?
        }
    }

    @FXML
    public void handleNavigateToRegister() {
        StageFactory.getInstance().switchScene(StageView.REGISTER);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
