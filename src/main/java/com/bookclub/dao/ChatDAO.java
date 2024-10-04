package com.bookclub.dao;

import com.bookclub.iao.IChatAO;
import com.bookclub.model.ChatMessage;
import com.bookclub.util.DatabaseManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ChatDAO implements IChatAO {

    private Connection getConnection() {
        return DatabaseManager.getInstance().getConnection();
    }

    private void tryCreateTable() {
        String createTableSQL = """
        CREATE TABLE IF NOT EXISTS ChatMessages (
            MessageId INTEGER PRIMARY KEY AUTOINCREMENT,
            ChatId INTEGER NOT NULL,
            AuthorUsername TEXT NOT NULL,
            MessageContent TEXT,
            Timestamp TEXT,
            FOREIGN KEY (AuthorUsername) REFERENCES Users(Username),
            FOREIGN KEY (ChatId) REFERENCES BookClubs(Id)
        )
        """;

        try (Statement stmt = getConnection().createStatement()) {
            stmt.execute(createTableSQL);
            System.out.println("Successfully created or verified ChatMessages table.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ChatDAO() {
        tryCreateTable();
    }

    @Override
    public boolean InsertMessage(ChatMessage chatMessage) throws SQLException {
        String sql = "INSERT INTO ChatMessages (ChatId, AuthorUsername, MessageContent, Timestamp) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, chatMessage.getChatId());
            pstmt.setString(2, chatMessage.getAuthorUsername());  // Changed from getAuthorId() to getAuthorUsername()
            pstmt.setString(3, chatMessage.getMessage());
            pstmt.setString(4, chatMessage.getTimestamp());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        chatMessage.setMessageId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public List<ChatMessage> GetMessagesByChatId(int chatId) throws SQLException {
        List<ChatMessage> messages = new ArrayList<>();
        String sql = "SELECT * FROM ChatMessages WHERE ChatId = ? ORDER BY Timestamp ASC";
        try (PreparedStatement pstmt = getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, chatId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    messages.add(new ChatMessage(
                            rs.getInt("MessageId"),
                            rs.getInt("ChatId"),
                            rs.getString("AuthorUsername"),  // Changed from rs.getInt("AuthorId")
                            rs.getString("MessageContent"),
                            rs.getString("Timestamp")
                    ));
                }
            }
        }
        return messages;
    }
}