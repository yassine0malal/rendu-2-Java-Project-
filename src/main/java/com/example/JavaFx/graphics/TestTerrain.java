package com.example.JavaFx.graphics;

import java.sql.SQLException;
import java.util.ArrayList;

import com.example.DAOImplementation.TerrainDAO;
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

public class TestTerrain extends GridPane {

    private TextField nameField;
    private TextField typeField;
    private Button registerButton;

    private HBox crudMenu;
    private AnchorPane contentArea;
    private GridPane addTerrainForm;
    private TableView<Terrain> terrainTable;

    public TestTerrain() {
        this.setAlignment(Pos.CENTER);
        this.setHgap(10);
        this.setVgap(10);
        this.setPadding(new Insets(20));
        this.setStyle("-fx-background-color: #f9f9f9;");

        contentArea = new AnchorPane();
        contentArea.setPrefSize(800, 600);

        crudMenu = new HBox(10);
        crudMenu.setPadding(new Insets(10));
        crudMenu.setSpacing(10);

        Button addTerrainButton = new Button("Add Terrain");
        Button displayTerrainButton = new Button("Display Terrains");

        addTerrainButton.setOnAction(e -> displayAddTerrainForm());
        displayTerrainButton.setOnAction(e -> displayAllTerrains());

        crudMenu.getChildren().addAll(addTerrainButton, displayTerrainButton);
        this.add(crudMenu, 0, 0);

        Label emptyLabel = new Label("Select an action from the navbar above");
        emptyLabel.setStyle("-fx-font-size: 18px;");
        contentArea.getChildren().add(emptyLabel);
        this.add(contentArea, 0, 1, 2, 1);
    }

    private void displayAddTerrainForm() {
        clearContentArea();
        if (terrainTable != null) {
            terrainTable.getItems().clear();
        }
        if (addTerrainForm != null) {
            addTerrainForm.getChildren().clear();
            
        }
        
        addTerrainForm = new GridPane();
        addTerrainForm.setAlignment(Pos.CENTER);
        addTerrainForm.setHgap(10);
        addTerrainForm.setVgap(10);
        addTerrainForm.setPadding(new Insets(20));
        addTerrainForm.setStyle("-fx-background-color: #f9f9f9;");

        Label titleLabel = new Label("Terrain Registration");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #333;");
        addTerrainForm.add(titleLabel, 0, 0, 2, 1);
        GridPane.setHalignment(titleLabel, HPos.CENTER);

        Label nameLabel = new Label("Terrain Name:");
        nameLabel.setStyle("-fx-font-size: 14px;");
        nameField = new TextField();
        nameField.setPromptText("Enter terrain name");
        addTerrainForm.add(nameLabel, 0, 1);
        addTerrainForm.add(nameField, 1, 1);

        Label typeLabel = new Label("Terrain Type:");
        typeLabel.setStyle("-fx-font-size: 14px;");
        typeField = new TextField();
        typeField.setPromptText("Enter terrain type");
        addTerrainForm.add(typeLabel, 0, 2);
        addTerrainForm.add(typeField, 1, 2);

        registerButton = new Button("Register");
        registerButton.setStyle("-fx-font-size: 14px; -fx-background-color: #4CAF50; -fx-text-fill: white;");
        addTerrainForm.add(registerButton, 1, 3);
        GridPane.setHalignment(registerButton, HPos.RIGHT);

        this.add(addTerrainForm, 0, 1, 2, 1);

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
            addTerrainForm.getChildren().clear();
        }
      

    
        terrainTable = new TableView<>();
        terrainTable.setPrefWidth(800);
        terrainTable.setPrefHeight(400);
    
        TableColumn<Terrain, Integer> idColumn = new TableColumn<>("Number.");
        idColumn.setPrefWidth(60);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
    
        TableColumn<Terrain, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setPrefWidth(200);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
    
        TableColumn<Terrain, String> typeColumn = new TableColumn<>("Type");
        typeColumn.setPrefWidth(200);
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
    
        // Add an "Action" column
        TableColumn<Terrain, Void> actionColumn = new TableColumn<>("Action");
        actionColumn.setPrefWidth(150);
    
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
    
        terrainTable.getColumns().addAll(idColumn, nameColumn, typeColumn, actionColumn);
        terrainTable.setItems(getTerrainsFromDatabase());
    
        contentArea.getChildren().add(terrainTable);
        this.add(contentArea, 0, 4, 2, 1);
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
    
    
    
    // Method to handle delete action
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
