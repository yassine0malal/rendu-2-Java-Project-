package com.example.DAOImplementation;
import com.example.PostgreSQLConnection;
import com.example.Models.Reservation;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ReservationDAO implements GenericDAO<Reservation> {
    // Connection connexion = PostgreSQLConnection.getConnection();
    public ReservationDAO() {
    }

    @Override
    public void ajouter(Reservation reservation) {
        try (Connection conexion = PostgreSQLConnection.getConnection()) {
            if (this.verifierDisponible(reservation.getDate_reservation(), reservation.getId_salle()) == 0) {
                System.out.println("La salle n'est pas disponible pour cette date");
                return;
            }
            String query = "INSERT INTO reservations (id_user, id_terrain, id_event, id_salle, date_reservation) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement prep = conexion.prepareStatement(query)) {
                prep.setInt(1, reservation.getId_user());
                prep.setInt(2, reservation.getId_terrain());
                prep.setInt(3, reservation.getId_event());
                prep.setInt(4, reservation.getId_salle());
                prep.setDate(5, reservation.getDate_reservation());
                int rowsInserted = prep.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("New reservation added to a user successfully.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("Error connecting to the database.");
        }
    }

    public int verifierDisponible( Date date, int id_salle) {
        String query = "SELECT * FROM reservations WHERE date_reservation = ? AND id_salle = ?";
        try (PreparedStatement prep = PostgreSQLConnection.getConnection().prepareStatement(query)) {
            prep.setDate(1, date);
            prep.setInt(2, id_salle);
            ResultSet result = prep.executeQuery();
            if (result.next()) {
                System.out.println("La salle est deja reserve pour cette date");
                return 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("La salle est disponible pour cette date");
        return 1;

    }

    @Override
    public ArrayList<Reservation> afficher() {
        try (Connection conexion = PostgreSQLConnection.getConnection()) {
            String query = "SELECT * FROM reservations";
            ArrayList<Reservation> reservations = new ArrayList<>();
            try (PreparedStatement prep = conexion.prepareStatement(query)) {
                ResultSet result = prep.executeQuery();
                while (result.next()) {
                    Reservation reservation = new Reservation();
                    reservation.setId_reservation(result.getInt("id_reservation"));
                    reservation.setId_user(result.getInt("id_user"));
                    reservation.setId_terrain(result.getInt("id_terrain"));
                    reservation.setId_event(result.getInt("id_event"));
                    reservation.setId_salle(result.getInt("id_salle"));
                    reservation.setDate_reservation(result.getDate("date_reservation"));
                    reservations.add(reservation);
                }
                return reservations;
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Error executing the query.");
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("error de connexion !");
        }   
        return null;
    }

    @Override
    public void supprimer(int id) {
        try (Connection conexion = PostgreSQLConnection.getConnection()) {
            String query = "DELETE FROM reservations WHERE id_reservation = ?";
            try (PreparedStatement prep = conexion.prepareStatement(query)) {
                prep.setInt(1, id);
                int rowsDeleted = prep.executeUpdate();
                if (rowsDeleted > 0) {
                    System.out.println("Event deleted successfully.");
                } else {
                    System.out.println("Event not found.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Error deleting event.");
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("Error connecting to the database.");
        }

    }

    @Override
    public void update(Reservation reservation) {
        try (Connection conexion = PostgreSQLConnection.getConnection()) {
            String query = "UPDATE reservations SET id_user = ?, id_terrain = ?, id_event = ?, id_salle = ?, date_reservation = ? WHERE id_reservation = ?";
            try (PreparedStatement prep = conexion.prepareStatement(query)) {
                prep.setInt(1, reservation.getId_user());
                prep.setInt(2, reservation.getId_terrain());
                prep.setInt(3, reservation.getId_event());
                prep.setInt(4, reservation.getId_salle());
                prep.setDate(5, reservation.getDate_reservation());
                prep.setInt(6, reservation.getId_reservation());
                int rowsUpdated = prep.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println("reservation updated successfully.");
                } else {
                    System.out.println("reservatoin not found.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("Error connecting to the database.");
        }
    }

    @Override
    public Reservation get(int id){
        try (Connection connection = PostgreSQLConnection.getConnection()) {
            String query = "SELECT * FROM reservations WHERE id_reservation = ?";
            try (PreparedStatement prep = connection.prepareStatement(query)) {
                prep.setInt(1, id);
                ResultSet result = prep.executeQuery();
                if (result.next()){
                    Reservation reservation = new Reservation();
                    reservation.setId_reservation(result.getInt("id_reservation"));
                    reservation.setId_user(result.getInt("id_user"));
                    reservation.setId_terrain(result.getInt("id_terrain"));
                    reservation.setId_event(result.getInt("id_event"));
                    reservation.setId_salle(result.getInt("id_salle"));
                    reservation.setDate_reservation(result.getDate("date_reservation"));
                    return reservation;
                }
   }catch(SQLException e){
            e.printStackTrace();
            
   }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
}


}