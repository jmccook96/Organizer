package com.bookclub.service;

import com.bookclub.iao.IChatAO;
import com.bookclub.iao.IUserAO;
import com.bookclub.model.ChatMessage;
import com.bookclub.model.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatService {
    private static ChatService instance;

    /**
     * Helper class for UI handling of messages.
     */
    public static class ChatDisplay {

        // Not strictly necessary, if we want to add reporting of message etc. will make life easier.
        public final int messageId;

        public final String username;
        public final String message;
        public final String timestamp;

        public ChatDisplay(int messageId, String username, String message, String timestamp) {
            this.messageId = messageId;
            this.username = username;
            this.message = message;
            this.timestamp = timestamp;
        }
    }
    
    private IUserAO userAO;
    private IChatAO chatAO;
    
    private int chatId;
    
    public static ChatService getInstance() {
        if (instance == null) {
            throw new IllegalStateException("ChatService must be initialized before instance is accessed.");
        }
        return instance;
    }
    
    public static void initialize(IChatAO chatAO, IUserAO userAO) {
        if (instance == null) {
            instance = new ChatService();
        }
        instance.chatAO = chatAO;
        instance.userAO = userAO;
        
    }
    
    private ChatService() {
        setChatId(1);
        // TODO: Investigate thread spool for reloading messages in background
    }
    
    public void setChatId(int chatId) {
        this.chatId = chatId;
    }
    
    public List<ChatDisplay> getDisplayMessages() {
        List<ChatDisplay> ret = new ArrayList<>();
        
        // Map for IDs to username for optimisation to not choke SQL on big chats.
        Map<Integer, String> userIdToNameMap = new HashMap<>();
        
        for (ChatMessage msg : loadChatMessages()) {
            if (!userIdToNameMap.containsKey(msg.getAuthorId())) {
                // Load the users username and add it to the map.
                userIdToNameMap.put(msg.getAuthorId(),
                        getUsernameById(msg.getAuthorId()));
            }
            
            ChatDisplay displayMsg = new ChatDisplay(
                    msg.getMessageId(),
                    userIdToNameMap.get(msg.getAuthorId()),
                    msg.getMessage(),
                    msg.getTimestamp());
            
            ret.add(displayMsg);
        }
        return ret;
    }
    
    private List<ChatMessage> loadChatMessages() {
        try {
            return chatAO.GetMessagesByChatId(chatId);
        } catch (SQLException e) {
            System.out.println("Failed to load messages for chatId");
            e.printStackTrace();
        }
        return null;
    }
    
    public void sendMessage(String message) {
        ChatMessage newMsg = new ChatMessage(getActiveUserId(), chatId, message);
        try {
            chatAO.InsertMessage(newMsg);
        } catch (SQLException e) {
            System.out.println("Failed to insert message: " + message);
            e.printStackTrace();
        }
    }
    
    public int getActiveUserId() {
        return LoginService.getCurrentUser().getId();
    }
    
    public boolean isActiveUsername(String username) {
        return username.equals(LoginService.getCurrentUser().getUsername());
    }
    
    public String getUsernameById(int userId) {
        User userFound = userAO.findUserById(userId);
        return userFound == null ? "" : userFound.getUsername();
    }
}
