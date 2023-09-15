package com.etiennecollin.ift2255.clientGUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class ClientLauncher extends Application {
    /**
     * Runs the client.
     */
    public static void main(String[] args) {
        launch();
    }

    /**
     * This method starts the JavaFX client.
     *
     * @param stage A JavaFX application stage object.
     *
     * @throws IOException If there is an error loading the FXML file.
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ClientLauncher.class.getResource("client_interface.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.setFill(Color.TRANSPARENT);

        // Set window properties
        stage.setTitle("NullPointerException");
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setResizable(true);

        // Display window
        stage.show();
    }
}