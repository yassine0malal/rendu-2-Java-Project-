package com.example.DAOImplementation;
import java.util.ArrayList;

public interface GenericDAO<T> {
    void ajouter(T entity); 
    void update(T entity);
    void supprimer(int id);

    T get (int id);
    ArrayList<T> afficher();
}
