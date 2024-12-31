package com.example.JavaFx.other;

import java.net.URL;
import java.util.ResourceBundle;

import com.example.DAOImplementation.UserDAO;
import com.example.Models.User;
import com.example.transaction.TransactionManager;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class RegisterController implements Initializable {

    @FXML
    private TextField usernameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField emailField;

    @FXML
    private ChoiceBox<String> userTypeComboBox;
    private String array[] = {"ETUDIANT", "PROFESSEUR"};
    
    private UserDAO userDAO = new UserDAO();

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        userTypeComboBox.getItems().addAll(array);
    }
    


    @FXML
    private void handleRegister()throws Exception {
        TransactionManager.beginTransaction();
        userDAO.setConnection(TransactionManager.getCurrentConnection());
        String firstName = usernameField.getText();
        String lastName = lastNameField.getText();
        String email = emailField.getText();
        String type = userTypeComboBox.getValue();

        // Validate form fields
        if (firstName.isEmpty() || lastName.isEmpty() || type == null || email.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "All fields are required!");
        } else {
            // Debugging statement to verify selected user type
            System.out.println("Selected user type: " + type);

            User user = new User(firstName, lastName, email, type);
            userDAO.ajouter(user);
            TransactionManager.commit();
            // Show success alert
            showAlert(Alert.AlertType.INFORMATION, "Success", "User registered successfully!");
            usernameField.clear();
            lastNameField.clear();
            emailField.clear();
            
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }



 
}

