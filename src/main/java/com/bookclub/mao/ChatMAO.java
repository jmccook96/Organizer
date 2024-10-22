package com.bookclub.mao;

import com.bookclub.iao.IChatAO;
import com.bookclub.model.ChatMessage;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ChatMAO implements IChatAO  {
    private final List<ChatMessage> chats;
    
    public ChatMAO() {
        chats = new ArrayList<>();
        addTestData();
    }

    private void addTestData() {
    }
    
    @Override
    public boolean InsertMessage(ChatMessage chatMessage) throws SQLException {
        if (chatMessage == null) {
            throw new NullPointerException("Added messages cannot be null.");
        }
        if (!chats.add(chatMessage))
            throw new SQLException("ChatMAO failed to add message.");
        
        return true;
    }

    @Override
    public List<ChatMessage> GetMessagesByChatId(int chatId) throws SQLException {
        if (chatId < 0) {
            throw new IllegalArgumentException("Chat ID must be valid.");
        }
        
        List<ChatMessage> ret = new ArrayList<>();
        for (ChatMessage msg : chats){
            if (msg.getChatId() == chatId)
                ret.add(msg);
        }
        return ret;
    }
}
