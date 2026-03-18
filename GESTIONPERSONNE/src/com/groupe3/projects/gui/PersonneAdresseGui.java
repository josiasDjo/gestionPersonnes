package com.groupe3.projects.gui;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.groupe3.projects.model.Adresse;
import com.groupe3.projects.model.PersonneAdresse;

public class PersonneAdresseGui extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JComboBox<Adresse> cbAdresse; // Utilise l'objet Adresse directement
    private JTextField txtAvenue, txtNumero, txtId; // txtId pour la clé primaire
    private JTable table;
    private DefaultTableModel model;

    public PersonneAdresseGui() {
        setTitle("Liaison Personne-Adresse - Groupe 3");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 900, 600);
        setLocationRelativeTo(null);

        contentPane = new JPanel(new BorderLayout(10, 10));
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);

        // --- 1. FORMULAIRE ---
        JPanel pnlForm = new JPanel(new GridLayout(4, 2, 10, 10));
        pnlForm.setBorder(BorderFactory.createTitledBorder("Nouvelle Liaison"));

        pnlForm.add(new JLabel("ID Liaison (Auto) :"));
        txtId = new JTextField();
        txtId.setEditable(false);
        pnlForm.add(txtId);

        pnlForm.add(new JLabel("Choisir Adresse :"));
        cbAdresse = new JComboBox<>();
        pnlForm.add(cbAdresse);

        pnlForm.add(new JLabel("Avenue :"));
        txtAvenue = new JTextField(); 
        pnlForm.add(txtAvenue);

        pnlForm.add(new JLabel("Numéro Avenue :"));
        txtNumero = new JTextField(); 
        pnlForm.add(txtNumero);

        // --- 2. BOUTONS ---
        JPanel pnlActions = new JPanel();
        JButton btnLier = new JButton("ENREGISTRER");
        JButton btnSupprimer = new JButton("SUPPRIMER");
        JButton btnActualiser = new JButton("ACTUALISER");
        
        pnlActions.add(btnLier);
        pnlActions.add(btnSupprimer);
        pnlActions.add(btnActualiser);

        JPanel pnlNord = new JPanel(new BorderLayout());
        pnlNord.add(pnlForm, BorderLayout.CENTER);
        pnlNord.add(pnlActions, BorderLayout.SOUTH);
        contentPane.add(pnlNord, BorderLayout.NORTH);

        // --- 3. TABLE ---
        model = new DefaultTableModel(new Object[]{"ID", "Avenue", "Numéro"}, 0);
        table = new JTable(model);
        contentPane.add(new JScrollPane(table), BorderLayout.CENTER);

        // --- ÉVÉNEMENTS ---
        btnLier.addActionListener(e -> enregistrerLiaison());
        btnSupprimer.addActionListener(e -> supprimerLiaison());
        btnActualiser.addActionListener(e -> chargerDonnees());

        // --- INITIALISATION ---
        chargerAdresses();
        chargerDonnees();
        preparerNouveau();
    }

    private void chargerAdresses() {
        cbAdresse.removeAllItems();
        List<Adresse> list = Adresse.getAdresses();
        for (Adresse a : list) {
            cbAdresse.addItem(a);
        }
    }

    private void chargerDonnees() {
        model.setRowCount(0);
        List<PersonneAdresse> list = PersonneAdresse.getPersonneAdresses();
        for (PersonneAdresse pa : list) {
            model.addRow(new Object[]{pa.getId(), pa.getAvenue(), pa.getNumero()});
        }
    }

    private void preparerNouveau() {
        PersonneAdresse pa = new PersonneAdresse();
        txtId.setText(String.valueOf(pa.nouveau()));
        txtAvenue.setText("");
        txtNumero.setText("");
    }

    private void enregistrerLiaison() {
        try {
            int id = Integer.parseInt(txtId.getText());
            String ave = txtAvenue.getText();
            int num = Integer.parseInt(txtNumero.getText());

            PersonneAdresse pa = new PersonneAdresse(id, ave, num);
            pa.enregistrer(pa);
            
            JOptionPane.showMessageDialog(this, "Liaison enregistrée !");
            chargerDonnees();
            preparerNouveau();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erreur : " + ex.getMessage());
        }
    }

    private void supprimerLiaison() {
        int ligne = table.getSelectedRow();
        if (ligne != -1) {
            int id = (int) model.getValueAt(ligne, 0);
            new PersonneAdresse().supprimer(id);
            chargerDonnees();
            preparerNouveau();
        } else {
            JOptionPane.showMessageDialog(this, "Sélectionnez une ligne !");
        }
    }
}
