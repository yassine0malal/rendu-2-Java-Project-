package com.example.transaction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TransactionManager {
    private static ThreadLocal<Connection> connectionHolder = new ThreadLocal<>();

    // Initialisation d'une nouvelle transaction
    public static void beginTransaction() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Gestion_reservations", "postgres", "123");
        connection.setAutoCommit(false);
        connectionHolder.set(connection);
    }

    // Validation de la transaction
    public static void commit() throws SQLException {
        Connection connection = connectionHolder.get();
        if (connection != null) {
            connection.commit();
            closeConnection();
        }
    }

    // Annulation de la transaction
    public static void rollback() throws SQLException {
        Connection connection = connectionHolder.get();
        if (connection != null) {
            connection.rollback();
            closeConnection();
        }
    }

    // Fermeture de la connexion
    private static void closeConnection() throws SQLException {
        Connection connection = connectionHolder.get();
        if (connection != null) {
            connection.close();
            connectionHolder.remove();
        }
    }

    // Simplification avec une lambda
    public static void executeInTransaction(Runnable operation) {
        try {
            beginTransaction();
            operation.run();
            commit();
        } catch (Exception e) {
            try {
                rollback();
            } catch (SQLException rollbackException) {
                rollbackException.printStackTrace();
            }
            throw new RuntimeException(e);
        }
    }

    // Récupération de la connexion courante
    public static Connection getCurrentConnection() {
        return connectionHolder.get();
    }
}
