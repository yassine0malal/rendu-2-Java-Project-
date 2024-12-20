package com.example.DAOImplementation;

import com.example.Models.Terrain;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TerrainDAO {
    private Connection conexion;
    public void setConnection(Connection connexion) {
        this.conexion = connexion;
    }
    
    public TerrainDAO() {
    }
    public void ajouter(Terrain terrain){
        String query = "INSERT INTO terrains (nom_terrain, type) VALUES (?, ?)";
        try (PreparedStatement prep = conexion.prepareStatement(query)) {
            prep.setString(1, terrain.getNom());
            prep.setString(2, terrain.getType());
            int rowsInserted = prep.executeUpdate();
            if (rowsInserted > 0){
                System.out.println("New terrain added  successfully.");
            }
        }catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    public void supprimer(int id){
        String query = "DELETE FROM terrains WHERE id_terrain = ?";
        try (PreparedStatement prep = conexion.prepareStatement(query)) {
            prep.setInt(1, id);
            int rowsDeleted = prep.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Terrain deleted successfully.");
            } else {
                System.out.println("Terrain not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Terrain> afficher(){
        String  query = "SELECT * FROM terrains";
        ArrayList <Terrain> terrains = new ArrayList<>();
        try (PreparedStatement prep = conexion.prepareStatement(query)) {
            ResultSet result = prep.executeQuery();
            while (result.next()) {
                Terrain terrain = new Terrain();
                terrain.setId(result.getInt("id_terrain"));
                terrain.setNom(result.getString("nom_terrain"));
                terrain.setType(result.getString("type"));
                terrains.add(terrain);
                } 
                return terrains;
            }catch (SQLException e) {
            e.printStackTrace();
            return null;
            }
    } 

}

