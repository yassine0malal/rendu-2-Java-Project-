package com.example.JavaFx.graphics;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import com.example.DAOImplementation.TerrainDAO;
import com.example.Models.Evenement;
import com.example.Models.Terrain;
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
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

public class TerrainPage extends GridPane {

    private TextField nameField;
    private TextField typeField;
    private Button registerButton;

    private HBox crudMenu;
    private AnchorPane contentArea;
    private GridPane addTerrainForm;
    private TableView<Terrain> terrainTable;

    public TerrainPage() {
        this.setAlignment(Pos.CENTER);
        this.setHgap(10);
        this.setVgap(10);
        this.setPadding(new Insets(20));
        this.setStyle("-fx-background-color: #DEDEDE;");

        contentArea = new AnchorPane();
        contentArea.setPrefSize(800, 600);

        crudMenu = new HBox(10);
        crudMenu.setPadding(new Insets(10));
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


        Button addTerrainButton = new Button("Add Terrain");
        Button displayTerrainButton = new Button("Display Terrains");
        addTerrainButton.setStyle("-fx-font-size: 14px; -fx-background-color:#5211e9; -fx-text-fill: white; -f-decoration: none; -fx-background-radius: 15px;");
        displayTerrainButton.setStyle("-fx-font-size: 14px; -fx-background-color:#5211e9; -fx-text-fill: white; -f-decoration: none; -fx-background-radius: 15px;");


        addTerrainButton.setOnAction(e -> displayAddTerrainForm());
        displayTerrainButton.setOnAction(e -> displayAllTerrains());

        crudMenu.getChildren().addAll(addTerrainButton, displayTerrainButton);
        this.add(crudMenu, 0, 0);

                Label emptyLabel = new Label("Select an action from the navbar above");
        emptyLabel.setStyle("-fx-font-size: 18px;");
        StackPane stackPane = new StackPane(emptyLabel);
        stackPane.setPrefSize(1000, 600);
        stackPane.setStyle("-fx-content-display: center; -fx-alignment: center;");

        contentArea.getChildren().add(stackPane);

        this.add(contentArea, 0, 1, 2, 1);
    }

    private void displayAddTerrainForm() {
        // Clear the content area to remove any previous content
        clearContentArea();
    
        // Remove any existing terrain table data
        if (terrainTable != null) {
            this.getChildren().remove(terrainTable);
        }
    
        // Clear and reset the terrain form if it exists
        if (addTerrainForm != null) {
            this.getChildren().remove(addTerrainForm);
        }
    
        // Initialize the terrain form layout
        addTerrainForm = new GridPane();
        addTerrainForm.setAlignment(Pos.CENTER);
        addTerrainForm.setHgap(10);
        addTerrainForm.setVgap(10);
        addTerrainForm.setPadding(new Insets(20));
        addTerrainForm.setStyle(
                "-fx-background-color: #f9f9f9; -fx-border-radius: 15px; -fx-background-radius: 15px; -fx-padding: 20;");
    
        // Add a title for the form
        Label titleLabel = new Label("Terrain Registration");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #333;");
        addTerrainForm.add(titleLabel, 0, 0, 2, 1);
        GridPane.setHalignment(titleLabel, HPos.CENTER);
    
        // Add terrain name field
        Label nameLabel = new Label("Terrain Name:");
        nameLabel.setStyle("-fx-font-size: 14px;");
        nameField = new TextField();
        nameField.setPromptText("Enter terrain name");
        nameField.setStyle("-fx-font-size: 14px; -fx-pref-width: 250px;");
        addTerrainForm.add(nameLabel, 0, 1);
        addTerrainForm.add(nameField, 1, 1);
    
        // Add terrain type field
        Label typeLabel = new Label("Terrain Type:");
        typeLabel.setStyle("-fx-font-size: 14px;");
        typeField = new TextField();
        typeField.setPromptText("Enter terrain type");
        typeField.setStyle("-fx-font-size: 14px; -fx-pref-width: 250px;");
        addTerrainForm.add(typeLabel, 0, 2);
        addTerrainForm.add(typeField, 1, 2);
    
        // Add register button
        registerButton = new Button("Register");
        registerButton.setStyle(
                "-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px; -fx-border-radius: 5px; -fx-padding: 10px 20px;");
        registerButton.setOnMouseEntered(e -> registerButton.setStyle(
                "-fx-background-color:rgb(158, 226, 13); -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px; -fx-border-radius: 5px; -fx-padding: 10px 20px;"));
        registerButton.setOnMouseExited(e -> registerButton.setStyle(
                "-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px; -fx-border-radius: 5px; -fx-padding: 10px 20px;"));
        addTerrainForm.add(registerButton, 1, 3);
        GridPane.setHalignment(registerButton, HPos.RIGHT);
    
        // Add form to the content area and ensure proper positioning
        contentArea.getChildren().add(addTerrainForm);
        AnchorPane.setTopAnchor(addTerrainForm, 50.0);
        AnchorPane.setLeftAnchor(addTerrainForm, 100.0);
        AnchorPane.setRightAnchor(addTerrainForm, 100.0);
    
        this.add(contentArea, 0, 1, 2, 1);
    
        // Set action for register button
        registerButton.setOnAction(event -> handleRegister());
    }
    
    private void handleRegister() {
        String name = nameField.getText();
        String type = typeField.getText();

        try {
            TransactionManager.beginTransaction();

            TerrainDAO terrainDAO = new TerrainDAO();
            terrainDAO.setConnection(TransactionManager.getCurrentConnection());

            Terrain terrain = new Terrain();
            terrain.setNom(name);
            terrain.setType(type);

            terrainDAO.ajouter(terrain);

            TransactionManager.commit();
            showAlert(Alert.AlertType.INFORMATION, "Success", "Terrain registered successfully!");
            displayAllTerrains();
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Terrain registration failed.");
        }

        nameField.clear();
        typeField.clear();
    }

 private void displayAllTerrains() {
    clearContentArea();
    if (addTerrainForm != null) {
        this.getChildren().remove(addTerrainForm);
    }

    terrainTable = new TableView<>();
    terrainTable.setPrefWidth(1000);
    terrainTable.setPrefHeight(TableView.USE_COMPUTED_SIZE);
    terrainTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    terrainTable.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.4), 12, 0, 4, 4); -fx-border-color: rgba(71, 68, 68, 0.2);");
    terrainTable.setStyle("-fx-border-radius: 15px; -fx-background-radius: 15px; -fx-padding: 20; -fx-border-width: 2px;");

    // Name Column
    TableColumn<Terrain, String> nameColumn = new TableColumn<>("Name");
    nameColumn.setPrefWidth(333.4);
    nameColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));

    // Type Column
    TableColumn<Terrain, String> typeColumn = new TableColumn<>("Type");
    typeColumn.setPrefWidth(333.4);
    typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));

    // Action Column
    TableColumn<Terrain, Void> actionColumn = new TableColumn<>("Action");
    actionColumn.setPrefWidth(333.4);

    actionColumn.setCellFactory(param -> new TableCell<>() {
        private final Button updateButton = new Button("Update");
        private final Button deleteButton = new Button("Delete");

        {
            updateButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
            deleteButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");

            updateButton.setOnAction(event -> {
                Terrain terrain = getTableView().getItems().get(getIndex());
                modifyTerrainForm(terrain);
            });

            deleteButton.setOnAction(event -> {
                Terrain terrain = getTableView().getItems().get(getIndex());
                handleDelete(terrain);
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

    terrainTable.getColumns().addAll(nameColumn, typeColumn, actionColumn);
    terrainTable.setItems(getTerrainsFromDatabase());

    // Create a "Download All Terrains" button
    Button downloadButton = new Button("Download All Terrains");
    downloadButton.setStyle("-fx-background-color: #0078D7; -fx-text-fill: white; -fx-padding: 10; -fx-border-radius: 5px; -fx-font-weight: bold; -fx-font-size: 14px; -fx-border-radius: 5px; -fx-padding: 10px 20px;");
    downloadButton.setOnAction(event -> handleDownloadAllTerrains());

    // Layout: Add table and button to a VBox
    VBox container = new VBox(10, terrainTable, downloadButton);
    container.setAlignment(Pos.CENTER);
    contentArea.getChildren().clear();
    contentArea.getChildren().add(container);

    this.add(contentArea, 0, 4, 2, 1);
}

     public void handleDownloadAllTerrains() {
    ObservableList<Terrain> allTerrains = terrainTable.getItems();
    
    String fileName = "all_Terrains.csv";
    
    FileChooser fileChooser = new FileChooser();
    fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
    fileChooser.setInitialFileName(fileName);
    
    File file = fileChooser.showSaveDialog(contentArea.getScene().getWindow());
    if (file != null) {
        try (FileWriter writer = new FileWriter(file)) {
            writer.append("ID ,Terrain Name,Terrain Type, \n");

            for (Terrain terrain : allTerrains) {
                writer.append(terrain.getId() + ",");
                writer.append(terrain.getNom() + ",");
                writer.append(terrain.getType() + "\n");
            }

            showAlert(Alert.AlertType.INFORMATION, "Success", "All reservations have been saved to file.");
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to save the reservation file.");
        }
    }
}

    private void modifyTerrainForm(Terrain terrain) {
        clearContentArea();
    
        addTerrainForm = new GridPane();
        addTerrainForm.setAlignment(Pos.CENTER);
        addTerrainForm.setHgap(10);
        addTerrainForm.setVgap(10);
        addTerrainForm.setPadding(new Insets(20));
        addTerrainForm.setStyle("-fx-background-color: #f9f9f9;");
    
        Label titleLabel = new Label("Terrain Modification");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #333;");
        addTerrainForm.add(titleLabel, 0, 0, 2, 1);
        GridPane.setHalignment(titleLabel, HPos.CENTER);
    
        // Name Field
        Label nameLabel = new Label("Terrain Name:");
        nameLabel.setStyle("-fx-font-size: 14px;");
        TextField nameField = new TextField();
        nameField.setText(terrain.getNom());
        nameField.setStyle("-fx-font-size: 14px; -fx-pref-width: 250px;");
        addTerrainForm.add(nameLabel, 0, 1);
        addTerrainForm.add(nameField, 1, 1);
    
        // Type Field
        Label typeLabel = new Label("Terrain Type:");
        typeLabel.setStyle("-fx-font-size: 14px;");
        TextField typeField = new TextField();
        typeField.setText(terrain.getType());
        typeField.setStyle("-fx-font-size: 14px; -fx-pref-width: 250px;");
        addTerrainForm.add(typeLabel, 0, 2);
        addTerrainForm.add(typeField, 1, 2);
    
        // Update Button
        Button updateButton = new Button("Update");
        updateButton.setStyle("-fx-font-size: 14px; -fx-background-color: #4CAF50; -fx-text-fill: white; -fx-padding: 5 20; -fx-cursor: hand;");
        addTerrainForm.add(updateButton, 1, 3);
        GridPane.setHalignment(updateButton, HPos.RIGHT);
    
        this.add(addTerrainForm, 0, 1, 2, 1);
    
        updateButton.setOnAction(event -> {
            Terrain updatedTerrain = new Terrain();
            updatedTerrain.setId(terrain.getId());
            updatedTerrain.setNom(nameField.getText());
            updatedTerrain.setType(typeField.getText());
    
            handleUpdateTerrain(updatedTerrain);
        });
    }
        
    private void handleUpdateTerrain(Terrain updatedTerrain) {
        try {
            TransactionManager.beginTransaction();
    
            TerrainDAO terrainDAO = new TerrainDAO();
            terrainDAO.setConnection(TransactionManager.getCurrentConnection());
            terrainDAO.update(updatedTerrain);
    
            TransactionManager.commit();
            showAlert(Alert.AlertType.INFORMATION, "Success", "Terrain updated successfully!");
    
            // Refresh the terrain list after a successful update
            displayAllTerrains();
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to update terrain.");
        }
    }
    
    private void handleDelete(Terrain terrain) {
        try {
            TransactionManager.beginTransaction();
    
            TerrainDAO terrainDAO = new TerrainDAO();
            terrainDAO.setConnection(TransactionManager.getCurrentConnection());
            terrainDAO.supprimer(terrain.getId());
    
            TransactionManager.commit();
            terrainTable.setItems(getTerrainsFromDatabase()); // Refresh table data
            showAlert(Alert.AlertType.INFORMATION, "Delete Action", "Deleted terrain: " + terrain.getNom());
            displayAllTerrains();
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to delete terrain.");
        }
    }
    
    private ObservableList<Terrain> getTerrainsFromDatabase() {
        ObservableList<Terrain> terrains = FXCollections.observableArrayList();
        try {
            TransactionManager.beginTransaction();

            TerrainDAO terrainDAO = new TerrainDAO();
            terrainDAO.setConnection(TransactionManager.getCurrentConnection());

            terrains.addAll(terrainDAO.afficher());

            TransactionManager.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return terrains;
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
