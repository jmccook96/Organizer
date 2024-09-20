package com.bookclub.controller;

import com.bookclub.service.LoginService;
import com.bookclub.util.StageFactory;
import com.bookclub.util.StageView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class AccountSettingsController {
    @FXML
    private Label usernameLabel;
    @FXML
    private HBox navBar;

    public void initialize() {
        // Set nav bar button colour
        Button booksButton = (Button)navBar.lookup("#accountButton");
        if (booksButton != null) {
            booksButton.setStyle("-fx-background-color: lightsteelblue");
        }
        usernameLabel.setText(LoginService.getCurrentUser().getUsername());
    }
    
    @FXML
    protected void onSignOutButton() {
        // Revoke active user, and transition back to Login screen.
        LoginService.getInstance().dropCurrentUser();
        StageFactory.getInstance().switchScene(StageView.LOGIN);
    }
}
