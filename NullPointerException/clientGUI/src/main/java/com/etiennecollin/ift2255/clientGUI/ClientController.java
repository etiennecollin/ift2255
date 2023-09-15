package com.etiennecollin.ift2255.clientGUI;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ClientController {
    @FXML
    private Label welcomeText;
    /**
     * A BorderPane in the GUI used to drag the client window.
     */
    @FXML
    private BorderPane borderPane;
    /**
     * Stores the x-coordinate of the client window.
     */
    private double x = 0;
    /**
     * Stores the y-coordinate of the client window.
     */
    private double y = 0;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    /**
     * Closes the application.
     */
    @FXML
    private void onCloseButtonClick() {
        Stage stage = (Stage) borderPane.getScene().getWindow();
        stage.close();
    }

    /**
     * Sends the application the taskbar.
     */
    @FXML
    private void onHideButtonClick() {
        Stage stage = (Stage) borderPane.getScene().getWindow();
        stage.setIconified(true);
    }

    /**
     * Allows the dragging of the application window.
     *
     * @param event An event representing the actions of the mouse.
     */
    @FXML
    private void onBorderPaneDragged(MouseEvent event) {
        Stage stage = (Stage) borderPane.getScene().getWindow();
        stage.setX(event.getScreenX() - x);
        stage.setY(event.getScreenY() - y);
    }

    /**
     * Saves the current coordinates of the application window.
     *
     * @param event An event representing the actions of the mouse.
     */
    @FXML
    private void onBorderPanePressed(MouseEvent event) {
        x = event.getSceneX();
        y = event.getSceneY();
    }
}