package com.example.JavaFx;

import java.util.Stack;

import com.example.JavaFx.graphics.TestEvent;
import com.example.JavaFx.graphics.TestReservation;
import com.example.JavaFx.graphics.TestSalle;
import com.example.JavaFx.graphics.TestTerrain;
import com.example.JavaFx.graphics.testUser;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DashbordCode extends Application {

    private BorderPane root; // Main layout to manage the scene


    @Override
    public void start(Stage primaryStage) {
        root = new BorderPane();
        // Image image = new Image(getClass().getResourceAsStream("/images/dashboard.jpg"));
        // root.setStyle("-fx-background-image: url('images/dashboard.jpg'); -fx-background-size: cover;");
        VBox navigationMenu = createNavigationMenu();

        root.setLeft(navigationMenu);
        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(new Label("© 2025 All rights reserved"));
        stackPane.setStyle("-fx-background-color:#98cbff; -fx-text-fill: white; -fx-font-size: 14px; -fx-cursor: hand;");
        root.setBottom(stackPane);

        // Set up the initial page (Home Page)
        // loadSallePage();

        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());
        primaryStage.setTitle("JavaFX Project ");
        
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private VBox createNavigationMenu() {
        VBox navigationMenu = new VBox(10);
        navigationMenu.setPrefWidth(200);
        navigationMenu.setStyle("""
            -fx-background-color: linear-gradient(to right, #4A90E2, #50B3A2); 
            -fx-padding: 10 0 10 0; 
            -fx-border-radius: 15px 0px 0px 15px; 
            -fx-background-radius: 15px 0px 0px 15px;
            -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.4), 12, 0, 4, 4); 
            -fx-border-width: 2px;
            -fx-border-color: rgba(255, 255, 255, 0.2); 
        """);
    
        navigationMenu.setOnMouseEntered(e -> navigationMenu.setStyle("""
            -fx-background-color: linear-gradient(to right, #50B3A2, #4A90E2); 
            -fx-padding: 10 0 10 0;
            -fx-border-radius: 15px 0px 0px 15px;
            -fx-background-radius: 15px 0px 0px 15px;
            -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 15, 0, 5, 5);
            -fx-border-width: 2px;
            -fx-border-color: rgba(255, 255, 255, 0.4); 
        """));
    
        navigationMenu.setOnMouseExited(e -> navigationMenu.setStyle("""
            -fx-background-color: linear-gradient(to right,#4a90e2, #50B3A2); /* Reset to original gradient */
            -fx-padding: 10 0 10 0;
            -fx-border-radius: 15px 0px 0px 15px;
            -fx-background-radius: 15px 0px 0px 15px;
            -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.4), 12, 0, 4, 4);
            -fx-border-width: 2px;
            -fx-border-color: rgba(255, 255, 255, 0.2); 
        """));
    
        Button salleButton = createStyledButton("Salle");
        Button eventButton = createStyledButton("Event");
        Button reservationButton = createStyledButton("Reservation");
        Button userPageButton = createStyledButton("User");
        Button terrainPageButton = createStyledButton("Terrain");
    
        addHoverEffectToButton(salleButton);
        addHoverEffectToButton(eventButton);
        addHoverEffectToButton(reservationButton);
        addHoverEffectToButton(userPageButton);
        addHoverEffectToButton(terrainPageButton);
    
        Label menuLabel = new Label("Menu");
        menuLabel.setStyle("""
            -fx-text-fill: white;
            -fx-padding: 10;
            -fx-font-weight: bold;
            -fx-font-size: 20px;
            -fx-text-alignment: center;
        """);
    
        salleButton.setOnAction(e -> loadSallePage());
        eventButton.setOnAction(e -> loadEventsPage());
        reservationButton.setOnAction(e -> loadReservationPage());
        userPageButton.setOnAction(event -> root.setCenter(new testUser()));
        terrainPageButton.setOnAction(event -> root.setCenter(new TestTerrain()));
    
        navigationMenu.getChildren().addAll(menuLabel, userPageButton, salleButton, reservationButton, eventButton, terrainPageButton);
    
        return navigationMenu;
    }
    
    private Button createStyledButton(String text) {
        Button button = new Button(text);
        button.setStyle("""
            -fx-background-color: linear-gradient(to right, #4A90E2, #50B3A2);
            -fx-text-fill: white; 
            -fx-font-size: 14px;
            -fx-cursor: hand;
            -fx-border-radius: 5px;
            -fx-background-radius: 5px;
            -fx-padding: 8 15;
            -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.3), 5, 0, 2, 2);
        """);
        button.setPrefWidth(180);
        return button;
    }
    
    // Method to add hover effect to a button
    private void addHoverEffectToButton(Button button) {
        button.setOnMouseEntered(e -> button.setStyle("""
            -fx-background-color: linear-gradient(to right, #50B3A2, #4A90E2); 
            -fx-text-fill: white;
            -fx-font-size: 14px;
            -fx-cursor: hand;
            -fx-border-radius: 5px;
            -fx-background-radius: 5px;
            -fx-padding: 8 15;
            -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.4), 8, 0, 3, 3); 
        """));
        button.setOnMouseExited(e -> button.setStyle("""
            -fx-background-color: linear-gradient(to right, #4A90E2, #50B3A2); /* Reset to original gradient */
            -fx-text-fill: white;
            -fx-font-size: 14px;
            -fx-cursor: hand;
            -fx-border-radius: 5px;
            -fx-background-radius: 5px;
            -fx-padding: 8 15;
            -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.3), 5, 0, 2, 2); /* Reset shadow */
        """));
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

