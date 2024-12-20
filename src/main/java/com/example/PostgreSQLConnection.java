package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgreSQLConnection {

    private static final String URL = "jdbc:postgresql://localhost:5432/Gestion_reservations";
    private static final String USER = "postgres";
    private static final String PASSWORD = "123";
    private static Connection connection = null;

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connection to PostgreSQL database established!");
        }
        return connection;
    }

    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Connection closed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void setConnectionDetails(String jdbcUrl, String username, String password2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setConnectionDetails'");
    }
}