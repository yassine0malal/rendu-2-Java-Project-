package com.example.TestJunite;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.example.Models.Salle;
import com.example.transaction.TransactionManager;
import com.example.DAOImplementation.SalleDAO;

import java.sql.Connection;
import java.util.List;

public class SalleDAOTest {

    private static SalleDAO salleDAO = new SalleDAO();
    private Connection connection;

    @BeforeEach
    public void setup() throws Exception {
        TransactionManager.beginTransaction();
        connection = TransactionManager.getCurrentConnection();
        salleDAO.setConexion(connection); 
    }

    @AfterEach
    public void teardown() throws Exception {
        TransactionManager.rollback();
    }

    @Test
    public void testAjouterSalle() {
        Salle salle = new Salle();
        salle.setNom("Salle de conférence");
        salle.setCapacite(50);

        salleDAO.ajouter(salle); 

        List<Salle> salles = salleDAO.afficher();
        assertNotNull(salles);
        assertTrue(salles.stream().anyMatch(s -> "Salle de conférence".equals(s.getNom()) && s.getCapacite() == 50));

    }

    @Test
    public void testAfficherSalles() {
        Salle salle = new Salle();
        salle.setNom("Salle de réunion");
        salle.setCapacite(30);

        salleDAO.ajouter(salle); 

        List<Salle> salles = salleDAO.afficher(); 
        assertNotNull(salles);
        assertTrue(salles.size() > 0);

        Salle fetchedSalle = salles.stream()
                                   .filter(s -> "Salle de réunion".equals(s.getNom()))
                                   .findFirst()
                                   .orElse(null);

        assertNotNull(fetchedSalle);
        assertEquals("Salle de réunion", fetchedSalle.getNom());
        assertEquals(30, fetchedSalle.getCapacite());

        salleDAO.supprimer(fetchedSalle.getId());
    }

    @Test
    public void testUpdateSalle() {
        Salle salle = new Salle();
        salle.setNom("Petite salle");
        salle.setCapacite(10);

        salleDAO.ajouter(salle); 

        List<Salle> salles = salleDAO.afficher();
        Salle existingSalle = salles.stream()
                                    .filter(s -> "Petite salle".equals(s.getNom()))
                                    .findFirst()
                                    .orElse(null);

        assertNotNull(existingSalle);

        existingSalle.setNom("Salle mise à jour");
        existingSalle.setCapacite(20);
        salleDAO.update(existingSalle);

        Salle updatedSalle = salleDAO.get(existingSalle.getId());
        assertNotNull(updatedSalle);
        assertEquals("Salle mise à jour", updatedSalle.getNom());
        assertEquals(20, updatedSalle.getCapacite());

        salleDAO.supprimer(updatedSalle.getId());
    }

    @Test
    public void testGetSalle() {
        Salle salle = new Salle();
        salle.setNom("Salle spécifique");
        salle.setCapacite(40);

        salleDAO.ajouter(salle); 

        List<Salle> salles = salleDAO.afficher();
        Salle existingSalle = salles.stream()
                                    .filter(s -> "Salle spécifique".equals(s.getNom()))
                                    .findFirst()
                                    .orElse(null);

        assertNotNull(existingSalle);

        Salle fetchedSalle = salleDAO.get(existingSalle.getId());
        assertNotNull(fetchedSalle);
        assertEquals("Salle spécifique", fetchedSalle.getNom());
        assertEquals(40, fetchedSalle.getCapacite());

        salleDAO.supprimer(fetchedSalle.getId());
    }

    @Test
    public void testSupprimerSalle() {
        Salle salle = new Salle();
        salle.setNom("Salle à supprimer");
        salle.setCapacite(15);

        salleDAO.ajouter(salle); 

        List<Salle> salles = salleDAO.afficher();
        Salle existingSalle = salles.stream()
                                    .filter(s -> "Salle à supprimer".equals(s.getNom()))
                                    .findFirst()
                                    .orElse(null);

        assertNotNull(existingSalle);

        salleDAO.supprimer(existingSalle.getId()); 
        Salle deletedSalle = salleDAO.get(existingSalle.getId()); 
        assertNull(deletedSalle);
    }
}
