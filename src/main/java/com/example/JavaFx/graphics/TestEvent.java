package com.example.JavaFx.graphics;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.DAOImplementation.EvenementDAO;
import com.example.DAOImplementation.UserDAO;
import com.example.Models.Evenement;
import com.example.transaction.TransactionManager;

import javafx.beans.property.SimpleStringProperty;
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
        this.setStyle("-fx-background-color: #DEDEDE;");

        contentArea = new AnchorPane();
        contentArea.setPrefSize(800, 600);

        // Create CRUD Menu
        crudMenu = new HBox(10);
        crudMenu.setPadding(new Insets(10));
        crudMenu.setSpacing(10);
        crudMenu.setMinWidth(800);
        crudMenu.setStyle("-fx-text-color: #f9f9f9; -fx-font-size: 18px; -fx-font-weight: bold;");

        Button addEventButton = new Button("Add Event");
        addEventButton.setStyle("-fx-background-color: transparent; -fx-border-width: 0;");
        Button displayEventsButton = new Button("Display Events");

        addEventButton.setOnAction(e -> displayAddEventForm());
        displayEventsButton.setOnAction(e -> displayAllEvents());

        crudMenu.getChildren().addAll(addEventButton, displayEventsButton);
        this.add(crudMenu, 0, 0);
        crudMenu.setStyle("-fx-background-color:#E44621;");

        // Empty content area
        Label emptyLabel = new Label("Select an action from the navbar above");
        emptyLabel.setStyle("-fx-font-size: 18px;");
        contentArea.getChildren().add(emptyLabel);
        this.add(contentArea, 0, 1, 2, 1);
    }

    private ComboBox<String> userComboBox;

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
    
        Label userIdLabel = new Label("User:");
        userComboBox = new ComboBox<>(getUsersFromDatabase());
        addEventForm.add(userIdLabel, 0, 4);
        addEventForm.add(userComboBox, 1, 4);
    
        addEventButton = new Button("Add Event");
        addEventButton.setOnAction(e -> handleAddEvent());
        addEventForm.add(addEventButton, 1, 5);
        GridPane.setHalignment(addEventButton, HPos.RIGHT);
    
        contentArea.getChildren().add(addEventForm);
    }
    

    private void handleAddEvent() {
        String eventName = eventNameField.getText();
        String eventDescription = eventDescriptionField.getText();
        Date eventDate = null;
        int userId = -1;
    
        // Validate the event date
        if (eventDatePicker.getValue() != null) {
            eventDate = Date.valueOf(eventDatePicker.getValue());
        } else {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Please select a valid event date.");
            return;
        }
    
        // Validate the user selection
        if (userComboBox.getValue() != null && !userComboBox.getValue().isEmpty()) {
            try {
                userId = Integer.parseInt(userComboBox.getValue().split(" - ")[0]); // Extract user ID
            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.ERROR, "Validation Error", "Invalid user selection.");
                return;
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Please select a user.");
            return;
        }
    
        // Check for empty fields
        if (eventName.isEmpty() || eventDescription.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "All fields are required.");
            return;
        }
    
        try {
            // Begin the transaction
            TransactionManager.beginTransaction();
    
            // Create the DAO and event object
            EvenementDAO eventDAO = new EvenementDAO();
            eventDAO.setConnection(TransactionManager.getCurrentConnection());
            Evenement event = new Evenement(eventName, eventDate, eventDescription, userId);
    
            // Add the event to the database
            eventDAO.ajouter(event);
    
            // Commit the transaction
            TransactionManager.commit();
    
            // Show success message
            showAlert(Alert.AlertType.INFORMATION, "Success", "Event added successfully!");
    
            // Refresh the event list and clear fields
            displayAllEvents();
            eventNameField.clear();
            eventDescriptionField.clear();
            eventDatePicker.setValue(null);
            userComboBox.setValue(null);
    
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to add the event.");
            try {
                TransactionManager.rollback();
            } catch (SQLException rollbackException) {
                rollbackException.printStackTrace();
            }
        }
    }
    
    private void displayAllEvents() {
        clearContentArea();
    
        eventTable = new TableView<>();
        eventTable.setPrefWidth(600);
        eventTable.setPrefHeight(400);
    
        // Columns for event details
        TableColumn<Evenement, String> nameColumn = new TableColumn<>("Event Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("nomEvent"));
    
        TableColumn<Evenement, String> descriptionColumn = new TableColumn<>("Description");
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
    
        TableColumn<Evenement, Date> dateColumn = new TableColumn<>("Date");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
    

        TableColumn<Evenement, String> userNameColumn = new TableColumn<>("User Name");
        userNameColumn.setCellValueFactory(cellData -> {
            System.out.println(cellData.getValue().getId_user());
        int userId = cellData.getValue().getId_user();
        String userName = getUserNameById(userId); // Fetch username based on user ID
        return new SimpleStringProperty(userName);
    });


    
        // Action column
        TableColumn<Evenement, Void> actionColumn = new TableColumn<>("Action");
        actionColumn.setCellFactory(param -> new TableCell<>() {
            private final Button updateButton = new Button("Update");
            private final Button deleteButton = new Button("Delete");
    
            {
                updateButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
                deleteButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");
    
                // Update action
                updateButton.setOnAction(event -> {
                    Evenement evenement = getTableView().getItems().get(getIndex());
                    modifyEventForm(evenement);
                });
    
                // Delete action
                deleteButton.setOnAction(event -> {
                    Evenement evenement = getTableView().getItems().get(getIndex());
                    handleDeleteEvent(evenement);
                });
    
                HBox actionButtons = new HBox(10, updateButton, deleteButton);
                actionButtons.setAlignment(Pos.CENTER);
                setGraphic(actionButtons);
            }
    
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : getGraphic());
            }
        });
    
        // Add columns to the table
        eventTable.getColumns().addAll(nameColumn, descriptionColumn, dateColumn, userNameColumn, actionColumn);
        eventTable.setItems(getEventsFromDatabase());
        contentArea.getChildren().add(eventTable);
    }
    



private String getUserNameById(int userId) {
    try {
    TransactionManager.beginTransaction();
    Connection connexion = TransactionManager.getCurrentConnection();

    String query = "SELECT prenom FROM utilisateurs WHERE id_user = ?";
    try (PreparedStatement statement = connexion.prepareStatement(query)) {
        statement.setInt(1, userId);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {

            TransactionManager.commit();
            TransactionManager.closeConnection();
            System.out.println("************get it_____________");
            return resultSet.getString("prenom");

        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
} catch (SQLException e) {
    e.printStackTrace();
}

    return "******"; // Return a default value if no username is found
}








    // Modify Event Form
    private void modifyEventForm(Evenement evenement) {
        clearContentArea();
    
        addEventForm = new GridPane();
        addEventForm.setAlignment(Pos.CENTER);
        addEventForm.setHgap(10);
        addEventForm.setVgap(10);
        addEventForm.setPadding(new Insets(20));
        addEventForm.setStyle("-fx-background-color: #f9f9f9;");
    
        Label titleLabel = new Label("Modify Event");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #333;");
        addEventForm.add(titleLabel, 0, 0, 2, 1);
        GridPane.setHalignment(titleLabel, HPos.CENTER);
    
        Label eventNameLabel = new Label("Event Name:");
        TextField eventNameField = new TextField(evenement.getNomEvent());
        addEventForm.add(eventNameLabel, 0, 1);
        addEventForm.add(eventNameField, 1, 1);
    
        Label eventDescriptionLabel = new Label("Description:");
        TextField eventDescriptionField = new TextField(evenement.getDescription());
        addEventForm.add(eventDescriptionLabel, 0, 2);
        addEventForm.add(eventDescriptionField, 1, 2);
    
        Label eventDateLabel = new Label("Event Date:");
        DatePicker eventDatePicker = new DatePicker(evenement.getDate().toLocalDate());
        addEventForm.add(eventDateLabel, 0, 3);
        addEventForm.add(eventDatePicker, 1, 3);
    
        Label userIdLabel = new Label("User:");
        ComboBox<String> userComboBox = new ComboBox<>(getUsersFromDatabase());
        userComboBox.setValue(evenement.getId_user() + ""); // Set current user
        addEventForm.add(userIdLabel, 0, 4);
        addEventForm.add(userComboBox, 1, 4);
    
        Button updateButton = new Button("Update");
        updateButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        addEventForm.add(updateButton, 1, 5);
        GridPane.setHalignment(updateButton, HPos.RIGHT);
    
        updateButton.setOnAction(event -> {
            evenement.setNomEvent(eventNameField.getText());
            evenement.setDescription(eventDescriptionField.getText());
            evenement.setDate(Date.valueOf(eventDatePicker.getValue()));
            evenement.setId_user(Integer.parseInt(userComboBox.getValue().split(" - ")[0])); // Extract user ID
    
            handleUpdateEvent(evenement);
        });
    
        contentArea.getChildren().add(addEventForm);
    }
    
    
    private void handleUpdateEvent(Evenement evenement) {
        try {
            TransactionManager.beginTransaction();
            EvenementDAO eventDAO = new EvenementDAO();
            eventDAO.setConnection(TransactionManager.getCurrentConnection());
            eventDAO.update(evenement);
            TransactionManager.commit();
            showAlert(Alert.AlertType.INFORMATION, "Success", "Event updated successfully!");
            displayAllEvents();
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to update the event.");
        }
    }
    private void handleDeleteEvent(Evenement evenement) {
        try {
            TransactionManager.beginTransaction();
            EvenementDAO eventDAO = new EvenementDAO();
            eventDAO.setConnection(TransactionManager.getCurrentConnection());
            eventDAO.supprimer(evenement.getId());
            TransactionManager.commit();
            showAlert(Alert.AlertType.INFORMATION, "Success", "Event deleted successfully!");
            displayAllEvents();
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to delete the event.");
        }
    }
    private ObservableList<String> getUsersFromDatabase() {
        ObservableList<String> users = FXCollections.observableArrayList();
        try {
            TransactionManager.beginTransaction();
            // Assuming you have a UserDAO with a method to fetch all users
            UserDAO userDAO = new UserDAO();
            userDAO.setConnection(TransactionManager.getCurrentConnection());
            userDAO.afficher().forEach(user -> 
                users.add(user.getId() + " - " + user.getNom()) // Example format: "1 - John Doe"
            );
            TransactionManager.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to fetch users");
        }
        return users;
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
