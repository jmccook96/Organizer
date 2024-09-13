import com.bookclub.model.Book;
import com.bookclub.model.Review;
import com.bookclub.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReviewTest {

    private User user;
    private Book book;
    private Review review;

    @BeforeEach
    public void setUp() {
        user = new User("testUser", "testPassword");
        book = new Book("testTitle", "testAuthor");
        review = new Review(user, book, 5);
    }

    @Test
    public void testGetUser() {
        assertEquals(user, review.getUser());
    }

    @Test
    public void testGetBook() {
        assertEquals(book, review.getBook());
    }

    @Test
    public void testGetRating() {
        assertEquals(5, review.getRating());
    }

    @Test
    public void testSetRating() {
        review.setRating(1);
        assertEquals(1, review.getRating());
    }
}
