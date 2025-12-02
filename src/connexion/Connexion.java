/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author X1 YOGA
 */
//JDBC
public class Connexion {
    
    private static String url = "jdbc:mysql://localhost/hotel";
    private static String login = "root";
    private static String password = "";
    private static Connection connection = null;
    
    static {
        initializeConnection();
    }

    private static void initializeConnection() {
        try {
            // Chargement du Driver
            Class.forName("com.mysql.jdbc.Driver");
            // Etablir la connexion avec la base de donnees
            connection = DriverManager.getConnection(url, login, password);
            System.out.println("Database connected successfully!");
        } catch (ClassNotFoundException ex) {
            System.out.println("Driver non charge: " + ex.getMessage());
        } catch (SQLException ex) {
            System.out.println("Connection failed: " + ex.getMessage());
            System.out.println("Impossible d'etablir la connexion");
        }
    }

    public static Connection getConnection() {
        // If connection is null or closed, try to reconnect
        try {
            if (connection == null || connection.isClosed()) {
                System.out.println("Attempting to reconnect to database...");
                initializeConnection();
            }
        } catch (SQLException e) {
            System.out.println("Connection check failed: " + e.getMessage());
        }
        return connection;
    }
    
    public static void main(String[] args) {
        Connection conn = getConnection();
        if (conn != null) {
            System.out.println("Connection test: SUCCESS");
        } else {
            System.out.println("Connection test: FAILED");
        }
    }
}