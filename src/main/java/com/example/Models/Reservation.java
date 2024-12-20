package com.example.Models;
import java.sql.Date;

public class Reservation {
    private static int counter = 0;
    private int id_reservation;
    private int id_user;
    private int id_terrain;
    private int id_event;
    private int id_salle;
    private Date date_reservation;
    
    public Date getDate_reservation() {
        return date_reservation;
    }
    public int getId_event() {
        return id_event;
    }
    public int getId_salle() {
        return id_salle;
    }
    public int getId_terrain() {
        return id_terrain;
    }
    public int getId_user() {
        return id_user;
    }
    public int getId_reservation() {
        return id_reservation;
    }

    public void setDate_reservation(Date date_reservation) {
        this.date_reservation = date_reservation;
    }
    public void setId_event(int id_event) {
        this.id_event = id_event;
    }
    public void setId_salle(int id_salle) {
        this.id_salle = id_salle;
    }
    public void setId_terrain(int id_terrain) {
        this.id_terrain = id_terrain;
    }
    public void setId_user(int id_user) {
        this.id_user = id_user;
    }
    public Reservation(){
    id_reservation = ++counter;
    }
    public void setId_reservation(int id_reservation) {
        this.id_reservation = id_reservation;
    }

    public Reservation(int id_user, int id_terrain, int id_salle, int id_event, Date date_reservation) {
        this.id_user = id_user;
        this.id_terrain = id_terrain;
        this.id_salle = id_salle;
        this.id_event = id_event;
        this.date_reservation = date_reservation;
    }

}
