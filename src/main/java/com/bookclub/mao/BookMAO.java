package com.bookclub.mao;

import com.bookclub.iao.IBookAO;
import com.bookclub.model.Book;

import java.util.*;

import java.util.List;

public class BookMAO implements IBookAO {

    private List<Book> books;

    public BookMAO() {
        books = new ArrayList<>();
        addTestData();
    }

    private void addTestData() {
        books.add(new Book("It", "Stephen King"));
        books.add(new Book("The Shining", "Stephen King"));
        books.add(new Book("testTitle", "Stephen King"));
        books.add(new Book("1994", "George Orwell"));
        books.add(new Book("Animal Farm", "George Orwell"));
        books.add(new Book("testTitle", "George Orwell"));
    }

    public Book findBookByTitleAndAuthor(String title, String author) {
        for (Book book : books) {
            if (book.getTitle().equals(title) && book.getAuthor().equals(author)) {
                return book;
            }
        }
        return null;
    }

    public List<Book> findBooksByTitle(String title) {
        List<Book> booksByTitle = new ArrayList<>();
        for (Book book : books) {
            if (book.getTitle().equals(title)) {
                booksByTitle.add(book);
            }
        }
        return booksByTitle.isEmpty() ? null : booksByTitle;
    }

    public List<Book> findBooksByAuthor(String author) {
        List<Book> booksByAuthor = new ArrayList<>();
        for (Book book : books) {
            if (book.getAuthor().equals(author)) {
                booksByAuthor.add(book);
            }
        }
        return booksByAuthor.isEmpty() ? null : booksByAuthor;
    }

    public boolean addBook(Book book) {
        return books.add(book);
    }

    public boolean updateBook(Book book) {
        return books.set(books.indexOf(findBookByTitleAndAuthor(book.getTitle(), book.getAuthor())), book) != null;
    }

    public boolean deleteBook(Book book) {
        return books.remove(book);
    }
}
