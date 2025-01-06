package com.example.JavaFx.graphics;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

import com.example.DAOImplementation.UserDAO;
import com.example.Models.Reservation;
import com.example.Models.User;
import com.example.transaction.TransactionManager;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

public class UserPage extends GridPane { 
    private TextField usernameField;
    private TextField lastNameField;
    private TextField emailField;
    private ChoiceBox<String> userTypeComboBox;
    private Button registerButton;

    private HBox crudMenu; 
    private AnchorPane contentArea; 

    private GridPane addUserForm; 

    private TableView<User> userTable; 

    public UserPage() {
        // Set grid properties
        this.setAlignment(Pos.CENTER);
        this.setHgap(10);
        this.setVgap(10);
        this.setPadding(new Insets(20));
        this.setStyle("-fx-background-color: #DEDEDE;");

        // Initialize AnchorPane for dynamic content display
        contentArea = new AnchorPane();
        contentArea.setPrefSize(800, 600);

        // Create CRUD Menu (Navbar) horizontally
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
            crudMenu.setStyle(
                    """
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

        Button addUserButton = new Button("Add User");
        Button displayUserButton = new Button("Daisplay Users");
        addUserButton.setStyle(
                "-fx-font-size: 14px; -fx-background-color:#5211e9; -fx-text-fill: white; -f-decoration: none; -fx-background-radius: 15px;");
        displayUserButton.setStyle(
                "-fx-font-size: 14px; -fx-background-color:#5211e9; -fx-text-fill: white; -f-decoration: none; -fx-background-radius: 15px;");

        addUserButton.setOnAction(e -> displayAddUserForm());
        displayUserButton.setOnAction(e -> displayAllUsers());

        crudMenu.getChildren().addAll(addUserButton, displayUserButton);
        this.add(crudMenu, 0, 0);

        // Empty content area initially
        Label emptyLabel = new Label("Select an action from the navbar above");
        emptyLabel.setStyle("-fx-font-size: 18px;");
        StackPane stackPane = new StackPane(emptyLabel);
        stackPane.setPrefSize(1000, 600);
        stackPane.setStyle("-fx-content-display: center; -fx-alignment: center;");

        contentArea.getChildren().add(stackPane);
        this.add(contentArea, 0, 1, 2, 1);

        registerButton = new Button("Register");
        registerButton.setStyle(
                "-fx-font-size: 14px; -fx-background-color: #4CAF50; -fx-text-fill: white; -fx-padding: 5 20; -fx-cursor: hand;");
        GridPane.setHalignment(registerButton, HPos.RIGHT);
        registerButton.setOnAction(event -> handleRegister());
    }

    public void displayAddUserForm() {
        if (userTable != null) {
            this.getChildren().remove(userTable);
        }

        clearContentArea();

        addUserForm = new GridPane();
        addUserForm.setAlignment(Pos.CENTER);
        addUserForm.setHgap(10);
        addUserForm.setVgap(10);
        addUserForm.setPadding(new Insets(20));
        addUserForm.setStyle(
                "-fx-background-color: #F9F9F9; -fx-border-radius: 15px; -fx-background-radius: 15px; -fx-padding: 20;");

        Label titleLabel = new Label("User Registration");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #333;");
        addUserForm.add(titleLabel, 0, 0, 2, 1);
        GridPane.setHalignment(titleLabel, HPos.CENTER);

        Label usernameLabel = new Label("Username:");
        usernameLabel.setStyle("-fx-font-size: 14px;");
        usernameField = new TextField();
        usernameField.setPromptText("Enter your name");
        usernameField.setStyle("-fx-font-size: 14px; -fx-pref-width: 250px;");
        addUserForm.add(usernameLabel, 0, 1);
        addUserForm.add(usernameField, 1, 1);

        Label lastNameLabel = new Label("Last Name:");
        lastNameLabel.setStyle("-fx-font-size: 14px;");
        lastNameField = new TextField();
        lastNameField.setPromptText("Enter your last name");
        lastNameField.setStyle("-fx-font-size: 14px; -fx-pref-width: 250px;");
        addUserForm.add(lastNameLabel, 0, 2);
        addUserForm.add(lastNameField, 1, 2);

        Label emailLabel = new Label("Email:");
        emailLabel.setStyle("-fx-font-size: 14px;");
        emailField = new TextField();
        emailField.setPromptText("Enter your email");
        emailField.setStyle("-fx-font-size: 14px; -fx-pref-width: 250px;");
        addUserForm.add(emailLabel, 0, 3);
        addUserForm.add(emailField, 1, 3);

        Label userTypeLabel = new Label("User Type:");
        userTypeLabel.setStyle("-fx-font-size: 14px;");
        userTypeComboBox = new ChoiceBox<>();
        userTypeComboBox.getItems().addAll("ETUDIANT", "PROFESSEUR");
        userTypeComboBox.setStyle("-fx-font-size: 14px; -fx-pref-width: 250px;");
        addUserForm.add(userTypeLabel, 0, 4);
        addUserForm.add(userTypeComboBox, 1, 4);

        registerButton = new Button("Register");
        registerButton.setStyle(
                "-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px; -fx-border-radius: 5px; -fx-padding: 10px 20px;");
        registerButton.setOnMouseEntered(e -> registerButton.setStyle(
                "-fx-background-color:rgb(158, 226, 13); -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px; -fx-border-radius: 5px; -fx-padding: 10px 20px;"));
        registerButton.setOnMouseExited(e -> registerButton.setStyle(
                "-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px; -fx-border-radius: 5px; -fx-padding: 10px 20px;"));
        addUserForm.add(registerButton, 1, 6);
        GridPane.setHalignment(registerButton, HPos.RIGHT);

        registerButton.setOnAction(event -> handleRegister());

        contentArea.getChildren().add(addUserForm);
        AnchorPane.setTopAnchor(addUserForm, 50.0);
        AnchorPane.setLeftAnchor(addUserForm, 100.0);
        AnchorPane.setRightAnchor(addUserForm, 100.0);

        this.add(contentArea, 0, 1, 2, 1);

    }

    public boolean isValidEmail(String email) {
        // Regular expression to validate email format
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public void handleRegister() {
        String username = usernameField.getText();
        String lastName = lastNameField.getText();
        String email = emailField.getText();
        String userType = userTypeComboBox.getValue();

        if (username.isEmpty() || lastName.isEmpty() || email.isEmpty() || userType == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "All fields are required!");
            return;
        }
        if (!isValidEmail(email)) {
            showAlert(Alert.AlertType.ERROR, "Error", "Invalid email format!");
            return;
        }

        try {
            TransactionManager.beginTransaction();
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to connect to the database.");
            return;
        }

        UserDAO userDAO = new UserDAO();
        userDAO.setConnection(TransactionManager.getCurrentConnection());
        User user = new User(username, lastName, email, userType);
        if (userDAO.verifyEmailExist(email) > 0) {
            showAlert(Alert.AlertType.ERROR, "Error", "Email already exists in the database.");
            return;

        }
        userDAO.ajouter(user);

        try {

            TransactionManager.commit();

            sendEmail(email);
            showAlert(Alert.AlertType.INFORMATION, "Success", "User registered successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "User registration failed.");
            return;
        }

        usernameField.clear();
        lastNameField.clear();
        emailField.clear();
        userTypeComboBox.setValue(null);
    }

    public void handleDownloadAllUsers() {
        ObservableList<User> allUsers = userTable.getItems();

        String fileName = "all_users.csv";

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        fileChooser.setInitialFileName(fileName);

        File file = fileChooser.showSaveDialog(contentArea.getScene().getWindow());
        if (file != null) {
            try (FileWriter writer = new FileWriter(file)) {
                writer.append("ID ,Last Name,First Name, Email,User Type\n");

                for (User user : allUsers) {
                    writer.append(user.getId() + ",");
                    writer.append(user.getNom() + ",");
                    writer.append(user.getPrenom() + ",");
                    writer.append(user.getEmail() + ",");
                    writer.append(user.getTypeUser() + "\n");
                }

                showAlert(Alert.AlertType.INFORMATION, "Success", "All reservations have been saved to file.");
            } catch (IOException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to save the reservation file.");
            }
        }
    }

    public void sendEmail(String recipientEmail) {
        // Sender's email and SMTP server settings
        String senderEmail = "malalyassin6@gmail.com"; // Sender's email address
        String senderPassword = "cseu vjve evjc lfcf"; // Sender's email password
        String subject = "Welcome to Our Service!";
        String messageBody = "Thank you for registering with us. We are glad to have you on our service!";

        // Set up the properties for the SMTP server
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        // Get the Session object and authenticate using the sender's email credentials
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
            // Create a MimeMessage object
            MimeMessage message = new MimeMessage(session);

            // Set the sender and recipient addresses
            message.setFrom(new InternetAddress(senderEmail));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));

            // Set the email subject and body
            message.setSubject(subject);
            message.setText(messageBody);

            // Send the email
            Transport.send(message);
            System.out.println("Email sent successfully to " + recipientEmail);
        } catch (MessagingException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to send the email.");
        }
    }

    public void displayAllUsers() {
        if (addUserForm != null) {
            this.getChildren().remove(addUserForm);
        }
        clearContentArea();

        userTable = new TableView<>();
        userTable.setPrefWidth(1000);
        userTable.setPrefHeight(TableView.USE_COMPUTED_SIZE);
        userTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        userTable.setStyle(
                "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.4), 12, 0, 4, 4); -fx-border-color: rgba(71, 68, 68, 0.2);");
        userTable.setStyle(
                "-fx-border-radius: 15px; -fx-background-radius: 15px; -fx-padding: 20; -fx-border-width: 2px;");

        TableColumn<User, String> usernameColumn = new TableColumn<>("Username");
        usernameColumn.setPrefWidth(200);
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("prenom"));

        TableColumn<User, String> lastNameColumn = new TableColumn<>("Last Name");
        lastNameColumn.setPrefWidth(200);
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));

        TableColumn<User, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setPrefWidth(200);
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<User, String> userTypeColumn = new TableColumn<>("User Type");
        userTypeColumn.setPrefWidth(200);
        userTypeColumn.setCellValueFactory(new PropertyValueFactory<>("typeUser"));

        TableColumn<User, Void> actionColumn = new TableColumn<>("Action");
        actionColumn.setCellFactory(param -> new TableCell<>() {
            private final Button modifyButton = new Button("Modify");
            private final Button deleteButton = new Button("Delete");
            private final HBox actionButtons = new HBox(10, modifyButton, deleteButton);

            {
                modifyButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
                deleteButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");

                modifyButton.setOnAction(event -> {
                    User selectedUser = getTableView().getItems().get(getIndex());
                    System.out.println("Selected user: " + selectedUser.getTypeUser());
                    modifyUserForm(selectedUser);
                });

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

        userTable.getColumns().addAll(usernameColumn, lastNameColumn, emailColumn, userTypeColumn, actionColumn);

        userTable.setItems(getUsersFromDatabase());

        VBox contentLayout = new VBox(20);
        contentLayout.setAlignment(Pos.CENTER);
        contentLayout.setPadding(new Insets(20));

        contentLayout.getChildren().add(userTable);

        Button downloadAllUsersButton = new Button("Download All Users");
        downloadAllUsersButton.setStyle(
                "-fx-background-color: #0078D7; -fx-text-fill: white; -fx-padding: 10; -fx-border-radius: 5px; -fx-font-weight: bold; -fx-font-size: 14px; -fx-border-radius: 5px; -fx-padding: 10px 20px;");
        downloadAllUsersButton.setOnAction(event -> handleDownloadAllUsers());

        VBox buttonContainer = new VBox(downloadAllUsersButton);
        buttonContainer.setAlignment(Pos.CENTER);
        contentLayout.getChildren().add(buttonContainer);

        contentArea.getChildren().add(contentLayout);

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

    public void clearContentArea() {

        contentArea.getChildren().clear();

    }

    public ObservableList<User> getUsersFromDatabase() {
        ObservableList<User> users = FXCollections.observableArrayList();
        try {
            TransactionManager.beginTransaction();
            UserDAO userDAO = new UserDAO();
            userDAO.setConnection(TransactionManager.getCurrentConnection());
            users.addAll(userDAO.afficher());

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

    public void modifyUserForm(User user) {
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
        registerButton.setStyle(
                "-fx-font-size: 14px; -fx-background-color: #4CAF50; -fx-text-fill: white; -fx-padding: 5 20; -fx-cursor: hand;");
        addUserForm.add(registerButton, 1, 6);
        GridPane.setHalignment(registerButton, HPos.RIGHT);
        this.add(addUserForm, 0, 1, 2, 1); // Add form to grid
        registerButton.setOnAction(event -> {
            User modifyUser = new User();
            modifyUser.setId(user.getId());
            modifyUser.setPrenom(usernameField.getText());
            modifyUser.setNom(lastNameField.getText());
            modifyUser.setEmail(emailField.getText());
            modifyUser.setTypeUser(userTypeComboBox.getValue());
            handleModifayButton(modifyUser);
            System.out.println("Selected user type: " + modifyUser.getTypeUser());
        });

    }

    public void handleModifayButton(User modifyuser) {

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
            showAlert(Alert.AlertType.INFORMATION, "Error", "User registeration is failed");
            System.out.println("erreur de commit");
        }
        showAlert(Alert.AlertType.INFORMATION, "Success", "User registered successfully!");
        usernameField.clear();
        lastNameField.clear();
        emailField.clear();
        userTypeComboBox.setValue(null);
    }
}
