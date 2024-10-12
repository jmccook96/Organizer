package com.example;

import com.bookclub.model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BookTest {

    private Book book;
    
    @BeforeEach
    public void setUp() {
        book = new Book("testTitle", "testAuthor");
    }

    // Basic functionality
    @Test
    public void testGetTitle() {
        assertEquals("testTitle", book.getTitle());
    }

    @Test
    public void testSetTitleValid() {
        book.setTitle("testNewTitle");
        assertEquals("testNewTitle", book.getTitle());
    }

    @Test
    public void testGetAuthor() {
        assertEquals("testAuthor", book.getAuthor());
    }

    @Test
    public void testSetAuthorValid() {
        book.setAuthor("testNewAuthor");
        assertEquals("testNewAuthor", book.getAuthor());
    }

    // Edge cases
    @Test
    public void testSetTitleInvalidNull() {
        assertThrows(IllegalArgumentException.class, () -> book.setTitle(null));
    }

    @Test
    public void testSetTitleInvalidEmpty() {
        assertThrows(IllegalArgumentException.class, () -> book.setTitle(""));
    }

    @Test
    public void testSetAuthorInvalidNull() {
        assertThrows(IllegalArgumentException.class, () -> book.setAuthor(null));
    }

    @Test
    public void testSetAuthorInvalidEmpty() {
        assertThrows(IllegalArgumentException.class, () -> book.setAuthor(""));
    }

    @Test
    public void testConstructorInvalidTitleNull() {
        assertThrows(IllegalArgumentException.class, () -> new Book(null, "author"));
    }

    @Test
    public void testConstructorInvalidTitleEmpty() {
        assertThrows(IllegalArgumentException.class, () -> new Book("", "author"));
    }

    @Test
    public void testConstructorInvalidAuthorNull() {
        assertThrows(IllegalArgumentException.class, () -> new Book("title", null));
    }

    @Test
    public void testConstructorInvalidAuthorEmpty() {
        assertThrows(IllegalArgumentException.class, () -> new Book("title", ""));
    }
}
