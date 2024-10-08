package com.bookclub.service;

import com.bookclub.dao.BookProgressDAO;
import com.bookclub.iao.IBookProgressAO;
import com.bookclub.iao.IRSVPAO;
import com.bookclub.iao.IUserAO;
import com.bookclub.model.BookProgress;
import com.bookclub.model.Book;
import com.bookclub.model.User;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class BookProgressService {
    private static BookProgressService instance;
    private IBookProgressAO bookProgressAO;

    public static BookProgressService getInstance() {
        if (instance == null) {
            throw new IllegalStateException("RSVPService is not initialized. Call initialize() first.");
        }
        return instance;
    }

    public static void initialize(IBookProgressAO bookProgressAO) {
        if (instance == null) {
            instance = new BookProgressService();
        }
        instance.bookProgressAO = bookProgressAO;
    }

    public BookProgress getBookProgress(Book book, User user) {
        return bookProgressAO.findBookProgressByBookAndUser(book.getId(), user.getId());
    }

    public boolean hasBookProgress(Book book, User user) {
        return getBookProgress(book, user) != null;
    }

    public boolean startBookProgress(Book book, User user) {
        return !hasBookProgress(book, user) && bookProgressAO.addBookProgress(new BookProgress(book.getId(), user.getId(), 0));
    }

    public boolean finishBookProgress(Book book, User user) {
        return hasBookProgress(book, user) && bookProgressAO.deleteBookProgress(getBookProgress(book, user));
    }

    public boolean saveBookProgress(Book book, User user, int pageNumber) {
        // TODO: Implement book total pages
        if (pageNumber < 0) return false;
        BookProgress bookProgress = getBookProgress(book, user);
        if (bookProgress != null) {
            bookProgress.setPageNumber(pageNumber);
        }
        return bookProgress != null ? bookProgressAO.updateBookProgress(bookProgress) : bookProgressAO.addBookProgress(new BookProgress(book.getId(), user.getId(), pageNumber));
    }

    public List<BookProgress> getBookProgressListForBook(Book book) {
        return bookProgressAO.findBookProgressesByBook(book.getId()).stream()
                .sorted(Comparator.comparingInt(BookProgress::getPageNumber))
                .collect(Collectors.toList());
    }
}
