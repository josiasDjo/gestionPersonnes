package com.groupe3.projects.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import com.groupe3.projects.model.Adresse; // Importation du modèle corrigé
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class Address extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtQuartier, txtCommune, txtVille, txtPays, txtId;
    private JTable table;
    private DefaultTableModel modelTable;

    public Address() {
        setTitle("Gestion des Adresses - Groupe 3");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 750, 600);
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
        
        // --- 2. BOUTONS ---
        JPanel pnlActions = new JPanel(new FlowLayout());
        JButton btnAjouter = new JButton("AJOUTER");
        JButton btnModifier = new JButton("MODIFIER");
        JButton btnSupprimer = new JButton("SUPPRIMER");
        JButton btnContact = new JButton("CONTACTS");

        pnlActions.add(btnAjouter);
        pnlActions.add(btnModifier);
        pnlActions.add(btnSupprimer);
        pnlActions.add(btnContact);

        JPanel pnlSaisie = new JPanel(new BorderLayout());
        pnlSaisie.add(pnlForm, BorderLayout.CENTER);
        pnlSaisie.add(pnlActions, BorderLayout.SOUTH);
        contentPane.add(pnlSaisie, BorderLayout.NORTH);

        // --- 3. TABLEAU ---
        table = new JTable();
        modelTable = new DefaultTableModel(new Object[]{"ID", "Quartier", "Commune", "Ville", "Pays"}, 0);
        table.setModel(modelTable);
        contentPane.add(new JScrollPane(table), BorderLayout.CENTER);

        // --- ÉVÉNEMENTS ---
        btnAjouter.addActionListener(e -> ajouter());
        btnModifier.addActionListener(e -> modifier());
        btnSupprimer.addActionListener(e -> supprimer());
        btnContact.addActionListener(e ->{
            new Contact().setVisible(true); 
        });

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

        chargerTable();
    }

    // --- LOGIQUE ---

    private void chargerTable() {
        modelTable.setRowCount(0); 
        List<Adresse> liste = Adresse.getAdresses();
        for (Adresse a : liste) {
            modelTable.addRow(new Object[]{a.getId(), a.getQuartier(), a.getCommune(), a.getVille(), a.getPays()});
        }
    }

    private void ajouter() {
        Adresse a = new Adresse();
        int id = a.genererId(); 
        a = new Adresse(id, txtQuartier.getText(), txtCommune.getText(), txtVille.getText(), txtPays.getText());
        a.ajouter(); 
        chargerTable();
        effacerChamps();
    }

    private void modifier() {
        if (!txtId.getText().isEmpty()) {
            int id = Integer.parseInt(txtId.getText());
            Adresse a = new Adresse(id, txtQuartier.getText(), txtCommune.getText(), txtVille.getText(), txtPays.getText());
            a.modifier();
            chargerTable();
            effacerChamps();
        }
    }

    private void supprimer() {
        if (!txtId.getText().isEmpty()) {
            int id = Integer.parseInt(txtId.getText());
            new Adresse().supprimer(id);
            chargerTable();
            effacerChamps();
        }
    }

    private void effacerChamps() {
        txtId.setText("");
        txtQuartier.setText("");
        txtCommune.setText("");
        txtVille.setText("");
        txtPays.setText("");
    }
}
