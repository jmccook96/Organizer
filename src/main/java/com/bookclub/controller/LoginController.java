package com.bookclub.controller;

import com.bookclub.mao.UserMAO;
import com.bookclub.service.LoginService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
    private LoginService loginService;
    
    @FXML 
    private PasswordField passwordField;
    @FXML
    private TextField usernameField;
    
    public LoginController() {
        // TODO: Swap to UserDAO.
        loginService = new LoginService(new UserMAO());
    }
    
    @FXML
    public void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        
        if (loginService.authenticate(username, password)) {
            // Successful login
            showAlert("Login successfufl.", "Welcome " + username + "!");
            // TODO: Navigate to home screen.
        } else {
            showAlert("Login Failed.", "Incorrect username or password.");
            // TODO: Offer password reset or something.
        }
    }
    
    @FXML
    public void handleRegister() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        // TODO: Additional mandated data on sign up here.
        
        if (loginService.register(username, password)) {
            showAlert("Registration Successful", "User registered: " + username + ".\nPlease login to continue.");
        } else {
            showAlert("Registration Failed!", "Reason goes here.");
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
