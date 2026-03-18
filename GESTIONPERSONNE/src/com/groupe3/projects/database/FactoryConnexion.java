package com.groupe3.projects.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import com.groupe3.projects.interfaces.IConnexion;

public class FactoryConnexion implements IConnexion {
    private static Connection connection; 
    private static FactoryConnexion instance;

    private FactoryConnexion() {} 

    public static FactoryConnexion getInstance() {
        if (instance == null) instance = new FactoryConnexion();
        return instance;
    }

    public Connection getConnection() throws SQLException {
        if (connection == null) {
            throw new SQLException("Erreur : La connexion n'est pas initialisée.");
        }
        return connection;
    }

    
    public static String formatConnectionString(Connexion connexion, ConnexionType type) {
        return "jdbc:" + type.toString().toLowerCase() + "://" 
               + connexion.getServeur() + ":" + connexion.getPort() + "/" + connexion.getDatabase();
    }

    
    public static void setConnection(Connexion c, ConnexionType type) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = formatConnectionString(c, type);
            connection = DriverManager.getConnection(url, c.getUser(), c.getPassword());
            System.out.println("✅ [FACTORY] Connecté à MySQL.");
        } catch (Exception e) {
            System.err.println("❌ [FACTORY] Erreur : " + e.getMessage());
            connection = null;
        }
    }
}
