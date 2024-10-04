package com.bookclub.controller;

import com.bookclub.model.User;
import com.bookclub.service.LoginService;
import com.bookclub.util.StageFactory;
import com.bookclub.util.StageView;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AccountSettingsController {
    @FXML
    private Label usernameLabel;
    @FXML
    private TextField nameField;
    @FXML
    private TextField emailField;

    public void initialize() {
        User currentUser = LoginService.getInstance().getCurrentUser();
        usernameLabel.setText(currentUser.getUsername());
        nameField.setText(currentUser.getName());
        emailField.setText(currentUser.getEmail());
    }

    @FXML
    protected void onSaveButton() {
        String name = nameField.getText();
        String email = emailField.getText();
        if (LoginService.getInstance().updateUserDetails(name, email)) {
            // Show success message
        } else {
            // Show error message
        }
    }

    @FXML
    protected void onSignOutButton() {
        LoginService.getInstance().dropCurrentUser();
        StageFactory.getInstance().switchScene(StageView.LOGIN);
    }
}