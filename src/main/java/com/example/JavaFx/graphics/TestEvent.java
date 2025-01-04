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
import javafx.scene.layout.StackPane;

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
    private ComboBox<String> userComboBox;


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
        crudMenu.setStyle("""
            -fx-background-color: linear-gradient(to right, #4A90E2, #50B3A2); /* Gradient from blue to aqua */
            -fx-padding: 10 0 10 200px; /* Top, Right, Bottom, Left padding */
            -fx-border-radius: 20px; /* Smooth rounded edges */
            -fx-background-radius: 20px;
            -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.4), 12, 0, 4, 4); /* Soft drop shadow */
            -fx-border-width: 2px;
            -fx-border-color: rgba(255, 255, 255, 0.2); /* Subtle border */
        """);
        crudMenu.setOnMouseEntered(e -> {
            crudMenu.setStyle("""
                -fx-background-color: linear-gradient(to right, #50B3A2, #4A90E2); /* Reversed gradient on hover */
                -fx-padding: 10 0 10 200px;
                -fx-border-radius: 20px;
                -fx-background-radius: 20px;
                -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 15, 0, 5, 5);
                -fx-border-width: 2px;
                -fx-border-color: rgba(255, 255, 255, 0.4); /* More visible border on hover */
            """);
        });
        crudMenu.setOnMouseExited(e -> {
            crudMenu.setStyle("""
                -fx-background-color: linear-gradient(to right, #4A90E2, #50B3A2); /* Reset to original gradient */
                -fx-padding: 10 0 10 200px;
                -fx-border-radius: 20px;
                -fx-background-radius: 20px;
                -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.4), 12, 0, 4, 4);
                -fx-border-width: 2px;
                -fx-border-color: rgba(255, 255, 255, 0.2); /* Reset border */
            """);
        });
        
        crudMenu.setMinWidth(1000);


        Button addEventButton = new Button("Add Event");
        Button displayEventsButton = new Button("Display Events");
        addEventButton.setStyle("-fx-font-size: 14px; -fx-background-color:#5211e9; -fx-text-fill: white; -f-decoration: none; -fx-background-radius: 15px;");
        displayEventsButton.setStyle("-fx-font-size: 14px; -fx-background-color:#5211e9; -fx-text-fill: white; -f-decoration: none; -fx-background-radius: 15px;a");

        // addEventButton.setStyle("-fx-background-color: transparent; -fx-border-width: 0;");

        addEventButton.setOnAction(e -> displayAddEventForm());
        displayEventsButton.setOnAction(e -> displayAllEvents());

        crudMenu.getChildren().addAll(addEventButton, displayEventsButton);
        this.add(crudMenu, 0, 0);

        // Empty content area
               Label emptyLabel = new Label("Select an action from the navbar above");
        emptyLabel.setStyle("-fx-font-size: 18px;");
        StackPane stackPane = new StackPane(emptyLabel);
        stackPane.setPrefSize(1000, 600);
        stackPane.setStyle("-fx-content-display: center; -fx-alignment: center;");

        contentArea.getChildren().add(stackPane);

        this.add(contentArea, 0, 1, 2, 1);
    }


    public void displayAddEventForm() {
        // Clear the content area
        clearContentArea();
    
        // Initialize the event form layout
        addEventForm = new GridPane();
        addEventForm.setAlignment(Pos.CENTER);
        addEventForm.setHgap(10);
        addEventForm.setVgap(10);
        addEventForm.setPadding(new Insets(20));
        addEventForm.setStyle(
                "-fx-background-color: #f9f9f9; -fx-border-radius: 15px; -fx-background-radius: 15px; -fx-padding: 20;");
    
        // Add the title
        Label titleLabel = new Label("Add New Event");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #333;");
        addEventForm.add(titleLabel, 0, 0, 2, 1);
        GridPane.setHalignment(titleLabel, HPos.CENTER);
    
        // Add Event Name field
        Label eventNameLabel = new Label("Event Name:");
        eventNameLabel.setStyle("-fx-font-size: 14px;");
        eventNameField = new TextField();
        eventNameField.setPromptText("Enter event name");
        eventNameField.setStyle("-fx-font-size: 14px; -fx-pref-width: 250px;");
        addEventForm.add(eventNameLabel, 0, 1);
        addEventForm.add(eventNameField, 1, 1);
    
        // Add Event Description field
        Label eventDescriptionLabel = new Label("Description:");
        eventDescriptionLabel.setStyle("-fx-font-size: 14px;");
        eventDescriptionField = new TextField();
        eventDescriptionField.setPromptText("Enter event description");
        eventDescriptionField.setStyle("-fx-font-size: 14px; -fx-pref-width: 250px;");
        addEventForm.add(eventDescriptionLabel, 0, 2);
        addEventForm.add(eventDescriptionField, 1, 2);
    
        // Add Event Date Picker
        Label eventDateLabel = new Label("Event Date:");
        eventDateLabel.setStyle("-fx-font-size: 14px;");
        eventDatePicker = new DatePicker();
        eventDatePicker.setStyle("-fx-font-size: 14px;");
        addEventForm.add(eventDateLabel, 0, 3);
        addEventForm.add(eventDatePicker, 1, 3);
    
        // Add User ComboBox
        Label userIdLabel = new Label("User:");
        userIdLabel.setStyle("-fx-font-size: 14px;");
        userComboBox = new ComboBox<>(getUsersFromDatabase());
        userComboBox.setPromptText("Select a user");
        userComboBox.setStyle("-fx-font-size: 14px; -fx-pref-width: 250px;");
        addEventForm.add(userIdLabel, 0, 4);
        addEventForm.add(userComboBox, 1, 4);
    
        // Add Add Event button
        addEventButton = new Button("Add Event");
        addEventButton.setStyle(
                "-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px; -fx-border-radius: 5px; -fx-padding: 10px 20px;");
        addEventButton.setOnMouseEntered(e -> addEventButton.setStyle(
                "-fx-background-color:rgb(158, 226, 13); -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px; -fx-border-radius: 5px; -fx-padding: 10px 20px;"));
        addEventButton.setOnMouseExited(e -> addEventButton.setStyle(
                "-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px; -fx-border-radius: 5px; -fx-padding: 10px 20px;"));
        addEventButton.setOnAction(e -> handleAddEvent());
        addEventForm.add(addEventButton, 1, 5);
        GridPane.setHalignment(addEventButton, HPos.RIGHT);
    
        // Add the form to the content area with proper anchoring
        contentArea.getChildren().add(addEventForm);
        AnchorPane.setTopAnchor(addEventForm, 50.0);
        AnchorPane.setLeftAnchor(addEventForm, 100.0);
        AnchorPane.setRightAnchor(addEventForm, 100.0);
    
        this.add(contentArea, 0, 1, 2, 1);
    }
    
    public void handleAddEvent() {
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
    
    public void displayAllEvents() {
        clearContentArea();
    
        eventTable = new TableView<>();
        eventTable.setPrefWidth(990);
        eventTable.setPrefHeight(TableView.USE_COMPUTED_SIZE);
        eventTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        eventTable.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.4), 12, 0, 4, 4); -fx-border-color: rgba(71, 68, 68, 0.2);");

        TableColumn<Evenement, String> nameColumn = new TableColumn<>("Event Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("nomEvent"));
        nameColumn.setMinWidth(100);
    
        TableColumn<Evenement, String> descriptionColumn = new TableColumn<>("Description");
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        descriptionColumn.setMinWidth(180);
    
        TableColumn<Evenement, Date> dateColumn = new TableColumn<>("Date");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        dateColumn.setMinWidth(180);
    

        TableColumn<Evenement, String> userNameColumn = new TableColumn<>("User Name");
        userNameColumn.setMinWidth(180);
        userNameColumn.setCellValueFactory(cellData -> {
            System.out.println(cellData.getValue().getId_user());
        int userId = cellData.getValue().getId_user();
        String userName = getUserNameById(userId); // Fetch username based on user ID
        eventTable.setStyle("-fx-background-color: #f9f9f9; -fx-border-radius: 15px; -fx-background-radius: 15px; -fx-padding: 20; -fx-border-width: 2px; -fx-border-color: #DEDEDE;");
        return new SimpleStringProperty(userName);
    });


    
        // Action column
        TableColumn<Evenement, Void> actionColumn = new TableColumn<>("Action");
        actionColumn.setMinWidth(180);
        // actionColumn.setStyle("-fx-background-color: #f9f9f9; -fx-border-clolor:rgb(189, 185, 185); -fx-border-color:rgb(199, 193, 193); -fx-border-width: 1px; ");
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
    



public String getUserNameById(int userId) {
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



    public void modifyEventForm(Evenement evenement) {
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
    public void handleUpdateEvent(Evenement evenement) {
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
    public void handleDeleteEvent(Evenement evenement) {
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
    public ObservableList<String> getUsersFromDatabase() {
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
    public ObservableList<Evenement> getEventsFromDatabase() {
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

    public void clearContentArea() {
        contentArea.getChildren().clear();
    }

    public void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
