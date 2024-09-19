package com.bookclub.util;

import com.bookclub.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * The StageFactory class is responsible for managing and switching scenes in the JavaFX application.
 * It preloads all FXML views on startup and allows for easy switching between them using an enum to represent each view.
 */
public class StageFactory {
    private static StageFactory instance;
    private Stage primaryStage;

    /**
     * Empty private constructor for singleton.
     */
    private StageFactory() {
    }
    
    /**
     * Creates the singleton instance and sets the primary stage.
     * Preloads all views at startup and stores them in the loadedViews map.
     *
     * @param stage The primary stage of the JavaFX application.
     */
    public static void initalize(Stage stage) {
        if (instance == null) {
            instance = new StageFactory();
        }
        instance.primaryStage = stage;
    }

    /**
     * Returns the singleton instance of StageFactory
     * 
     * @return the singleton intance of StageFactory
     * @throws IllegalStateException if StageFactory has not been initialized yet.
     * 
     * @see #initalize(Stage) 
     */
    public static StageFactory getInstance() {
        if (instance == null) {
            throw new IllegalStateException("StageFactory is not initialized. Call initialize() first.");
        }
        return instance;
    }

    /**
     * Switches the current scene of the primary stage to the specified view.
     * If the view has not been preloaded, an exception is thrown to prevent runtime errors.
     *
     * @param view The view to switch to, represented by the View enum.
     * @throws IllegalStateException If the specified view is not found in the preloaded views.
     */
    public void switchScene(StageView view) {
        System.out.println("Switching scene to " + view.name());

        try {
            Parent root = FXMLLoader.load(getClass().getResource(view.getFxmlPath()));
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/com/bookclub/styles/styles.css").toString());
            primaryStage.setScene(scene);
            primaryStage.setWidth(1920);
            primaryStage.setHeight(1080);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to load FXML for view: " + view.name());
        }
    }
}
