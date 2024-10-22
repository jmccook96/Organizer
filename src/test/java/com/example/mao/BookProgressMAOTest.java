package com.example.mao;

import com.bookclub.mao.BookProgressMAO;
import com.bookclub.model.BookProgress;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BookProgressMAOTest {

    private BookProgressMAO bookProgressMAO;

    @BeforeEach
    public void setUp() {
        bookProgressMAO = new BookProgressMAO();
    }

    // Test addBookProgress
    @Test
    public void testAddBookProgress() {
        BookProgress progress = new BookProgress(1, 101, 201, 5);
        boolean result = bookProgressMAO.addBookProgress(progress);
        assertTrue(result);
        assertEquals(1, bookProgressMAO.findBookProgressesByBook(101).size());
    }

    // Test findBookProgressByBookAndUser
    @Test
    public void testFindBookProgressByBookAndUserFound() {
        BookProgress progress = new BookProgress(1, 101, 201, 5);
        bookProgressMAO.addBookProgress(progress);

        BookProgress found = bookProgressMAO.findBookProgressByBookAndUser(101, 201);
        assertNotNull(found);
        assertEquals(101, found.getBookId());
        assertEquals(201, found.getUserId());
    }

    @Test
    public void testFindBookProgressByBookAndUserNotFound() {
        BookProgress found = bookProgressMAO.findBookProgressByBookAndUser(101, 201);
        assertNull(found);
    }

    // Test findBookProgressesByBook
    @Test
    public void testFindBookProgressesByBook() {
        bookProgressMAO.addBookProgress(new BookProgress(1, 101, 201, 5));
        bookProgressMAO.addBookProgress(new BookProgress(2, 101, 202, 3));

        List<BookProgress> progresses = bookProgressMAO.findBookProgressesByBook(101);
        assertEquals(2, progresses.size());
    }

    @Test
    public void testFindBookProgressesByBookEmpty() {
        List<BookProgress> progresses = bookProgressMAO.findBookProgressesByBook(101);
        assertTrue(progresses.isEmpty());
    }

    // Test findBookProgressesByUser
    @Test
    public void testFindBookProgressesByUser() {
        bookProgressMAO.addBookProgress(new BookProgress(1, 101, 201, 5));
        bookProgressMAO.addBookProgress(new BookProgress(2, 102, 201, 4));

        List<BookProgress> progresses = bookProgressMAO.findBookProgressesByUser(201);
        assertEquals(2, progresses.size());
    }

    @Test
    public void testFindBookProgressesByUserEmpty() {
        List<BookProgress> progresses = bookProgressMAO.findBookProgressesByUser(201);
        assertTrue(progresses.isEmpty());
    }

    // Test updateBookProgress
    @Test
    public void testUpdateBookProgressSuccess() {
        BookProgress progress = new BookProgress(1, 101, 201, 5);
        bookProgressMAO.addBookProgress(progress);

        BookProgress updatedProgress = new BookProgress(1, 101, 201, 10);
        boolean result = bookProgressMAO.updateBookProgress(updatedProgress);

        assertTrue(result);
        assertEquals(10, bookProgressMAO.findBookProgressByBookAndUser(101, 201).getChapterNumber());
    }

    @Test
    public void testUpdateBookProgressFailure() {
        BookProgress updatedProgress = new BookProgress(1, 101, 201, 10);
        boolean result = bookProgressMAO.updateBookProgress(updatedProgress);

        assertFalse(result);
    }

    // Test deleteBookProgress
    @Test
    public void testDeleteBookProgressSuccess() {
        BookProgress progress = new BookProgress(1, 101, 201, 5);
        bookProgressMAO.addBookProgress(progress);

        boolean result = bookProgressMAO.deleteBookProgress(progress);
        assertTrue(result);
        assertNull(bookProgressMAO.findBookProgressByBookAndUser(101, 201));
    }

    @Test
    public void testDeleteBookProgressFailure() {
        BookProgress progress = new BookProgress(1, 101, 201, 5);
        boolean result = bookProgressMAO.deleteBookProgress(progress);
        assertFalse(result);
    }
}
