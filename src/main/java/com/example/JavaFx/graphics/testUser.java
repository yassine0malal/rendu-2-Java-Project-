package com.example.JavaFx.graphics;

import java.sql.SQLException;
import java.util.ArrayList;

import com.example.DAOImplementation.UserDAO;
import com.example.Models.User;
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

public class testUser extends GridPane { // Extend GridPane

    private String nom;
    private String prenom;
    private String email;   
    private String typeUser;           

    private static int counter =0;
    private TextField usernameField;
    private TextField lastNameField;
    private TextField emailField;
    private ChoiceBox<String> userTypeComboBox;
    private Button registerButton;

    private HBox crudMenu;  // HBox for CRUD buttons
    private AnchorPane contentArea; // Area to display the content after button clicks

    private GridPane addUserForm; // Form for adding a user


    private TableView<User> userTable; // TableView for displaying users

    public  testUser() {
        // Set grid properties
        this.setAlignment(Pos.CENTER);
        this.setHgap(10);
        this.setVgap(10);
        this.setPadding(new Insets(20));
        this.setStyle("-fx-background-color: #f9f9f9;");

        // Initialize AnchorPane for dynamic content display
        contentArea = new AnchorPane();
        contentArea.setPrefSize(800, 600);

        // Create CRUD Menu (Navbar) horizontally
        crudMenu = new HBox(10);
        crudMenu.setPadding(new Insets(10));
        crudMenu.setSpacing(10);
        
        Button addUserButton = new Button("Add User");
        Button deleteUserButton = new Button("Daisplay Users");
        
        addUserButton.setOnAction(e -> displayAddUserForm());
        deleteUserButton.setOnAction(e -> displayAllUsers());

        crudMenu.getChildren().addAll(addUserButton, deleteUserButton);
        this.add(crudMenu, 0, 0);

        // Empty content area initially
        Label emptyLabel = new Label("Select an action from the navbar above");
        emptyLabel.setStyle("-fx-font-size: 18px;");
        contentArea.getChildren().add(emptyLabel);
        this.add(contentArea, 0, 1, 2, 1); // Place contentArea below the navbar

        // Register Button (you can remove this if not needed)
        registerButton = new Button("Register");
        registerButton.setStyle("-fx-font-size: 14px; -fx-background-color: #4CAF50; -fx-text-fill: white; -fx-padding: 5 20; -fx-cursor: hand;");
        GridPane.setHalignment(registerButton, HPos.RIGHT);
        registerButton.setOnAction(event -> handleRegister());
    }

                        private void displayAddUserForm() {
                            clearContentArea();
                            addUserForm = new GridPane();
                            addUserForm.setAlignment(Pos.CENTER);
                            addUserForm.setHgap(10);
                            addUserForm.setVgap(10);
                            addUserForm.setPadding(new Insets(20));
                            addUserForm.setStyle("-fx-background-color: #f9f9f9;");

                            Label titleLabel = new Label("User Registration");
                            titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #333;");
                            addUserForm.add(titleLabel, 0, 0, 2, 1); // Add to grid
                            GridPane.setHalignment(titleLabel, HPos.CENTER);
                            
                            Label usernameLabel = new Label("Username:");
                            usernameLabel.setStyle("-fx-font-size: 14px;");
                            usernameField = new TextField();
                            usernameField.setPromptText("Enter your name");
                            usernameField.setStyle("-fx-font-size: 14px; -fx-pref-width: 250px;");
                            addUserForm.add(usernameLabel, 0, 1);
                            addUserForm.add(usernameField, 1, 1);
                    
                            // Last Name Field
                            Label lastNameLabel = new Label("LastName:");
                            lastNameLabel.setStyle("-fx-font-size: 14px;");
                            lastNameField = new TextField();
                            lastNameField.setPromptText("Enter your last name");
                            lastNameField.setStyle("-fx-font-size: 14px; -fx-pref-width: 250px;");
                            addUserForm.add(lastNameLabel, 0, 2);
                            addUserForm.add(lastNameField, 1, 2);
                    
                            // Email Field
                            Label emailLabel = new Label("Email:");
                            emailLabel.setStyle("-fx-font-size: 14px;");
                            emailField = new TextField();
                            emailField.setPromptText("Enter your email");
                            emailField.setStyle("-fx-font-size: 14px; -fx-pref-width: 250px;");
                            addUserForm.add(emailLabel, 0, 3);
                            addUserForm.add(emailField, 1, 3);
                    
                            // User Type Field
                            Label userTypeLabel = new Label("User Type:");
                            userTypeLabel.setStyle("-fx-font-size: 14px;");
                            userTypeComboBox = new ChoiceBox<>();
                            userTypeComboBox.getItems().addAll("ETUDIANT", "PROFESSEUR"); // Add user types
                            userTypeComboBox.setStyle("-fx-font-size: 14px; -fx-pref-width: 250px;");
                            addUserForm.add(userTypeLabel, 0, 4);
                            addUserForm.add(userTypeComboBox, 1, 4);

                            // Register Button
                            registerButton = new Button("Register");
                            registerButton.setStyle("-fx-font-size: 14px; -fx-background-color: #4CAF50; -fx-text-fill: white; -fx-padding: 5 20; -fx-cursor: hand;");
                            addUserForm.add(registerButton, 1, 6);
                            GridPane.setHalignment(registerButton, HPos.RIGHT);
                            this.add(addUserForm, 0, 1, 2, 1); // Add form to grid

                            // Add action to button
                            registerButton.setOnAction(event -> handleRegister());
                            // if (usernameField.getText().isEmpty() || lastNameField.getText().isEmpty() || emailField.getText().isEmpty() || userTypeComboBox.getValue() == null) {
                            //     // if (counter != 0) {
                            //     //     showAlert(Alert.AlertType.ERROR, "Error", "All fields are required!");
                            //     //     counter++;
                                    
                            //     // }
                            // } else {
                            //     System.out.println("Selected user type: " + userTypeComboBox.getValue());
                                
                            // }
                        }

                        private void handleRegister() {
                            String username = usernameField.getText();
                            String lastName = lastNameField.getText();
                            String email = emailField.getText();
                            String userType = userTypeComboBox.getValue();
                    
                            try {
                                TransactionManager.beginTransaction();
                            } catch (SQLException e) {
                                e.printStackTrace();
                                System.out.println("erreur de connexion");
                            }
                    
                            UserDAO userDAO = new UserDAO();
                            userDAO.setConnection(TransactionManager.getCurrentConnection());
                            User user =new User(username, lastName, email, userType);
                            userDAO.ajouter(user);
                    
                            try {
                                TransactionManager.commit();
                            } catch (SQLException e) {
                                e.printStackTrace();
                                showAlert(Alert.AlertType.INFORMATION,"Error", "User registeration is failed");
                                System.out.println("erreur de commit");
                            }
                            showAlert(Alert.AlertType.INFORMATION, "Success", "User registered successfully!"); 
                            usernameField.clear();
                            lastNameField.clear();
                            emailField.clear();
                            userTypeComboBox.setValue(null);                            
                        }






    // Method to display the Delete User form
    private void displayAllUsers() {
        if (addUserForm != null) {
            addUserForm.getChildren().clear();
        }
        clearContentArea();
    
        // Initialisation du TableView
        userTable = new TableView<>();
        userTable.setPrefWidth(600);
        userTable.setPrefHeight(400);
    
        // Colonnes existantes
        TableColumn<User, String> usernameColumn = new TableColumn<>("Username");
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("prenom"));
    
        TableColumn<User, String> lastNameColumn = new TableColumn<>("Last Name");
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
    
        TableColumn<User, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
    
        TableColumn<User, String> userTypeColumn = new TableColumn<>("User Type");
        userTypeColumn.setCellValueFactory(new PropertyValueFactory<>("typeUser"));
    
        // Nouvelle colonne Action
        TableColumn<User, Void> actionColumn = new TableColumn<>("Action");
        actionColumn.setCellFactory(param -> new TableCell<>() {
            private final Button modifyButton = new Button("Modify");
            private final Button deleteButton = new Button("Delete");
            private final HBox actionButtons = new HBox(10, modifyButton, deleteButton);
    
            {
                // Style des boutons
                modifyButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
                deleteButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");
    
                // Action sur le bouton Modifier
                modifyButton.setOnAction(event -> {
                    User selectedUser = getTableView().getItems().get(getIndex());
                    System.out.println("Selected user: " + selectedUser.getTypeUser());
                    modifyUserForm(selectedUser);
                });
    
                // Action sur le bouton Supprimer
                deleteButton.setOnAction(event -> {
                    User selectedUser = getTableView().getItems().get(getIndex());
                    deleteUser(selectedUser);
                });
            }
    
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(actionButtons);
                }
            }
        });
    
        // Ajouter les colonnes au TableView
        userTable.getColumns().addAll(usernameColumn, lastNameColumn, emailColumn, userTypeColumn, actionColumn);
    
        // Charger les données dans le TableView
        userTable.setItems(getUsersFromDatabase());
    
        // Afficher le TableView dans la zone de contenu
        contentArea.getChildren().add(userTable);
        this.add(contentArea, 0, 4, 2, 1);
    }

    public void deleteUser(User user) {
        try {
            TransactionManager.beginTransaction();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("erreur de connexion");
        }

        UserDAO userDAO = new UserDAO();
        userDAO.setConnection(TransactionManager.getCurrentConnection());
        userDAO.supprimer(user.getId());
        try {
            TransactionManager.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.INFORMATION, "Error", "User deletion failed");
            System.out.println("erreur de commit");
        }
        showAlert(Alert.AlertType.INFORMATION, "Success", "User deleted successfully!");
        displayAllUsers();
    }
    // Method to clear the content area and grid pane also 
    private void clearContentArea() {

        contentArea.getChildren().clear();
        
    }

     private ObservableList<User> getUsersFromDatabase() {
        ObservableList<User> users = FXCollections.observableArrayList();
        try {
            // Begin transaction
            TransactionManager.beginTransaction();

            // Fetch users using DAO
            UserDAO userDAO = new UserDAO();
            userDAO.setConnection(TransactionManager.getCurrentConnection());
            users.addAll(userDAO.afficher());

            // Commit transaction
            TransactionManager.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error fetching users from database.");
        }

        return users;
    }

    public void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setAlertType(alertType);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    public void modifyUserForm(User user ){
    clearContentArea();
   

                            addUserForm = new GridPane();
                            addUserForm.setAlignment(Pos.CENTER);
                            addUserForm.setHgap(10);
                            addUserForm.setVgap(10);
                            addUserForm.setPadding(new Insets(20));
                            addUserForm.setStyle("-fx-background-color: #f9f9f9;");

                            Label titleLabel = new Label("User Modification");
                            titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #333;");
                            addUserForm.add(titleLabel, 0, 0, 2, 1); // Add to grid
                            GridPane.setHalignment(titleLabel, HPos.CENTER);
                            
                            Label usernameLabel = new Label("Username:");
                            usernameLabel.setStyle("-fx-font-size: 14px;");
                            usernameField = new TextField();
                            usernameField.setText(user.getPrenom());
                            usernameField.setStyle("-fx-font-size: 14px; -fx-pref-width: 250px;");
                            addUserForm.add(usernameLabel, 0, 1);
                            addUserForm.add(usernameField, 1, 1);
                    
                            // Last Name Field
                            Label lastNameLabel = new Label("LastName:");
                            lastNameLabel.setStyle("-fx-font-size: 14px;");
                            lastNameField = new TextField();
                            lastNameField.setText(user.getNom());
                            lastNameField.setStyle("-fx-font-size: 14px; -fx-pref-width: 250px;");
                            addUserForm.add(lastNameLabel, 0, 2);
                            addUserForm.add(lastNameField, 1, 2);
                    
                            // Email Field
                            Label emailLabel = new Label("Email:");
                            emailLabel.setStyle("-fx-font-size: 14px;");
                            emailField = new TextField();
                            emailField.setText(user.getEmail());
                            emailField.setStyle("-fx-font-size: 14px; -fx-pref-width: 250px;");
                            addUserForm.add(emailLabel, 0, 3);
                            addUserForm.add(emailField, 1, 3);
                    
                            // User Type Field
                            Label userTypeLabel = new Label("User Type:");
                            userTypeLabel.setStyle("-fx-font-size: 14px;");
                            userTypeComboBox = new ChoiceBox<>();
                            userTypeComboBox.getItems().addAll("ETUDIANT", "PROFESSEUR"); // Add user types
                            userTypeComboBox.setValue(user.getTypeUser());
                            userTypeComboBox.setStyle("-fx-font-size: 14px; -fx-pref-width: 250px;");
                            addUserForm.add(userTypeLabel, 0, 4);
                            addUserForm.add(userTypeComboBox, 1, 4);

                           
                            if (userTypeComboBox.getValue() == null) {
                                showAlert(Alert.AlertType.ERROR, "Error", "Dont forget type field !");
                                
                            }

                            // Register Button
                            registerButton = new Button("Register");
                            registerButton.setStyle("-fx-font-size: 14px; -fx-background-color: #4CAF50; -fx-text-fill: white; -fx-padding: 5 20; -fx-cursor: hand;");
                            addUserForm.add(registerButton, 1, 6);
                            GridPane.setHalignment(registerButton, HPos.RIGHT);
                            this.add(addUserForm, 0, 1, 2, 1); // Add form to grid
                            registerButton.setOnAction(event -> {
                                User modifyUser = new User();
                                modifyUser.setId(user.getId());
                                modifyUser.setNom(usernameField.getText());
                                modifyUser.setPrenom(lastNameField.getText());
                                modifyUser.setEmail(emailField.getText());
                                modifyUser.setTypeUser(userTypeComboBox.getValue());
                                handleModifayButton(modifyUser);
                                System.out.println("Selected user type: " + modifyUser.getTypeUser());
                            });


}
private void handleModifayButton(User modifyuser){

    try {
        TransactionManager.beginTransaction();
    } catch (SQLException e) {
        e.printStackTrace();
        System.out.println("erreur de connexion");
    }

    UserDAO userDAO = new UserDAO();
    userDAO.setConnection(TransactionManager.getCurrentConnection());
    userDAO.update(modifyuser);
    try {
        TransactionManager.commit();
    } catch (SQLException e) {
        e.printStackTrace();
        showAlert(Alert.AlertType.INFORMATION,"Error", "User registeration is failed");
        System.out.println("erreur de commit");
    }
    showAlert(Alert.AlertType.INFORMATION, "Success", "User registered successfully!"); 
    usernameField.clear();
    lastNameField.clear();
    emailField.clear();
    userTypeComboBox.setValue(null);                            
}
}


