package com.bookclub.model;

import java.util.Map;

public class ChatMessage {
    private int messageId;
    private int authorId;
    private int chatId; // Or bookclubId
    
    private String messageContent;
    private String timestamp;

    /**
     * Constructor for full suite of fields.
     *
     * @param messageId  The unique ID for this message (autogenerated).
     * @param chatId     The ID of the chat to which this message belongs.
     * @param authorId   The ID of the user who authored the message.
     * @param message    The actual message content.
     * @param timestamp  The time at which the message was sent.
     *                   
     * @throws IllegalArgumentException If chatId or authorId is invalid.
     */
    public ChatMessage(int messageId, int chatId, int authorId, String message, String timestamp) {
        setMessageId(messageId);
        setChatId(chatId);
        setAuthorId(authorId);
        setMessage(message);
        setTimestamp(timestamp);
    }
    
    /**
     * Constructor for non-automatically generated by SQL fields.
     *
     * @param chatId     The ID of the chat to which this message belongs.
     * @param authorId   The ID of the user who authored the message.
     * @param message    The actual message content.
     *                   
     * @throws IllegalArgumentException If chatId or authorId is invalid.
     */
    public ChatMessage(int authorId, int chatId, String message) {
        setChatId(chatId);
        setAuthorId(authorId);
        setMessage(message);
    }
    
    /**
     * Gets the unique ID of this message.
     * @return messageId The message ID.
     */
    public int getMessageId() { return messageId; }

    /**
     * Sets the unique ID of this message.
     * @param messageId The message ID.
     */
    public void setMessageId(int messageId) { this.messageId = messageId; }

    /**
     * Gets the ID of the chat to which this message belongs.
     * @return chatId The chat ID.
     */
    public int getChatId() { return chatId; }

    /**
     * Sets the ID of the chat to which this message belongs.
     * @param chatId The chat ID.
     *               
     * @throws IllegalArgumentException If chatId is invalid.
     */
    public void setChatId(int chatId) {
        if (chatId <= 0)
            throw new IllegalArgumentException("chatId must be greater than 0.");
        this.chatId = chatId; 
    }

    /**
     * Gets the ID of the user who authored the message.
     * @return authorId The author ID.
     */
    public int getAuthorId() { return authorId; }

    /**
     * Sets the ID of the user who authored the message.
     * @param authorId The author ID.
     *                 
     * @throws IllegalArgumentException If authorId is invalid.
     */
    public void setAuthorId(int authorId) {
        if (authorId <= 0)
            throw new IllegalArgumentException("authorId must be greater than 0.");
        this.authorId = authorId; 
    }

    /**
     * Gets the content of the message.
     * @return messageContent The message content.
     */
    public String getMessage() { return messageContent; }

    /**
     * Sets the content of the message.
     * @param message The message content.
     */
    public void setMessage(String message) { this.messageContent = message; }

    /**
     * Gets the timestamp when the message was sent.
     * @return timestamp The time the message was sent.
     */
    public String getTimestamp() { return timestamp; }

    /**
     * Sets the timestamp when the message was sent.
     * @param timestamp The time the message was sent.
     */
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }

    /**
     * Returns a string representation of the ChatMessage object
     * 
     * @return String representation of the object.
     */
    public String toString() {
        return "ChatMessage{" +
                "messageId=" + messageId +
                ", chatId=" + chatId +
                ", authorId=" + authorId +
                ", message='" + messageContent + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}
