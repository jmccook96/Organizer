package com.bookclub.mao;

import com.bookclub.iao.IBookProgressAO;
import com.bookclub.model.BookProgress;

import java.util.ArrayList;
import java.util.List;

/**
 * This class implements the {@link IBookProgressAO} interface and acts as a Mock Access Object (MAO)
 * for managing the {@link BookProgress} objects. It handles operations related to tracking
 * and managing the progress of books for specific users.
 */
public class BookProgressMAO implements IBookProgressAO {

    private List<BookProgress> bookProgressList;

    /**
     * Constructor to initialize an empty list of book progress entries.
     */
    public BookProgressMAO() {
        bookProgressList = new ArrayList<>();
    }

    /**
     * Finds a specific {@link BookProgress} entry based on the book's ID and the user's ID.
     *
     * @param bookId the ID of the book.
     * @param userId the ID of the user.
     * @return the {@link BookProgress} object for the specified book and user, or {@code null} if not found.
     */
    @Override
    public BookProgress findBookProgressByBookAndUser(int bookId, int userId) {
        for (BookProgress bookProgress : bookProgressList) {
            if (bookProgress.getBookId() == bookId && bookProgress.getUserId() == userId) {
                return bookProgress;
            }
        }
        return null;
    }

    /**
     * Retrieves all {@link BookProgress} entries for a given book ID.
     *
     * @param bookId the ID of the book to search for.
     * @return a list of {@link BookProgress} objects associated with the specified book.
     */
    @Override
    public List<BookProgress> findBookProgressesByBook(int bookId) {
        List<BookProgress> bookProgresses = new ArrayList<>();
        for (BookProgress bookProgress : bookProgressList) {
            if (bookProgress.getBookId() == bookId) {
                bookProgresses.add(bookProgress);
            }
        }
        return bookProgresses;
    }

    /**
     * Retrieves all {@link BookProgress} entries for a given user ID.
     *
     * @param userId the ID of the user to search for.
     * @return a list of {@link BookProgress} objects associated with the specified user.
     */
    @Override
    public List<BookProgress> findBookProgressesByUser(int userId) {
        List<BookProgress> bookProgresses = new ArrayList<>();
        for (BookProgress bookProgress : bookProgressList) {
            if (bookProgress.getUserId() == userId) {
                bookProgresses.add(bookProgress);
            }
        }
        return bookProgresses;
    }

    /**
     * Adds a new {@link BookProgress} entry to the list.
     *
     * @param bookProgress the {@link BookProgress} object to be added.
     * @return {@code true} if the progress was successfully added, {@code false} otherwise.
     */
    @Override
    public boolean addBookProgress(BookProgress bookProgress) {
        return bookProgressList.add(bookProgress);
    }

    /**
     * Updates an existing {@link BookProgress} entry for the given book and user.
     *
     * @param bookProgress the {@link BookProgress} object containing updated information.
     * @return {@code true} if the progress was successfully updated, {@code false} otherwise.
     */
    @Override
    public boolean updateBookProgress(BookProgress bookProgress) {
        int index = bookProgressList.indexOf(findBookProgressByBookAndUser(bookProgress.getBookId(), bookProgress.getUserId()));

        // Ensure that the progress exists before attempting to update it
        if (index >= 0) {
            bookProgressList.set(index, bookProgress);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Deletes an existing {@link BookProgress} entry from the list.
     *
     * @param bookProgress the {@link BookProgress} object to be removed.
     * @return {@code true} if the progress was successfully removed, {@code false} otherwise.
     */
    @Override
    public boolean deleteBookProgress(BookProgress bookProgress) {
        return bookProgressList.remove(bookProgress);
    }
}
