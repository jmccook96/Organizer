package com.example.model;

import com.bookclub.model.BookProgress;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BookProgressTest {

    private BookProgress bookProgress;

    @BeforeEach
    public void setUp() {
        bookProgress = new BookProgress(1, 101, 201, 5);
    }

    // Basic functionality tests
    @Test
    public void testGetId() {
        assertEquals(1, bookProgress.getId());
    }

    @Test
    public void testSetId() {
        bookProgress.setId(2);
        assertEquals(2, bookProgress.getId());
    }

    @Test
    public void testGetBookId() {
        assertEquals(101, bookProgress.getBookId());
    }

    @Test
    public void testSetBookId() {
        bookProgress.setBookId(102);
        assertEquals(102, bookProgress.getBookId());
    }

    @Test
    public void testGetUserId() {
        assertEquals(201, bookProgress.getUserId());
    }

    @Test
    public void testSetUserId() {
        bookProgress.setUserId(202);
        assertEquals(202, bookProgress.getUserId());
    }

    @Test
    public void testGetChapterNumber() {
        assertEquals(5, bookProgress.getChapterNumber());
    }

    @Test
    public void testSetChapterNumber() {
        bookProgress.setChapterNumber(6);
        assertEquals(6, bookProgress.getChapterNumber());
    }

    // Constructor tests
    @Test
    public void testConstructorWithId() {
        BookProgress progressWithId = new BookProgress(2, 102, 202, 3);
        assertEquals(2, progressWithId.getId());
        assertEquals(102, progressWithId.getBookId());
        assertEquals(202, progressWithId.getUserId());
        assertEquals(3, progressWithId.getChapterNumber());
    }

    @Test
    public void testConstructorWithoutId() {
        BookProgress progressWithoutId = new BookProgress(103, 203, 4);
        assertEquals(103, progressWithoutId.getBookId());
        assertEquals(203, progressWithoutId.getUserId());
        assertEquals(4, progressWithoutId.getChapterNumber());
    }

    // Edge case tests

    @Test
    public void testSetBookIdNegative() {
        assertThrows(IllegalArgumentException.class, () -> bookProgress.setBookId(-101));
    }

    @Test
    public void testSetUserIdNegative() {
        assertThrows(IllegalArgumentException.class, () -> bookProgress.setUserId(-201));
    }

    @Test
    public void testSetChapterNumberNegative() {
        assertThrows(IllegalArgumentException.class, () -> bookProgress.setChapterNumber(-5));
    }

    @Test
    public void testConstructorInvalidBookId() {
        assertThrows(IllegalArgumentException.class, () -> new BookProgress(-102, 202, 5));
    }

    @Test
    public void testConstructorInvalidUserId() {
        assertThrows(IllegalArgumentException.class, () -> new BookProgress(102, -202, 5));
    }

    @Test
    public void testConstructorInvalidChapterNumber() {
        assertThrows(IllegalArgumentException.class, () -> new BookProgress(102, 202, -5));
    }
}
