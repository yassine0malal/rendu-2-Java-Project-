package com.example.TestJunite;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.example.Models.Terrain;
import com.example.transaction.TransactionManager;
import com.example.DAOImplementation.TerrainDAO;

import java.sql.Connection;
import java.util.List;

public class TerrainDAOTest {

    private static TerrainDAO terrainDAO = new TerrainDAO();
    private Connection connection;

    @BeforeEach
    public void setup() throws Exception {
        TransactionManager.beginTransaction();
        connection = TransactionManager.getCurrentConnection();
        terrainDAO.setConnection(connection); 
    }

    @AfterEach
    public void teardown() throws Exception {
        TransactionManager.rollback();
    }

    @Test
    public void testAjouterTerrain() {
        Terrain terrain = new Terrain();
        terrain.setNom("Terrain de football");
        terrain.setType("Sport");

        terrainDAO.ajouter(terrain);

        List<Terrain> terrains = terrainDAO.afficher();
        assertNotNull(terrains);
        assertTrue(terrains.stream().anyMatch(t -> "Terrain de football".equals(t.getNom()) && "Sport".equals(t.getType())));

    }

    @Test
    public void testAfficherTerrains() {
        Terrain terrain = new Terrain();
        terrain.setNom("Terrain de tennis");
        terrain.setType("Sport");

        terrainDAO.ajouter(terrain);

        List<Terrain> terrains = terrainDAO.afficher();
        assertNotNull(terrains);
        assertTrue(terrains.size() > 0);

        Terrain fetchedTerrain = terrains.stream()
                                          .filter(t -> "Terrain de tennis".equals(t.getNom()))
                                          .findFirst()
                                          .orElse(null);

        assertNotNull(fetchedTerrain);
        assertEquals("Terrain de tennis", fetchedTerrain.getNom());
        assertEquals("Sport", fetchedTerrain.getType());

        terrainDAO.supprimer(fetchedTerrain.getId());
    }

    @Test
    public void testSupprimerTerrain() {
        Terrain terrain = new Terrain();
        terrain.setNom("Terrain à supprimer");
        terrain.setType("Multi-usage");

        terrainDAO.ajouter(terrain);

        List<Terrain> terrains = terrainDAO.afficher();
        Terrain existingTerrain = terrains.stream()
                                          .filter(t -> "Terrain à supprimer".equals(t.getNom()))
                                          .findFirst()
                                          .orElse(null);

        assertNotNull(existingTerrain);

        terrainDAO.supprimer(existingTerrain.getId());

        Terrain deletedTerrain = terrainDAO.afficher().stream()
                                           .filter(t -> "Terrain à supprimer".equals(t.getNom()))
                                           .findFirst()
                                           .orElse(null);

        assertNull(deletedTerrain);
    }
}
