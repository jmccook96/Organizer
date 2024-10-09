package com.bookclub;

import com.bookclub.util.StageFactory;
import com.bookclub.util.StageView;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The {@code Main} class serves as the entry point for the Book Club Organizer application.
 * It extends {@code Application} and handles the initial setup and launching of the application UI.
 */
public class Main extends Application {

    /**
     * Starts the JavaFX application by initializing the primary stage and switching to the login scene.
     *
     * @param stage The primary stage for this application, onto which the application scenes will be set.
     * @throws IOException If an input or output exception occurs during the stage initialization.
     */
    @Override
    public void start(Stage stage) throws IOException {
        StageFactory.initialize(stage);
        StageFactory.getInstance().switchScene(StageView.LOGIN);
        
        stage.setTitle("Book Club Organizer");
        stage.show();
    }

    /**
     * The main method which launches the JavaFX application.
     *
     * @param args The command-line arguments (UNUSED).
     */
    public static void main(String[] args) {
        launch();
    }
}