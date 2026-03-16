package com.groupe3.projects.database;

import java.sql.DriverManager;

import com.groupe3.projects.interfaces.Iconnexion; // Vérifie bien ce chemin selon ton projet

public class Connection implements Iconnexion {
    
    private String serveur = "localhost";
    private String database = "gestion_personne"; 

    private String user = "root";
    private String password = ""; 
    private int port = 3306;

    //Constructeur
    public Connection() {
    }

    //Constructeur avec paramètres 
    public Connection(String serveur, String database, String user, String password, int port) {
        this.serveur = serveur;
        this.database = database;
        this.user = user;
        this.password = password;
        this.port = port;
    }

    public String getServeur() { return serveur; }
    public void setServeur(String serveur) { this.serveur = serveur; }

    public String getDatabase() { return database; }
    public void setDatabase(String database) { this.database = database; }

    public String getUser() { return user; }
    public void setUser(String user) { this.user = user; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public int getPort() { return port; }
    public void setPort(int port) { this.port = port; }

    @Override
    public java.sql.Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://" + serveur + ":" + port + "/" + database;
            return DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            System.out.println("Erreur de connexion : " + e.getMessage());
            return null;
        }
    }
}
