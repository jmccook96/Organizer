package com.bookclub.controller;

import com.bookclub.model.ChatMessage;
import com.bookclub.service.ChatService;
import com.bookclub.service.LoginService;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.util.List;

public class ChatController {

    @FXML
    private ListView<String> messageListView;

    @FXML
    private TextField messageInputField;

    private int currentChatId;

    public void initialize() {
        // Assuming we have a way to get the current chat ID
        currentChatId = 1; // This should be set properly when opening a chat
        refreshMessages();
    }

    @FXML
    private void handleSendMessage() {
        String messageContent = messageInputField.getText().trim();
        if (!messageContent.isEmpty()) {
            String currentUsername = LoginService.getCurrentUser().getUsername();
            boolean sent = ChatService.getInstance().sendMessage(currentChatId, currentUsername, messageContent);
            if (sent) {
                messageInputField.clear();
                refreshMessages();
            } else {
                // Show error message
            }
        }
    }

    private void refreshMessages() {
        messageListView.getItems().clear();
        List<ChatMessage> messages = ChatService.getInstance().getChatMessages(currentChatId);
        for (ChatMessage message : messages) {
            messageListView.getItems().add(formatMessage(message));
        }
    }

    private String formatMessage(ChatMessage message) {
        return String.format("[%s] %s: %s", message.getTimestamp(), message.getAuthorUsername(), message.getMessage());
    }
}