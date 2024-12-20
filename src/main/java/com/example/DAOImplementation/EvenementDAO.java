package com.example.DAOImplementation;
import com.example.Models.Evenement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EvenementDAO implements GenericDAO<Evenement> {
private Connection conexion;
    public EvenementDAO() {
    }

    public void setConnection(Connection connexion) {
        this.conexion = connexion;
    }

    @Override
    public void ajouter(Evenement evenement) {

            String query = "INSERT INTO evenements (nom, date_event, description, id_user,id) VALUES (?,?, ?, ?,?)";
            try (PreparedStatement prep = conexion.prepareStatement(query)) {
                prep.setString(1, evenement.getNomEvent());
                prep.setDate(2, evenement.getDate());
                prep.setString(3, evenement.getDescription());
                prep.setInt(4, evenement.getId_user());
                prep.setInt(5, evenement.getId());
                // prep.setInt(6, 12);
                int rowsInserted = prep.executeUpdate();

                if (rowsInserted > 0){
                    System.out.println("New event added to a user successfully.");
                }
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
                System.out.println("ggff");
            }
    }

    @Override
    public ArrayList<Evenement> afficher() {
            String query = "SELECT * FROM evenements";
            ArrayList<Evenement> evenements = new ArrayList<>();
            try (PreparedStatement prep = conexion.prepareStatement(query)) {
                try (ResultSet result = prep.executeQuery()) {
                    while (result.next()) {
                        Evenement evenement = new Evenement();
                        evenement.setId(result.getInt("id_event"));
                        evenement.setId_user(result.getInt("id_user"));
                        evenement.setNomEvent(result.getString("nom"));
                        evenement.setDate(result.getDate("date_event"));
                        evenement.setDescription(result.getString("description"));
                        evenement.setId(result.getInt("id"));
                        evenements.add(evenement);
                    }
                    return evenements;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                
            }
        return null;
    }

    @Override
    public void supprimer(int id) {
            String query = "DELETE FROM evenements WHERE id = ?";
            try (PreparedStatement prep = conexion.prepareStatement(query)) {
                prep.setInt(1, id);
                int rowsDeleted = prep.executeUpdate();
                if (rowsDeleted > 0) {
                    System.out.println("Event deleted successfully.");
                } else {
                    System.out.println("No event found with the given ID.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }


    @Override
    public void update(Evenement evenement) {
        

            String query = "UPDATE evenements SET nom = ?, date_event = ?, description = ?, id_user = ? WHERE id = ?";
            try (PreparedStatement prep = conexion.prepareStatement(query)) {
                prep.setString(1, evenement.getNomEvent());
                prep.setDate(2, evenement.getDate());
                prep.setString(3, evenement.getDescription());
                prep.setInt(4, evenement.getId_user());
                prep.setInt(5, evenement.getId());
                int rowsUpdated = prep.executeUpdate();

                if (rowsUpdated > 0) {
                    System.out.println("Event updated successfully.");
                } else {
                    System.out.println("No event found with the given ID.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }

    @Override
    public Evenement get(int id) {
            String query = "SELECT * FROM evenements WHERE id = ?";
            try (PreparedStatement prep = conexion.prepareStatement(query)) {
                prep.setInt(1, id);
                try (ResultSet result = prep.executeQuery()) {
                    if (result.next()) {
                        Evenement evenement = new Evenement();
                        evenement.setId_user(result.getInt("id_user"));
                        evenement.setNomEvent(result.getString("nom"));
                        evenement.setDate(result.getDate("date_event"));
                        evenement.setDescription(result.getString("description"));
                        evenement.setId(result.getInt("id"));
                        return evenement;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("connection failed");
            }
        return null;
    }

}
