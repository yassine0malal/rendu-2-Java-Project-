package com.example.JavaFx.graphics;

import java.sql.SQLException;

import com.example.DAOImplementation.SalleDAO;
import com.example.Models.Salle;
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
import javafx.scene.layout.StackPane;

public class SallePage extends GridPane {

    private TextField nameField;
    private TextField capacityField;
    private Button registerButton;

    private HBox crudMenu;
    private AnchorPane contentArea;
    private GridPane addSalleForm;
    private TableView<Salle> salleTable;

    public SallePage() {
        this.setAlignment(Pos.CENTER);
        this.setHgap(10);
        this.setVgap(10);
        this.setPadding(new Insets(20));
        this.setStyle("-fx-background-color: #DEDEDE;");

        contentArea = new AnchorPane();
        contentArea.setPrefSize(800, 600);

        crudMenu = new HBox(10);
        crudMenu.setPadding(new Insets(20,20,20,330));
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

        Button addSalleButton = new Button("Add Salle");
        Button displaySalleButton = new Button("Display Salles");

        addSalleButton.setOnAction(e -> displayAddSalleForm());
        displaySalleButton.setOnAction(e -> displayAllSalles());
        addSalleButton.setStyle("-fx-font-size: 14px; -fx-background-color:#5211e9; -fx-text-fill: white; -f-decoration: none; -fx-background-radius: 15px;");
        displaySalleButton.setStyle("-fx-font-size: 14px; -fx-background-color:#5211e9; -fx-text-fill: white; -f-decoration: none; -fx-background-radius: 15px;");

        crudMenu.getChildren().addAll(addSalleButton, displaySalleButton);
        this.add(crudMenu, 0, 0);

               Label emptyLabel = new Label("Select an action from the navbar above");
        emptyLabel.setStyle("-fx-font-size: 18px;");
        StackPane stackPane = new StackPane(emptyLabel);
        stackPane.setPrefSize(1000, 600);
        stackPane.setStyle("-fx-content-display: center; -fx-alignment: center;");

        contentArea.getChildren().add(stackPane);

        this.add(contentArea, 0, 1, 2, 1);
    }

    private void displayAddSalleForm() {
        // Clear the content area to remove any previous content
        clearContentArea();
    
        // Remove any existing salle table data
        if (salleTable != null) {
            this.getChildren().remove(salleTable);
        }
    
        // Clear and reset the salle form if it exists
        if (addSalleForm != null) {
            this.getChildren().remove(addSalleForm);
        }
    
        // Initialize the salle form layout
        addSalleForm = new GridPane();
        addSalleForm.setAlignment(Pos.CENTER);
        addSalleForm.setHgap(10);
        addSalleForm.setVgap(10);
        addSalleForm.setPadding(new Insets(20));
        addSalleForm.setStyle(
                "-fx-background-color: #f9f9f9; -fx-border-radius: 15px; -fx-background-radius: 15px; -fx-padding: 20;");
    
        // Add a title for the form
        Label titleLabel = new Label("Salle Registration");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #333;");
        addSalleForm.add(titleLabel, 0, 0, 2, 1);
        GridPane.setHalignment(titleLabel, HPos.CENTER);
    
        // Add salle name field
        Label nameLabel = new Label("Salle Name:");
        nameLabel.setStyle("-fx-font-size: 14px;");
        nameField = new TextField();
        nameField.setPromptText("Enter salle name");
        nameField.setStyle("-fx-font-size: 14px; -fx-pref-width: 250px;");
        addSalleForm.add(nameLabel, 0, 1);
        addSalleForm.add(nameField, 1, 1);
    
        // Add salle capacity field
        Label capacityLabel = new Label("Capacity:");
        capacityLabel.setStyle("-fx-font-size: 14px;");
        capacityField = new TextField();
        capacityField.setPromptText("Enter salle capacity");
        capacityField.setStyle("-fx-font-size: 14px; -fx-pref-width: 250px;");
        addSalleForm.add(capacityLabel, 0, 2);
        addSalleForm.add(capacityField, 1, 2);
    
        // Add register button
        registerButton = new Button("Register");
        registerButton.setStyle(
                "-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px; -fx-border-radius: 5px; -fx-padding: 10px 20px;");
        registerButton.setOnMouseEntered(e -> registerButton.setStyle(
                "-fx-background-color:rgb(158, 226, 13); -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px; -fx-border-radius: 5px; -fx-padding: 10px 20px;"));
        registerButton.setOnMouseExited(e -> registerButton.setStyle(
                "-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px; -fx-border-radius: 5px; -fx-padding: 10px 20px;"));
        addSalleForm.add(registerButton, 1, 3);
        GridPane.setHalignment(registerButton, HPos.RIGHT);
    
        // Add form to the content area and ensure proper positioning
        contentArea.getChildren().add(addSalleForm);
        AnchorPane.setTopAnchor(addSalleForm, 50.0);
        AnchorPane.setLeftAnchor(addSalleForm, 100.0);
        AnchorPane.setRightAnchor(addSalleForm, 100.0);
    
        this.add(contentArea, 0, 1, 2, 1);
    
        // Set action for register button
        registerButton.setOnAction(event -> handleRegister());
    }
    
    private void handleRegister() {
        String name = nameField.getText();
        int capacity = Integer.parseInt(capacityField.getText());

        try {
            TransactionManager.beginTransaction();

            SalleDAO salleDAO = new SalleDAO();
            salleDAO.setConexion(TransactionManager.getCurrentConnection());

            Salle salle = new Salle();
            salle.setNom(name);
            salle.setCapacite(capacity);

            salleDAO.ajouter(salle);

            TransactionManager.commit();
            showAlert(Alert.AlertType.INFORMATION, "Success", "Salle registered successfully!");
            displayAllSalles();
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Salle registration failed.");
        }

        nameField.clear();
        capacityField.clear();
    }

    private void displayAllSalles() {
        clearContentArea();
        if (addSalleForm != null) {
            this.getChildren().remove(addSalleForm);
            
        }
        if (salleTable != null) {
            salleTable.getItems().clear();
            
        }
    
       salleTable = new TableView<>();
        salleTable.setPrefWidth(1000);
        salleTable.setPrefHeight(TableView.USE_COMPUTED_SIZE);
        salleTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        salleTable.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.4), 12, 0, 4, 4); -fx-border-color: rgba(71, 68, 68, 0.2);");
        salleTable.setStyle("-fx-border-radius: 15px; -fx-background-radius: 15px; -fx-padding: 20; -fx-border-width: 2px;");
    
        // ID Column
        // TableColumn<Salle, Integer> idColumn = new TableColumn<>("ID");
        
        // // idColumn.setStyle(" -fx-background-radius: 15px; -fx-border-radius: 15px;");
        // idColumn.setPrefWidth(60);
        // idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
    
        // Name Column
        TableColumn<Salle, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setPrefWidth(333.4);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
    
        // Capacity Column
        TableColumn<Salle, Integer> capacityColumn = new TableColumn<>("Capacity");
        capacityColumn.setPrefWidth(333.4);
        capacityColumn.setCellValueFactory(new PropertyValueFactory<>("capacite"));
    
        // Action Column
        TableColumn<Salle, Void> actionColumn = new TableColumn<>("Action");
        actionColumn.setPrefWidth(333.4);
    
        actionColumn.setCellFactory(param -> new TableCell<>() {
            private final Button updateButton = new Button("Update");
            private final Button deleteButton = new Button("Delete");
    
            {
                updateButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
                deleteButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");
    
                updateButton.setOnAction(event -> {
                    Salle salle = getTableView().getItems().get(getIndex());
                    modifySalleForm(salle);
                });
    
                deleteButton.setOnAction(event -> {
                    Salle salle = getTableView().getItems().get(getIndex());
                    handleDeleteSalle(salle);
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
    
        salleTable.getColumns().addAll(nameColumn, capacityColumn, actionColumn);
        salleTable.setItems(getSallesFromDatabase());
    
        contentArea.getChildren().add(salleTable);
        this.add(contentArea, 0, 4, 2, 1);
    }
    
    // Method to modify Salle
    private void modifySalleForm(Salle salle) {
        clearContentArea();
    
        addSalleForm = new GridPane();
        addSalleForm.setAlignment(Pos.CENTER);
        addSalleForm.setHgap(10);
        addSalleForm.setVgap(10);
        addSalleForm.setPadding(new Insets(20));
        addSalleForm.setStyle("-fx-background-color: #f9f9f9;");
    
        Label titleLabel = new Label("Salle Modification");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        addSalleForm.add(titleLabel, 0, 0, 2, 1);
        GridPane.setHalignment(titleLabel, HPos.CENTER);
    
        // Name Field
        Label nameLabel = new Label("Salle Name:");
        TextField nameField = new TextField(salle.getNom());
        addSalleForm.add(nameLabel, 0, 1);
        addSalleForm.add(nameField, 1, 1);
    
        // Capacity Field
        Label capacityLabel = new Label("Capacity:");
        TextField capacityField = new TextField(String.valueOf(salle.getCapacite()));
        addSalleForm.add(capacityLabel, 0, 2);
        addSalleForm.add(capacityField, 1, 2);
    
        // Update Button
        Button updateButton = new Button("Update");
        updateButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        addSalleForm.add(updateButton, 1, 3);
        GridPane.setHalignment(updateButton, HPos.RIGHT);
    
        this.add(addSalleForm, 0, 1, 2, 1);
    
        updateButton.setOnAction(event -> {
            Salle updatedSalle = new Salle();
            updatedSalle.setId(salle.getId());
            updatedSalle.setNom(nameField.getText());
            updatedSalle.setCapacite(Integer.parseInt(capacityField.getText()));
    
            handleUpdateSalle(updatedSalle);
        });
    }
    
    // Method to handle Salle update
    private void handleUpdateSalle(Salle updatedSalle) {
        try {
            TransactionManager.beginTransaction();
    
            SalleDAO salleDAO = new SalleDAO();
            salleDAO.setConexion(TransactionManager.getCurrentConnection());
            salleDAO.update(updatedSalle);
    
            TransactionManager.commit();
            showAlert(Alert.AlertType.INFORMATION, "Success", "Salle updated successfully!");
    
            displayAllSalles(); // Refresh the list
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to update Salle.");
        }
    }
    
    // Method to handle Salle deletion
    private void handleDeleteSalle(Salle salle) {
        try {
            TransactionManager.beginTransaction();
    
            SalleDAO salleDAO = new SalleDAO();
            salleDAO.setConexion(TransactionManager.getCurrentConnection());
            salleDAO.supprimer(salle.getId());
    
            TransactionManager.commit();
            showAlert(Alert.AlertType.INFORMATION, "Success", "Salle deleted successfully!");
    
            displayAllSalles(); // Refresh the list
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to delete Salle.");
        }
    }
    
    private ObservableList<Salle> getSallesFromDatabase() {
        ObservableList<Salle> salles = FXCollections.observableArrayList();
        try {
            TransactionManager.beginTransaction();

            SalleDAO salleDAO = new SalleDAO();
            salleDAO.setConexion(TransactionManager.getCurrentConnection());

            salles.addAll(salleDAO.afficher());

            TransactionManager.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return salles;
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
