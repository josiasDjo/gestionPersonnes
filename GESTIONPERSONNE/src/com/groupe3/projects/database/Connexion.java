    package com.groupe3.projects.database;

    import com.groupe3.projects.interfaces.IConnexion;
    import java.sql.DriverManager;

    public class Connexion implements IConnexion { 
        public static final Connexion MySQL = null;
		private String serveur = "localhost";
        private String database = "gestion_personne"; 
        private String user = "root";    
        private String password = "";
        private int port = 3306;

        public Connexion() {}

        
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
                String url = "jdbc:mysql://" + serveur + ":" + port + "/" + database;
                return DriverManager.getConnection(url, user, password);
            } catch (Exception e) {
                return null;
            }
        }
    }
