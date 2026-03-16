package com.groupe3.projects.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import com.groupe3.projects.database.Utilitaire;
import com.groupe3.projects.model.Sexe;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Workflow extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtId, txtNom, txtPostnom, txtPrenom;
    private JComboBox<Sexe> cbSexe;
    private JTable table;

    public Workflow() {
        setTitle("Gestion des Personnes - Utilitaire");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 850, 650);
        setLocationRelativeTo(null);
        
        contentPane = new JPanel(new BorderLayout(10, 10));
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);

        // --- 1. Formulaire ---
        JPanel pnlForm = new JPanel(new GridLayout(5, 2, 5, 5));
        pnlForm.setBorder(BorderFactory.createTitledBorder("Saisie des informations"));

        pnlForm.add(new JLabel("ID (Auto) :"));
        txtId = new JTextField(); 
        txtId.setEditable(false); 
        txtId.setBackground(new Color(240, 240, 240));
        pnlForm.add(txtId);

        pnlForm.add(new JLabel("Nom :"));
        txtNom = new JTextField(); pnlForm.add(txtNom);

        pnlForm.add(new JLabel("Postnom :"));
        txtPostnom = new JTextField(); pnlForm.add(txtPostnom);

        pnlForm.add(new JLabel("Prénom :"));
        txtPrenom = new JTextField(); pnlForm.add(txtPrenom);

        pnlForm.add(new JLabel("Sexe :"));
        cbSexe = new JComboBox<>(Sexe.values());
        pnlForm.add(cbSexe);

        // --- 2. Boutons ---
        JPanel pnlBoutons = new JPanel();
        JButton btnEnregistrer = new JButton("Enregistrer");
        JButton btnModifier = new JButton("Modifier");
        JButton btnSupprimer = new JButton("Supprimer");
        JButton btnNouveau = new JButton("Nouveau");
        JButton btnAdresse = new JButton("Gérer Adresse");

        pnlBoutons.add(btnEnregistrer);
        pnlBoutons.add(btnModifier);
        pnlBoutons.add(btnSupprimer);
        pnlBoutons.add(btnNouveau);
        pnlBoutons.add(btnAdresse);

        JPanel pnlNord = new JPanel(new BorderLayout());
        pnlNord.add(pnlForm, BorderLayout.CENTER);
        pnlNord.add(pnlBoutons, BorderLayout.SOUTH);
        contentPane.add(pnlNord, BorderLayout.NORTH);

        // --- 3. Tableau ---
        table = new JTable();
        contentPane.add(new JScrollPane(table), BorderLayout.CENTER);

        // --- Événements ---
        btnEnregistrer.addActionListener(e -> enregistrer());
        btnModifier.addActionListener(e -> modifier());
        btnSupprimer.addActionListener(e -> supprimer());
        btnNouveau.addActionListener(e -> viderChamps());
        btnAdresse.addActionListener(e -> new Address().setVisible(true));

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                txtId.setText(table.getValueAt(row, 0).toString());
                txtNom.setText(table.getValueAt(row, 1).toString());
                txtPostnom.setText(table.getValueAt(row, 2).toString());
                txtPrenom.setText(table.getValueAt(row, 3).toString());
                String s = table.getValueAt(row, 4).toString().toUpperCase();
                cbSexe.setSelectedIndex(s.startsWith("M") ? 0 : 1);
            }
        });

        chargerDonnees(); 
    }

    private void chargerDonnees() {
        table.setModel(Utilitaire.chargerTableau("SELECT id, nom, postnom, prenom, sexe FROM personne ORDER BY nom ASC"));
    }

    private void enregistrer() {
        int id = Utilitaire.genererProchainId("personne");
        
        String sql = "INSERT INTO personne (id, nom, postnom, prenom, sexe) VALUES (?, ?, ?, ?, ?)";
        String sexe = cbSexe.getSelectedItem().toString().substring(0, 1);
        int res = Utilitaire.executer(sql, id, txtNom.getText(), txtPostnom.getText(), txtPrenom.getText(), sexe);
        
        if(res > 0) {
            JOptionPane.showMessageDialog(this, "Personne ajoutée !");
            chargerDonnees();
            viderChamps();
        }
    }

    private void modifier() {
        if (txtId.getText().isEmpty()) return;
        
        String sql = "UPDATE personne SET nom=?, postnom=?, prenom=?, sexe=? WHERE id=?";
        String sexe = cbSexe.getSelectedItem().toString().substring(0, 1);
        
        Utilitaire.executer(sql, txtNom.getText(), txtPostnom.getText(), txtPrenom.getText(), sexe, txtId.getText());
        chargerDonnees();
        JOptionPane.showMessageDialog(this, "Modifié avec succès !");
    }

    private void supprimer() {
        if (txtId.getText().isEmpty()) return;
        
        int confirm = JOptionPane.showConfirmDialog(this, "Supprimer cette personne ?", "Confirmation", JOptionPane.YES_NO_OPTION);
        if(confirm == JOptionPane.YES_OPTION) {
            Utilitaire.executer("DELETE FROM personne WHERE id=?", txtId.getText());
            chargerDonnees();
            viderChamps();
        }
    }

    private void viderChamps() {
        txtId.setText("");
        txtNom.setText("");
        txtPostnom.setText("");
        txtPrenom.setText("");
        cbSexe.setSelectedIndex(0);
        table.clearSelection();
    }
}
