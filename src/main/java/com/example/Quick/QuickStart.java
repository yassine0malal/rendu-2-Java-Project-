package com.example.Quick;
import java.sql.Date;
import java.util.ArrayList;
import com.example.DAOImplementation.EvenementDAO;
import com.example.DAOImplementation.ReservationDAO;
import com.example.DAOImplementation.SalleDAO;
import com.example.DAOImplementation.TerrainDAO;
import com.example.DAOImplementation.UserDAO;
import com.example.Models.Evenement;
import com.example.Models.Reservation;
import com.example.Models.Salle;
import com.example.Models.Terrain;
import com.example.Models.User;

public class QuickStart {
    
        public static void main(String[] args) {
            
                UserDAO userDAO = new UserDAO();
                EvenementDAO eventDAO = new EvenementDAO();
                SalleDAO salleDAO = new SalleDAO();
                TerrainDAO terrainDAO = new TerrainDAO();
                ReservationDAO reservationDAO = new ReservationDAO();
    
                System.out.println("do you want to add someone ? press 1 if yes, press 2 if you wannt to see the list of users");
                System.out.println("entrer 4 pour ajouter un evenement");
                System.out.println("entrer 5 pour voir les evenement");
                System.out.println("entrer 6 pour supprimer un evenement");
                System.out.println("entrer 7 pour modifier un evenement");
                System.out.println("entrer 8 pour ajouter une salle ");
                System.out.println("entrer 9 pour voir les salles");
                System.out.println("entrer 10 pour supprimer une salle");
                System.out.println("entrer 11 pour modifier une salle");
                System.out.println("entrer 12 pour ajouter un terrain");
                System.out.println("entrer 13 pour voir les terrains");
                System.out.println("entrer 14 pour supprimer un terrain");
                System.out.println("entrer 15 pour reserver une reservation");
                System.out.println("entrer 16 pour voir les reservations");
                System.out.println("entrer 17 pour supprimer une reservation");
                System.out.println("entrer 18 pour modifier une reservation");
                System.out.println("entrer 19 pour verfier la  disponibilites d'une salle pour l'ai reservation");
                int choix = Integer.parseInt(System.console().readLine());
                    
                
                if (choix == 1) { 
                    System.out.println("entrer le nom de litilisateur ?");
                    String nom = System.console().readLine();
                    System.out.println("entrer le prenom de litilisateur ?");
                    String prenom = System.console().readLine();
                    System.out.println("entrer l'email de litilisateur ?");
                    String email = System.console().readLine();
                    System.out.println("entrer le type de litilisateur ?");
                    String typeUser = System.console().readLine();
                    User user = new User(nom, prenom, email, typeUser);
                    System.out.println(user.getEmail()+" "+user.getNom()+" "+user.getPrenom()+" "+user.getTypeUser()+" "+user.getId());
                    userDAO.ajouter(user);
                }else if (choix == 2) {
                    ArrayList <User> lisUser = userDAO.afficher();
                    for (User user : lisUser) {
                        System.out.println(user.getEmail()+" "+user.getNom()+" "+user.getPrenom()+" "+user.getTypeUser()+" "+user.getId());
                    }
                }else if (choix==3) {
                    System.out.println("entrer l'id de litilisateur  pour le supprimer ?");
                    int id = Integer.parseInt(System.console().readLine());
                    userDAO.supprimer(id);
                }
                else if (choix == 4) {
                    System.out.println("entrer le nom de l'evenement ?");
                    String nom =System.console().readLine();
                    System.out.println("entrer la date de l'evenement sous forme de yyyy-mm-dd ?");
                    String dateTime = System.console().readLine();
                    Date da =Date.valueOf(dateTime);
                    System.out.println("entrer la description de l'evenement ?");
                    String description = System.console().readLine();
                    System.out.println("entrer l'id de user ");
                    int id = Integer.parseInt(System.console().readLine());
                    Evenement event = new Evenement(nom, da, description,id);
                    eventDAO.ajouter(event);   
                }
                else if (choix ==5) {
                    ArrayList <Evenement> listEvent = eventDAO.afficher();
                    for (Evenement event : listEvent) {
                        System.out.println(event.getId()+" "+event.getNomEvent()+" "+event.getDate()+" "+event.getDescription()+" "+event.getId_user());
                    }
                    
                }else if (choix ==6) {
                    System.out.println("entrer l'id de l'evenement  pour le supprimer ?");
                    int id = Integer.parseInt(System.console().readLine());
                    eventDAO.supprimer(id);
                }
                else if (choix ==7) {
                    System.out.println("entrer l'id de l'evenement  pour le modifier ?");
                    int id = Integer.parseInt(System.console().readLine());
                    System.out.println("entrer le nom de l'evenement ?");
                    System.out.println("entrer la date de l'evenement ?");
                    System.out.println("entrer la description de l'evenement ?");
                    System.out.println("entrer l'id de user ");
                    String nom = System.console().readLine();
                    String dateTime = System.console().readLine();
                    Date da =Date.valueOf(dateTime);
                    String description = System.console().readLine();
                    int id_user = Integer.parseInt(System.console().readLine());
                    Evenement event = new Evenement(nom, da, description,id_user);
                    event.setId(id);
                    eventDAO.update(event);
                }
                else if (choix ==8) {
                    System.out.println("entrer le nom de la salle ?");
                    String nom = System.console().readLine();
                    System.out.println("entrer la capacite de la salle ?");
                    int capacite = Integer.parseInt(System.console().readLine());
                    Salle sall = new Salle(nom, capacite);
                    salleDAO.ajouter(sall);
                }
                else if (choix ==9) {
                    ArrayList <Salle> listSalle = salleDAO.afficher();
                    for (Salle salle : listSalle) {
                        System.out.println(salle.getId()+" "+salle.getNom()+" "+salle.getCapacite());
                    }
                }
                else if (choix ==10) {
                    System.out.println("entrer l'id de la salle  pour le sipprimer ?");
                    int id = Integer.parseInt(System.console().readLine());
                    salleDAO.supprimer(id);
                }
                else if (choix == 11) {
                    System.out.println("entrer l'id de la salle  pour le modifier ?");
                    int id = Integer.parseInt(System.console().readLine());
                    System.out.println("entrer le neuveau nom de la salle ?");
                    System.out.println("entrer le neuveau capacite de la salle ?");
                    String nom = System.console().readLine();
                    int capacite = Integer.parseInt(System.console().readLine());
                    Salle sall = new Salle(nom, capacite);
                    sall.setId(id);
                    salleDAO.update(sall);
                    
                }
                else if (choix == 12) {
                    System.out.println("entrer le nom de terrain ?");
                    String nom = System.console().readLine();
                    System.out.println("entrer la type de terrain ?");
                    String type = System.console().readLine();
                    Terrain terrain = new Terrain(nom, type);
                    terrainDAO.ajouter(terrain);
                }
                
                else if (choix==13) {
                    for(Terrain terrain : terrainDAO.afficher()) {
                        System.out.println(terrain.getId()+" "+terrain.getNom()+" "+terrain.getType());
                    }
                    
                }
                else if (choix ==14) {
                    System.out.println("entrer l'id de terrain  pour le sipprimer ?");
                    int id = Integer.parseInt(System.console().readLine());
                    terrainDAO.supprimer(id);                
                }
                else if (choix ==15) {
                    System.out.println("entrer la date de la reservation ?");
                    String date = System.console().readLine();
                    System.out.println("entrer l'id de user ");
                    System.out.println("entrer l'id de terrain ");
                    System.out.println("entrer l'id de salle ");
                    System.out.println("entrer l'id de event ");
                    int id_user = Integer.parseInt(System.console().readLine());
                    int id_terrain = Integer.parseInt(System.console().readLine());
                    int id_salle = Integer.parseInt(System.console().readLine());
                    int id_event = Integer.parseInt(System.console().readLine());
                    Date da =Date.valueOf(date);
                    
                    Reservation reservation = new Reservation(id_user, id_terrain, id_salle, id_event, da);
    
                    reservationDAO.ajouter(reservation);
                }
                else if (choix ==16) {
                    ArrayList <Reservation> listRes = reservationDAO.afficher();
                    for (Reservation reservation : listRes) {
                        System.out.println(reservation.getId_reservation()+" "+reservation.getId_user()+" "+reservation.getId_terrain()+" "+reservation.getId_salle()+" "+reservation.getId_event()+" "+reservation.getDate_reservation());
                    }
                }
                else if (choix ==17) {
                    System.out.println("entrer l'id de reservation  pour le sipprimer ?");
                    int id = Integer.parseInt(System.console().readLine());
                    reservationDAO.supprimer(id);
                }
                else if (choix == 18) {
                    System.out.println("entrer l'id de reservation  pour le modifier ?");
                    int id = Integer.parseInt(System.console().readLine());
                    System.out.println("entrer l'id de user ");
                    System.out.println("entrer l'id de terrain ");
                    System.out.println("entrer l'id de event ");
                    System.out.println("entrer l'id de salle ");
                    System.out.println("entrer la date de la reservation ?");
                    int id_user = Integer.parseInt(System.console().readLine());
                    int id_terrain = Integer.parseInt(System.console().readLine());
                    int id_event = Integer.parseInt(System.console().readLine());
                    int id_salle = Integer.parseInt(System.console().readLine());
                    String date = System.console().readLine();
                    Date da =Date.valueOf(date);
                    Reservation reservation = new Reservation(id_user, id_terrain, id_salle, id_event, da);
                    reservation.setId_reservation(id);
                    reservationDAO.update(reservation);
                }else if (choix==19) {
                    System.out.println("entrer l'id de reservation  pour le reccuperer ?");
                    int id = Integer.parseInt(System.console().readLine());
                   Reservation res= reservationDAO.get(id);
                   System.out.println(res.getId_reservation()+" "+res.getId_user()+" "+res.getId_terrain()+" "+res.getId_salle()+" "+res.getId_event()+" "+res.getDate_reservation());
                    
                }
    
                // else if (choix == 19) {
                //     System.out.println("entrer une date  pour voir l'ai tester reservations ?");
                //     String date = System.console().readLine();
                //     System.out.println("entrer l'id salle qui vous shuiter pour l'ai reserver ?");
                //     int id_salle = Integer.parseInt(System.console().readLine());  
                //     reservationDAO.verifierDisponible();
                // }
        
    }
    }
    



