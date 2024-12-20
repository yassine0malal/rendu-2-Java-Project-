package com.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args)throws SQLException {
        Connection conn = PostgreSQLConnection.getConnection();
        if(conn != null && !conn.isClosed()){
            System.out.println("Connexion ok");
            System.out.println("dsjb");
            String sql = "SELECT * FROM utilisateurs";
            try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                preparedStatement.executeQuery();
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String username = resultSet.getString("nom");
                    String password = resultSet.getString("prenom");;
                    System.out.println("id: " + id + ", username: " + username + ", password: " + password);
                }
                

            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("erreur");
            }
        }else{
            System.out.println("Connexion faite"); 
        }
    }
}

