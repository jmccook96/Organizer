package com.bookclub.model;

public class BookProgress {
    private int id;
    private int bookId;
    private int userId;
    private int chapterNumber;

    public BookProgress(int id, int bookId, int userId, int ChapterNumber) {
        setId(id);
        setBookId(bookId);
        setUserId(userId);
        setChapterNumber(ChapterNumber);
    }

    public BookProgress(int bookId, int userId, int ChapterNumber) {
        setBookId(bookId);
        setUserId(userId);
        setChapterNumber(ChapterNumber);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID must be greater than zero.");
        }
        this.id = id;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        if (bookId <= 0) {
            throw new IllegalArgumentException("Book ID must be a valid number.");
        }
        this.bookId = bookId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        if (userId <= 0) {
            throw new IllegalArgumentException("User ID must be a valid number.");
        }
        this.userId = userId;
    }

    public int getChapterNumber() {
        return chapterNumber;
    }

    public void setPageNumber(int pageNumber) {
        if (pageNumber <= 0) {
            throw new IllegalArgumentException("User ID must be a valid number.");
        }
        this.pageNumber = pageNumber;
    }
}
