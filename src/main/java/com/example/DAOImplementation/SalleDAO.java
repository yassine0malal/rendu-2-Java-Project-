package com.example.DAOImplementation;
import com.example.PostgreSQLConnection;
import com.example.Models.Salle;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SalleDAO implements GenericDAO<Salle> {
    
    public SalleDAO() {
    }

    public void ajouter (Salle salle){
        try (Connection conexion = PostgreSQLConnection.getConnection()) {
            String query = "INSERT INTO salles (nom_salle, capacite) VALUES (?, ?)";
            try (PreparedStatement prep = conexion.prepareStatement(query)) {
                prep.setString(1, salle.getNom());
                prep.setInt(2, salle.getCapacite());
                int rowsInserted = prep.executeUpdate();
                if (rowsInserted > 0){
                    System.out.println("New salle added to a user successfully.");
                }
            }catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
                System.out.println("Error: " + e.getMessage());
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("Error: de connecxion a la base de données");
        }
    }

    public void supprimer(int id){
        try (Connection conexion = PostgreSQLConnection.getConnection()) {
            String query = "DELETE FROM salles WHERE id_salle = ?";
            try (PreparedStatement prep = conexion.prepareStatement(query)) {
                prep.setInt(1, id);
                int rowsDeleted = prep.executeUpdate();
                if (rowsDeleted > 0) {
                    System.out.println("salle deleted successfully.");
                } else {
                    System.out.println("salle not found.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void update(Salle salle){
        try (Connection conexion = PostgreSQLConnection.getConnection()) {
            String query = "UPDATE salles SET capacite = ? ,nom_salle= ? WHERE id_salle = ?";
            try (PreparedStatement prep = conexion.prepareStatement(query)) {
                prep.setInt(1, salle.getCapacite());
                prep.setString(2, salle.getNom());
                prep.setInt(3, salle.getId());

                int rowsUpdated = prep.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println("salle updated successfully.");
                } else {
                    System.out.println("salle not found.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("Error: de connecxion a la base de donnees");
        }
    }

    @Override
    public ArrayList<Salle> afficher(){
        try (Connection conexion = PostgreSQLConnection.getConnection()) {
            String query = "SELECT * FROM salles";
            ArrayList <Salle> salles = new ArrayList<>();
            try (PreparedStatement prep = conexion.prepareStatement(query)) {
                try (ResultSet result = prep.executeQuery()) {
                    while (result.next()) {
                        Salle salle = new Salle();
                        salle.setId(result.getInt("id_salle"));
                        salle.setNom(result.getString("nom_salle"));
                        salle.setCapacite(result.getInt("capacite"));
                        salles.add(salle);
                    }
                }
                return salles;
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Error: de sql moi ");
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("Error: de connecxion a la base de donnees");
        }
        return null;
    }

    @Override
    public Salle get(int id) {
        try (Connection conexion = PostgreSQLConnection.getConnection()) {
            String query = "SELECT * FROM salles WHERE id_salle = ?";
            Salle salle = null;
            try (PreparedStatement prep = conexion.prepareStatement(query)) {
                prep.setInt(1, id);
                try (ResultSet result = prep.executeQuery()) {
                    if (result.next()) {
                        salle = new Salle();
                        salle.setId(result.getInt("id_salle"));
                        salle.setNom(result.getString("nom_salle"));
                        salle.setCapacite(result.getInt("capacite"));
                    }
                    return salle;
                }catch(SQLException e){
                    e.printStackTrace();
                    System.out.println("Error: de sql moi ");
                }
            }catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Error: de connecxion a la base de donnees");
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("Error: de connecxion a la base de donnees");
        }
                return null;
        
    }
 
}
