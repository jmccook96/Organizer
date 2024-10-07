package com.bookclub.mao;

import com.bookclub.iao.IChatAO;
import com.bookclub.model.ChatMessage;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ChatMAO implements IChatAO  {
    private List<ChatMessage> chats;
    
    public ChatMAO() {
        chats = new ArrayList<>();
        addTestData();
    }

    private void addTestData() {
        chats.add(new ChatMessage(1, 1, "Test Message"));
        chats.add(new ChatMessage(2, 1, "Another test message"));
    }
    
    @Override
    public boolean InsertMessage(ChatMessage chatMessage) throws SQLException {
        if (!chats.add(chatMessage))
            throw new SQLException("ChatMAO failed to add message.");
        
        return true;
    }

    @Override
    public List<ChatMessage> GetMessagesByChatId(int chatId) throws SQLException {
        List<ChatMessage> ret = new ArrayList<>();
        for (ChatMessage msg : chats){
            if (msg.getChatId() == chatId)
                ret.add(msg);
        }
        return ret;
    }
}
