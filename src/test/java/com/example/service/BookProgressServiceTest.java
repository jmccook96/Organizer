package com.example.service;

import com.bookclub.mao.BookProgressMAO;
import com.bookclub.mao.UserMAO;
import com.bookclub.model.Book;
import com.bookclub.model.BookProgress;
import com.bookclub.model.User;
import com.bookclub.service.BookProgressService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BookProgressServiceTest {

    private BookProgressService bookProgressService;
    private BookProgressMAO bookProgressMAO;
    private Book book;
    private User user;

    @BeforeEach
    public void setUp() {
        bookProgressMAO = new BookProgressMAO();
        UserMAO userMAO = new UserMAO();
        book = new Book(1, "TestTitle", "TestAuthor", "TestGenre", 100);
        user = new User(1, "testUser", "testPassword", "Test Name", "test@example.com");
        BookProgressService.initialize(bookProgressMAO, userMAO);
        bookProgressService = BookProgressService.getInstance();
    }

    @Test
    void testGetBookProgress_NoProgressExists() {
        BookProgress actual = bookProgressService.getBookProgress(book, user).orElse(null);
        assertNull(actual);
    }

    @Test
    void testGetBookProgress_ProgressExists() {
        bookProgressMAO.addBookProgress(new BookProgress(book.getId(), user.getId(), 1));

        BookProgress actual = bookProgressService.getBookProgress(book, user).orElse(null);

        assertNotNull(actual);
        assertEquals(1, actual.getBookId());
        assertEquals(1, actual.getUserId());
        assertEquals(1, actual.getChapterNumber());
    }

    @Test
    void testStartBookProgress_NewProgressPermitted() {
        boolean hasNewProgress = bookProgressService.startBookProgress(book, user);
        BookProgress actual = bookProgressService.getBookProgress(book, user).orElse(null);

        assertTrue(hasNewProgress);
        assertNotNull(actual);
    }

    @Test
    void testStartBookProgress_NewProgressNotPermitted() {
        bookProgressMAO.addBookProgress(new BookProgress(book.getId(), user.getId(), 1));

        boolean hasNewProgress = bookProgressService.startBookProgress(book, user);
        BookProgress actual = bookProgressService.getBookProgress(book, user).orElse(null);

        assertFalse(hasNewProgress);
        assertNotNull(actual);
    }

    @Test
    void testFinishBookProgress_FinishProgressNotPermitted() {
        boolean hasFinished = bookProgressService.finishBookProgress(book, user);
        BookProgress actual = bookProgressService.getBookProgress(book, user).orElse(null);

        assertFalse(hasFinished);
        assertNull(actual);
    }

    @Test
    void testFinishBookProgress_FinishProgressPermitted() {
        bookProgressMAO.addBookProgress(new BookProgress(book.getId(), user.getId(), 1));

        boolean hasFinished = bookProgressService.finishBookProgress(book, user);
        BookProgress actual = bookProgressService.getBookProgress(book, user).orElse(null);

        assertTrue(hasFinished);
        assertNull(actual);
    }

    @Test
    void testSaveBookProgress_ProgressExists() {
        BookProgress actual = new BookProgress(book.getId(), user.getId(), 1);
        bookProgressMAO.addBookProgress(actual);

        boolean hasSaved = bookProgressService.saveBookProgress(book, user, 10);

        assertTrue(hasSaved);
        assertEquals(1, actual.getBookId());
        assertEquals(1, actual.getUserId());
        assertEquals(10, actual.getChapterNumber());
    }

    @Test
    void testSaveBookProgress_NoProgressExists() {
        boolean hasSaved = bookProgressService.saveBookProgress(book, user, 10);
        BookProgress actual = bookProgressService.getBookProgress(book, user).orElse(null);

        assertTrue(hasSaved);
        assertNotNull(actual);
        assertEquals(1, actual.getBookId());
        assertEquals(1, actual.getUserId());
        assertEquals(10, actual.getChapterNumber());
    }

    @Test
    void testGetBookProgressListForBook_NoProgress() {
        List<BookProgress> expected = new ArrayList<>();
        List<BookProgress> actual = bookProgressService.getBookProgressListForBook(book);

        assertEquals(expected, actual);
    }

    @Test
    void testGetBookProgressListForBook_OneProgress() {
        bookProgressMAO.addBookProgress(new BookProgress(book.getId(), 1, 1));

        List<BookProgress> actual = bookProgressService.getBookProgressListForBook(book);

        assertEquals(1, actual.size());
        assertEquals(1, actual.get(0).getBookId());
        assertEquals(1, actual.get(0).getUserId());
        assertEquals(1, actual.get(0).getChapterNumber());
    }

    @Test
    void testGetBookProgressListForBook_MultipleProgress() {
        bookProgressMAO.addBookProgress(new BookProgress(book.getId(), 1, 10));
        bookProgressMAO.addBookProgress(new BookProgress(book.getId(), 2, 3));
        bookProgressMAO.addBookProgress(new BookProgress(book.getId(), 3, 1));

        List<BookProgress> actual = bookProgressService.getBookProgressListForBook(book);

        assertEquals(3, actual.size());
        assertEquals(1, actual.get(0).getBookId());
        assertEquals(3, actual.get(0).getUserId());
        assertEquals(1, actual.get(0).getChapterNumber());

        assertEquals(1, actual.get(1).getBookId());
        assertEquals(2, actual.get(1).getUserId());
        assertEquals(3, actual.get(1).getChapterNumber());

        assertEquals(1, actual.get(2).getBookId());
        assertEquals(1, actual.get(2).getUserId());
        assertEquals(10, actual.get(2).getChapterNumber());
    }
}
