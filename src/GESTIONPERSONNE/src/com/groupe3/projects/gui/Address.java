package com.groupe3.projects.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import com.groupe3.projects.database.Utilitaire;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Address extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtQuartier, txtCommune, txtVille, txtPays, txtId;
    private JTable table;

    public Address() {
        setTitle("Gestion des Adresses - Groupe 3");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 700, 600);
        setLocationRelativeTo(null);

        contentPane = new JPanel(new BorderLayout(10, 10));
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);

        // --- 1. FORMULAIRE ---
        JPanel pnlForm = new JPanel(new GridLayout(5, 2, 10, 10));
        pnlForm.setBorder(BorderFactory.createTitledBorder("Saisie Adresse"));

        pnlForm.add(new JLabel("ID (Automatique) :"));
        txtId = new JTextField(); txtId.setEditable(false); pnlForm.add(txtId);

        pnlForm.add(new JLabel("Quartier :"));
        txtQuartier = new JTextField(); pnlForm.add(txtQuartier);

        pnlForm.add(new JLabel("Commune :"));
        txtCommune = new JTextField(); pnlForm.add(txtCommune);

        pnlForm.add(new JLabel("Ville :"));
        txtVille = new JTextField(); pnlForm.add(txtVille);

        pnlForm.add(new JLabel("Pays :"));
        txtPays = new JTextField(); pnlForm.add(txtPays);
        
        // --- 2. BOUTONS (CENTRE) ---
        JPanel pnlActions = new JPanel(new FlowLayout());
        JButton btnAjouter = new JButton("AJOUTER");
        JButton btnModifier = new JButton("MODIFIER");
        JButton btnSupprimer = new JButton("SUPPRIMER");
        JButton btnContact = new JButton("CONTACT");

        pnlActions.add(btnAjouter);
        pnlActions.add(btnModifier);
        pnlActions.add(btnSupprimer);
        pnlActions.add(btnContact);

        // Regroupement du formulaire et des boutons
        JPanel pnlSaisie = new JPanel(new BorderLayout());
        pnlSaisie.add(pnlForm, BorderLayout.CENTER);
        pnlSaisie.add(pnlActions, BorderLayout.SOUTH);
        contentPane.add(pnlSaisie, BorderLayout.NORTH);

        // --- 3. DATAGRIDVIEW (SUD) ---
        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(650, 250));
        contentPane.add(scrollPane, BorderLayout.SOUTH);

        // --- ÉVÉNEMENTS ---
        btnAjouter.addActionListener(e -> ajouter());
        btnModifier.addActionListener(e -> modifier());
        btnSupprimer.addActionListener(e -> supprimer());
        
        btnContact.addActionListener(e -> {
            new Contact().setVisible(true); 
       });

        
        // Sélection dans la table
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                txtId.setText(table.getValueAt(row, 0).toString());
                txtQuartier.setText(table.getValueAt(row, 1).toString());
                txtCommune.setText(table.getValueAt(row, 2).toString());
                txtVille.setText(table.getValueAt(row, 3).toString());
                txtPays.setText(table.getValueAt(row, 4).toString());
            }
        });

        actualiserTableau();
    }

    private void actualiserTableau() {
        // Utilisation MÉTHODE 3 : Charger le tableau
        table.setModel(Utilitaire.chargerTableau("SELECT * FROM adresse"));
    }

    private void ajouter() {
        int id = Utilitaire.genererProchainId("adresse");
        String sql = "INSERT INTO adresse (id, quartier, commune, ville, pays) VALUES (?,?,?,?,?)";
        Utilitaire.executer(sql, id, txtQuartier.getText(), txtCommune.getText(), txtVille.getText(), txtPays.getText());
        actualiserTableau();
        vider();
    }

    private void modifier() {
        if(txtId.getText().isEmpty()) return;
        String sql = "UPDATE adresse SET quartier=?, commune=?, ville=?, pays=? WHERE id=?";
        Utilitaire.executer(sql, txtQuartier.getText(), txtCommune.getText(), txtVille.getText(), txtPays.getText(), txtId.getText());
        actualiserTableau();
    }

    private void supprimer() {
        if(txtId.getText().isEmpty()) return;
        Utilitaire.executer("DELETE FROM adresse WHERE id=?", txtId.getText());
        actualiserTableau();
        vider();
    }

    private void vider() {
        txtId.setText(""); txtQuartier.setText(""); txtCommune.setText(""); txtVille.setText(""); txtPays.setText("");
    }
}
