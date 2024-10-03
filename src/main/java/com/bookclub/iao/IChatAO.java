package com.bookclub.iao;

import com.bookclub.model.ChatMessage;

import java.sql.SQLException;
import java.util.List;

public interface IChatAO {
    /**
     * Inserts a new ChatMessage into the database.
     *
     * @param chatMessage The ChatMessage object to be inserted.
     * @return The boolean success of inserting the message.
     * @throws SQLException If the insertion fails.
     */
    public boolean InsertMessage(ChatMessage chatMessage) throws SQLException;
    
    /**
     * Retrieves all ChatMessage objects from the database for a given chat ID.
     *
     * @param chatId The ID of the chat for which messages should be retrieved.
     * @return A list of ChatMessage objects belonging to the specified chat.
     * @throws SQLException If the retrieval fails.
     */
    public List<ChatMessage> GetMessagesByChatId(int chatId) throws SQLException;
}
