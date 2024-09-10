package com.bookclub.util;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneHelper {
    // Singleton implementation
    private static SceneHelper instance;
    
    private SceneHelper() {}
    
    public static SceneHelper getInstance() {
        if (instance == null) {
            instance = new SceneHelper();
        }
        return instance;
    }

    /**
     * A utility method to switch the scene of the current stage.
     * This method centralizes scene switching logic to make scene transitions consistent
     * across the application, reducing redundancy.
     *
     * @param newStage The primary stage of the application where the scene will be set.
     * @param fxmlFilePath The path to the FXML file representing the new scene layout.
     *
     * Usage example:
     * {@code
     * SceneHelper.getInstance().switchScene(stage, "/com/bookclub/login.fxml");
     * }
     *
     * This will load the new FXML layout from the provided path and display it
     * on the current stage, switching from the current view to the desired view.
     *
     * This method is useful for applications with multiple scenes (e.g., login,
     * home, settings, etc.) to make scene transitions cleaner and more maintainable.
     *
     * Potential Exceptions:
     * If the FXML file cannot be found, an {@link IOException} will be caught and
     * the stack trace will be printed.
     */
    public void switchScene(Stage newStage, String fxmlFilePath) { 
        // TODO: Potentially implement passing the size to cast to?
        // TODO: We might be able to escape passing in the stage by leveraging a stage factory.
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlFilePath));
            Scene scene = new Scene(root); // Could be worth transitioning to a vector implementation.
            newStage.setScene(scene);
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void switchScene(ActionEvent event,  String fxmlFilePath) {
        switchScene(getStageFromEvent(event), fxmlFilePath);
    }

    /**
     * Helper function to translate event into Stage value.
     * 
     * @param event The event to pull Stage from.
     * @return The Stage found in the event passed.
     */
    public Stage getStageFromEvent(ActionEvent event) {
        return (Stage) ((Node)event.getSource()).getScene().getWindow(); // https://stackoverflow.com/questions/32922424/how-to-get-the-current-opened-stage-in-javafx#:~:text=Stage%20stage%20%3D%20(Stage)((Node)%20event.getSource()).getScene().getWindow()%3B
    }
}
