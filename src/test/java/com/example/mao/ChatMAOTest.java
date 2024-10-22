package com.example.mao;

import com.bookclub.mao.ChatMAO;
import com.bookclub.model.ChatMessage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ChatMAOTest {

    private ChatMAO chatMAO;

    @BeforeEach
    public void setUp() {
        chatMAO = new ChatMAO();
        //chats.add(new ChatMessage(1, 1, "Test Message"));
        //chats.add(new ChatMessage(2, 1, "Another test message"));
    }

    // Test InsertMessage
    @Test
    public void testInsertMessageSuccess() throws SQLException {
        ChatMessage newMessage = new ChatMessage(3, 1, "New test message");
        boolean result = chatMAO.InsertMessage(newMessage);
        assertTrue(result);

        List<ChatMessage> messages = chatMAO.GetMessagesByChatId(1);
        assertEquals(1, messages.size());
    }

    // Test GetMessagesByChatId
    @Test
    public void testGetMessagesByChatIdSuccess() throws SQLException {
        List<ChatMessage> messages = chatMAO.GetMessagesByChatId(1);
        assertNotNull(messages);
        assertEquals(0, messages.size());

        chatMAO.InsertMessage(new ChatMessage(1, 1, "Test Message"));
        chatMAO.InsertMessage(new ChatMessage(2, 1, "Another test message"));

        messages = chatMAO.GetMessagesByChatId(1);
        assertEquals("Test Message", messages.get(0).getMessage());
        assertEquals("Another test message", messages.get(1).getMessage());
    }

    @Test
    public void testGetMessagesByChatIdNoMessages() throws SQLException {
        List<ChatMessage> messages = chatMAO.GetMessagesByChatId(99);  // Non-existing chat ID
        assertTrue(messages.isEmpty());
    }

    // Test edge cases
    @Test
    public void testInsertNullMessage() {
        assertThrows(NullPointerException.class, () -> chatMAO.InsertMessage(null));
    }

    @Test
    public void testInsertEmptyMessage() throws SQLException {
        ChatMessage emptyMessage = new ChatMessage(3, 1, "");
        boolean result = chatMAO.InsertMessage(emptyMessage);
        assertTrue(result);

        List<ChatMessage> messages = chatMAO.GetMessagesByChatId(1);
        assertEquals(1, messages.size());  // The new empty message should still be added
    }

    @Test
    public void testGetMessagesByChatIdInvalidId() throws SQLException {
        assertThrows(IllegalArgumentException.class, () -> chatMAO.GetMessagesByChatId(-1));
    }
}
