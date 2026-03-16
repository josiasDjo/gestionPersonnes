package com.groupe3.projects.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import com.groupe3.projects.database.Utilitaire;
import java.awt.*;

public class PersonneAdresseGui extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JComboBox<String> cbPersonne, cbAdresse;
    private JTextField txtAvenue, txtNumero;
    private JTable table;

    public PersonneAdresseGui() {
        setTitle("Liaison Personne-Adresse & Rapport Final - Groupe 3");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 900, 600);
        setLocationRelativeTo(null);

        contentPane = new JPanel(new BorderLayout(10, 10));
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);

        // --- 1. FORMULAIRE ---
        JPanel pnlForm = new JPanel(new GridLayout(4, 2, 10, 10));
        pnlForm.setBorder(BorderFactory.createTitledBorder("Nouvelle Liaison"));

        pnlForm.add(new JLabel("Choisir Personne :"));
        cbPersonne = new JComboBox<>();
        pnlForm.add(cbPersonne);

        pnlForm.add(new JLabel("Choisir Adresse :"));
        cbAdresse = new JComboBox<>();
        pnlForm.add(cbAdresse);

        pnlForm.add(new JLabel("Avenue :"));
        txtAvenue = new JTextField(); pnlForm.add(txtAvenue);

        pnlForm.add(new JLabel("Numéro Avenue :"));
        txtNumero = new JTextField(); pnlForm.add(txtNumero);

        // --- 2. BOUTONS ---
        JPanel pnlActions = new JPanel();
        JButton btnLier = new JButton("ENREGISTRER LIAISON");
        JButton btnSupprimer = new JButton("SUPPRIMER LIAISON");
        JButton btnRapport = new JButton("AFFICHER LE RAPPORT COMPLET");
        btnRapport.setBackground(new Color(46, 204, 113)); 
        btnRapport.setForeground(Color.WHITE);
        
        pnlActions.add(btnLier);
        pnlActions.add(btnSupprimer);
        pnlActions.add(btnRapport);

        JPanel pnlNord = new JPanel(new BorderLayout());
        pnlNord.add(pnlForm, BorderLayout.CENTER);
        pnlNord.add(pnlActions, BorderLayout.SOUTH);
        contentPane.add(pnlNord, BorderLayout.NORTH);

        table = new JTable();
        contentPane.add(new JScrollPane(table), BorderLayout.CENTER);

        // --- ÉVÉNEMENTS ---
        btnLier.addActionListener(e -> enregistrerLiaison());
        btnSupprimer.addActionListener(e -> supprimerLiaison());
        btnRapport.addActionListener(e -> genererRapport());

        // Initialisation
        remplirCombos();
        actualiserTable();
    }

    private void remplirCombos() {
        cbPersonne.removeAllItems();
        cbAdresse.removeAllItems();
       
        DefaultTableModel mP = Utilitaire.chargerTableau("SELECT id, nom, prenom FROM personne");
        for (int i = 0; i < mP.getRowCount(); i++) {
            cbPersonne.addItem(mP.getValueAt(i, 0) + " - " + mP.getValueAt(i, 1) + " " + mP.getValueAt(i, 2));
        }

        DefaultTableModel mA = Utilitaire.chargerTableau("SELECT id, ville, quartier FROM adresse");
        for (int i = 0; i < mA.getRowCount(); i++) {
            cbAdresse.addItem(mA.getValueAt(i, 0) + " - " + mA.getValueAt(i, 1) + " (" + mA.getValueAt(i, 2) + ")");
        }
    }

    private void actualiserTable() {
        table.setModel(Utilitaire.chargerTableau("SELECT * FROM personne_adresse"));
    }

    private void genererRapport() {
        String sqlCall = "CALL sp_rapport_complet()";
        
        table.setModel(Utilitaire.chargerTableau(sqlCall));
        
        JOptionPane.showMessageDialog(this, "État de sortie (Rapport) généré depuis MySQL !");
    }

    private void enregistrerLiaison() {
        try {
            int id = Utilitaire.genererProchainId("personne_adresse");
            int idPerso = Integer.parseInt(cbPersonne.getSelectedItem().toString().split(" - ")[0]);
            int idAdres = Integer.parseInt(cbAdresse.getSelectedItem().toString().split(" - ")[0]);
            
            String sql = "INSERT INTO personne_adresse (id, id_adresse, id_personne, avenue, numero_avenue) VALUES (?, ?, ?, ?, ?)";
            Utilitaire.executer(sql, id, idAdres, idPerso, txtAvenue.getText(), Integer.parseInt(txtNumero.getText()));
            
            actualiserTable();
            JOptionPane.showMessageDialog(this, "Liaison enregistrée !");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erreur : Saisie invalide ou sélection manquante.");
        }
    }

    private void supprimerLiaison() {
        int row = table.getSelectedRow();
        if (row != -1) {
            Object id = table.getValueAt(row, 0);
            Utilitaire.executer("DELETE FROM personne_adresse WHERE id=?", id);
            actualiserTable();
        }
    }
}
