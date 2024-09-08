module com.example.organizer {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jdk.compiler;

    opens com.bookclub.controller to javafx.fxml;
    exports com.bookclub.controller;

    exports com.bookclub;
    opens com.bookclub to javafx.fxml;
    exports com.bookclub.model;
    opens com.bookclub.model to javafx.fxml;
}