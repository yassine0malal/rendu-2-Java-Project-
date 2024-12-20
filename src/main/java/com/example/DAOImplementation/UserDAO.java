package com.example.DAOImplementation;
import com.example.PostgreSQLConnection;
import com.example.Models.User;
import com.example.transaction.TransactionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class UserDAO implements GenericDAO<User> {
    @Override
    public void ajouter(User user) {
         
        try (Connection connexion = TransactionManager.getCurrentConnection()) {
            if (connexion !=null &&!connexion.isClosed()) {
                System.out.println("Connection established successfully.");
            }
            
            if (user.getEmail().isEmpty() || user.getNom().isEmpty() || user.getPrenom().isEmpty() || user.getTypeUser().isEmpty() ) {
                System.out.println("Please fill in all fields or correct the values that you have entered.");
                return;   
            }

            String verfifeir = "SELECT COUNT(*) FROM utilisateurs WHERE email = ?";
            try(PreparedStatement prep = connexion.prepareStatement(verfifeir))
            {
                prep.setString(1, user.getEmail()); 
                try(ResultSet result = prep.executeQuery())
                {
                    if(result.next() && result.getInt(1) > 0)
                    {
                        System.out.println("Email already exists in the database.");
                        return;
                    }
                }
            }catch(SQLException e){
                e.printStackTrace();
                System.out.println("eroor de sql 1 ");
            }

            String query = "INSERT INTO utilisateurs (id, nom, prenom, email, type) VALUES ( ?, ?, ?, ?, ?)";
            try (PreparedStatement prep = connexion.prepareStatement(query)) {
                prep.setInt(1, user.getId());
                prep.setString(2, user.getNom());
                prep.setString(3, user.getPrenom());
                prep.setString(4, user.getEmail());
                prep.setString(5, user.getTypeUser());

                int rowsInserted = prep.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("New user added.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("eroor de sql");
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            System.out.println("connection failed");
            e.printStackTrace();
        }
        
    }

    @Override
    public ArrayList<User> afficher() {
        try (Connection connexion = PostgreSQLConnection.getConnection()) {
            String query = "SELECT * FROM utilisateurs";
            ArrayList<User> users = new ArrayList<>();
            try (Statement stmt = connexion.createStatement();
                 ResultSet rs = stmt.executeQuery(query)) {
                while (rs.next()){
                    User user = new User();
                    user.setId(rs.getInt("id"));
                    user.setNom(rs.getString("nom"));
                    user.setPrenom(rs.getString("prenom"));
                    user.setEmail(rs.getString("email"));
                    user.setTypeUser(rs.getString("type"));
                    users.add(user);
                }
                return users;
            } catch (SQLException e) {
                System.out.println("Error fetching users");
                return null;
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            System.out.println("connection failed");
            return null;
        }
    }

    @Override
    public void update( User user){
        try(Connection connexion = PostgreSQLConnection.getConnection();){
        String query = "UPDATE utilisateurs SET nom = ?, prenom = ?, email = ?, type = ? WHERE id = ?";
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
    }
    catch (SQLException e) {
        // TODO Auto-generated catch block
        System.out.println("connection failed");
        e.printStackTrace();

    }
}
    
    @Override
    public void supprimer (int id){
        try (Connection connexion = PostgreSQLConnection.getConnection()) {
        String query = "DELETE FROM utilisateurs WHERE id = ?";

        try (PreparedStatement prep = connexion.prepareStatement(query)) {
            prep.setInt(1, id);
            int rowsDeleted = prep.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("User deleted successfully.");
            } else {
                System.out.println("User not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }catch(SQLException e) {
        // TODO Auto-generated catch block
        System.out.println("connection failed");
        e.printStackTrace();
    }

}
    @Override
    public User get(int id){
        try (Connection connexion = PostgreSQLConnection.getConnection()) {
        String query = "SELECT * FROM utilisateurs WHERE id = ?";
        try (PreparedStatement prep = connexion.prepareStatement(query)) {
            prep.setInt(1, id);
            try (ResultSet rs = prep.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setId(rs.getInt("id"));
                    user.setNom(rs.getString("nom"));
                    user.setPrenom(rs.getString("prenom"));
                    user.setEmail(rs.getString("email"));
                    user.setTypeUser(rs.getString("type"));
                    return user;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }catch(SQLException e) {
        // TODO Auto-generated catch block
        System.out.println("connection failed");
    }
    return null;
}

}