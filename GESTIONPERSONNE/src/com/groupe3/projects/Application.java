package com.groupe3.projects;

import com.groupe3.projects.database.*;
import com.groupe3.projects.gui.FenetrePrincipale;

public class Application {
    public static void main(String[] args) {
        // 1. Config XAMPP
        Connexion config = new Connexion(); 
        
        // 2. ON INITIALISE LA FACTORY (C'est l'étape cruciale)
        FactoryConnexion.setConnection(config, ConnexionType.MySQL);

        // 3. Lancement
        java.awt.EventQueue.invokeLater(() -> {
            new FenetrePrincipale().setVisible(true);
        });
    }
}
