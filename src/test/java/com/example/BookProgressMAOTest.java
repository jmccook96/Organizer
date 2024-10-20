package com.example;

import com.bookclub.mao.BookProgressMAO;
import com.bookclub.model.BookProgress;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BookProgressMAOTest {

    private BookProgressMAO bookProgressMAO;
    private BookProgress bookProgress1;
    private BookProgress bookProgress2;
    private BookProgress bookProgress3;

    @BeforeEach
    public void setUp() {
        bookProgressMAO = new BookProgressMAO();
        bookProgress1 = new BookProgress(1, 1, 10);
        bookProgress2 = new BookProgress(1, 2, 20);
        bookProgress3 = new BookProgress(2, 1, 30);

        bookProgressMAO.addBookProgress(bookProgress1);
        bookProgressMAO.addBookProgress(bookProgress2);
        bookProgressMAO.addBookProgress(bookProgress3);
    }

    @Test
    public void testFindBookProgressByBookAndUser() {
        BookProgress found = bookProgressMAO.findBookProgressByBookAndUser(1, 1);
        assertNotNull(found);
        assertEquals(10, found.getPageNumber());

        BookProgress notFound = bookProgressMAO.findBookProgressByBookAndUser(1, 3);
        assertNull(notFound);
    }

    @Test
    public void testFindBookProgressesByBook() {
        List<BookProgress> progressList = bookProgressMAO.findBookProgressesByBook(1);
        assertEquals(2, progressList.size());
        assertTrue(progressList.contains(bookProgress1));
        assertTrue(progressList.contains(bookProgress2));
    }

    @Test
    public void testFindBookProgressesByUser() {
        List<BookProgress> progressList = bookProgressMAO.findBookProgressesByUser(1);
        assertEquals(2, progressList.size());
        assertTrue(progressList.contains(bookProgress1));
        assertTrue(progressList.contains(bookProgress3));
    }

    @Test
    public void testAddBookProgress() {
        BookProgress newProgress = new BookProgress(3, 1, 40);
        boolean added = bookProgressMAO.addBookProgress(newProgress);
        assertTrue(added);

        BookProgress found = bookProgressMAO.findBookProgressByBookAndUser(3, 1);
        assertNotNull(found);
        assertEquals(40, found.getPageNumber());
    }

    @Test
    public void testUpdateBookProgress() {
        bookProgress1.setPageNumber(50);
        boolean updated = bookProgressMAO.updateBookProgress(bookProgress1);
        assertTrue(updated);

        BookProgress found = bookProgressMAO.findBookProgressByBookAndUser(1, 1);
        assertEquals(50, found.getPageNumber());
    }

    @Test
    public void testDeleteBookProgress() {
        boolean deleted = bookProgressMAO.deleteBookProgress(bookProgress1);
        assertTrue(deleted);

        BookProgress found = bookProgressMAO.findBookProgressByBookAndUser(1, 1);
        assertNull(found);
    }
}
