package com.bookclub.controller;

import com.bookclub.dao.ChatDAO;
import com.bookclub.dao.UserDAO;
import com.bookclub.service.ChatService;
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
    
    static private final String messageFormattingStyle = 
            "-fx-padding: 10px; -fx-border-radius: 10px; -fx-background-radius: 10px; -fx-background-color: ";

    public ChatController() {
        ChatService.initialize(new ChatDAO(), new UserDAO());
    }
    
    @FXML public void initialize() {
        setChatTitleLabel("Default Chat Title");
        reloadChatMessages();
    }
    
    public void setChatTitleLabel(String titleLabel) {
        chatTitleLabel.setText(titleLabel);
    }
    
    @FXML private void sendMessage() {
        String msgEntered = messageInput.getText().trim();
        if (msgEntered.isEmpty())
            return;
        
        ChatService.getInstance().sendMessage(msgEntered);
        reloadChatMessages();
        messageInput.clear();
    }
    
    private void reloadChatMessages() {
        flushDisplay();
        
        for (ChatService.ChatDisplay msg : ChatService.getInstance().getDisplayMessages())
            displayMessage(msg);
        
        // Scroll to bottom after loading.
        scrollToBottom();
    }
    
    private void displayMessage(ChatService.ChatDisplay message) {
        VBox messageContainer = new VBox();
        messageContainer.setSpacing(5); // Set some spacing between username and message
        
        // Create a new HBox per message.
        HBox msgBox = new HBox();
        Label msgLabel = new Label(message.message);
        
        String msgColour = "lightgray;"; // Default message colour
        
        // Check if the author is the current user, and pin to the left.
        // TODO: Investigate setting colour based on user preferences.
        if (ChatService.getInstance().isActiveUsername(message.username)) {
            msgBox.setAlignment(Pos.BASELINE_LEFT);
            msgColour = "lightgreen;";
        } else {
            msgBox.setAlignment(Pos.BASELINE_RIGHT);

            // Add a label to show the username
            Label usernameLabel = new Label(message.username);
            usernameLabel.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: gray;");

            // Add the username above the message
            messageContainer.getChildren().add(usernameLabel);
        }

        // Set the style of message.
        msgLabel.setStyle(messageFormattingStyle + msgColour);
        
        messageContainer.getChildren().add(msgLabel);
        msgBox.getChildren().add(messageContainer);
        chatBox.getChildren().add(msgBox);
        
        // Scroll to bottom (?)
        scrollToBottom();
    }
    
    private void scrollToBottom() {
        chatScrollPane.layout();
        chatScrollPane.setVvalue(1.);
    }
    
    // Clears all messages in the displayed chat.
    private void flushDisplay() {
        chatBox.getChildren().clear();
    }
}
