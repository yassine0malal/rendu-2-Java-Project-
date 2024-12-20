package com.example.transaction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TransactionManager {
    private static Connection connection;

    public static void beginTransaction() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/Gestion_reservations", 
                "postgres", 
                "123"
            );
            connection.setAutoCommit(false);
        } else {
            throw new IllegalStateException("Transaction already started");
        }
    }

    public static void commit() throws SQLException {
        if (connection != null) {
            connection.commit();
            closeConnection();
        } else {
            throw new IllegalStateException("No active transaction to commit");
        }
    }

    public static void rollback() throws SQLException {
        if (connection != null) {
            connection.rollback();
            closeConnection();
        } else {
            throw new IllegalStateException("No active transaction to rollback");
        }
    }

    private static void closeConnection() throws SQLException {
        if (connection != null) {
            connection.close();
            connection = null;
        }
    }

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

    public static Connection getCurrentConnection() {
        return connection;
    }
}
