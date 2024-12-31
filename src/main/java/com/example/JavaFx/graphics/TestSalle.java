package com.example.JavaFx.graphics;

import java.sql.SQLException;
import java.util.ArrayList;

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

public class TestSalle extends GridPane {

    private TextField nameField;
    private TextField capacityField;
    private Button registerButton;

    private HBox crudMenu;
    private AnchorPane contentArea;
    private GridPane addSalleForm;
    private TableView<Salle> salleTable;

    public TestSalle() {
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

        Button addSalleButton = new Button("Add Salle");
        Button displaySalleButton = new Button("Display Salles");

        addSalleButton.setOnAction(e -> displayAddSalleForm());
        displaySalleButton.setOnAction(e -> displayAllSalles());

        crudMenu.getChildren().addAll(addSalleButton, displaySalleButton);
        this.add(crudMenu, 0, 0);

        Label emptyLabel = new Label("Select an action from the navbar above");
        emptyLabel.setStyle("-fx-font-size: 18px;");
        contentArea.getChildren().add(emptyLabel);
        this.add(contentArea, 0, 1, 2, 1);
    }

    private void displayAddSalleForm() {
        clearContentArea();
        if (salleTable != null) {
            salleTable.getItems().clear();
        }
        if (addSalleForm != null) {
            addSalleForm.getChildren().clear();
        }

        addSalleForm = new GridPane();
        addSalleForm.setAlignment(Pos.CENTER);
        addSalleForm.setHgap(10);
        addSalleForm.setVgap(10);
        addSalleForm.setPadding(new Insets(20));
        addSalleForm.setStyle("-fx-background-color: #f9f9f9;");

        Label titleLabel = new Label("Salle Registration");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #333;");
        addSalleForm.add(titleLabel, 0, 0, 2, 1);
        GridPane.setHalignment(titleLabel, HPos.CENTER);

        Label nameLabel = new Label("Salle Name:");
        nameLabel.setStyle("-fx-font-size: 14px;");
        nameField = new TextField();
        nameField.setPromptText("Enter salle name");
        addSalleForm.add(nameLabel, 0, 1);
        addSalleForm.add(nameField, 1, 1);

        Label capacityLabel = new Label("Capacity:");
        capacityLabel.setStyle("-fx-font-size: 14px;");
        capacityField = new TextField();
        capacityField.setPromptText("Enter salle capacity");
        addSalleForm.add(capacityLabel, 0, 2);
        addSalleForm.add(capacityField, 1, 2);

        registerButton = new Button("Register");
        registerButton.setStyle("-fx-font-size: 14px; -fx-background-color: #4CAF50; -fx-text-fill: white;");
        addSalleForm.add(registerButton, 1, 3);
        GridPane.setHalignment(registerButton, HPos.RIGHT);

        this.add(addSalleForm, 0, 1, 2, 1);

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
            addSalleForm.getChildren().clear();
            
        }
        if (salleTable != null) {
            salleTable.getItems().clear();
            
        }
    
        TableView<Salle> salleTable = new TableView<>();
        salleTable.setPrefWidth(800);
        salleTable.setPrefHeight(400);
    
        // ID Column
        TableColumn<Salle, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setPrefWidth(60);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
    
        // Name Column
        TableColumn<Salle, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setPrefWidth(200);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
    
        // Capacity Column
        TableColumn<Salle, Integer> capacityColumn = new TableColumn<>("Capacity");
        capacityColumn.setPrefWidth(100);
        capacityColumn.setCellValueFactory(new PropertyValueFactory<>("capacite"));
    
        // Action Column
        TableColumn<Salle, Void> actionColumn = new TableColumn<>("Action");
        actionColumn.setPrefWidth(150);
    
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
    
        salleTable.getColumns().addAll(idColumn, nameColumn, capacityColumn, actionColumn);
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
