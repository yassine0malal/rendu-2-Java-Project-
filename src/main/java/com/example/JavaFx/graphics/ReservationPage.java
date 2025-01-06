package com.example.JavaFx.graphics;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.DAOImplementation.ReservationDAO;
import com.example.DAOImplementation.UserDAO;
import com.example.Models.Evenement;
import com.example.Models.Reservation;
import com.example.transaction.TransactionManager;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javafx.stage.FileChooser;
import javafx.stage.Window;

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
import javafx.stage.FileChooser;

public class ReservationPage extends GridPane {

    private DatePicker reservationDatePicker;
    private Button addReservationButton;

    private HBox crudMenu;
    private AnchorPane contentArea;
    private GridPane addReservationForm;
    private TableView<Reservation> reservationTable;

    public ReservationPage() {
        // Set grid properties
        this.setAlignment(Pos.CENTER);
        this.setHgap(10);
        this.setVgap(10);
        this.setPadding(new Insets(20));
        this.setStyle("-fx-background-color:#DEDEDE");

        contentArea = new AnchorPane();
        contentArea.setPrefSize(800, 600);

        // Create CRUD Menu
        crudMenu = new HBox(10);
        crudMenu.setPadding(new Insets(20, 20, 20, 330));
        crudMenu.setSpacing(10);
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
            crudMenu.setStyle(
                    """
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
                        -fx-background-color: linear-gradient(to right, #4A90E2, #50B3A2);
                        -fx-padding: 10 0 10 200px;
                        -fx-border-radius: 20px;
                        -fx-background-radius: 20px;
                        -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.4), 12, 0, 4, 4);
                        -fx-border-width: 2px;
                        -fx-border-color: rgba(255, 255, 255, 0.2); /* Reset border */
                    """);
        });

        crudMenu.setMinWidth(1000);

        Button addReservationButton = new Button("Add Reservation");
        Button displayReservationsButton = new Button("Display Reservations");
        addReservationButton.setStyle(
                "-fx-font-size: 14px; -fx-background-color:#5211e9; -fx-text-fill: white; -f-decoration: none; -fx-background-radius: 15px;");
        displayReservationsButton.setStyle(
                "-fx-font-size: 14px; -fx-background-color:#5211e9; -fx-text-fill: white; -f-decoration: none; -fx-background-radius: 15px;");

        addReservationButton.setOnAction(e -> displayAddReservationForm());
        displayReservationsButton.setOnAction(e -> displayAllReservations());

        crudMenu.getChildren().addAll(addReservationButton, displayReservationsButton);
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

    public void displayAddReservationForm() {
        // Clear the content area
        clearContentArea();

        // Initialize the reservation form layout
        addReservationForm = new GridPane();
        addReservationForm.setAlignment(Pos.CENTER);
        addReservationForm.setHgap(10);
        addReservationForm.setVgap(10);
        addReservationForm.setPadding(new Insets(20));
        addReservationForm.setStyle(
                "-fx-background-color: #f9f9f9; -fx-border-radius: 15px; -fx-background-radius: 15px; -fx-padding: 20;");

        // Title Label
        Label titleLabel = new Label("Add New Reservation");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #333;");
        addReservationForm.add(titleLabel, 0, 0, 2, 1);
        GridPane.setHalignment(titleLabel, HPos.CENTER);

        // User Selection
        Label userIdLabel = new Label("User:");
        userIdLabel.setStyle("-fx-font-size: 14px;");
        ComboBox<String> userComboBox = new ComboBox<>(getUsersFromDatabase());
        userComboBox.setPromptText("Select a user");
        userComboBox.setStyle("-fx-font-size: 14px; -fx-pref-width: 250px;");
        addReservationForm.add(userIdLabel, 0, 1);
        addReservationForm.add(userComboBox, 1, 1);

        // Terrain Selection
        Label terrainIdLabel = new Label("Terrain:");
        terrainIdLabel.setStyle("-fx-font-size: 14px;");
        ComboBox<String> terrainComboBox = new ComboBox<>(getTerrainsFromDatabase());
        terrainComboBox.setPromptText("Select a terrain");
        terrainComboBox.setStyle("-fx-font-size: 14px; -fx-pref-width: 250px;");
        addReservationForm.add(terrainIdLabel, 0, 2);
        addReservationForm.add(terrainComboBox, 1, 2);

        // Salle Selection
        Label salleIdLabel = new Label("Salle:");
        salleIdLabel.setStyle("-fx-font-size: 14px;");
        ComboBox<String> salleComboBox = new ComboBox<>(getSallesFromDatabase());
        salleComboBox.setPromptText("Select a salle");
        salleComboBox.setStyle("-fx-font-size: 14px; -fx-pref-width: 250px;");
        addReservationForm.add(salleIdLabel, 0, 3);
        addReservationForm.add(salleComboBox, 1, 3);

        // Event Selection
        Label eventIdLabel = new Label("Event:");
        eventIdLabel.setStyle("-fx-font-size: 14px;");
        ComboBox<String> eventComboBox = new ComboBox<>(getEventsFromDatabase());
        eventComboBox.setPromptText("Select an event");
        eventComboBox.setStyle("-fx-font-size: 14px; -fx-pref-width: 250px;");
        addReservationForm.add(eventIdLabel, 0, 4);
        addReservationForm.add(eventComboBox, 1, 4);

        // Date Picker
        Label reservationDateLabel = new Label("Reservation Date:");
        reservationDateLabel.setStyle("-fx-font-size: 14px;");
        reservationDatePicker = new DatePicker();
        reservationDatePicker.setPromptText("Select a date");
        reservationDatePicker.setStyle("-fx-font-size: 14px; -fx-pref-width: 250px;");
        addReservationForm.add(reservationDateLabel, 0, 5);
        addReservationForm.add(reservationDatePicker, 1, 5);

        // Add Reservation Button
        addReservationButton = new Button("Add Reservation");
        addReservationButton.setStyle(
                "-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px; -fx-border-radius: 5px; -fx-padding: 10px 20px;");
        addReservationButton.setOnMouseEntered(e -> addReservationButton.setStyle(
                "-fx-background-color:rgb(158, 226, 13); -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px; -fx-border-radius: 5px; -fx-padding: 10px 20px;"));
        addReservationButton.setOnMouseExited(e -> addReservationButton.setStyle(
                "-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px; -fx-border-radius: 5px; -fx-padding: 10px 20px;"));
        addReservationButton.setOnAction(e -> {
            try {
                int userId = Integer.parseInt(userComboBox.getValue().split(" - ")[0]);
                int terrainId = Integer.parseInt(terrainComboBox.getValue().split(" - ")[0]);
                int salleId = Integer.parseInt(salleComboBox.getValue().split(" - ")[0]);
                int eventId = Integer.parseInt(eventComboBox.getValue().split(" - ")[0]);
                Date reservationDate = Date.valueOf(reservationDatePicker.getValue());

                Reservation reservation = new Reservation(userId, terrainId, salleId, eventId, reservationDate);

                TransactionManager.beginTransaction();
                ReservationDAO reservationDAO = new ReservationDAO();
                reservationDAO.setConexion(TransactionManager.getCurrentConnection());
                reservationDAO.ajouter(reservation);
                TransactionManager.commit();

                showAlert(Alert.AlertType.INFORMATION, "Success", "Reservation added successfully!");
                displayAllReservations();
            } catch (Exception ex) {
                ex.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to add the reservation.");
            }
        });

        addReservationForm.add(addReservationButton, 1, 6);
        GridPane.setHalignment(addReservationButton, HPos.RIGHT);

        // Add form to the content area with proper anchoring
        contentArea.getChildren().add(addReservationForm);
        AnchorPane.setTopAnchor(addReservationForm, 50.0);
        AnchorPane.setLeftAnchor(addReservationForm, 100.0);
        AnchorPane.setRightAnchor(addReservationForm, 100.0);

        this.add(contentArea, 0, 1, 2, 1);
    }

    public void displayAllReservations() {
        clearContentArea();
        if (addReservationForm != null) {
            this.getChildren().remove(addReservationForm);
        }
        reservationTable = new TableView<>();
        reservationTable.setPrefWidth(1000);
        reservationTable.setPrefHeight(TableView.USE_COMPUTED_SIZE);
        reservationTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        reservationTable.setStyle(
                "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.4), 12, 0, 4, 4); -fx-border-color: rgba(71, 68, 68, 0.2);");
        reservationTable.setStyle(
                "-fx-border-radius: 15px; -fx-background-radius: 15px; -fx-padding: 20; -fx-border-width: 2px;");

        TableColumn<Reservation, String> userIdColumn = new TableColumn<>("User Name");
        userIdColumn.setMinWidth(166.7);
        userIdColumn.setCellValueFactory(cellData -> {
            int id_user = cellData.getValue().getId_user();
            return new SimpleStringProperty(getUserName(id_user));
        });

        TableColumn<Reservation, String> terrainIdColumn = new TableColumn<>("Terrain Name");
        terrainIdColumn.setMinWidth(166.7);
        terrainIdColumn.setCellValueFactory(cellData -> {
            int id_terrain = cellData.getValue().getId_terrain();
            return new SimpleStringProperty(getTerrainName(id_terrain));
        });

        TableColumn<Reservation, String> salleIdColumn = new TableColumn<>("Salle Name");
        salleIdColumn.setMinWidth(166.7);
        salleIdColumn.setCellValueFactory(cellData -> {
            int id_salle = cellData.getValue().getId_salle();
            return new SimpleStringProperty(getSalleName(id_salle));
        });

        TableColumn<Reservation, String> eventIdColumn = new TableColumn<>("Event Name");
        eventIdColumn.setMinWidth(166.7);
        eventIdColumn.setCellValueFactory(cellData -> {
            int id_event = cellData.getValue().getId_event();
            String eventName = getEventName(id_event);
            return new SimpleStringProperty(eventName);
        });

        TableColumn<Reservation, Date> dateColumn = new TableColumn<>("Reservation Date");
        dateColumn.setMinWidth(166.7);
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date_reservation"));

        TableColumn<Reservation, String> action = new TableColumn<>("Action");
        action.setMinWidth(166.7);
        action.setCellFactory(param -> new TableCell<>() {
            private final Button updateButton = new Button("Update");
            private final Button deleteButton = new Button("Delete");
            {
                updateButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
                deleteButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");

                updateButton.setOnAction(reserv -> {
                    Reservation reservation = getTableView().getItems().get(getIndex());
                    modifyReservationForm(reservation);
                });

                deleteButton.setOnAction(event -> {
                    Reservation evenement = getTableView().getItems().get(getIndex());
                    handleDeleteReservation(evenement);
                });

                HBox actionButtons = new HBox(10, updateButton, deleteButton);
                actionButtons.setAlignment(Pos.CENTER);
                setGraphic(actionButtons);
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : getGraphic());
            }
        });

      
        reservationTable.getColumns().addAll(userIdColumn, terrainIdColumn, salleIdColumn, eventIdColumn, dateColumn,
                action);

        
        reservationTable.setItems(getReservationsFromDatabase());
        contentArea.getChildren().add(reservationTable);
        Button downloadAllButton = new Button("Download All Reservations");
        downloadAllButton.setStyle("-fx-background-color: #0078D7; -fx-text-fill: white; -fx-padding: 10; -fx-border-radius: 5px; -fx-font-weight: bold; -fx-font-size: 14px; -fx-border-radius: 5px; -fx-padding: 10px 20px;");
        downloadAllButton.setOnAction(event -> handleDownloadAllReservations());
        StackPane stackPane = new StackPane(downloadAllButton);
        stackPane.setPrefSize(1000, 1000);
        stackPane.setStyle("-fx-content-display: center; -fx-alignment: center;");
        contentArea.getChildren().add(stackPane);


    }


    public void handleDownloadAllReservations() {
    ObservableList<Reservation> allReservations = reservationTable.getItems();
    
    String fileName = "all_reservations.csv";
    
    FileChooser fileChooser = new FileChooser();
    fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
    fileChooser.setInitialFileName(fileName);
    
    File file = fileChooser.showSaveDialog(contentArea.getScene().getWindow());
    if (file != null) {
        try (FileWriter writer = new FileWriter(file)) {
            writer.append("Reservation ID,User Name,Terrain Name,Salle Name,Event Name,Reservation Date\n");

            for (Reservation reservation : allReservations) {
                writer.append(reservation.getId_reservation() + ",");
                writer.append(getUserName(reservation.getId_user()) + ",");
                writer.append(getTerrainName(reservation.getId_terrain()) + ",");
                writer.append(getSalleName(reservation.getId_salle()) + ",");
                writer.append(getEventName(reservation.getId_event()) + ",");
                writer.append(reservation.getDate_reservation().toString() + "\n");
            }

            showAlert(Alert.AlertType.INFORMATION, "Success", "All reservations have been saved to file.");
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to save the reservation file.");
        }
    }
}



    public void modifyReservationForm(Reservation reservation) {
        clearContentArea();
        if (addReservationForm != null) {
            contentArea.getChildren().remove(addReservationForm);
        }

        addReservationForm = new GridPane();
        addReservationForm.setAlignment(Pos.CENTER);
        addReservationForm.setHgap(10);
        addReservationForm.setVgap(10);
        addReservationForm.setPadding(new Insets(20));
        addReservationForm.setStyle("-fx-background-color: #f9f9f9;");

        Label titleLabel = new Label("Modify Reservation");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #333;");
        addReservationForm.add(titleLabel, 0, 0, 2, 1);

        Label userLabel = new Label("User:");
        ComboBox<String> userComboBox = new ComboBox<>(getUsersFromDatabase());
        userComboBox.setValue(getUserName(reservation.getId_user()));
        addReservationForm.add(userLabel, 0, 1);
        addReservationForm.add(userComboBox, 1, 1);

        Label terrainLabel = new Label("Terrain:");
        ComboBox<String> terrainComboBox = new ComboBox<>(getTerrainsFromDatabase());
        terrainComboBox.setValue(getTerrainName(reservation.getId_terrain()));
        addReservationForm.add(terrainLabel, 0, 2);
        addReservationForm.add(terrainComboBox, 1, 2);

        Label salleLabel = new Label("Salle:");
        ComboBox<String> salleComboBox = new ComboBox<>(getSallesFromDatabase()); // Fix ComboBox variable name
        salleComboBox.setValue(getSalleName(reservation.getId_salle())); // Fix setting value
        addReservationForm.add(salleLabel, 0, 3);
        addReservationForm.add(salleComboBox, 1, 3);

        Label eventLabel = new Label("Event:");
        ComboBox<String> eventComboBox = new ComboBox<>(getEventsFromDatabase()); // Fix ComboBox variable name
        eventComboBox.setValue(getEventName(reservation.getId_event())); // Fix setting value
        addReservationForm.add(eventLabel, 0, 4);
        addReservationForm.add(eventComboBox, 1, 4);

        Label dateLabel = new Label("Reservation Date:");
        DatePicker datePicker = new DatePicker(reservation.getDate_reservation().toLocalDate());
        addReservationForm.add(dateLabel, 0, 5);
        addReservationForm.add(datePicker, 1, 5);

        Button updateButton = new Button("Update");
        updateButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        addReservationForm.add(updateButton, 1, 6);

        updateButton.setOnAction(event -> {
            reservation.setId_user(Integer.parseInt(userComboBox.getValue().split(" - ")[0]));
            reservation.setId_event(Integer.parseInt(eventComboBox.getValue().split(" - ")[0]));
            reservation.setId_salle(Integer.parseInt(salleComboBox.getValue().split(" - ")[0])); // Fix ComboBox
                                                                                                 // variable
            reservation.setId_terrain(Integer.parseInt(terrainComboBox.getValue().split(" - ")[0]));
            reservation.setDate_reservation(Date.valueOf(datePicker.getValue()));
            handleUpdateReservation(reservation);
        });

        contentArea.getChildren().add(addReservationForm);
    }

    public void handleUpdateReservation(Reservation reservation) {
        try {
            TransactionManager.beginTransaction();
            ReservationDAO reservationDAO = new ReservationDAO();
            reservationDAO.setConexion(TransactionManager.getCurrentConnection());
            reservationDAO.update(reservation);
            TransactionManager.commit();

            showAlert(Alert.AlertType.INFORMATION, "Success", "Reservation updated successfully!");
            displayAllReservations();
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to update reservation.");
        }
    }

    public ObservableList<String> getUsersFromDatabase() {
        ObservableList<String> users = FXCollections.observableArrayList();
        try {
            TransactionManager.beginTransaction();
            UserDAO userDAO = new UserDAO();
            userDAO.setConnection(TransactionManager.getCurrentConnection());
            userDAO.afficher().forEach(user -> users.add(user.getId() + " - " + user.getNom()));
            TransactionManager.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to fetch users.");
        }
        return users;
    }

    public ObservableList<String> getTerrainsFromDatabase() {
        ObservableList<String> terrains = FXCollections.observableArrayList();
        try {
            TransactionManager.beginTransaction();
            Connection connection = TransactionManager.getCurrentConnection();
            PreparedStatement preparedStatement = connection
                    .prepareStatement("SELECT id_terrain, nom_terrain FROM terrains");
            ResultSet resultSet = preparedStatement.executeQuery();

            // Add terrain names to the list
            while (resultSet.next()) {
                int idTerrain = resultSet.getInt("id_terrain");
                String terrainName = resultSet.getString("nom_terrain");
                terrains.add(idTerrain + " - " + terrainName);
            }

            // Commit the transaction
            TransactionManager.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to fetch terrains.");
        }
        return terrains;
    }

    public ObservableList<String> getSallesFromDatabase() {
        ObservableList<String> salles = FXCollections.observableArrayList();
        try {
            TransactionManager.beginTransaction();
            Connection connection = TransactionManager.getCurrentConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT id_salle, nom_salle FROM salles");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int idSalle = resultSet.getInt("id_salle");
                String salleName = resultSet.getString("nom_salle");
                salles.add(idSalle + " - " + salleName); // Add salle ID and name to list
            }
            TransactionManager.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to fetch salles.");
        }
        return salles;
    }

    public ObservableList<String> getEventsFromDatabase() {
        ObservableList<String> events = FXCollections.observableArrayList();
        try {
            TransactionManager.beginTransaction();
            Connection connection = TransactionManager.getCurrentConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT id_event, nom FROM evenements");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int idEvent = resultSet.getInt("id_event");
                String eventName = resultSet.getString("nom");
                events.add(idEvent + " - " + eventName);
            }
            TransactionManager.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to fetch events.");
        }
        return events;
    }

    public ObservableList<Reservation> getReservationsFromDatabase() {
        ObservableList<Reservation> reservations = FXCollections.observableArrayList();
        try {
            TransactionManager.beginTransaction();
            ReservationDAO reservationDAO = new ReservationDAO();
            reservationDAO.setConexion(TransactionManager.getCurrentConnection());
            reservations.addAll(reservationDAO.afficher());
            TransactionManager.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservations;
    }

    public String getEventName(int id_event) {
        String eventName = "Unknown";
        try {
            TransactionManager.beginTransaction();
            Connection connection = TransactionManager.getCurrentConnection();
            PreparedStatement preparedStatement = connection
                    .prepareStatement("SELECT nom FROM evenements WHERE id_event = ?");
            preparedStatement.setInt(1, id_event);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                eventName = resultSet.getString("nom");
                TransactionManager.closeConnection();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return eventName;

    }

    public String getUserName(int id_user) {
        String userName = "Unknown";

        try {
            TransactionManager.beginTransaction();
            Connection connection = TransactionManager.getCurrentConnection();
            PreparedStatement preparedStatement = connection
                    .prepareStatement("SELECT nom,prenom FROM utilisateurs WHERE id_user = ?");
            preparedStatement.setInt(1, id_user);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                userName = resultSet.getString("nom") + " " + resultSet.getString("prenom");

                TransactionManager.closeConnection();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userName;

    }

    public String getSalleName(int id_salle) {
        String salle_name = "Unknown";

        try {
            TransactionManager.beginTransaction();
            Connection connection = TransactionManager.getCurrentConnection();
            PreparedStatement preparedStatement = connection
                    .prepareStatement("SELECT nom_salle FROM salles WHERE id_salle = ?");
            preparedStatement.setInt(1, id_salle);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                salle_name = resultSet.getString("nom_salle");
                TransactionManager.closeConnection();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return salle_name;

    }

    public String getTerrainName(int id_terrain) {
        String terrainName = "wihout Terrain";

        try {
            TransactionManager.beginTransaction();
            Connection connection = TransactionManager.getCurrentConnection();
            PreparedStatement preparedStatement = connection
                    .prepareStatement("SELECT nom_terrain FROM terrains WHERE id_terrain = ?");
            preparedStatement.setInt(1, id_terrain);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                terrainName = resultSet.getString("nom_terrain");
                TransactionManager.closeConnection();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return terrainName;

    }

    public void handleDeleteReservation(Reservation reservation) {
        try {
            TransactionManager.beginTransaction();
            ReservationDAO reservationDAO = new ReservationDAO();
            reservationDAO.setConexion(TransactionManager.getCurrentConnection());
            reservationDAO.supprimer(reservation.getId_reservation());
            TransactionManager.commit();
            showAlert(Alert.AlertType.INFORMATION, "Success", "Reservation deleted successfully!");
            TransactionManager.closeConnection();
            displayAllReservations();
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to delete the reservation.");
        }
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
