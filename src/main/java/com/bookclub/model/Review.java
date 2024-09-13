package com.bookclub.model;

public class Review {

    private User user;
    private Book book;
    private int rating;

    public Review(User user, Book book, int rating) {
        this.user = user;
        this.book = book;
        this.rating = rating;
    }

    public User getUser() {
        return user;
    }

    public Book getBook() {
        return book;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
