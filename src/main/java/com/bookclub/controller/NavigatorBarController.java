package com.bookclub.controller;

import com.bookclub.util.StageFactory;
import com.bookclub.util.StageView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class NavigatorBarController {

    @FXML
    private VBox navBarVBox;
    @FXML
    private Button accountButton;
    @FXML
    private Button booksButton;
    @FXML
    private Button homeButton;
    @FXML
    private Button eventsButton;
    @FXML
    private Button chatButton;

    @FXML
    private ImageView accountIcon;
    @FXML
    private ImageView booksIcon;
    @FXML
    private ImageView homeIcon;
    @FXML
    private ImageView eventsIcon;
    @FXML
    private ImageView chatIcon;

    @FXML
    public void initialize() {
        navBarVBox.prefWidthProperty().bind(StageFactory.getInstance().getPrimaryStage().widthProperty().multiply(0.1));
        navBarVBox.prefHeightProperty().bind(StageFactory.getInstance().getPrimaryStage().heightProperty());

        accountButton.prefWidthProperty().bind(navBarVBox.widthProperty());
        accountButton.prefHeightProperty().bind(navBarVBox.prefHeightProperty().multiply(0.2));
        booksButton.prefWidthProperty().bind(navBarVBox.widthProperty());
        booksButton.prefHeightProperty().bind(accountButton.prefHeightProperty());
        homeButton.prefWidthProperty().bind(navBarVBox.widthProperty());
        homeButton.prefHeightProperty().bind(accountButton.prefHeightProperty());
        eventsButton.prefWidthProperty().bind(navBarVBox.widthProperty());
        eventsButton.prefHeightProperty().bind(accountButton.prefHeightProperty());
        chatButton.prefWidthProperty().bind(navBarVBox.widthProperty());
        chatButton.prefHeightProperty().bind(accountButton.prefHeightProperty());

        accountIcon.fitHeightProperty().bind(accountButton.prefHeightProperty().multiply(0.5));
        booksIcon.fitHeightProperty().bind(accountIcon.fitHeightProperty());
        homeIcon.fitHeightProperty().bind(accountIcon.fitHeightProperty());
        eventsIcon.fitHeightProperty().bind(accountIcon.fitHeightProperty());
        chatIcon.fitHeightProperty().bind(accountIcon.fitHeightProperty());
    }

    public void navigateTest() {
        StageFactory.getInstance().switchScene(StageView.HOME);
        System.out.println("YET TO BE IMPLEMENTED. GOING HOME.");
    }

    public void navigateHome() {
        StageFactory.getInstance().switchScene(StageView.HOME);
    }

    public void navigateBooks() {
        StageFactory.getInstance().switchScene(StageView.BOOKS);
    }

    public void navigateAccount() {
        StageFactory.getInstance().switchScene(StageView.ACCOUNT_SETTINGS);
    }

    public void navigateEvents() {
        StageFactory.getInstance().switchScene(StageView.EVENTS);
    }

    public void navigateChat() {
        StageFactory.getInstance().switchScene(StageView.CHAT);

    }
}
