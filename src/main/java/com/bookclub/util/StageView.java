package com.bookclub.util;

public enum StageView {
    MAIN("/com/bookclub/main.fxml"),
    LOGIN("/com/bookclub/login.fxml"),
    //HOME("/com/bookclub/home.fxml"),
    //BOOK_CLUBS("/com/bookclub/book_clubs.fxml"),
    BOOKS("/com/bookclub/books.fxml"),
    //EVENTS("/com/bookclub/events.fxml"),
    //PROFILE("/com/bookclub/profile.fxml"),
    //CHAT("/com/bookclub/chat.fxml")
    ;

    private final String fxmlPath;

    StageView(String fxmlPath) {
        this.fxmlPath = fxmlPath;
    }

    public String getFxmlPath() {
        return fxmlPath;
    }
}