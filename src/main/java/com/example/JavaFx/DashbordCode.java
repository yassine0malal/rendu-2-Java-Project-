package com.example.JavaFx;

import com.example.JavaFx.graphics.TestEvent;
import com.example.JavaFx.graphics.TestReservation;
import com.example.JavaFx.graphics.TestSalle;
import com.example.JavaFx.graphics.TestTerrain;
import com.example.JavaFx.graphics.testUser;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class DashbordCode extends Application {

    private BorderPane root; // Main layout to manage the scene


    @Override
    public void start(Stage primaryStage) {
        // Initialize the root layout
        root = new BorderPane();
        // Scene scene = new Scene(root, 800, 600);


        // Create the main navigation menu
        VBox navigationMenu = createNavigationMenu();

        // Set the navigation menu on the left
        root.setLeft(navigationMenu);
        root.setBottom(new Label("© 2021 All rights reserved"));

        // Set up the initial page (Home Page)
        // loadSallePage();

        // Create and set the scene
        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());
        primaryStage.setTitle("JavaFX Project ");
        
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private VBox createNavigationMenu() {
        VBox navigationMenu = new VBox(10);
        navigationMenu.setStyle("-fx-background-color: #2C3E50; -fx-padding: 10;");
        navigationMenu.setPrefWidth(200);

        // Create buttons
        Button salleButton = new Button("Salle");
        Button EventButton = new Button("Event");
        Button reservationButton = new Button("Reservation");
        Button userPageButton = new Button("User");
        Button terrainPageButton = new Button("Terrain");
        


        styleButton(salleButton);
        styleButton(EventButton);
        styleButton(reservationButton);
        styleButton(userPageButton);
        styleButton(terrainPageButton);

        userPageButton.setOnMouseEntered(e -> userPageButton.setStyle("-fx-background-color:rgb(28, 65, 105); -fx-text-fill: white; -fx-font-size: 14px; -fx-cursor: hand;"));
        userPageButton.setOnMouseExited(e -> userPageButton.setStyle("-fx-background-color: #34495E; -fx-text-fill: white; -fx-font-size: 14px; -fx-cursor: hand;"));
        EventButton.setOnMouseEntered(e -> EventButton.setStyle("-fx-background-color:rgb(28, 65, 105); -fx-text-fill: white; -fx-font-size: 14px; -fx-cursor: hand;"));
        EventButton.setOnMouseExited(e -> EventButton.setStyle("-fx-background-color: #34495E; -fx-text-fill: white; -fx-font-size: 14px; -fx-cursor: hand;"));
        salleButton.setOnMouseEntered(e -> salleButton.setStyle("-fx-background-color:rgb(28, 65, 105); -fx-text-fill: white; -fx-font-size: 14px; -fx-cursor: hand;"));
        salleButton.setOnMouseExited(e -> salleButton.setStyle("-fx-background-color: #34495E; -fx-text-fill: white; -fx-font-size: 14px; -fx-cursor: hand;"));
        terrainPageButton.setOnMouseEntered(e -> terrainPageButton.setStyle("-fx-background-color:rgb(28, 65, 105); -fx-text-fill: white; -fx-font-size: 14px; -fx-cursor: hand;"));
        terrainPageButton.setOnMouseExited(e -> terrainPageButton.setStyle("-fx-background-color: #34495E; -fx-text-fill: white; -fx-font-size: 14px; -fx-cursor: hand;"));
        reservationButton.setOnMouseEntered(e -> reservationButton.setStyle("-fx-background-color:rgb(28, 65, 105); -fx-text-fill: white; -fx-font-size: 14px; -fx-cursor: hand;"));
        reservationButton.setOnMouseExited(e -> reservationButton.setStyle("-fx-background-color: #34495E; -fx-text-fill: white; -fx-font-size: 14px; -fx-cursor: hand;"));


        Label l1 = new Label("Menu");
        l1.setFont(Font.font("SpaceMono-BoldItalic", 20));

        salleButton.setFont(Font.font("SpaceMono-BoldItalic", 14));
        // l1.setStyle("-fx-text-fill: white; -fx-padding: 10; -fx-font-weight: bold; -fx-font-size: 20px; -fx-cursor: hand; -fx-justify-content: center; -fx-alignment: center;");



        // Set button actions to load specific pages
        salleButton.setOnAction(e -> loadSallePage());
        EventButton.setOnAction(e -> loadEventsPage());
        reservationButton.setOnAction(e -> loadReservationPage());
        userPageButton.setOnAction(event -> {
            testUser userPage = new testUser();
            root.setCenter(userPage); // Assuming 'root' is a BorderPane
        });
        terrainPageButton.setOnAction(event -> {
            TestTerrain terrainPage = new TestTerrain();
            root.setCenter(terrainPage); // Assuming 'root' is a BorderPane
        });

        // Add buttons to the menu
        navigationMenu.getChildren().addAll(l1,userPageButton, EventButton, reservationButton, salleButton, terrainPageButton);

        return navigationMenu;
    }

   
    private void loadSallePage() {
        TestSalle testSalle = new TestSalle();

        root.setCenter(testSalle);
    }


    private void loadEventsPage() {
       TestEvent testEvent = new TestEvent();
        root.setCenter(testEvent);
    }

    private void loadReservationPage() {

        TestReservation testReservation = new TestReservation();
        root.setCenter(testReservation);
    }

    /**
     * Utility method to style buttons.
     */
    private void styleButton(Button button) {
        button.setStyle("-fx-background-color: #34495E; -fx-text-fill: white; -fx-font-size: 14px; -fx-cursor: hand;");
        button.setPrefWidth(180);
    }

    public static void main(String[] args) {
        launch(args);
    }
}

