package com.bookclub.service;

import com.bookclub.dao.UserDAO;
import com.bookclub.iao.IChatAO;
import com.bookclub.iao.IUserAO;
import com.bookclub.mao.ChatMAO;
import com.bookclub.model.ChatDisplay;
import com.bookclub.model.ChatMessage;
import com.bookclub.model.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatService {
    private static ChatService instance;
    
    private final IUserAO userAO;
    private final IChatAO chatAO;
    
    private int chatId;
    
    public static ChatService getInstance() {
        if (instance == null) {
            instance = new ChatService();
        }
        return instance;
    }
    
    private ChatService() {
        userAO = new UserDAO();
        chatAO = new ChatMAO();
        
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
