package com.groupe3.projects.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.groupe3.projects.model.Personne;
import com.groupe3.projects.model.Sexe;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;

public class Workflow extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtId, txtNom, txtPostnom, txtPrenom;
    private JComboBox<Sexe> cbSexe;
    private JTable table;
    private DefaultTableModel tableModel;

    public Workflow() {
        setTitle("TP Génie Logiciel - Gestion des Personnes");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 900, 600);
        setLocationRelativeTo(null);
        
        contentPane = new JPanel(new BorderLayout(10, 10));
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);

        // --- 1. Formulaire ---
        JPanel pnlForm = new JPanel(new GridLayout(5, 2, 10, 10));
        pnlForm.setBorder(BorderFactory.createTitledBorder("Informations Personnelles"));

        pnlForm.add(new JLabel("ID (Lecture seule) :"));
        txtId = new JTextField(); 
        txtId.setEditable(false); 
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
        JPanel pnlBoutons = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton btnEnregistrer = new JButton("Enregistrer");
        JButton btnModifier = new JButton("Modifier");
        JButton btnSupprimer = new JButton("Supprimer");
        JButton btnAdresse = new JButton("Adresse"); 

        pnlBoutons.add(btnAdresse);
        pnlBoutons.add(btnEnregistrer);
        pnlBoutons.add(btnModifier);
        pnlBoutons.add(btnSupprimer);

        JPanel pnlNord = new JPanel(new BorderLayout());
        pnlNord.add(pnlForm, BorderLayout.CENTER);
        pnlNord.add(pnlBoutons, BorderLayout.SOUTH);
        contentPane.add(pnlNord, BorderLayout.NORTH);

        // --- 3. Tableau ---
        String[] colonnes = {"ID", "Nom", "Postnom", "Prénom", "Sexe"};
        tableModel = new DefaultTableModel(colonnes, 0);
        table = new JTable(tableModel);
        contentPane.add(new JScrollPane(table), BorderLayout.CENTER);

     // --- Événements ---
        btnEnregistrer.addActionListener(e -> enregistrer());
        btnModifier.addActionListener(e -> modifier());
        btnSupprimer.addActionListener(e -> supprimer());

        // Correction de l'appel à la fenêtre Adresse
        btnAdresse.addActionListener(e -> {
            new Address().setVisible(true); 
        });

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if(row != -1) {
                    txtId.setText(table.getValueAt(row, 0).toString());
                    txtNom.setText(table.getValueAt(row, 1).toString());
                    txtPostnom.setText(table.getValueAt(row, 2).toString());
                    txtPrenom.setText(table.getValueAt(row, 3).toString());
                    // Gestion du sexe pour la JComboBox
                    String s = table.getValueAt(row, 4).toString();
                    cbSexe.setSelectedItem(Sexe.valueOf(s));
                }
            }
        });

        chargerDonnees();
    }

    private void chargerDonnees() {
        // 1. On vide le modèle actuel du tableau
        tableModel.setRowCount(0); 
        
        // 2. On récupère les données via la procédure stockée
        Personne dao = new Personne();
        List<Personne> maListe = null;
		try {
			maListe = dao.lister();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
        
        // 3. On remplit le tableau ligne par ligne
        for (Personne p : maListe) {
            Object[] ligne = {
                p.getId(),
                p.getNom(),
                p.getPostnom(),
                p.getPrenom(),
                p.getSex() // Affichera "Masculin" ou "Féminin"
            };
            tableModel.addRow(ligne);
        }
    }

    private void enregistrer() {
        if(!new Personne().validateName(txtNom.getText())) {
            JOptionPane.showMessageDialog(this, "Le nom est obligatoire !");
            return;
        }

        Personne p = new Personne();
        // Pour un nouvel enregistrement, l'ID peut être 0 si ta DB est en Auto-increment
        p.setNom(txtNom.getText());
        p.setPostnom(txtPostnom.getText());
        p.setPrenom(txtPrenom.getText());
        p.setSex((Sexe) cbSexe.getSelectedItem());

        if(p.enregistrer(p) > 0) {
            JOptionPane.showMessageDialog(this, "Enregistré avec succès !");
            chargerDonnees();
            viderChamps();
        }
    }

    private void modifier() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Sélectionnez une personne dans le tableau.");
            return;
        }
        
        Personne p = new Personne();
        p.setId(Integer.parseInt(txtId.getText()));
        p.setNom(txtNom.getText());
        p.setPostnom(txtPostnom.getText());
        p.setPrenom(txtPrenom.getText());
        p.setSex((Sexe) cbSexe.getSelectedItem());

        // Note: l'UML ne montre pas de méthode modifier(), tu devrais l'ajouter à IPersonne
        if(p.enregistrer(p) > 0) { // Souvent sp_insert gère l'UPDATE si l'ID existe
            JOptionPane.showMessageDialog(this, "Mise à jour effectuée !");
            chargerDonnees();
        }
    }

    private void supprimer() {
        if (txtId.getText().isEmpty()) return;
        
        int confirmation = JOptionPane.showConfirmDialog(this, "Supprimer cette personne ?", "Confirmation", JOptionPane.YES_NO_OPTION);
        if(confirmation == JOptionPane.YES_OPTION) {
            int id = Integer.parseInt(txtId.getText());
            Personne p = new Personne();
            if(p.supprimer(id) > 0) {
                JOptionPane.showMessageDialog(this, "Supprimé !");
                chargerDonnees();
                viderChamps();
            }
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Workflow().setVisible(true));
    }
}
