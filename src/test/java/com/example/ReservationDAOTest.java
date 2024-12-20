package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.example.Models.Reservation;
import com.example.DAOImplementation.ReservationDAO;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class ReservationDAOTest {

    private static ReservationDAO reservationDAO = new ReservationDAO();

    @Test
    public void testAjouterReservation() {
        Reservation reservation = new Reservation();
        reservation.setId_user(34);
        reservation.setId_terrain(7);
        reservation.setId_event(13);
        reservation.setId_salle(6);
        reservation.setDate_reservation(Date.valueOf("2024-12-25"));

        // Ajouter une réservation
        reservationDAO.ajouter(reservation);

        // Vérifier que la réservation est bien ajoutée
        List<Reservation> reservations = reservationDAO.afficher();
        assertNotNull(reservations);
        assertTrue(reservations.stream().anyMatch(r -> r.getDate_reservation().equals(Date.valueOf("2024-12-25"))
                && r.getId_salle() == 6));

        // Nettoyage : supprimer la réservation ajoutée
        reservations.stream()
                .filter(r -> r.getDate_reservation().equals(Date.valueOf("2024-12-25")) && r.getId_salle() == 4)
                .findFirst()
                .ifPresent(r -> reservationDAO.supprimer(r.getId_reservation()));
    }

    @Test
    public void testAfficherReservations() {
        Reservation reservation = new Reservation();
        reservation.setId_user(34);
        reservation.setId_terrain(7);
        reservation.setId_event(13);
        reservation.setId_salle(6);
        reservation.setDate_reservation(Date.valueOf("2024-12-26"));

        // Ajouter une réservation
        reservationDAO.ajouter(reservation);

        // Afficher les réservations
        ArrayList<Reservation> reservations = reservationDAO.afficher();
        assertNotNull(reservations);
        assertTrue(reservations.size() > 0);

        // Vérifier la présence de la réservation ajoutée
        Reservation fetchedReservation = reservations.stream()
                .filter(r -> r.getDate_reservation().equals(Date.valueOf("2024-12-26")) && r.getId_salle() == 6)
                .findFirst()
                .orElse(null);

        assertNotNull(fetchedReservation);

        // Nettoyage : supprimer la réservation ajoutée
        reservationDAO.supprimer(fetchedReservation.getId_reservation());
    }

    @Test
    public void testSupprimerReservation() {
        Reservation reservation = new Reservation();
        reservation.setId_user(34);
        reservation.setId_terrain(7);
        reservation.setId_event(15);
        reservation.setId_salle(6);
        reservation.setDate_reservation(Date.valueOf("2024-12-27"));

        // Ajouter une réservation
        reservationDAO.ajouter(reservation);

        // Vérifier qu'elle est bien ajoutée
        List<Reservation> reservations = reservationDAO.afficher();
        Reservation existingReservation = reservations.stream()
                .filter(r -> r.getDate_reservation().equals(Date.valueOf("2024-12-27")) && r.getId_salle() == 6)
                .findFirst()
                .orElse(null);

        assertNotNull(existingReservation);

        // Supprimer la réservation
        reservationDAO.supprimer(existingReservation.getId_reservation());

        // Vérifier qu'elle est bien supprimée
        Reservation deletedReservation = reservationDAO.afficher().stream()
                .filter(r -> r.getDate_reservation().equals(Date.valueOf("2024-12-27")) && r.getId_salle() == 6)
                .findFirst()
                .orElse(null);

        assertNull(deletedReservation);
    }

    @Test
    public void testUpdateReservation() {
        Reservation reservation = new Reservation();
        reservation.setId_user(34);
        reservation.setId_terrain(7);
        reservation.setId_event(15);
        reservation.setId_salle(6);
        reservation.setDate_reservation(Date.valueOf("2024-12-28"));

        // Ajouter une réservation
        reservationDAO.ajouter(reservation);

        // Récupérer la réservation ajoutée
        List<Reservation> reservations = reservationDAO.afficher();
        Reservation existingReservation = reservations.stream()
                .filter(r -> r.getDate_reservation().equals(Date.valueOf("2024-12-28")) && r.getId_salle() == 6)
                .findFirst()
                .orElse(null);

        assertNotNull(existingReservation);

        // Mettre à jour la réservation
        existingReservation.setDate_reservation(Date.valueOf("2024-12-29"));
        reservationDAO.update(existingReservation);

        // Vérifier la mise à jour
        Reservation updatedReservation = reservationDAO.get(existingReservation.getId_reservation());
        assertNotNull(updatedReservation);
        assertEquals(Date.valueOf("2024-12-29"), updatedReservation.getDate_reservation());

        // Nettoyage : supprimer la réservation
        reservationDAO.supprimer(updatedReservation.getId_reservation());
    }

    @Test
    public void testVerifierDisponible() {
        Reservation reservation = new Reservation();
        reservation.setId_user(34);
        reservation.setId_terrain(7);
        reservation.setId_event(15);
        reservation.setId_salle(6);
        reservation.setDate_reservation(Date.valueOf("2024-12-30"));

        // Ajouter une réservation
        reservationDAO.ajouter(reservation);

        // Vérifier qu'une réservation est déjà présente pour cette salle et cette date
        int disponible = reservationDAO.verifierDisponible(
                Date.valueOf("2024-12-30"),
                6
        );

        assertEquals(0, disponible);

        // Nettoyage : supprimer la réservation
        List<Reservation> reservations = reservationDAO.afficher();
        reservations.stream()
                .filter(r -> r.getDate_reservation().equals(Date.valueOf("2024-12-30")) && r.getId_salle() == 6)
                .findFirst()
                .ifPresent(r -> reservationDAO.supprimer(r.getId_reservation()));
    }
}
