package com.example.JavaFx;

import com.example.JavaFx.graphics.TestEvent;
import com.example.JavaFx.graphics.TestSalle;
import com.example.JavaFx.graphics.TestTerrain;
import com.example.JavaFx.graphics.testUser;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class DashbordCode extends Application {

    private BorderPane root; // Main layout to manage the scene

    @Override
    public void start(Stage primaryStage) {
        // Initialize the root layout
        root = new BorderPane();

        // Create the main navigation menu
        VBox navigationMenu = createNavigationMenu();

        // Set the navigation menu on the left
        root.setLeft(navigationMenu);

        // Set up the initial page (Home Page)
        loadSallePage();

        // Create and set the scene
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("JavaFX Dashboard with Navigation");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Creates the navigation menu with buttons.
     */
    private VBox createNavigationMenu() {
        VBox navigationMenu = new VBox(10);
        navigationMenu.setStyle("-fx-background-color: #2C3E50; -fx-padding: 10;");
        navigationMenu.setPrefWidth(200);

        // Create buttons
        Button salleButton = new Button("Salle");
        Button EventButton = new Button("Events");
        Button reservationButton = new Button("Reservations");
        Button userPageButton = new Button("User");
        Button terrainPageButton = new Button("Terrain");
        


        styleButton(salleButton);
        styleButton(EventButton);
        styleButton(reservationButton);
        styleButton(userPageButton);
        styleButton(terrainPageButton);

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
        navigationMenu.getChildren().addAll(userPageButton, EventButton, reservationButton, salleButton, terrainPageButton);

        return navigationMenu;
    }

    /**
     * Loads the Home Page.
    */
    private void loadSallePage() {
        TestSalle testSalle = new TestSalle();

        root.setCenter(testSalle);
    }

    /**
     * Loads the Settings Page.
     */
    private void loadEventsPage() {
       TestEvent testEvent = new TestEvent();

        // Replace the center of the root layout with the Settings Page
        root.setCenter(testEvent);
    }

    /**
     * Loads the About Page.
     */
    private void loadReservationPage() {
        VBox aboutPage = new VBox();
        aboutPage.setAlignment(Pos.CENTER);
        aboutPage.setPadding(new Insets(20));
        Text aboutText = new Text("About Page");
        aboutText.setStyle("-fx-font-size: 16px;");
        aboutPage.getChildren().add(aboutText);

        // Replace the center of the root layout with the About Page
        root.setCenter(aboutPage);
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

