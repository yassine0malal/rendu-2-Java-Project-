package com.example.Models;
public class User {
    private static int counter = 0; 
    private int id;
    private String nom;
    private String prenom;
    private String email;
    private String typeUser;
    
    public String getEmail() {
        return email;
    }
    public String getNom() {
        return nom;
    }public String getPrenom() {
        return prenom;
    }public String getTypeUser() {
        return typeUser;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    public void setTypeUser(String typeUser) {
        this.typeUser = typeUser;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public User(){
        id=++counter; 
    }
    
    public User(String nom, String prenom, String email, String typeUser){
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.typeUser = typeUser;
        id=++counter;
    }
}
