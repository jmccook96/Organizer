package com.bookclub.iao;

import com.bookclub.model.BookProgress;
import java.util.List;

public interface IBookProgressAO {
    /**
     * Retrieves a BookProgress from the database based on the bookId and userId
     * @param bookId The bookId of the BookProgress to retrieve
     * @param userId The userId of the BookProgress to retrieve
     * @return The BookProgress with the given bookId and userId, or null if not found.
     */
    BookProgress findBookProgressByBookAndUser(int bookId, int userId);

    /**
     * Retrieves a list of BookProgresses from the database based on the bookId
     * @param bookId The bookId of the BookProgresses to retrieve
     * @return A list of BookProgresses with the given bookId.
     */
    List<BookProgress> findBookProgressesByBook(int bookId);

    /**
     * Retrieves a list of BookProgresses from the database based on the userId
     * @param userId The userId of the BookProgresses to retrieve
     * @return A list of BookProgresses with the given userId.
     */
    List<BookProgress> findBookProgressesByUser(int userId);

    /**
     * Adds a new BookProgress to the database
     * @param bookProgress The BookProgress to add.
     * @return If operation succeeded
     */
    boolean addBookProgress(BookProgress bookProgress);

    /**
     * Updates an existing BookProgress in the database.
     * @param bookProgress The BookProgress with information to update.
     * @return If operation succeeded
     */
    boolean updateBookProgress(BookProgress bookProgress);

    /**
     * Removes a BookProgress from the database.
     * @param bookProgress The BookProgress to delete.
     * @return If operation succeeded
     */
    boolean deleteBookProgress(BookProgress bookProgress);
}
