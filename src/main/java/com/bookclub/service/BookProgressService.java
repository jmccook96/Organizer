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

/**
 * The {@code BookProgressService} class provides a service layer to manage the user's
 * progress in reading books. It interacts with the {@link IBookProgressAO} data access object
 * to perform actions like starting, saving, and finishing book progress.
 * <p>
 * This is a singleton class. It should be initialized once via {@code initialize()} before use.
 */
public class BookProgressService {
    private static BookProgressService instance;
    private IBookProgressAO bookProgressAO;

    /**
     * Returns the singleton instance of the {@code BookProgressService}.
     *
     * @return the singleton instance of the {@code BookProgressService}
     * @throws IllegalStateException if the service is not initialized
     */
    public static BookProgressService getInstance() {
        if (instance == null) {
            throw new IllegalStateException("RSVPService is not initialized. Call initialize() first.");
        }
        return instance;
    }

    /**
     * Initializes the {@code BookProgressService} with the provided {@link IBookProgressAO}.
     * This method must be called before any operations are performed.
     *
     * @param bookProgressAO the data access object for book progress management
     */
    public static void initialize(IBookProgressAO bookProgressAO) {
        if (instance == null) {
            instance = new BookProgressService();
        }
        instance.bookProgressAO = bookProgressAO;
    }

    /**
     * Retrieves the progress of a specific book for a given user.
     *
     * @param book the book whose progress is being retrieved
     * @param user the user whose progress is being retrieved
     * @return the {@link BookProgress} object if found, otherwise {@code null}
     */
    public BookProgress getBookProgress(Book book, User user) {
        return bookProgressAO.findBookProgressByBookAndUser(book.getId(), user.getId());
    }

    /**
     * Checks if the user has started the book progress for a specific book.
     *
     * @param book the book being checked
     * @param user the user being checked
     * @return {@code true} if the user has started the book, otherwise {@code false}
     */
    public boolean hasBookProgress(Book book, User user) {
        return getBookProgress(book, user) != null;
    }

    /**
     * Starts a new book progress entry for the user with an initial page number of 0.
     *
     * @param book the book to start progress on
     * @param user the user who is starting the progress
     * @return {@code true} if the progress was successfully started, otherwise {@code false}
     */
    public boolean startBookProgress(Book book, User user) {
        return !hasBookProgress(book, user) && bookProgressAO.addBookProgress(new BookProgress(book.getId(), user.getId(), 0));
    }

    /**
     * Finishes and removes the user's book progress for the given book.
     *
     * @param book the book to finish
     * @param user the user who is finishing the progress
     * @return {@code true} if the progress was successfully finished, otherwise {@code false}
     */
    public boolean finishBookProgress(Book book, User user) {
        return hasBookProgress(book, user) && bookProgressAO.deleteBookProgress(getBookProgress(book, user));
    }

    /**
     * Saves the user's current page progress for a specific book.
     *
     * @param book       the book whose progress is being saved
     * @param user       the user saving the progress
     * @param pageNumber the current page number the user is on
     * @return {@code true} if the progress was saved successfully, otherwise {@code false}
     */
    public boolean saveBookProgress(Book book, User user, int pageNumber) {
        // TODO: Implement book total pages
        if (pageNumber < 0) return false;
        BookProgress bookProgress = getBookProgress(book, user);
        if (bookProgress != null) {
            bookProgress.setPageNumber(pageNumber);
        }
        return bookProgress != null ? 
                bookProgressAO.updateBookProgress(bookProgress) : 
                bookProgressAO.addBookProgress(new BookProgress(book.getId(), user.getId(), pageNumber));
    }

    /**
     * Retrieves a sorted list of all users' progress for a specific book.
     *
     * @param book the book whose progress is being retrieved
     * @return a list of {@link BookProgress} entries sorted by page number
     */
    public List<BookProgress> getBookProgressListForBook(Book book) {
        return bookProgressAO.findBookProgressesByBook(book.getId()).stream()
                .sorted(Comparator.comparingInt(BookProgress::getPageNumber))
                .collect(Collectors.toList());
    }
}
