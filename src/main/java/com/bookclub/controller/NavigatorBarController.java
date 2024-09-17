package com.bookclub.controller;

import com.bookclub.util.StageFactory;
import com.bookclub.util.StageView;

import java.awt.event.ActionEvent;

public class NavigatorBarController {
        public void navigateTest() {
            StageFactory.getInstance().switchScene(StageView.HOME);
            System.out.println("YET TO BE IMPLEMENTED. GOING HOME.");
        }
    
        public void navigateHome() {
            StageFactory.getInstance().switchScene(StageView.HOME);
        }
        
        public void navigateBooks() {
            StageFactory.getInstance().switchScene(StageView.BOOKS);
        }
        
        public void navigateAccount() {
            StageFactory.getInstance().switchScene(StageView.ACCOUNT_SETTINGS);
        }
        
        public void navigateEvents() {
            navigateTest();
        }
        
        public void navigateChat() {
            navigateTest();
        }
}
