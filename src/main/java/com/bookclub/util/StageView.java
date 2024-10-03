package com.bookclub.util;

public enum StageView {
    LOGIN("/com/bookclub/login.fxml"),
    HOME("/com/bookclub/home.fxml"),
    //BOOK_CLUBS("/com/bookclub/book_clubs.fxml"),
    BOOKS("/com/bookclub/books.fxml"),
    EVENTS("/com/bookclub/events.fxml"),
    ACCOUNT_SETTINGS("/com/bookclub/accountSettings.fxml"),
    //CHAT("/com/bookclub/chat.fxml")
    REVIEWS("/com/bookclub/review.fxml"),
    REGISTER("/com/bookclub/register.fxml"),
    CHAT("/com/bookclub/chat.fxml");
    ;

    private final String fxmlPath;

    StageView(String fxmlPath) {
        this.fxmlPath = fxmlPath;
    }

    public String getFxmlPath() {
        return fxmlPath;
    }
}