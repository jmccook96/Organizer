package com.bookclub.service;

import com.bookclub.dao.BookProgressDAO;
import com.bookclub.iao.IBookProgressAO;
import com.bookclub.model.BookProgress;
import com.bookclub.model.Book;
import com.bookclub.model.User;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class BookProgressService {
    private static BookProgressService instance;
    private IBookProgressAO bookProgressAO;

    private BookProgressService() {
        bookProgressAO = new BookProgressDAO();
    }

    public static BookProgressService getInstance() {
        if (instance == null) {
            instance = new BookProgressService();
        }
        return instance;
    }

    public BookProgress getBookProgress(Book book, User user) {
        return bookProgressAO.findBookProgressByBookAndUser(book.getId(), user.getId());
    }

    public boolean hasBookProgress(Book book, User user) {
        return getBookProgress(book, user) != null;
    }

    public boolean startBookProgress(Book book, User user) {
        return bookProgressAO.addBookProgress(new BookProgress(book.getId(), user.getId(), 0));
    }

    public boolean finishBookProgress(Book book, User user) {
        return bookProgressAO.deleteBookProgress(getBookProgress(book, user));
    }

    public boolean saveBookProgress(Book book, User user, int pageNumber) {
        // TODO: Implement book total pages
        if (pageNumber < 0) return false;
        BookProgress bookProgress = getBookProgress(book, user);
        if (bookProgress != null) {
            bookProgress.setPageNumber(pageNumber);
        }
        return bookProgress != null ? bookProgressAO.updateBookProgress(bookProgress) : startBookProgress(book, user);
    }

    public List<BookProgress> getBookProgressListForBook(Book book) {
        return bookProgressAO.findBookProgressesByBook(book.getId()).stream()
                .sorted(Comparator.comparingInt(BookProgress::getPageNumber))
                .collect(Collectors.toList());
    }
}
