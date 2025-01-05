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
        User user = new User("Dupont", "Jean", "jean.dupont@example.czom", "ETUDIANT");
        user.setId(6);
        userDAO.ajouterTest(user); 

        User fetchedUser = userDAO.getByIdUser(6); 
        assertNotNull(fetchedUser);
        assertEquals("Dupont", fetchedUser.getNom());
        assertEquals("Jean", fetchedUser.getPrenom());
        assertEquals("jean.dupont@example.czom", fetchedUser.getEmail());
        assertEquals("ETUDIANT", fetchedUser.getTypeUser());
        assertEquals(6, fetchedUser.getId());
        // userDAO.supprimer(1);
    }

    @Test
    public void testAfficherUsers() {
        // User user = new User("Martin", "Pierre", "pierre.maretinwe@example.com", "ETUDIANT");
        // user.setId(6);
        // userDAO.ajouterTest(user); 

        var users = userDAO.afficher(); 
        assertNotNull(users);
        // assertEquals(2, users.size());
        assertEquals("sad", users.get(1).getNom());
    }

    @Test
    public void testUpdateUser() {
        User user = new User("Dupont", "Jean", "jean.dupontww@example.com", "ETUDIANT");
        user.setId(6);
        userDAO.ajouterTest(user); 

        user.setNom("Updated");
        userDAO.updateTest(user); 

        User updatedUser = userDAO.getByIdUser(6); 
        assertNotNull(updatedUser);
        assertEquals("Updated", updatedUser.getNom());
    }

    @Test
    public void testSupprimerUser() {
        User user = new User("Dupont", "Jean", "jean.dupontww@example.com", "PROFESSEUR");
        user.setId(6);
        userDAO.ajouter(user); 
        userDAO.supprimer(6); 
        User deletedUser = userDAO.get(6); 
        assertNull(deletedUser);
    }
}
