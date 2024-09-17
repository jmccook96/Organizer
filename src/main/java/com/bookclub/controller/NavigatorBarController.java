package com.bookclub.controller;

import com.bookclub.util.StageFactory;
import com.bookclub.util.StageView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import java.awt.event.ActionEvent;

public class NavigatorBarController {
        private Button activeButton;

        // FXML references to buttons
        @FXML private Button accountButton;
        @FXML private Button booksButton;
        @FXML private Button homeButton;
        @FXML private Button eventsButton;
        @FXML private Button chatButton;

        public void initialize() {
            setActiveButton(homeButton);
        }
        
        private void setActiveButton(Button newActiveButton) {
            if (activeButton != null) {
                activeButton.setStyle(null);
            }
            activeButton = newActiveButton;
            activeButton.setStyle("-fx-background-color: #00aaff;");
        }
        
        private void navigateView(StageView newView) {
            if (newView == null) {
                navigateTest();
                return;
            }
            switch (newView) {
                case ACCOUNT_SETTINGS -> setActiveButton(accountButton);
                case BOOKS            -> setActiveButton(booksButton);
                case HOME             -> setActiveButton(homeButton);
                case EVENTS           -> setActiveButton(eventsButton);
            }

            StageFactory.getInstance().switchScene(newView);
        }
    
        private void navigateTest() {
            System.out.println("YET TO BE IMPLEMENTED. GOING HOME.");
            navigateView(StageView.HOME);
        }

        public void navigateAccount() {
            navigateView(StageView.ACCOUNT_SETTINGS);
        }

        public void navigateBooks() {
            navigateView(StageView.BOOKS);
        }
    
        public void navigateHome() {
            navigateView(StageView.HOME);
        }
        
        public void navigateEvents() {
            navigateView(StageView.EVENTS);
        }
        
        public void navigateChat() {
            navigateTest();
        }
}
