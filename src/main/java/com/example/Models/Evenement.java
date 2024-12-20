package com.example.Models;
import java.sql.Date;

public class Evenement {
    
    private int id;
    private String nomEvent;
    private Date date;
    private String description;
    private int id_user;

   public Date getDate() {
       return date;
   }
    public String getDescription() {
        return description;
    }
    public int getId_user() {
        return id_user;
    }
    public String getNomEvent() {
        return nomEvent;
    }
    public int getId() {
        return id;
    }

   public void setDate(Date date) {
       this.date = date;
   }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setId_user(int id_user) {
        this.id_user = id_user;
    }
    public void setNomEvent(String nomEvent) {
        this.nomEvent = nomEvent;
    }
    public void setId(int id) {
        this.id = id;
    }
    public Evenement(){}
    public  Evenement(String nomEvent, Date date, String description, int id_user) {
        this.nomEvent = nomEvent;
        this.date = date;
        this.description = description;
        this.id_user = id_user;
    }

}
