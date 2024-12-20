package com.example.Models;
public class Salle {
    private int count = 0;
    private int id;
    private String nom;
    private int capacite;
    
    public int getId() {
        return id;
    }
    public String getNom() {
        return nom;
    }
    public int getCapacite() {
        return capacite;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
    public void setCapacite(int capacite) {
        this.capacite = capacite;
    }
    public void setId(int id) {
        this.id = id;
    }

    public Salle(){
        id=++count;
    }
    public Salle(String nom, int capacite) {
        this.nom = nom;
        this.capacite = capacite;
    }

}
