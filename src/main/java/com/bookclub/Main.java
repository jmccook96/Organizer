package com.bookclub;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login.fxml"));
        // Get screen size to default application to a quarter of the screen
        // Rectangle2D primaryScreenBounds = Screen.getPrimary().getBounds();
        
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Book Club Organizer");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}