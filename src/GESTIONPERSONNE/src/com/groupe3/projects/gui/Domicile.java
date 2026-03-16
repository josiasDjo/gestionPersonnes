package com.groupe3.projects.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class Domicile extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
    private JTextField txtquqrtier;
	private JTextField txtcommune;
    private JTextField txtpays;
    private JTextField txtville;
    private JTable table;
    private DefaultTableModel tableModel;

    public Domicile() {
        setTitle("Gestion des Personnes - Groupe 3");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 600);
        
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        contentPane.setLayout(new BorderLayout(10, 10));
        setContentPane(contentPane);

        // --- 1. Formulaire (Haut) ---
        JPanel pnlForm = new JPanel(new GridLayout(5, 2, 5, 5));
        pnlForm.setBorder(BorderFactory.createTitledBorder("Informations Adresser"));

        pnlForm.add(new JLabel("quartier :"));
        txtquqrtier = new JTextField(); pnlForm.add(txtquqrtier);

        pnlForm.add(new JLabel("commune :"));
        txtcommune = new JTextField(); pnlForm.add(txtcommune);

        pnlForm.add(new JLabel("ville :"));
        txtville = new JTextField(); pnlForm.add(txtville);
        
        pnlForm.add(new JLabel("pays :"));
        txtpays = new JTextField(); pnlForm.add(txtpays);

        contentPane.add(pnlForm, BorderLayout.NORTH);

        // --- 2. Boutons d'action (Milieu) ---
        JPanel pnlBoutons = new JPanel();
        JButton btnEnregistrer = new JButton("Enregistrer");
        JButton btnModifier = new JButton("Modifier");
        JButton btnSupprimer = new JButton("Supprimer");
        
        pnlBoutons.add(btnEnregistrer);
        pnlBoutons.add(btnModifier);
        pnlBoutons.add(btnSupprimer);
        contentPane.add(pnlBoutons, BorderLayout.CENTER);

        // --- 3. Tableau (Bas - "DataGridView") ---
        String[] colonnes = {"pays", "ville", "commune","quartier"};
        tableModel = new DefaultTableModel(colonnes, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(750, 300));
        contentPane.add(scrollPane, BorderLayout.SOUTH);

        // --- Événements ---
        btnEnregistrer.addActionListener(e -> enregistrer());
        btnModifier.addActionListener(e -> modifier());
        btnSupprimer.addActionListener(e -> supprimer());
    }

    private void enregistrer() {
        Object[] ligne = {txtquqrtier.getText(), txtcommune.getText(), txtpays.getText()};
        tableModel.addRow(ligne);
        JOptionPane.showMessageDialog(this, "Enregistré avec succès !");
    }

    private void modifier() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            tableModel.setValueAt(txtquqrtier.getText(), selectedRow, 0);
            tableModel.setValueAt(txtcommune.getText(), selectedRow, 1);
            // ... etc
        } else {
            JOptionPane.showMessageDialog(this, "Sélectionnez une ligne à modifier.");
        }
    }

    private void supprimer() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            tableModel.removeRow(selectedRow);
        } else {
            JOptionPane.showMessageDialog(this, "Sélectionnez une ligne à supprimer.");
        }
    }
}
