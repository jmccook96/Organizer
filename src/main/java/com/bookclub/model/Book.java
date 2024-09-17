package com.bookclub.model;

public class Book {

    private String title;
    private String author;
    private Integer bookid;
    private String genre;

    public Book(Integer bookid, String title, String author, String genre) {
        this.bookid = bookid;
        this.title = title;
        this.author = author;
        this.genre = genre;
    }

    public Integer getBookID() {
        return bookid;
    }

    public String getGenre() {
        return genre;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return title + " by " + author;
    }
}
