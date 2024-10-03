package com.bookclub.service;

import com.bookclub.dao.ChatDAO;
import com.bookclub.model.ChatMessage;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ChatService {
    private static ChatService instance;
    private ChatDAO chatDAO;

    private ChatService() {
        this.chatDAO = new ChatDAO();
    }

    public static ChatService getInstance() {
        if (instance == null) {
            instance = new ChatService();
        }
        return instance;
    }

    public boolean sendMessage(int chatId, String authorUsername, String message) {
        try {
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            ChatMessage chatMessage = new ChatMessage(authorUsername, chatId, message);
            chatMessage.setTimestamp(timestamp);
            return chatDAO.InsertMessage(chatMessage);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<ChatMessage> getChatMessages(int chatId) {
        try {
            return chatDAO.GetMessagesByChatId(chatId);
        } catch (SQLException e) {
            e.printStackTrace();
            return List.of();
        }
    }
}