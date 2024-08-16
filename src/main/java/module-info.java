module com.example.organizer {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.organizer to javafx.fxml;
    exports com.example.organizer;
}