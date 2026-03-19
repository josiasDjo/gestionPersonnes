package com.groupe3.projects.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import com.groupe3.projects.model.Telephone;
import com.groupe3.projects.model.Personne;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class Contact extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtInitial, txtnumero, txtIdTel;
    private JComboBox<Personne> cbProprietaire; 
    private JTable table;
    private DefaultTableModel model;

    public Contact() {
        setTitle("Gestion des Contacts (Téléphone) - Groupe 3");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 800, 600);
        setLocationRelativeTo(null);
        
        contentPane = new JPanel(new BorderLayout(10, 10));
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);

        // --- 1. Formulaire ---
        JPanel pnlForm = new JPanel(new GridLayout(4, 2, 5, 5));
        pnlForm.setBorder(BorderFactory.createTitledBorder("Informations CONTACT"));

        pnlForm.add(new JLabel("ID Téléphone (Auto) :"));
        txtIdTel = new JTextField(); txtIdTel.setEditable(false); pnlForm.add(txtIdTel);

        pnlForm.add(new JLabel("Propriétaire :"));
        cbProprietaire = new JComboBox<>(); pnlForm.add(cbProprietaire);

        pnlForm.add(new JLabel("Initial (ex: +243) :"));
        txtInitial = new JTextField(); pnlForm.add(txtInitial);

        pnlForm.add(new JLabel("Numéro :"));
        txtnumero = new JTextField(); pnlForm.add(txtnumero);

        // --- 2. Boutons d'action ---
        JPanel pnlBoutons = new JPanel();
        JButton btnEnregistrer = new JButton("Enregistrer");
        JButton btnModifier = new JButton("Modifier");
        JButton btnSupprimer = new JButton("Supprimer");
        JButton btnLiaison = new JButton("LIAISON ADRESSE");
        JButton btnRapport = new JButton("Afficher Rapport");
        
        btnRapport.setBackground(new Color(52, 152, 219)); 
        btnRapport.setForeground(Color.WHITE);
        
        pnlBoutons.add(btnEnregistrer);
        pnlBoutons.add(btnModifier);
        pnlBoutons.add(btnSupprimer);
        pnlBoutons.add(btnRapport); 
        pnlBoutons.add(btnLiaison);

        JPanel pnlNord = new JPanel(new BorderLayout());
        pnlNord.add(pnlForm, BorderLayout.CENTER);
        pnlNord.add(pnlBoutons, BorderLayout.SOUTH);
        contentPane.add(pnlNord, BorderLayout.NORTH);

        // --- 3. Tableau ---
        model = new DefaultTableModel(new Object[]{"ID", "ID Proprio", "Initial", "Numéro"}, 0);
        table = new JTable(model);
        contentPane.add(new JScrollPane(table), BorderLayout.CENTER);

        // --- Événements ---
        btnEnregistrer.addActionListener(e -> enregistrer());
        btnModifier.addActionListener(e -> modifier());
        btnSupprimer.addActionListener(e -> supprimer());
        btnLiaison.addActionListener(e -> new PersonneAdresseGui().setVisible(true));
        
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) { selectionnerLigne(); }
        });

        // Initialisation
        chargerProprietaires();
        chargerTable();
        preparerNouveau();
    }

    private void chargerProprietaires() {
        cbProprietaire.removeAllItems();
        List<Personne> liste = Personne.getPersonnes();
        for (Personne p : liste) cbProprietaire.addItem(p);
    }

    private void chargerTable() {
        model.setRowCount(0);
        List<Telephone> liste = Telephone.getTelephones();
        for (Telephone t : liste) {
            model.addRow(new Object[]{t.getId(), t.getIdProprietaire(), t.getInitial(), t.getNumero()});
        }
    }

    private void preparerNouveau() {
        txtIdTel.setText(String.valueOf(new Telephone().genererId()));
        txtInitial.setText("");
        txtnumero.setText("");
    }

    private void selectionnerLigne() {
        int row = table.getSelectedRow();
        if (row != -1) {
            txtIdTel.setText(model.getValueAt(row, 0).toString());
            int idProprio = (int) model.getValueAt(row, 1);
            
            for (int i = 0; i < cbProprietaire.getItemCount(); i++) {
                if (cbProprietaire.getItemAt(i).getId() == idProprio) {
                    cbProprietaire.setSelectedIndex(i);
                    break;
                }
            }
            txtInitial.setText(model.getValueAt(row, 2).toString());
            txtnumero.setText(model.getValueAt(row, 3).toString());
        }
    }

    private void enregistrer() {
        Personne p = (Personne) cbProprietaire.getSelectedItem();
        if (p == null) return;
        Telephone t = new Telephone(Integer.parseInt(txtIdTel.getText()), p.getId(), txtInitial.getText(), txtnumero.getText());
        t.ajouter();
        chargerTable();
        preparerNouveau();
    }

    private void modifier() {
        Personne p = (Personne) cbProprietaire.getSelectedItem();
        if (p == null) return;
        Telephone t = new Telephone(Integer.parseInt(txtIdTel.getText()), p.getId(), txtInitial.getText(), txtnumero.getText());
        t.modifier();
        chargerTable();
    }

    private void supprimer() {
        int id = Integer.parseInt(txtIdTel.getText());
        new Telephone().supprimer(id);
        chargerTable();
        preparerNouveau();
    }
}
