module com.example.organizer {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jdk.compiler;
    requires java.desktop;
    requires org.controlsfx.controls;

    opens com.bookclub.controller to javafx.fxml;
    exports com.bookclub.controller;

    exports com.bookclub;
    opens com.bookclub to javafx.fxml;
    exports com.bookclub.model;
    opens com.bookclub.model to javafx.fxml;
}