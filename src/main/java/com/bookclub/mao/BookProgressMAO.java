package com.bookclub.mao;

import com.bookclub.iao.IBookProgressAO;
import com.bookclub.model.BookProgress;

import java.util.ArrayList;
import java.util.List;

public class BookProgressMAO implements IBookProgressAO {

    private List<BookProgress> bookProgressList;

    public BookProgressMAO() {
        bookProgressList = new ArrayList<>();
    }

    @Override
    public BookProgress findBookProgressByBookAndUser(int bookId, int userId) {
        for (BookProgress bookProgress : bookProgressList) {
            if (bookProgress.getBookId() == bookId && bookProgress.getUserId() == userId) {
                return bookProgress;
            }
        }
        return null;
    }

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

    @Override
    public boolean addBookProgress(BookProgress bookProgress) {
        return bookProgressList.add(bookProgress);
    }

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


    @Override
    public boolean deleteBookProgress(BookProgress bookProgress) {
        return bookProgressList.remove(bookProgress);
    }
}
