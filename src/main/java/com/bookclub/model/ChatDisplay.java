package com.bookclub.model;

/**
 * Helper class for UI handling of messages.
 */
public class ChatDisplay {

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
