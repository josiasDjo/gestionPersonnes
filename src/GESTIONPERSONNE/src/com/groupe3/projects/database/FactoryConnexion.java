package com.groupe3.projects.database;

public class FactoryConnexion {
    private static Connection connection = null;
    private static FactoryConnexion instance = null;
    private FactoryConnexion() {
        connection = new Connection();
    }

    // obtenir l'instance unique
    public static FactoryConnexion getInstance() {
        if (instance == null) {
            instance = new FactoryConnexion();
        }
        return instance;
    }

    // récupérer l'objet Connexion
    public Connection getConnection() {
        return connection;
    }
    public static String formatConnectionString(Connection connexion, connexionType type) {
        if (type == connexionType.MySQL) {
            return "jdbc:mysql://" + connexion.getServeur() + ":" + 
                   connexion.getPort() + "/" + connexion.getDatabase();
        }
        return "";
    }
}
