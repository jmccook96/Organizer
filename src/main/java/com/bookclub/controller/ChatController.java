package com.bookclub.controller;

import com.bookclub.iao.IChatAO;
import com.bookclub.mao.ChatMAO;
import com.bookclub.model.ChatMessage;
import com.bookclub.service.LoginService;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ChatController {
    @FXML private VBox chatBox;
    @FXML private ScrollPane chatScrollPane;
    @FXML private TextField messageInput;
    
    private IChatAO chatAO;
    private int currentChatId = 1;
    
    @FXML public void initalize() {
        chatAO = new ChatMAO(); // TODO: Migrate to DAO
        currentChatId = 1;
    }
    
    @FXML private void sendMessage() {
        String msgEntered = messageInput.getText().trim();
        if (msgEntered.isEmpty())
            return;
        
        ChatMessage newMsg = new ChatMessage(getActiveUserId(), currentChatId, msgEntered);
        try {
            chatAO.InsertMessage(newMsg);
        } catch (Exception e) {
            System.out.println("Failed to send new message.");
            System.out.println(e.getMessage());
        }
        
        displayMessage(newMsg);
        messageInput.clear();
    }
    
    // TODO: Implement passing chatId.
    private void loadChatMessages() {
        try {
            for (ChatMessage msg : chatAO.GetMessagesByChatId(1))
                displayMessage(msg);
        } catch (Exception e) {
            System.out.println("Failed to load messages for chatId: ");
            System.out.println(e.getMessage());
        }
        
        // Scroll to bottom after loading.
        scrollToBottom();
    }
    
    private void displayMessage(ChatMessage message) {
        // Create a new HBox per message.
        HBox msgBox = new HBox();
        Label msgLabel = new Label(message.getMessage());
        
        // Check if the author is the current user, and pin to the left.
        // TODO: Collapse styles to be inheritve
        // TODO: Investigate setting colour based on user preferences.
        if (message.getAuthorId() == getActiveUserId()) {
            msgBox.setAlignment(Pos.BASELINE_LEFT);
            msgLabel.setStyle("-fx-background-color: lightgreen; -fx-padding: 10px; -fx-border-radius: 10px; -fx-background-radius: 10px;");
        } else {
            msgBox.setAlignment(Pos.BASELINE_RIGHT);
            msgLabel.setStyle("-fx-background-color: lightgray; -fx-padding: 10px; -fx-border-radius: 10px; -fx-background-radius: 10px;");
        }
        
        msgBox.getChildren().add(msgLabel);
        chatBox.getChildren().add(msgBox);
        
        // Scroll to bottom (?)
        scrollToBottom();
    }
    
    private int getActiveUserId() {
        return LoginService.getCurrentUser().getId();
    }
    
    private void scrollToBottom() {
        chatScrollPane.layout();
        chatScrollPane.setVvalue(1.);
    }
}
