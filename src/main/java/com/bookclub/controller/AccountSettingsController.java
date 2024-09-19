package com.bookclub.controller;

import com.bookclub.service.LoginService;
import com.bookclub.util.StageFactory;
import com.bookclub.util.StageView;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class AccountSettingsController {
    @FXML
    private Label usernameLabel;

    // This was giving an error. Commented out so Account Settings page can be accessed.
//    public void initialize() {
//        usernameLabel.setText(LoginService.getCurrentUser().getUsername());
//    }
    
    @FXML
    protected void onSignOutButton() {
        // Revoke active user, and transition back to Login screen.
        LoginService.getInstance().dropCurrentUser();
        StageFactory.getInstance().switchScene(StageView.LOGIN);
    }
}
