package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.example.Models.Terrain;
import com.example.DAOImplementation.TerrainDAO;

import java.util.List;

public class TerrainDAOTest {

    private static TerrainDAO terrainDAO = new TerrainDAO();

    @Test
    public void testAjouterTerrain() {
        Terrain terrain = new Terrain();
        terrain.setNom("Terrain de football");
        terrain.setType("Sport");

        // Ajouter un terrain
        terrainDAO.ajouter(terrain);

        // Vérifier que le terrain est bien ajouté
        List<Terrain> terrains = terrainDAO.afficher();
        assertNotNull(terrains);
        assertTrue(terrains.stream().anyMatch(t -> "Terrain de football".equals(t.getNom()) && "Sport".equals(t.getType())));

        // Nettoyage : supprimer le terrain ajouté
        terrains.stream()
                .filter(t -> "Terrain de football".equals(t.getNom()) && "Sport".equals(t.getType()))
                .findFirst()
                .ifPresent(t -> terrainDAO.supprimer(t.getId()));
    }

    @Test
    public void testAfficherTerrains() {
        Terrain terrain = new Terrain();
        terrain.setNom("Terrain de tennis");
        terrain.setType("Sport");

        // Ajouter un terrain
        terrainDAO.ajouter(terrain);

        // Afficher les terrains
        List<Terrain> terrains = terrainDAO.afficher();
        assertNotNull(terrains);
        assertTrue(terrains.size() > 0);

        // Vérifier la présence du terrain ajouté
        Terrain fetchedTerrain = terrains.stream()
                                          .filter(t -> "Terrain de tennis".equals(t.getNom()))
                                          .findFirst()
                                          .orElse(null);

        assertNotNull(fetchedTerrain);
        assertEquals("Terrain de tennis", fetchedTerrain.getNom());
        assertEquals("Sport", fetchedTerrain.getType());

        // Nettoyage : supprimer le terrain ajouté
        terrainDAO.supprimer(fetchedTerrain.getId());
    }

    @Test
    public void testSupprimerTerrain() {
        Terrain terrain = new Terrain();
        terrain.setNom("Terrain à supprimer");
        terrain.setType("Multi-usage");

        // Ajouter un terrain
        terrainDAO.ajouter(terrain);

        // Vérifier que le terrain est bien ajouté
        List<Terrain> terrains = terrainDAO.afficher();
        Terrain existingTerrain = terrains.stream()
                                          .filter(t -> "Terrain à supprimer".equals(t.getNom()))
                                          .findFirst()
                                          .orElse(null);

        assertNotNull(existingTerrain);

        // Supprimer le terrain
        terrainDAO.supprimer(existingTerrain.getId());

        // Vérifier que le terrain est bien supprimé
        Terrain deletedTerrain = terrainDAO.afficher().stream()
                                           .filter(t -> "Terrain à supprimer".equals(t.getNom()))
                                           .findFirst()
                                           .orElse(null);

        assertNull(deletedTerrain);
    }
}
