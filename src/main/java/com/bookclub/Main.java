package com.bookclub;

import com.bookclub.util.StageFactory;
import com.bookclub.util.StageView;
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
        StageFactory.initalize(stage);
        StageFactory.getInstance().switchScene(StageView.LOGIN);
        
        stage.setTitle("Book Club Organizer");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}