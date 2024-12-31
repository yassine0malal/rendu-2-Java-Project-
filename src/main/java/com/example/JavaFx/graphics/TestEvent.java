package com.example.JavaFx.graphics;

import java.sql.Date;
import java.sql.SQLException;

import com.example.DAOImplementation.EvenementDAO;
import com.example.Models.Evenement;
import com.example.transaction.TransactionManager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class TestEvent extends GridPane {

    private TextField eventNameField;
    private TextField eventDescriptionField;
    private DatePicker eventDatePicker;
    private TextField userIdField;
    private Button addEventButton;

    private HBox crudMenu;
    private AnchorPane contentArea;
    private GridPane addEventForm;
    private TableView<Evenement> eventTable;

    public TestEvent() {
        // Set grid properties
        this.setAlignment(Pos.CENTER);
        this.setHgap(10);
        this.setVgap(10);
        this.setPadding(new Insets(20));
        this.setStyle("-fx-background-color: #f9f9f9;");

        contentArea = new AnchorPane();
        contentArea.setPrefSize(800, 600);

        // Create CRUD Menu
        crudMenu = new HBox(10);
        crudMenu.setPadding(new Insets(10));
        crudMenu.setSpacing(10);

        Button addEventButton = new Button("Add Event");
        Button displayEventsButton = new Button("Display Events");

        addEventButton.setOnAction(e -> displayAddEventForm());
        displayEventsButton.setOnAction(e -> displayAllEvents());

        crudMenu.getChildren().addAll(addEventButton, displayEventsButton);
        this.add(crudMenu, 0, 0);

        // Empty content area
        Label emptyLabel = new Label("Select an action from the navbar above");
        emptyLabel.setStyle("-fx-font-size: 18px;");
        contentArea.getChildren().add(emptyLabel);
        this.add(contentArea, 0, 1, 2, 1);
    }

    private void displayAddEventForm() {
        clearContentArea();
        addEventForm = new GridPane();
        addEventForm.setAlignment(Pos.CENTER);
        addEventForm.setHgap(10);
        addEventForm.setVgap(10);
        addEventForm.setPadding(new Insets(20));
        addEventForm.setStyle("-fx-background-color: #f9f9f9;");

        Label titleLabel = new Label("Add New Event");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #333;");
        addEventForm.add(titleLabel, 0, 0, 2, 1);
        GridPane.setHalignment(titleLabel, HPos.CENTER);

        Label eventNameLabel = new Label("Event Name:");
        eventNameField = new TextField();
        addEventForm.add(eventNameLabel, 0, 1);
        addEventForm.add(eventNameField, 1, 1);

        Label eventDescriptionLabel = new Label("Description:");
        eventDescriptionField = new TextField();
        addEventForm.add(eventDescriptionLabel, 0, 2);
        addEventForm.add(eventDescriptionField, 1, 2);

        Label eventDateLabel = new Label("Event Date:");
        eventDatePicker = new DatePicker();
        addEventForm.add(eventDateLabel, 0, 3);
        addEventForm.add(eventDatePicker, 1, 3);

        Label userIdLabel = new Label("User ID:");
        userIdField = new TextField();
        addEventForm.add(userIdLabel, 0, 4);
        addEventForm.add(userIdField, 1, 4);

        addEventButton = new Button("Add Event");
        addEventButton.setOnAction(e -> handleAddEvent());
        addEventForm.add(addEventButton, 1, 5);
        GridPane.setHalignment(addEventButton, HPos.RIGHT);

        contentArea.getChildren().add(addEventForm);
    }

    private void handleAddEvent() {
        String eventName = eventNameField.getText();
        String eventDescription = eventDescriptionField.getText();
        Date eventDate = Date.valueOf(eventDatePicker.getValue());
        int userId = Integer.parseInt(userIdField.getText());

        try {
            TransactionManager.beginTransaction();
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to start transaction");
            return;
        }

        EvenementDAO eventDAO = new EvenementDAO();
        eventDAO.setConnection(TransactionManager.getCurrentConnection());
        Evenement event = new Evenement(eventName, eventDate, eventDescription, userId);
        eventDAO.ajouter(event);


        try {
            TransactionManager.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to commit transaction");
        }

        showAlert(Alert.AlertType.INFORMATION, "Success", "Event added successfully!");
        displayAllEvents();
        eventNameField.clear();
        eventDescriptionField.clear();
        eventDatePicker.setValue(null);
        userIdField.clear();
    }

    private void displayAllEvents() {
        clearContentArea();

        eventTable = new TableView<>();
        eventTable.setPrefWidth(600);
        eventTable.setPrefHeight(400);

        TableColumn<Evenement, String> nameColumn = new TableColumn<>("Event Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("nomEvent"));

        TableColumn<Evenement, String> descriptionColumn = new TableColumn<>("Description");
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        TableColumn<Evenement, Date> dateColumn = new TableColumn<>("Date");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        TableColumn<Evenement, Integer> userIdColumn = new TableColumn<>("User ID");
        userIdColumn.setCellValueFactory(new PropertyValueFactory<>("id_user"));

        eventTable.getColumns().addAll(nameColumn, descriptionColumn, dateColumn, userIdColumn);

        eventTable.setItems(getEventsFromDatabase());
        contentArea.getChildren().add(eventTable);
    }

    private ObservableList<Evenement> getEventsFromDatabase() {
        ObservableList<Evenement> events = FXCollections.observableArrayList();
        try {
            TransactionManager.beginTransaction();
            EvenementDAO eventDAO = new EvenementDAO();
            eventDAO.setConnection(TransactionManager.getCurrentConnection());
            events.addAll(eventDAO.afficher());
            TransactionManager.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to fetch events");
        }

        return events;
    }

    private void clearContentArea() {
        contentArea.getChildren().clear();
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
