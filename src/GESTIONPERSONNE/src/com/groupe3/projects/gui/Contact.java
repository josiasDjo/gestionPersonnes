package com.groupe3.projects.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import com.groupe3.projects.database.Utilitaire;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Contact extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtIDperso, txtInitial, txtnumero, txtIdTel;
    private JTable table;

    public Contact() {
        setTitle("Gestion des Contacts (Téléphone) - Groupe 3");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 800, 600);
        setLocationRelativeTo(null);
        
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        contentPane.setLayout(new BorderLayout(10, 10));
        setContentPane(contentPane);

        // --- 1. Formulaire (Haut) ---
        JPanel pnlForm = new JPanel(new GridLayout(4, 2, 5, 5));
        pnlForm.setBorder(BorderFactory.createTitledBorder("Informations CONTACT"));

        pnlForm.add(new JLabel("ID Téléphone (Auto) :"));
        txtIdTel = new JTextField(); txtIdTel.setEditable(false); pnlForm.add(txtIdTel);

        pnlForm.add(new JLabel("ID Personne (Propriétaire) :"));
        txtIDperso = new JTextField(); pnlForm.add(txtIDperso);

        pnlForm.add(new JLabel("Initial (ex: +243) :"));
        txtInitial = new JTextField(); pnlForm.add(txtInitial);

        pnlForm.add(new JLabel("Numéro :"));
        txtnumero = new JTextField(); pnlForm.add(txtnumero);

        contentPane.add(pnlForm, BorderLayout.NORTH);

        // --- 2. Boutons d'action (Milieu) ---
        JPanel pnlBoutons = new JPanel();
        JButton btnEnregistrer = new JButton("Enregistrer");
        JButton btnModifier = new JButton("Modifier");
        JButton btnSupprimer = new JButton("Supprimer");
        JButton btnPersonneAdresseGui1 = new JButton("LIAISON ADRESSE");
        JButton btnRapport = new JButton("Afficher Rapport");
        
        btnRapport.setBackground(new Color(52, 152, 219)); 
        btnRapport.setForeground(Color.WHITE);
        
        pnlBoutons.add(btnEnregistrer);
        pnlBoutons.add(btnModifier);
        pnlBoutons.add(btnSupprimer);
        pnlBoutons.add(btnRapport); 
        pnlBoutons.add(btnPersonneAdresseGui1);

        contentPane.add(pnlBoutons, BorderLayout.CENTER);

        // --- 3. Tableau (Bas) ---
        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(750, 300));
        contentPane.add(scrollPane, BorderLayout.SOUTH);

        // --- Événements ---
        btnEnregistrer.addActionListener(e -> enregistrer());
        btnModifier.addActionListener(e -> modifier());
        btnSupprimer.addActionListener(e -> supprimer());
        
 
        btnPersonneAdresseGui1.addActionListener(e -> {
             new PersonneAdresseGui().setVisible(true);
        });
        
        btnRapport.addActionListener(e -> {
            table.setModel(Utilitaire.chargerTableau("CALL sp_liste_personnes()"));
            JOptionPane.showMessageDialog(this, "État de sortie généré !");
        });

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                try {
                    txtIdTel.setText(table.getValueAt(row, 0).toString());
                    txtIDperso.setText(table.getValueAt(row, 1).toString());
                    txtInitial.setText(table.getValueAt(row, 2).toString());
                    txtnumero.setText(table.getValueAt(row, 3).toString());
                } catch (Exception ex) {
                   
                }
            }
        });

        actualiser();
    }

    private void actualiser() {
        table.setModel(Utilitaire.chargerTableau("SELECT * FROM telephone"));
    }

    private void enregistrer() {
        int id = Utilitaire.genererProchainId("telephone");
        String sql = "INSERT INTO telephone (id, id_proprietaire, initial, numero) VALUES (?, ?, ?, ?)";
        Utilitaire.executer(sql, id, txtIDperso.getText(), txtInitial.getText(), txtnumero.getText());
        JOptionPane.showMessageDialog(this, "Contact ajouté !");
        actualiser();
        vider();
    }

    private void modifier() {
        if (txtIdTel.getText().isEmpty()) return;
        String sql = "UPDATE telephone SET id_proprietaire=?, initial=?, numero=? WHERE id=?";
        Utilitaire.executer(sql, txtIDperso.getText(), txtInitial.getText(), txtnumero.getText(), txtIdTel.getText());
        JOptionPane.showMessageDialog(this, "Contact modifié !");
        actualiser();
    }

    private void supprimer() {
        if (txtIdTel.getText().isEmpty()) return;
        Utilitaire.executer("DELETE FROM telephone WHERE id=?", txtIdTel.getText());
        actualiser();
        vider();
    }

    private void vider() {
        txtIdTel.setText("");
        txtIDperso.setText("");
        txtInitial.setText("");
        txtnumero.setText("");
    }
}
