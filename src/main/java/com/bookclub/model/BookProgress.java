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
        this.id = id;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getChapterNumber() {
        return chapterNumber;
    }
    public void setChapterNumber(int chapterNumber) {
        this.chapterNumber = chapterNumber;
    }
}
