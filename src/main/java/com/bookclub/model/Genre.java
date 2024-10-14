package com.bookclub.model;

public enum Genre {
    General("General"),
    FICTION("Fiction"),
    NON_FICTION("Non-fiction"),
    SCI_FI("Sci-Fi"),
    FANTASY("Fantasy"),
    ROMANCE("Romance"),
    HORROR("Horror");

    private final String displayName;

    Genre(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
