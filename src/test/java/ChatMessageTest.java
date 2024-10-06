import com.bookclub.model.ChatMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ChatMessageTest {
    private ChatMessage msg;
    
    @BeforeEach
    public void setUp() { msg = new ChatMessage(1, 2, 3, "Hello, World!", "2024-09-30 12:34:56"); }
    
    @Test
    public void testFullConstructor() {
        assertEquals(1, msg.getMessageId());
        assertEquals(2, msg.getChatId());
        assertEquals(3, msg.getAuthorId());
        assertEquals("Hello, World!", msg.getMessage());
        assertEquals("2024-09-30 12:34:56", msg.getTimestamp());
    }

    @Test
    public void testPartialConstructor() {
        msg = new ChatMessage(3, 2, "Another message");
        assertEquals(3, msg.getAuthorId());
        assertEquals(2, msg.getChatId());
        assertEquals("Another message", msg.getMessage());
        assertNull(msg.getTimestamp()); // No timestamp provided
    }
    
    @Test
    public void testGetAndSetMessageId() {
        msg.setMessageId(99);
        assertEquals(99, msg.getMessageId());
    }

    @Test
    public void testGetAndSetChatId() {
        msg.setChatId(5);
        assertEquals(5, msg.getChatId());
    }

    @Test
    public void testGetAndSetAuthorId() {
        msg.setAuthorId(10);
        assertEquals(10, msg.getAuthorId());
    }

    @Test
    public void testGetAndSetMessageContent() {
        msg.setMessage("New message content");
        assertEquals("New message content", msg.getMessage());
    }

    @Test
    public void testGetAndSetTimestamp() {
        msg.setTimestamp("2024-10-01 10:00:00");
        assertEquals("2024-10-01 10:00:00", msg.getTimestamp());
    }

    // Edge cases: Ensure no illegal or unexpected behavior
    @Test
    public void testSetMessageContentNull() {
        msg.setMessage(null);
        assertNull(msg.getMessage());
    }

    @Test
    public void testSetTimestampNull() {
        msg.setTimestamp(null);
        assertNull(msg.getTimestamp());
    }

    @Test
    public void testToString() {
        String expected = "ChatMessage{messageId=1, chatId=2, authorId=3, message='Hello, World!', timestamp='2024-09-30 12:34:56'}";
        assertEquals(expected, msg.toString());
    }
    
    @Test
    public void testEmptyMessageContent() {
        msg.setMessage("");
        assertEquals("", msg.getMessage());
    }

    @Test
    public void testEmptyTimestamp() {
        msg.setTimestamp("");
        assertEquals("", msg.getTimestamp());
    }
    
    @Test
    public void testConstructorMessageNull() {
        ChatMessage nullMessage = new ChatMessage(1, 1, 1, null, "2024-09-30 12:34:56");
        assertNull(nullMessage.getMessage());
    }

    @Test
    public void testConstructorTimestampNull() {
        ChatMessage nullTimestampMessage = new ChatMessage(1, 1, 1, "Some message", null);
        assertNull(nullTimestampMessage.getTimestamp());
    }

    @Test
    public void testConstructorInvalidChatId() {
        assertThrows(IllegalArgumentException.class, () -> new ChatMessage(1, -1, 3, "Message", "2024-09-30"));
        assertThrows(IllegalArgumentException.class, () -> new ChatMessage(1, 0, 3, "Message", "2024-09-30"));
    }

    @Test
    public void testConstructorInvalidAuthorId() {
        assertThrows(IllegalArgumentException.class, () -> new ChatMessage(1, 2, -1, "Message", "2024-09-30"));
        assertThrows(IllegalArgumentException.class, () -> new ChatMessage(1, 2, 0, "Message", "2024-09-30"));
    }

    @Test
    public void testSetChatIdInvalid() {
        assertThrows(IllegalArgumentException.class, () -> msg.setChatId(0));
        assertThrows(IllegalArgumentException.class, () -> msg.setChatId(-5));
    }

    @Test
    public void testSetAuthorIdInvalid() {
        assertThrows(IllegalArgumentException.class, () -> msg.setAuthorId(0));
        assertThrows(IllegalArgumentException.class, () -> msg.setAuthorId(-1));
    }
}
