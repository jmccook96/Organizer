package com.bookclub.dao;

import com.bookclub.iao.IChatAO;
import com.bookclub.model.ChatMessage;
import com.bookclub.util.DatabaseManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ChatDAO implements IChatAO {

    private DatabaseManager dbManager;

    public ChatDAO() {
        dbManager = DatabaseManager.getInstance();
        createTable();
    }

    @Override
    public boolean InsertMessage(ChatMessage chatMessage) throws SQLException {
        String query = "INSERT INTO ChatMessages (chatId, authorId, messageContent, timestamp) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = dbManager.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, chatMessage.getChatId());
            statement.setInt(2, chatMessage.getAuthorId());
            statement.setString(3, chatMessage.getMessage());
            statement.setString(4, chatMessage.getTimestamp());

            int rowsInserted = statement.executeUpdate();

            // Retrieve generated message ID
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    chatMessage.setMessageId(generatedKeys.getInt(1));
                }
            }
            return rowsInserted > 0;
        }
    }

    @Override
    public List<ChatMessage> GetMessagesByChatId(int chatId) throws SQLException {
        String query = "SELECT * FROM ChatMessages WHERE chatId = ?";
        List<ChatMessage> messages = new ArrayList<>();

        try (PreparedStatement statement = dbManager.getConnection().prepareStatement(query)) {
            statement.setInt(1, chatId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int messageId = resultSet.getInt("messageId");
                    int authorId = resultSet.getInt("authorId");
                    String messageContent = resultSet.getString("messageContent");
                    String timestamp = resultSet.getString("timestamp");

                    ChatMessage chatMessage = new ChatMessage(messageId, chatId, authorId, messageContent, timestamp);
                    messages.add(chatMessage);
                }
            }
        }
        return messages;
    }

    private void createTable() {
        String query = "CREATE TABLE IF NOT EXISTS ChatMessages ("
                + "messageId INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "chatId INTEGER NOT NULL,"
                + "authorId INTEGER NOT NULL,"
                + "messageContent TEXT NOT NULL,"
                + "timestamp DATETIME DEFAULT CURRENT_TIMESTAMP)";

        try (Statement statement = dbManager.getConnection().createStatement()) {
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
