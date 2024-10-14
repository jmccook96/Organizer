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
        book = new Book(1, "testTitle", "testAuthor", "testGenre");
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

    @Test
    public void testGetGenre() {
        assertEquals("testGenre", book.getGenre());
    }

    @Test
    public void testSetGenreValid() {
        book.setGenre("testNewGenre");
        assertEquals("testNewGenre", book.getGenre());
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
    public void testSetGenreInvalidNull() {
        assertThrows(IllegalArgumentException.class, () -> book.setGenre(null));
    }

    @Test
    public void testSetGenreInvalidEmpty() {
        assertThrows(IllegalArgumentException.class, () -> book.setGenre(""));
    }

    @Test
    public void testConstructorInvalidTitleNull() {
        assertThrows(IllegalArgumentException.class, () -> new Book(1, null, "author", "genre"));
    }

    @Test
    public void testConstructorInvalidTitleEmpty() {
        assertThrows(IllegalArgumentException.class, () -> new Book(1, "", "author", "genre"));
    }

    @Test
    public void testConstructorInvalidAuthorNull() {
        assertThrows(IllegalArgumentException.class, () -> new Book(1, "title", null, "genre"));
    }

    @Test
    public void testConstructorInvalidAuthorEmpty() {
        assertThrows(IllegalArgumentException.class, () -> new Book(1, "title", "", "genre"));
    }

    @Test
    public void testConstructorInvalidGenreNull() {
        assertThrows(IllegalArgumentException.class, () -> new Book(1, "title", "author", null));
    }

    @Test
    public void testConstructorInvalidGenreEmpty() {
        assertThrows(IllegalArgumentException.class, () -> new Book(1, "title", "author", ""));
    }

}
