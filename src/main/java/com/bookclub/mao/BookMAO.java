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

        books.add(new Book("It", "Stephen King","Horror"));
        books.add(new Book("The Shining", "Stephen King","Horror"));
        books.add(new Book("testTitle", "Stephen King", "Horror"));
        books.add(new Book("1994", "George Orwell", "Historical Fiction"));
        books.add(new Book("Animal Farm", "George Orwell", "Political Satire"));
        books.add(new Book("testTitle", "George Orwell", "Test Genre"));

    }

    @Override
    public List<Book> findAllBooks() {
        return books;
    }

    @Override
    public Book findBookByTitleAndAuthor(String title, String author) {
        for (Book book : books) {
            if (book.getTitle().equals(title) && book.getAuthor().equals(author)) {
                return book;
            }
        }
        return null;
    }


    public List<Book> findBooksByBookID(Integer bookid) {
        List<Book> booksByBookID = new ArrayList<>();
        for (Book book : books) {
            if (book.getBookID().equals(bookid)) {
                booksByBookID.add(book);
            }
        }
        return booksByBookID.isEmpty() ? null : booksByBookID;
    }


    public List<Book> findBooksByGenre(String genre) {
        List<Book> booksByGenre = new ArrayList<>();
        for (Book book : books) {
            if (book.getGenre().equals(genre)) {
                booksByGenre.add(book);
            }
        }
        return booksByGenre.isEmpty() ? null : booksByGenre;
    }


    public List<Book> findBooksByTitle(String title) {
        List<Book> booksByTitle = new ArrayList<>();
        for (Book book : books) {
            if (book.getTitle().equals(title)) {
                booksByTitle.add(book);
            }
        }
        return booksByTitle;
    }

    @Override
    public List<Book> findBooksByAuthor(String author) {
        List<Book> booksByAuthor = new ArrayList<>();
        for (Book book : books) {
            if (book.getAuthor().equals(author)) {
                booksByAuthor.add(book);
            }
        }
        return booksByAuthor;
    }

    @Override
    public boolean addBook(Book book) {
        return books.add(book);
    }

    @Override
    public boolean updateBook(Book book) {
        return books.set(books.indexOf(findBookByTitleAndAuthor(book.getTitle(), book.getAuthor())), book) != null;
    }

    @Override
    public boolean deleteBook(Book book) {
        return books.remove(book);
    }
}
