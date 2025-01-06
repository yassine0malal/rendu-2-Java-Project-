package com.example.JavaFx.graphics;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.JavaFx.DashbordCode;
import com.example.Models.User;
import com.example.transaction.TransactionManager;

import javafx.animation.FillTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class HomePage extends GridPane {
    public HomePage() {
        // Configure the GridPane
        this.setAlignment(Pos.CENTER);
        this.setHgap(20);
        this.setVgap(20);
        this.setPadding(new Insets(30));
        this.setStyle("-fx-background-color: #F9F9F9;");

        for (int i = 0; i < 6; i++) {
            if (i == 0) {
                VBox card = createCard("Total Users ", "Go to Users", i + 1, getUsersNumber());
                card.setPrefSize(200, 300);
                ;
                this.add(card, i % 3, i / 3);
            } else if (i == 2) {
                VBox card = createCard("Total Reservations ", "Go to Reservations", i + 1, getReservationsNumber());
                card.setPrefSize(400, 300);
                ;
                this.add(card, i % 3, i / 3);
            } else if (i == 1) {
                VBox card = createCard("Total Salles ", "Go to Salles", i + 1, getSallesNumber());
                card.setPrefSize(400, 300);
                ;
                this.add(card, i % 3, i / 3);
            } else if (i == 4) {
                VBox card = createCard("Total Terrains ", "Go to Terrains", i + 1, getTerrainsNumber());
                card.setPrefSize(400, 300);
                ;
                this.add(card, i % 3, i / 3);
            } else if (i == 3) {
                VBox card = createCard("Total Evenements", "Go to Evenements", i + 1, getEvenementsNumber());
                card.setPrefSize(400, 300);
                ;
                this.add(card, i % 3, i / 3);
            } else if (i == 5) {
                VBox card = createCard("Ai Assistance ", "How  can i help you with your today planes ", i + 1, 0);
                card.setPrefSize(400, 300);
                ;
                this.add(card, i % 3, i / 3);
            }
        }
    }

    private VBox createCard(String title, String description, int cardNumber, int nbr) {
        // Card layout
        VBox card = new VBox(10);
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(20));
        card.setStyle("-fx-background-color: #FFFFFF; -fx-border-radius: 10px; -fx-background-radius: 10px;"
                + " -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.2), 10, 0, 4, 4);");

        // Title
        Text cardTitle = new Text(title);
        cardTitle.setStyle(
                "-fx-font-size: 18px; -fx-font-weight: bold; -fx-font-family: 'Courier New'; -fx-fill: linear-gradient(to right, #0078D7, #00BFFF);");

        Text nbrText = new Text(" " + nbr + " ");
        nbrText.setStyle(
                "-fx-font-size: 30px; -fx-font-weight: bold; -fx-font-family:'Tahoma'; -fx-fill: linear-gradient(to right, #50B3A2, #4A90E2);");

        Text cardDescription = new Text(description);
        cardDescription.setStyle("-fx-font-size: 14px; -fx-fill: #666666; -fx-font-family: 'Courier New';");

        Button viewButton = new Button("-->");
        viewButton.setStyle(
                "-fx-background-color: #0078D7; -fx-text-fill: white; -fx-padding: 5 15; -fx-font-family: 'Courier New'; -fx-width: 100px; -fx-height: 30px;");

        Timeline enterTimeline = new Timeline(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(viewButton.styleProperty(),
                                "-fx-background-color: linear-gradient(to right, #0078D7, #00BFFF);")),
                new KeyFrame(Duration.seconds(1),
                        new KeyValue(viewButton.styleProperty(), "-fx-background-color: #0078D7;")));
        Timeline exitTimeline = new Timeline(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(viewButton.styleProperty(),
                                "-fx-background-color: linear-gradient(to right, #0078D7, #00BFFF);")),
                new KeyFrame(Duration.seconds(1),
                        new KeyValue(viewButton.styleProperty(), "-fx-background-color: #0078D7;")));
        viewButton.setOnMouseEntered(event -> {
            enterTimeline.play();
        });
        viewButton.setOnMouseExited(event -> {
            exitTimeline.play();
        });
        viewButton.setPrefSize(200, 30);
        HBox buttonBox = new HBox(10, viewButton);
        buttonBox.setAlignment(Pos.CENTER);

        Timeline enterTimeline2 = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(card.styleProperty(), "-fx-background-color: red;")),
                new KeyFrame(Duration.seconds(1), new KeyValue(card.styleProperty(),"-fx-background-color: #DEDEDE; -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 15, 0, 5, 5); -fx-border-width: 2px; -fx-border-color: rgba(255, 255, 255, 0.4);")));
        Timeline exitTimeline2 = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(card.styleProperty(), " -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 15, 0, 5, 5); -fx-border-width: 2px; -fx-border-color: rgba(255, 255, 255, 0.4);")),
                new KeyFrame(Duration.seconds(1), new KeyValue(card.styleProperty(), "-fx-background-color: blue;")));
        card.setOnMouseEntered(event -> enterTimeline2.play());
        card.setOnMouseExited(event -> exitTimeline2.play());

        card.setOnMouseEntered(event -> enterTimeline.play());
        card.setOnMouseExited(event -> exitTimeline.play());
        if (nbr==0&&cardNumber==6) {
            card.getChildren().addAll(cardTitle, cardDescription, buttonBox);
        }else{
            card.getChildren().addAll(nbrText, cardTitle, cardDescription, buttonBox);
        }

        // if (cardNumber==1) {
        //     viewButton.setOnAction(event -> {  
        //         DashbordCode.loadSallePage();});
        //     }
            
        // }
        return card;
    }

    public int getTerrainsNumber() {
        int terrainCount = 0;
        try {
            TransactionManager.beginTransaction();

            String query = "SELECT COUNT(*) FROM terrains";
            Connection connection = TransactionManager.getCurrentConnection();
            ResultSet resultSet = connection.createStatement().executeQuery(query);

            if (resultSet.next()) {
                terrainCount = resultSet.getInt(1);
            }
            TransactionManager.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(
                    "Error executing SQL query: " + e.getMessage() + " or in close connection " + e.getCause());
        }
        return terrainCount;
    }

    public int getUsersNumber() {
        int userCount = 0;
        try {
            TransactionManager.beginTransaction();

            String query = "SELECT COUNT(*) FROM utilisateurs";
            Connection connection = TransactionManager.getCurrentConnection();
            ResultSet resultSet = connection.createStatement().executeQuery(query);

            if (resultSet.next()) {
                userCount = resultSet.getInt(1);
            }
            TransactionManager.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(
                    "Error executing SQL query: " + e.getMessage() + " or in close connection " + e.getCause());
        }
        return userCount;
    }

    public int getReservationsNumber() {
        int reservationCount = 0;
        try {
            TransactionManager.beginTransaction();

            String query = "SELECT COUNT(*) FROM reservations";
            Connection connection = TransactionManager.getCurrentConnection();
            ResultSet resultSet = connection.createStatement().executeQuery(query);

            if (resultSet.next()) {
                reservationCount = resultSet.getInt(1);
            }
            TransactionManager.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(
                    "Error executing SQL query: " + e.getMessage() + " or in close connection " + e.getCause());
        }
        return reservationCount;
    }

    public int getSallesNumber() {
        int reservationCount = 0;
        try {
            TransactionManager.beginTransaction();

            String query = "SELECT COUNT(*) FROM salles";
            Connection connection = TransactionManager.getCurrentConnection();
            ResultSet resultSet = connection.createStatement().executeQuery(query);

            if (resultSet.next()) {
                reservationCount = resultSet.getInt(1);
            }
            TransactionManager.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(
                    "Error executing SQL query: " + e.getMessage() + " or in close connection " + e.getCause());
        }
        return reservationCount;
    }

    public int getEvenementsNumber() {
        int reservationCount = 0;
        try {
            TransactionManager.beginTransaction();

            String query = "SELECT COUNT(*) FROM evenements";
            Connection connection = TransactionManager.getCurrentConnection();
            ResultSet resultSet = connection.createStatement().executeQuery(query);

            if (resultSet.next()) {
                reservationCount = resultSet.getInt(1);
            }
            TransactionManager.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(
                    "Error executing SQL query: " + e.getMessage() + " or in close connection " + e.getCause());
        }
        return reservationCount;
    }

}
