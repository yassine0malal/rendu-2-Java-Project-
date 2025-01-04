package com.example.DAOImplementation;

import com.example.Models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class UserDAO implements GenericDAO<User> {
    private Connection connexion; 

    public void setConnection(Connection connexion) {
        this.connexion = connexion;
    }

    @Override
    public void ajouter(User user) {
        try {
            if (user.getEmail().isEmpty() || user.getNom().isEmpty() || user.getPrenom().isEmpty() || user.getTypeUser().isEmpty()) {
                System.out.println("Please fill in all fields or correct the values that you have entered.");
                return;
            }

            // String verfifeir = "SELECT COUNT(*) FROM utilisateurs WHERE email = ?";
            // try (PreparedStatement prep = connexion.prepareStatement(verfifeir)) {
            //     prep.setString(1, user.getEmail());
            //     try (ResultSet result = prep.executeQuery()) {
            //         if (result.next() && result.getInt(1) > 0) {
            //             System.out.println("Email already exists in the database.");
            //             return;
            //         }
            //     }
            // }
            if (verifyEmailExist(user.getEmail()) > 0) {
                System.out.println("Email already exists in the database.");
                return;
                
            }

            String query = "INSERT INTO utilisateurs (nom, prenom, email, type) VALUES (?, ?, ?, ?)";
            try (PreparedStatement prep = connexion.prepareStatement(query)) {
                prep.setString(1, user.getNom());
                prep.setString(2, user.getPrenom());
                prep.setString(3, user.getEmail());
                prep.setString(4, user.getTypeUser());

                int rowsInserted = prep.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("New user added.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQL error during user addition.");
        }
    }

    @Override
    public ArrayList<User> afficher() {
        ArrayList<User> users = new ArrayList<>();
        try {
            String query = "SELECT * FROM utilisateurs";
            try (Statement stmt = connexion.createStatement();
                 ResultSet rs = stmt.executeQuery(query)) {
                while (rs.next()) {
                    User user = new User();
                    user.setId(rs.getInt("id_user"));
                    user.setNom(rs.getString("nom"));
                    user.setPrenom(rs.getString("prenom"));
                    user.setEmail(rs.getString("email"));
                    user.setTypeUser(rs.getString("type"));
                    users.add(user);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching users");
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public void update(User user) {
        try {
            String query = "UPDATE utilisateurs SET nom = ?, prenom = ?, email = ?, type = ? WHERE id_user = ?";
            try (PreparedStatement prep = connexion.prepareStatement(query)) {
                prep.setString(1, user.getNom());
                prep.setString(2, user.getPrenom());
                prep.setString(3, user.getEmail());
                prep.setString(4, user.getTypeUser());
                prep.setInt(5, user.getId());
                int rowsUpdated = prep.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println("User updated successfully.");
                } else {
                    System.out.println("User not found.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQL error during user update.");
        }
    }

    @Override
    public void supprimer(int id) {
        try {
            String query = "DELETE FROM utilisateurs WHERE id_user = ?";
            try (PreparedStatement prep = connexion.prepareStatement(query)) {
                prep.setInt(1, id);
                int rowsDeleted = prep.executeUpdate();
                if (rowsDeleted > 0) {
                    System.out.println("User deleted successfully.");
                } else {
                    System.out.println("User not found.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQL error during user deletion.");
        }
    }

    @Override
    public User get(int id) {
        try {
            String query = "SELECT * FROM utilisateurs WHERE id_user = ?";
            try (PreparedStatement prep = connexion.prepareStatement(query)) {
                prep.setInt(1, id);
                try (ResultSet rs = prep.executeQuery()) {
                    if (rs.next()) {
                        User user = new User();
                        user.setId(rs.getInt("id_user"));
                        user.setNom(rs.getString("nom"));
                        user.setPrenom(rs.getString("prenom"));
                        user.setEmail(rs.getString("email"));
                        user.setTypeUser(rs.getString("type"));
                        return user;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQL error during user retrieval.");
        }
        return null;
    }

    public int verifyEmailExist(String email) {
        try {
            String query = "SELECT COUNT(*) FROM utilisateurs WHERE email = ?";
            try (PreparedStatement prep = connexion.prepareStatement(query)) {
                prep.setString(1, email);
                try (ResultSet rs = prep.executeQuery()) {
                    if (rs.next()) {
                        return rs.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQL error during email verification.");
        }
        return 0;
    }



}
