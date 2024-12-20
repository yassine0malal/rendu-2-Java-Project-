package com.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.example.Models.Evenement;
import com.example.DAOImplementation.EvenementDAO;
import com.example.transaction.TransactionManager;

import java.sql.Connection;
import java.sql.Date;

public class EvenementDAOTest {

    private static EvenementDAO evenementDAO = new EvenementDAO();
    private Connection connection;

    @BeforeEach
    public void setup() throws Exception {
        TransactionManager.beginTransaction();
        connection = TransactionManager.getCurrentConnection();
        evenementDAO.setConnection(connection); 
    }

    @AfterEach
    public void teardown() throws Exception {
        TransactionManager.rollback();
    }

    @Test
    public void testAjouterEvenement() {
        Evenement event = new Evenement("Reunion", Date.valueOf("2024-01-01"), "Reunion annuelle", 69);
        event.setId(1);
        evenementDAO.ajouter(event); 

        Evenement fetchedEvent = evenementDAO.get(1);
        assertNotNull(fetchedEvent);
        assertEquals("Reunion", fetchedEvent.getNomEvent());
        assertEquals(Date.valueOf("2024-01-01"), fetchedEvent.getDate());
        assertEquals("Reunion annuelle", fetchedEvent.getDescription());
        assertEquals(69, fetchedEvent.getId_user());
    }

    @Test
    public void testAfficherEvenements() {
        Evenement event = new Evenement("Séminaire", Date.valueOf("2024-02-15"), "Séminaire sur l'innovation", 69);
        event.setId(2);
        evenementDAO.ajouter(event); 

        var events = evenementDAO.afficher(); 
        assertNotNull(events);
        assertTrue(events.size() > 0);

        Evenement fetchedEvent = events.get(0);
        assertEquals("Séminaire", fetchedEvent.getNomEvent());
        assertEquals(Date.valueOf("2024-02-15"), fetchedEvent.getDate());
        assertEquals("Séminaire sur l'innovation", fetchedEvent.getDescription());
    }

    @Test
    public void testUpdateEvenement() {
        Evenement event = new Evenement("Conférence", Date.valueOf("2024-03-10"), "Conférence sur l'IA", 69);
        event.setId(3);
        evenementDAO.ajouter(event); 

        event.setNomEvent("Conférence mise à jour");
        event.setDate(Date.valueOf("2024-03-20"));
        event.setDescription("Conférence mise à jour sur l'IA");
        evenementDAO.update(event); 

        Evenement updatedEvent = evenementDAO.get(3); 
        assertNotNull(updatedEvent);
        assertEquals("Conférence mise à jour", updatedEvent.getNomEvent());
        assertEquals(Date.valueOf("2024-03-20"), updatedEvent.getDate());
        assertEquals("Conférence mise à jour sur l'IA", updatedEvent.getDescription());
    }

    @Test
    public void testSupprimerEvenement() {
        Evenement event = new Evenement("Atelier", Date.valueOf("2024-04-05"), "Atelier sur le développement durable", 1);
        event.setId(4);
        evenementDAO.ajouter(event); 

        evenementDAO.supprimer(4); 
        Evenement deletedEvent = evenementDAO.get(4); 
        assertNull(deletedEvent);
    }
}
