package com.example.JavaFx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load the FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/main.fxml"));
        Parent root = loader.load();

        // Create the scene
        Scene scene = new Scene(root, 800, 600);

        // Link the CSS file
        scene.getStylesheets().add(getClass().getResource("/styles/styles.css").toExternalForm());

        // Set the scene and display the stage
        primaryStage.setTitle("Registration Form with CSS");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
