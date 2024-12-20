package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.example.Models.User;
import com.example.transaction.TransactionManager;
import com.example.DAOImplementation.UserDAO;

public class UserDAOTest {

    private static UserDAO userDAO = new UserDAO();



    @Test
    public void testAjouterUser() {
        // TransactionManager.executeInTransaction(() -> {

       
        User user = new User("Dupont", "Jean", "jean.dupont@example.com", "ETUDIANT");
        user.setId(2);
        userDAO.ajouter(user);

        User fetchedUser = userDAO.get(2);
        assertNotNull(fetchedUser);
        assertEquals("Dupont", fetchedUser.getNom());
        assertEquals("Jean", fetchedUser.getPrenom());
        assertEquals("jean.dupont@example.com", fetchedUser.getEmail());
        assertEquals("ETUDIANT", fetchedUser.getTypeUser());
        assertEquals(2, fetchedUser.getId());
        // userDAO.supprimer(2);
    // });
    }

    @Test
    public void testAfficherUsers() {
        User user = new User("Martin", "Pierre", "pierre.martin@example.com", "ETUDIANT");
        user.setId(1);
        userDAO.ajouter(user);
        var users = userDAO.afficher();
        assertNotNull(users);
        assertEquals(1, users.size());
        assertEquals("Martin", users.get(0).getNom());
        userDAO.supprimer(1);
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
        userDAO.supprimer(1);
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
