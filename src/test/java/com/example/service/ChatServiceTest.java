package com.example.service;

import com.bookclub.service.ChatService;
import com.bookclub.mao.ChatMAO;
import com.bookclub.mao.UserMAO;
import com.bookclub.model.ChatMessage;
import com.bookclub.model.User;
import com.bookclub.service.LoginService;
import com.bookclub.util.PasswordHasher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ChatServiceTest {

    private ChatService chatService;
    private User mockUser;

    @BeforeEach
    public void setUp() {
        // Create instances of the MAOs
        UserMAO userMAO = new UserMAO();
        ChatMAO chatMAO = new ChatMAO();

        // Add some initial chat messages to the ChatMAO
        try {
            chatMAO.InsertMessage(new ChatMessage(1, 1, 1, "Hello", "2024-10-22 12:00"));
            chatMAO.InsertMessage(new ChatMessage(2, 1, 1, "World", "2024-10-22 12:01"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Create a mock user and add them to the UserMAO
        String mockPassword = "Password1";
        mockUser = new User("User1", PasswordHasher.hashPassword(mockPassword));
        mockUser.setId(1);
        userMAO.addUser(mockUser);
        
        // Set up login service for testing.
        LoginService.initialize(userMAO);
        LoginService.getInstance().attemptLogin(mockUser.getUsername(), mockPassword);

        // Set the content to the chat instance used.
        ChatService.initialize(chatMAO, userMAO);
        chatService = ChatService.getInstance();
    }

    // Test getDisplayMessages
    @Test
    public void testGetDisplayMessages() {
        List<ChatService.ChatDisplay> chatDisplays = chatService.getDisplayMessages();
        assertNotNull(chatDisplays);
        assertEquals(2, chatDisplays.size());

        ChatService.ChatDisplay firstMessage = chatDisplays.get(0);
        assertEquals("User1", firstMessage.username);
        assertEquals("Hello", firstMessage.message);
        assertEquals("2024-10-22 12:00", firstMessage.timestamp);
    }

    // Test getUsernameById
    @Test
    public void testGetUsernameById() {
        String username = chatService.getUsernameById(1);
        assertEquals("User1", username);
    }

    @Test
    public void testGetUsernameByIdNotFound() {
        String username = chatService.getUsernameById(999); // Non-existent user
        assertEquals("", username); // Expect empty string when user not found
    }

    // Test isActiveUsername
    @Test
    public void testIsActiveUsername() {
        boolean isActive = chatService.isActiveUsername("User1");
        assertTrue(isActive);
    }

    @Test
    public void testIsActiveUsernameNotActive() {
        boolean isActive = chatService.isActiveUsername("AnotherUser");
        assertFalse(isActive);
    }

    // Test sendMessage
    @Test
    public void testSendMessage() {
        chatService.sendMessage("Test message");

        List<ChatService.ChatDisplay> chatDisplays = chatService.getDisplayMessages();
        assertEquals(3, chatDisplays.size()); // We expect a third message after sending
        assertEquals("Test message", chatDisplays.get(2).message);
    }
}
