import com.bookclub.model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookTest {

    private Book book;

    @BeforeEach
    public void setUp() {
        book = new Book("testTitle", "testAuthor");
    }

    @Test
    public void testGetTitle() {
        assertEquals("testTitle", book.getTitle());
    }

    @Test
    public void testSetTitle() {
        book.setTitle("testNewTitle");
        assertEquals("testNewTitle", book.getTitle());
    }

    @Test
    public void testGetAuthor() {
        assertEquals("testAuthor", book.getAuthor());
    }

    @Test
    public void testSetAuthor() {
        book.setAuthor("testNewAuthor");
        assertEquals("testNewAuthor", book.getAuthor());
    }
}
