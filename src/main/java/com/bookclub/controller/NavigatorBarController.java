package com.bookclub.controller;

import com.bookclub.util.StageFactory;
import com.bookclub.util.StageView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

/**
 * The {@code NavigatorBarController} class handles the navigation bar functionality for the
 * book club application. It defines the buttons and icons for navigation and implements
 * navigation logic between different views such as home, books, account, events, and chat.
 */
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

    /**
     * Initializes the navigation bar layout by binding the width and height properties
     * of the buttons and icons to the main stage and navigation bar dimensions.
     * This method is automatically called after the controller's root element has been processed.
     */
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

    /**
     * A placeholder method that navigates to the home view. This is for testing purposes
     * and prints a message indicating that the navigation logic is yet to be implemented.
     */
    public void navigateTest() {
        StageFactory.getInstance().switchScene(StageView.HOME);
        System.out.println("YET TO BE IMPLEMENTED. GOING HOME.");
    }

    /**
     * Navigates to the home view by switching the scene to the {@code StageView.HOME}.
     */
    public void navigateHome() {
        StageFactory.getInstance().switchScene(StageView.HOME);
    }

    /**
     * Navigates to the books view by switching the scene to the {@code StageView.BOOKS}.
     */
    public void navigateBooks() {
        StageFactory.getInstance().switchScene(StageView.BOOKS);
    }

    /**
     * Navigates to the account settings view by switching the scene to the {@code StageView.ACCOUNT_SETTINGS}.
     */
    public void navigateAccount() {
        StageFactory.getInstance().switchScene(StageView.ACCOUNT_SETTINGS);
    }

    /**
     * Navigates to the events view by switching the scene to the {@code StageView.EVENTS}.
     */
    public void navigateEvents() {
        StageFactory.getInstance().switchScene(StageView.EVENTS);
    }

    /**
     * Navigates to the chat view by switching the scene to the {@code StageView.CHAT}.
     */
    public void navigateChat() { StageFactory.getInstance().switchScene(StageView.CHAT); }
}
