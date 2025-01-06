package com.example.JavaFx;

import java.util.Stack;

import com.example.JavaFx.graphics.AiAssistancePage;
import com.example.JavaFx.graphics.EventPage;
import com.example.JavaFx.graphics.HomePage;
import com.example.JavaFx.graphics.ReservationPage;
import com.example.JavaFx.graphics.SallePage;
import com.example.JavaFx.graphics.TerrainPage;
import com.example.JavaFx.graphics.UserPage;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DashbordCode extends Application {

    private static BorderPane root; // Main layout to manage the scene

    @Override
    public void start(Stage primaryStage) {
        root = new BorderPane();
        VBox navigationMenu = createNavigationMenu();

        root.setLeft(navigationMenu);
        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(new Label("© 2025 All rights reserved"));
        stackPane
                .setStyle("-fx-background-color:#98cbff; -fx-text-fill: white; -fx-font-size: 14px; -fx-cursor: hand;");
        root.setBottom(stackPane);

        // Set up the initial page (Home Page)
        HomePage homePage = new HomePage();
        root.setCenter(homePage);

        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());
        primaryStage.setTitle("JavaFX Project ");

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public VBox createNavigationMenu() {
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

        Button homeButton = createStyledButton("Home");
        Button salleButton = createStyledButton("Salle");
        Button eventButton = createStyledButton("Event");
        Button reservationButton = createStyledButton("Reservation");
        Button userPageButton = createStyledButton("User");
        Button terrainPageButton = createStyledButton("Terrain");
        Button aiAssistanceButton = createStyledButton("AI Assistance");

        addHoverEffectToButton(salleButton);
        addHoverEffectToButton(eventButton);
        addHoverEffectToButton(reservationButton);
        addHoverEffectToButton(userPageButton);
        addHoverEffectToButton(terrainPageButton);
        addHoverEffectToButton(aiAssistanceButton);
        addHoverEffectToButton(homeButton);

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
        userPageButton.setOnAction(event -> loadUserPage());
        terrainPageButton.setOnAction(event -> loadTerrainPage());
        aiAssistanceButton.setOnAction(event -> loadAiAssistancePage());
        homeButton.setOnAction(event -> root.setCenter(new HomePage()));

        navigationMenu.getChildren().addAll(menuLabel, homeButton, userPageButton, salleButton, reservationButton,
                eventButton, terrainPageButton, aiAssistanceButton);

        return navigationMenu;
    }

    public Button createStyledButton(String text) {
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

    public void addHoverEffectToButton(Button button) {
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

    static void loadSallePage() {
        SallePage testSalle = new SallePage();

        root.setCenter(testSalle);
    }

    static void loadEventsPage() {
        EventPage testEvent = new EventPage();
        root.setCenter(testEvent);
    }
    static void loadTerrainPage() {
        TerrainPage testTerrain = new TerrainPage();
        root.setCenter(testTerrain);
    }
    static void loadAiAssistancePage() {
        AiAssistancePage testAiAssistance = new AiAssistancePage();
        root.setCenter(testAiAssistance);
    }
    static void loadUserPage() {
        UserPage testUser = new UserPage();
        root.setCenter(testUser);
    }

    static void loadReservationPage() {

        ReservationPage testReservation = new ReservationPage();
        root.setCenter(testReservation);
    }

    public void styleButton(Button button) {
        button.setStyle("-fx-background-color: #34495E; -fx-text-fill: white; -fx-font-size: 14px; -fx-cursor: hand;");
        button.setPrefWidth(180);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
