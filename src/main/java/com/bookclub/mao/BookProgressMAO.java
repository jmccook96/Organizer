package com.bookclub.mao;

import com.bookclub.iao.IBookProgressAO;
import com.bookclub.model.BookProgress;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@code BookProgressMAO} class implements the {@link IBookProgressAO} interface.
 * It provides in-memory management of book progress for users, allowing to add, update,
 * delete, and retrieve progress based on book and user data.
 */
public class BookProgressMAO implements IBookProgressAO {

    private List<BookProgress> bookProgressList;

    /**
     * Constructs a {@code BookProgressMAO} with an empty list of book progress entries.
     */
    public BookProgressMAO() {
        bookProgressList = new ArrayList<>();
    }

    /**
     * Finds the {@link BookProgress} for a given book and user.
     *
     * @param bookId the ID of the book
     * @param userId the ID of the user
     * @return the {@code BookProgress} object if found, otherwise {@code null}
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
     * Finds all {@link BookProgress} entries for a given book.
     *
     * @param bookId the ID of the book
     * @return a list of {@code BookProgress} entries for the specified book
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
     * Finds all {@link BookProgress} entries for a given user.
     *
     * @param userId the ID of the user
     * @return a list of {@code BookProgress} entries for the specified user
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
     * @param bookProgress the {@code BookProgress} object to add
     * @return {@code true} if the progress was added successfully, otherwise {@code false}
     */
    @Override
    public boolean addBookProgress(BookProgress bookProgress) {
        return bookProgressList.add(bookProgress);
    }

    /**
     * Updates an existing {@link BookProgress} entry.
     *
     * @param bookProgress the {@code BookProgress} object to update
     * @return {@code true} if the progress was updated successfully, otherwise {@code false}
     */
    @Override
    public boolean updateBookProgress(BookProgress bookProgress) {
        return bookProgressList.set(bookProgressList.indexOf(findBookProgressByBookAndUser(bookProgress.getBookId(), bookProgress.getUserId())), bookProgress) != null;
    }

    /**
     * Deletes a {@link BookProgress} entry from the list.
     *
     * @param bookProgress the {@code BookProgress} object to remove
     * @return {@code true} if the progress was deleted successfully, otherwise {@code false}
     */
    @Override
    public boolean deleteBookProgress(BookProgress bookProgress) {
        return bookProgressList.remove(bookProgress);
    }
}
