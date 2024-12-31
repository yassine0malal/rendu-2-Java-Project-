package com.example.TestJunite;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.example.Models.User;
import com.example.transaction.TransactionManager;
import com.example.DAOImplementation.UserDAO;

import java.sql.Connection;

public class UserDAOTest {

    private static UserDAO userDAO = new UserDAO();
    private Connection connection;

    @BeforeEach
    public void setup() throws Exception {
        TransactionManager.beginTransaction();
        connection = TransactionManager.getCurrentConnection();
        userDAO.setConnection(connection); 
    }

    @AfterEach
    public void teardown() throws Exception {
        TransactionManager.rollback();
    }

    @Test
    public void testAjouterUser() {
        User user = new User("Dupont", "Jean", "jean.dupont@example.com", "ETUDIANT");
        user.setId(1);
        userDAO.ajouter(user); 

        User fetchedUser = userDAO.get(1); 
        assertNotNull(fetchedUser);
        assertEquals("Dupont", fetchedUser.getNom());
        assertEquals("Jean", fetchedUser.getPrenom());
        assertEquals("jean.dupont@example.com", fetchedUser.getEmail());
        assertEquals("ETUDIANT", fetchedUser.getTypeUser());
        assertEquals(1, fetchedUser.getId());
        // userDAO.supprimer(1);
    }

    @Test
    public void testAfficherUsers() {
        User user = new User("Martin", "Pierre", "pierre.martin@example.com", "ETUDIANT");
        user.setId(1);
        userDAO.ajouter(user); 

        var users = userDAO.afficher(); 
        assertNotNull(users);
        assertEquals(2, users.size());
        assertEquals("Martin", users.get(0).getNom());
    }

    @Test
    public void testUpdateUser() {
        User user = new User("Dupont", "Jean", "jean.dupont@example.com", "ETUDIANT");
        user.setId(1);
        userDAO.ajouter(user); 

        user.setNom("Updated");
        userDAO.update(user); 

        User updatedUser = userDAO.get(1); 
        assertNotNull(updatedUser);
        assertEquals("Updated", updatedUser.getNom());
    }

    @Test
    public void testSupprimerUser() {
        User user = new User("Dupont", "Jean", "jean.dupont@example.com", "admin");
        user.setId(1);
        userDAO.ajouter(user); 
        userDAO.supprimer(1); 
        User deletedUser = userDAO.get(1); 
        assertNull(deletedUser);
    }
}
