package com.example.Models;
public class Terrain {
    private int count = 0;
    private int id;
    private String nom;
    private String type;

    public int getId() {
        return id;
    }
    public String getNom() {
        return nom;
    }
    public String getType() {
        return type;
    }
    
    public void setNom(String nom) {
        this.nom = nom;
    }
    public void setType(String type) {
        this.type = type;
    }
    public void setId(int id) {
        this.id = id;
    }

public Terrain(){
        id=++count;
    }
public Terrain(String nom, String type) {
        this.nom = nom;
        this.type = type;
    }

}
