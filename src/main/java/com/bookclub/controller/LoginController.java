package com.bookclub.controller;

import com.bookclub.dao.UserDAO;
import com.bookclub.service.LoginService;
import com.bookclub.util.StageFactory;
import com.bookclub.util.StageView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {    
    @FXML 
    private PasswordField passwordField;
    @FXML
    private TextField usernameField;
    
    public LoginController() {
        LoginService.initialize(new UserDAO());
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
