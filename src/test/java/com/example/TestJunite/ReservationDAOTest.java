package com.example.TestJunite;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.example.Models.Reservation;
import com.example.DAOImplementation.ReservationDAO;
import com.example.transaction.TransactionManager;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

public class ReservationDAOTest {

    private static ReservationDAO reservationDAO = new ReservationDAO();
    private Connection connection;

    @BeforeEach
    public void setup() throws Exception {
        TransactionManager.beginTransaction();
        connection = TransactionManager.getCurrentConnection();
        reservationDAO.setConexion(connection); // Injecter la connexion dans ReservationDAO
    }

    @AfterEach
    public void teardown() throws Exception {
        TransactionManager.rollback();
    }

    @Test
    public void testAjouterReservation() {
        Reservation reservation = new Reservation();
        reservation.setId_user(69);
        reservation.setId_terrain(23);
        reservation.setId_event(52);
        reservation.setId_salle(227);
        reservation.setDate_reservation(Date.valueOf("2024-12-25"));

        reservationDAO.ajouter(reservation);

        List<Reservation> reservations = reservationDAO.afficher();
        assertNotNull(reservations);
        assertTrue(reservations.stream().anyMatch(r -> r.getDate_reservation().equals(Date.valueOf("2024-12-25"))
                && r.getId_salle() == 227));

        reservations.stream()
                .filter(r -> r.getDate_reservation().equals(Date.valueOf("2024-12-25")) && r.getId_salle() ==227)
                .findFirst()
                .ifPresent(r -> reservationDAO.supprimer(r.getId_reservation()));
    }

    @Test
    public void testAfficherReservations() {
        Reservation reservation = new Reservation();
        reservation.setId_user(69);
        reservation.setId_terrain(23);
        reservation.setId_event(52);
        reservation.setId_salle(227);
        reservation.setDate_reservation(Date.valueOf("2024-12-26"));

        reservationDAO.ajouter(reservation);

        List<Reservation> reservations = reservationDAO.afficher();
        assertNotNull(reservations);
        assertTrue(reservations.size() > 0);

        Reservation fetchedReservation = reservations.stream()
                .filter(r -> r.getDate_reservation().equals(Date.valueOf("2024-12-26")) && r.getId_salle() ==227)
                .findFirst()
                .orElse(null);

        assertNotNull(fetchedReservation);

        reservationDAO.supprimer(fetchedReservation.getId_reservation());
    }

    @Test
    public void testSupprimerReservation() {
        Reservation reservation = new Reservation();
        reservation.setId_user(69);
        reservation.setId_terrain(23);
        reservation.setId_event(52);
        reservation.setId_salle(227);
        reservation.setDate_reservation(Date.valueOf("2024-12-27"));

        reservationDAO.ajouter(reservation);

        List<Reservation> reservations = reservationDAO.afficher();
        Reservation existingReservation = reservations.stream()
                .filter(r -> r.getDate_reservation().equals(Date.valueOf("2024-12-27")) && r.getId_salle() ==227)
                .findFirst()
                .orElse(null);

        assertNotNull(existingReservation);

        reservationDAO.supprimer(existingReservation.getId_reservation());

        Reservation deletedReservation = reservationDAO.afficher().stream()
                .filter(r -> r.getDate_reservation().equals(Date.valueOf("2024-12-27")) && r.getId_salle() ==227)
                .findFirst()
                .orElse(null);

        assertNull(deletedReservation);
    }

    @Test
    public void testUpdateReservation() {
        Reservation reservation = new Reservation();
        reservation.setId_user(69);
        reservation.setId_terrain(23);
        reservation.setId_event(52);
        reservation.setId_salle(227);
        reservation.setDate_reservation(Date.valueOf("2024-12-28"));

        reservationDAO.ajouter(reservation);

        List<Reservation> reservations = reservationDAO.afficher();
        Reservation existingReservation = reservations.stream()
                .filter(r -> r.getDate_reservation().equals(Date.valueOf("2024-12-28")) && r.getId_salle() ==227)
                .findFirst()
                .orElse(null);

        assertNotNull(existingReservation);

        existingReservation.setDate_reservation(Date.valueOf("2024-12-29"));
        reservationDAO.update(existingReservation);

        Reservation updatedReservation = reservationDAO.get(existingReservation.getId_reservation());
        assertNotNull(updatedReservation);
        assertEquals(Date.valueOf("2024-12-29"), updatedReservation.getDate_reservation());

        reservationDAO.supprimer(updatedReservation.getId_reservation());
    }

    @Test
    public void testVerifierDisponible() {
        Reservation reservation = new Reservation();
        reservation.setId_user(69);
        reservation.setId_terrain(23);
        reservation.setId_event(52);
        reservation.setId_salle(227);
        reservation.setDate_reservation(Date.valueOf("2024-12-30"));

        reservationDAO.ajouter(reservation);

        int disponible = reservationDAO.verifierDisponible(
                Date.valueOf("2024-12-30"),
                227
        );

        assertEquals(0, disponible);

        List<Reservation> reservations = reservationDAO.afficher();
        reservations.stream()
                .filter(r -> r.getDate_reservation().equals(Date.valueOf("2024-12-30")) && r.getId_salle() ==227)
                .findFirst()
                .ifPresent(r -> reservationDAO.supprimer(r.getId_reservation()));
    }
}
