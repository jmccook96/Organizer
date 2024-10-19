package com.example.mao;

import com.bookclub.mao.BookMAO;
import com.bookclub.model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BookMAOTest {

    private BookMAO bookMAO;

    @BeforeEach
    public void setUp() {
        bookMAO = new BookMAO();  // Set up the MAO with test data.
    }

    @Test
    public void testFindAllBooks() {
        List<Book> allBooks = bookMAO.findAllBooks();
        assertNotNull(allBooks);
        assertEquals(6, allBooks.size(), "There should be 6 books in the test data.");
    }

    @Test
    public void testFindBookByTitleAndAuthor_Found() {
        Book foundBook = bookMAO.findBookByTitleAndAuthor("It", "Stephen King");
        assertNotNull(foundBook);
        assertEquals("It", foundBook.getTitle());
        assertEquals("Stephen King", foundBook.getAuthor());
    }

    @Test
    public void testFindBookByTitleAndAuthor_NotFound() {
        Book notFoundBook = bookMAO.findBookByTitleAndAuthor("Unknown", "Unknown Author");
        assertNull(notFoundBook);
    }

    @Test
    public void testFindBooksByTitle() {
        List<Book> booksByTitle = bookMAO.findBooksByTitle("testTitle");
        assertNotNull(booksByTitle);
        assertEquals(2, booksByTitle.size(), "There should be 2 books with the title 'testTitle'.");
    }

    @Test
    public void testFindBooksByGenre() {
        List<Book> horrorBooks = bookMAO.findBooksByGenre("Horror");
        assertNotNull(horrorBooks);
        assertEquals(6, horrorBooks.size(), "There should be 6 horror books in the test data.");
    }

    @Test
    public void testFindBooksByAuthor_Found() {
        List<Book> booksByAuthor = bookMAO.findBooksByAuthor("Stephen King");
        assertNotNull(booksByAuthor);
        assertEquals(3, booksByAuthor.size(), "There should be 3 books by Stephen King.");
    }

    @Test
    public void testFindBooksByAuthor_NotFound() {
        List<Book> booksByUnknownAuthor = bookMAO.findBooksByAuthor("Unknown Author");
        assertTrue(booksByUnknownAuthor.isEmpty(), "There should be no books by 'Unknown Author'.");
    }

    @Test
    public void testAddBook_Successful() {
        Book newBook = new Book("New Book", "New Author", "New Genre");
        boolean isAdded = bookMAO.addBook(newBook);
        assertTrue(isAdded);
        List<Book> allBooks = bookMAO.findAllBooks();
        assertEquals(7, allBooks.size(), "There should be 7 books after adding a new one.");
        assertTrue(allBooks.contains(newBook));
    }

    @Test
    public void testUpdateBook_Successful() {
        Book updatedBook = new Book("It", "Stephen King", "Thriller");  // Change genre
        boolean isUpdated = bookMAO.updateBook(updatedBook);
        assertTrue(isUpdated);

        Book retrievedBook = bookMAO.findBookByTitleAndAuthor("It", "Stephen King");
        assertEquals("Thriller", retrievedBook.getGenre(), "The genre should be updated to 'Thriller'.");
    }

    @Test
    public void testUpdateBook_NotFound() {
        Book nonExistentBook = new Book("Non-existent Book", "Unknown Author", "Unknown Genre");
        boolean isUpdated = bookMAO.updateBook(nonExistentBook);
        assertFalse(isUpdated, "Updating a non-existent book should return false.");
    }

    @Test
    public void testDeleteBook_Successful() {
        Book bookToDelete = new Book("It", "Stephen King", "Horror");
        boolean isDeleted = bookMAO.deleteBook(bookToDelete);
        assertTrue(isDeleted);

        List<Book> allBooks = bookMAO.findAllBooks();
        assertEquals(5, allBooks.size(), "There should be 5 books after deletion.");
        assertFalse(allBooks.contains(bookToDelete));
    }

    @Test
    public void testDeleteBook_NotFound() {
        Book nonExistentBook = new Book("Non-existent Book", "Unknown Author", "Unknown Genre");
        boolean isDeleted = bookMAO.deleteBook(nonExistentBook);
        assertFalse(isDeleted, "Deleting a non-existent book should return false.");
    }
}
