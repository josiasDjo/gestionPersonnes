package com.groupe3.projects;

import javax.swing.SwingUtilities;
import com.groupe3.projects.gui.FenetrePrincipale;
import java.sql.Connection;
import java.sql.DriverManager;

public class Application {

    public static void main(String[] args) {
        System.out.println("=== Démarrage du Projet de Génie Logiciel - Groupe 3 ===");
        
        verifierConnexionBD();

        //Lancement de l'interface
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    FenetrePrincipale window = new FenetrePrincipale();
                    window.setVisible(true);
                    System.out.println("Interface graphique lancée avec succès.");
                } catch (Exception e) {
                    System.err.println("Erreur lors du lancement de l'interface : " + e.getMessage());
                }
            }
        });
    }

    private static void verifierConnexionBD() {
       
        String url = "jdbc:mysql://localhost:3306/gestion_personne";
        String user = "root";
        String password = "";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            if (connection != null) {
                System.out.println("Succès : L'app est connectée à la base de données !");
            }
        } catch (Exception e) {
            System.err.println("Erreur : Impossible de se connecter à MySQL. Vérifiez XAMPP.");
            System.err.println("Détail : " + e.getMessage());
        }
    }
}
