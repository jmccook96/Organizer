package com.bookclub.service;

import com.bookclub.dao.BookProgressDAO;
import com.bookclub.iao.IBookProgressAO;
import com.bookclub.iao.IUserAO;
import com.bookclub.model.BookProgress;
import com.bookclub.model.Book;
import com.bookclub.model.User;

import java.util.*;
import java.util.stream.Collectors;

/**
 * The {@code BookProgressService} class is a singleton service responsible for managing book progress for users.
 * It provides methods for retrieving, updating, and deleting progress for books read by users.
 * The class works with two access objects: {@link IBookProgressAO} for book progress and {@link IUserAO} for user data.
 */
public class BookProgressService {

    private static BookProgressService instance;
    private IBookProgressAO bookProgressAO;
    private IUserAO userAO;

    /**
     * Returns the singleton instance of {@code BookProgressService}.
     *
     * @return the {@code BookProgressService} instance.
     * @throws IllegalStateException if the service is not initialized.
     */
    public static BookProgressService getInstance() {
        if (instance == null) {
            throw new IllegalStateException("BookProgressService is not initialized. Call initialize() first.");
        }
        return instance;
    }

    /**
     * Initializes the {@code BookProgressService} with the provided data access objects.
     * This method must be called before accessing the singleton instance.
     *
     * @param bookProgressAO the {@link IBookProgressAO} to manage book progress.
     * @param userAO the {@link IUserAO} to manage user data.
     */
    public static void initialize(IBookProgressAO bookProgressAO, IUserAO userAO) {
        if (instance == null) {
            instance = new BookProgressService();
        }
        instance.bookProgressAO = bookProgressAO;
        instance.userAO = userAO;
    }

    /**
     * Retrieves the book progress for the given book and user.
     *
     * @param book the {@link Book} for which progress is to be retrieved.
     * @param user the {@link User} whose progress is to be retrieved.
     * @return an {@link Optional} containing the {@link BookProgress}, or empty if not found.
     */
    public Optional<BookProgress> getBookProgress(Book book, User user) {
        return Optional.ofNullable(bookProgressAO.findBookProgressByBookAndUser(book.getId(), user.getId()));
    }

    /**
     * Checks if there is progress recorded for the given book and user.
     *
     * @param book the {@link Book} to check.
     * @param user the {@link User} to check.
     * @return {@code true} if there is progress recorded, {@code false} otherwise.
     */
    public boolean hasBookProgress(Book book, User user) {
        return getBookProgress(book, user).isPresent();
    }

    /**
     * Starts tracking progress for the given book and user if no progress exists.
     *
     * @param book the {@link Book} for which progress is to be started.
     * @param user the {@link User} who is starting the progress.
     * @return {@code true} if progress is successfully started, {@code false} if progress already exists.
     */
    public boolean startBookProgress(Book book, User user) {
        if (!hasBookProgress(book, user)) {
            return bookProgressAO.addBookProgress(new BookProgress(book.getId(), user.getId(), 0));
        }
        return false;
    }

    /**
     * Finishes (deletes) the book progress for the given book and user.
     *
     * @param book the {@link Book} whose progress is to be finished.
     * @param user the {@link User} who is finishing the progress.
     * @return {@code true} if the progress is successfully finished, {@code false} otherwise.
     */
    public boolean finishBookProgress(Book book, User user) {
        return getBookProgress(book, user)
                .map(bookProgress -> bookProgressAO.deleteBookProgress(bookProgress))
                .orElse(false);
    }

    /**
     * Saves or updates the progress of the given book for the specified user.
     *
     * @param book the {@link Book} for which progress is being saved.
     * @param user the {@link User} who is saving the progress.
     * @param pageNumber the page number to save as progress.
     * @return {@code true} if progress is successfully saved, {@code false} if the page number is invalid or save fails.
     */
    public boolean saveBookProgress(Book book, User user, int pageNumber) {
        if (pageNumber < 0 || pageNumber > book.getTotalPages()) {
            return false;
        }

        return getBookProgress(book, user)
                .map(progress -> updateExistingBookProgress(progress, pageNumber))
                .orElseGet(() -> createNewBookProgress(book, user, pageNumber));
    }

    /**
     * Updates the existing book progress for a user to a new page number.
     *
     * @param bookProgress the {@link BookProgress} to update.
     * @param pageNumber the new page number.
     * @return {@code true} if the progress was successfully updated, {@code false} otherwise.
     */
    private boolean updateExistingBookProgress(BookProgress bookProgress, int pageNumber) {
        bookProgress.setPageNumber(pageNumber);
        return bookProgressAO.updateBookProgress(bookProgress);
    }

    /**
     * Creates a new book progress entry for a user starting at a specific page number.
     *
     * @param book the {@link Book} to create progress for.
     * @param user the {@link User} to create progress for.
     * @param pageNumber the page number to start the progress.
     * @return {@code true} if the progress is successfully created, {@code false} otherwise.
     */
    private boolean createNewBookProgress(Book book, User user, int pageNumber) {
        BookProgress newProgress = new BookProgress(book.getId(), user.getId(), pageNumber);
        return bookProgressAO.addBookProgress(newProgress);
    }

    /**
     * Retrieves a formatted list of user progress for the given book.
     * Each entry contains the username and their current page number in the format: "{@code username is at Page X/Y}".
     *
     * @param book the {@link Book} for which progress is retrieved.
     * @return a list of formatted progress strings.
     */
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

    /**
     * Retrieves a list of {@link BookProgress} for a specific book, sorted by the current page number in ascending order.
     *
     * @param book the {@link Book} to get progress for.
     * @return a sorted list of {@link BookProgress} for the specified book.
     */
    public List<BookProgress> getBookProgressListForBook(Book book) {
        return bookProgressAO.findBookProgressesByBook(book.getId()).stream()
                .sorted(Comparator.comparingInt(BookProgress::getPageNumber))
                .collect(Collectors.toList());
    }
}
