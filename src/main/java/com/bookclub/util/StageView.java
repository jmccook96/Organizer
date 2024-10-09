package com.bookclub.util;

/**
 * An enumeration representing the various views (scenes) in the Book Club application.
 * Each enum constant corresponds to a specific FXML file that defines the layout for that view.
 */
public enum StageView {
    LOGIN("/com/bookclub/login.fxml"),
    HOME("/com/bookclub/home.fxml"),
    //BOOK_CLUBS("/com/bookclub/book_clubs.fxml"),
    BOOKS("/com/bookclub/books.fxml"),
    EVENTS("/com/bookclub/events.fxml"),
    ACCOUNT_SETTINGS("/com/bookclub/accountSettings.fxml"),
    CHAT("/com/bookclub/chat.fxml"),
    BOOK_ITEM("/com/bookclub/bookItem.fxml"),
    REGISTER("/com/bookclub/register.fxml")
    ;

    private final String fxmlPath;

    /**
     * Constructor to initialize the FXML path for each view.
     *
     * @param fxmlPath The path to the FXML file corresponding to the view.
     */
    StageView(String fxmlPath) {
        this.fxmlPath = fxmlPath;
    }

    /**
     * Gets the FXML path associated with the view.
     *
     * @return The FXML path for the view.
     */
    public String getFxmlPath() {
        return fxmlPath;
    }
}