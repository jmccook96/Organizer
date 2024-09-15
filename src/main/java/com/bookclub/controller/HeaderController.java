package com.bookclub.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.w3c.dom.Text;

public class HeaderController {
    @FXML
    private Label headerTitle;
    
    HeaderController(Text headerTitle) {
        this.headerTitle = (Label) headerTitle;
    }
}
