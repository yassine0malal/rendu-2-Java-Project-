package com.example.JavaFx;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
// import javafx.scene.control.Button;
// import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MainJavaFx extends Application  {
        
            @Override
    public void start(Stage primaryStage) throws Exception {
        // Charge le fichier FXML
        // System.out.println(getClass().getResource("/fxml/main.fxml"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/registerForm.fxml"));
        Parent root = loader.load();

        // Crée une scène et l'affiche dans la fenêtre principale
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("Application JavaFX avec FXML");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args); // Lance l'application
    }
    }

