package com.bookclub.controller;

import com.bookclub.iao.IChatAO;
import com.bookclub.dao.ChatDAO;
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
    @FXML private VBox       chatBox;
    @FXML private ScrollPane chatScrollPane;
    @FXML private TextField  messageInput;
    @FXML private Label      chatTitleLabel;
    
    private IChatAO chatAO;
    private int currentChatId; // TODO: Expose with setter if we implement multiple bookclubs/chats.
    
    @FXML public void initialize() {
        chatAO = new ChatDAO();
        currentChatId = 1;
        
        setChatTitleLabel("Default Chat Title");
        loadChatMessages();
    }
    
    public void setChatTitleLabel(String titleLabel) {
        chatTitleLabel.setText(titleLabel);
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
    
    private void loadChatMessages() {
        try {
            for (ChatMessage msg : chatAO.GetMessagesByChatId(currentChatId))
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
