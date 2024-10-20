package com.bookclub.service;

import com.bookclub.dao.BookProgressDAO;
import com.bookclub.iao.IBookProgressAO;
import com.bookclub.iao.IUserAO;
import com.bookclub.model.BookProgress;
import com.bookclub.model.Book;
import com.bookclub.model.User;

import java.util.*;
import java.util.stream.Collectors;

public class BookProgressService {
    private static BookProgressService instance;
    private IBookProgressAO bookProgressAO;
    private IUserAO userAO;

    public static BookProgressService getInstance() {
        if (instance == null) {
            throw new IllegalStateException("BookProgressService is not initialized. Call initialize() first.");
        }
        return instance;
    }

    public static void initialize(IBookProgressAO bookProgressAO, IUserAO userAO) {
        if (instance == null) {
            instance = new BookProgressService();
        }
        instance.bookProgressAO = bookProgressAO;
        instance.userAO = userAO;
    }

    public Optional<BookProgress> getBookProgress(Book book, User user) {
        return Optional.ofNullable(bookProgressAO.findBookProgressByBookAndUser(book.getId(), user.getId()));
    }

    public boolean hasBookProgress(Book book, User user) {
        return getBookProgress(book, user).isPresent();
    }

    public boolean startBookProgress(Book book, User user) {
        if (!hasBookProgress(book, user)) {
            return bookProgressAO.addBookProgress(new BookProgress(book.getId(), user.getId(), 0));
        }
        return false;
    }

    public boolean finishBookProgress(Book book, User user) {
        return getBookProgress(book, user)
                .map(bookProgress -> bookProgressAO.deleteBookProgress(bookProgress))
                .orElse(false);
    }

    public boolean saveBookProgress(Book book, User user, int pageNumber) {
        if (pageNumber < 0 || pageNumber > book.getTotalPages()) {
            return false;
        }

        return getBookProgress(book, user)
                .map(progress -> updateExistingBookProgress(progress, pageNumber))
                .orElseGet(() -> createNewBookProgress(book, user, pageNumber));
    }

    private boolean updateExistingBookProgress(BookProgress bookProgress, int pageNumber) {
        bookProgress.setPageNumber(pageNumber);
        return bookProgressAO.updateBookProgress(bookProgress);
    }

    private boolean createNewBookProgress(Book book, User user, int pageNumber) {
        BookProgress newProgress = new BookProgress(book.getId(), user.getId(), pageNumber);
        return bookProgressAO.addBookProgress(newProgress);
    }



    public List<String> getFormattedProgressForBook(Book book) {
        List<BookProgress> progressList = bookProgressAO.findBookProgressesByBook(book.getId());
        Map<Integer, User> userCache = new HashMap<>();
        int totalPages = book.getTotalPages();

        return progressList.stream()
                .map(progress -> {
                    User user = userCache.computeIfAbsent(progress.getUserId(), userAO::findUserById);
                    String userName = user.getUsername();
                    int currentPage = progress.getPageNumber();
                    return String.format("%s is at Page %d/%d", userName, currentPage, totalPages);
                })
                .collect(Collectors.toList());
    }

    public List<BookProgress> getBookProgressListForBook(Book book) {
        return bookProgressAO.findBookProgressesByBook(book.getId()).stream()
                .sorted(Comparator.comparingInt(BookProgress::getPageNumber))
                .collect(Collectors.toList());
    }
}
