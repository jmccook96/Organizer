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
        bookMAO = new BookMAO();
    }

    @Test
    public void testFindNoBooks() {
        List<Book> allBooks = bookMAO.findAllBooks();
        assertNotNull(allBooks);
        assertEquals(0, allBooks.size(), "There should be no books in the test data.");
    }

    @Test
    public void testFindAllBooks() {
        bookMAO.addBook(new Book("It", "Stephen King", "Horror", 20));
        bookMAO.addBook(new Book("The Shining", "Stephen King", "Horror", 447));
        
        List<Book> allBooks = bookMAO.findAllBooks();
        assertNotNull(allBooks);
        assertEquals(2, allBooks.size(), "There should be 2 books in the test data.");
    }

    @Test
    public void testFindBookByTitleAndAuthor_Found() {
        bookMAO.addBook(new Book("It", "Stephen King", "Horror", 20));
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
        bookMAO.addBook(new Book("testTitle", "Stephen King","Horror", 30));
        bookMAO.addBook(new Book("testTitle", "George Orwell","Horror", 30));
        List<Book> booksByTitle = bookMAO.findBooksByTitle("testTitle");
        assertNotNull(booksByTitle);
        assertEquals(2, booksByTitle.size(), "There should be 2 books with the title 'testTitle'.");
    }

    @Test
    public void testFindBooksByGenre() {
        bookMAO.addBook(new Book("testTitle", "Stephen King","Horror", 30));
        bookMAO.addBook(new Book("testTitle", "George Orwell","Horror", 30));
        bookMAO.addBook(new Book("testTitle", "Test King","Test", 30));
        bookMAO.addBook(new Book("testTitle", "George Test","NotHor", 30));
        
        List<Book> horrorBooks = bookMAO.findBooksByGenre("Horror");
        assertNotNull(horrorBooks);
        assertEquals(2, horrorBooks.size(), "There should be 2 horror books in the test data.");
    }

    @Test
    public void testFindBooksByAuthor_Found() {
        bookMAO.addBook(new Book("testTitle", "Stephen King","Horror", 30));
        bookMAO.addBook(new Book("testTitle", "George Orwell","Horror", 30));
        
        List<Book> booksByAuthor = bookMAO.findBooksByAuthor("Stephen King");
        assertNotNull(booksByAuthor);
        assertEquals(1, booksByAuthor.size(), "There should be 1 book by Stephen King.");
    }

    @Test
    public void testFindBooksByAuthor_NotFound() {
        List<Book> booksByUnknownAuthor = bookMAO.findBooksByAuthor("Unknown Author");
        assertTrue(booksByUnknownAuthor.isEmpty(), "There should be no books by 'Unknown Author'.");
    }

    @Test
    public void testAddBook_Successful() {
        Book newBook = new Book("New Book", "New Author", "New Genre", 20);
        boolean isAdded = bookMAO.addBook(newBook);
        assertTrue(isAdded);
        List<Book> allBooks = bookMAO.findAllBooks();
        assertEquals(1, allBooks.size(), "There should be 7 books after adding a new one.");
        assertTrue(allBooks.contains(newBook));
    }

    @Test
    public void testUpdateBook_Successful() {
        bookMAO.addBook(new Book("It", "Stephen King", "Horror", 20));
        Book updatedBook = new Book("It", "Stephen King", "Thriller", 20);  // Change genre
        boolean isUpdated = bookMAO.updateBook(updatedBook);
        assertTrue(isUpdated);

        Book retrievedBook = bookMAO.findBookByTitleAndAuthor("It", "Stephen King");
        assertEquals("Thriller", retrievedBook.getGenre(), "The genre should be updated to 'Thriller'.");
    }

    @Test
    public void testUpdateBook_NotFound() {
        Book nonExistentBook = new Book("Non-existent Book", "Unknown Author", "Unknown Genre", 20);
        boolean isUpdated = bookMAO.updateBook(nonExistentBook);
        assertFalse(isUpdated, "Updating a non-existent book should return false.");
    }

    @Test
    public void testDeleteBook_Successful() {
        Book bookToDelete = new Book("It", "Stephen King", "Horror", 20);
        bookMAO.addBook(new Book("testTitle", "Stephen King","Horror", 30));
        bookMAO.addBook(new Book("testTitle", "George Orwell","Horror", 30));
        bookMAO.addBook(bookToDelete);
        
        boolean isDeleted = bookMAO.deleteBook(bookToDelete);
        assertTrue(isDeleted);

        List<Book> allBooks = bookMAO.findAllBooks();
        assertEquals(2, allBooks.size(), "There should be 2 books after deletion.");
        assertFalse(allBooks.contains(bookToDelete));
    }

    @Test
    public void testDeleteBook_NotFound() {
        Book nonExistentBook = new Book("Non-existent Book", "Unknown Author", "Unknown Genre", 20);
        boolean isDeleted = bookMAO.deleteBook(nonExistentBook);
        assertFalse(isDeleted, "Deleting a non-existent book should return false.");
    }
}
