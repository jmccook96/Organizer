package com.bookclub.controller;

import com.bookclub.util.StageFactory;
import com.bookclub.util.StageView;

import java.awt.event.ActionEvent;

public class NavigatorBarController {
        public void navigateTest() {
            StageFactory.getInstance().switchScene(StageView.MAIN);
            System.out.println("YET TO BE IMPLEMENTED. GOING MAIN.");
        }
    
        public void navigateHome() {
            navigateTest();
        }
        
        public void navigateBooks() {
            StageFactory.getInstance().switchScene(StageView.BOOKS);
        }
        
        public void navigateAccount() {
            navigateTest();
        }
        
        public void navigateEvents() {
            navigateTest();
        }
        
        public void navigateChat() {
            navigateTest();
        }
}
