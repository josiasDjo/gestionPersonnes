package com.groupe3.projects.gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class FenetrePrincipale extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    /**
     * Lancement de l'application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    FenetrePrincipale frame = new FenetrePrincipale();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Création de la fenêtre.
     */
    public FenetrePrincipale() {
        setTitle("Accueil - Groupe 3");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 710, 426);
        
        // Initialisation du contentPane
        contentPane = new JPanel();
        contentPane.setForeground(Color.WHITE);
        contentPane.setBackground(Color.WHITE);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        
        JLabel lblNewLabel = new JLabel("BIENVENU CHER USERS ");
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setFont(new Font("Algerian", Font.PLAIN, 40));
        
        // Création du bouton LOGIN
        JButton btnNewButton = new JButton("LOGIN");
        btnNewButton.setFont(new Font("Algerian", Font.PLAIN, 14));

        // Action pour ouvrir la fenêtre Login
        btnNewButton.addActionListener(e -> {
            Login fenetreLogin = new Login();
            fenetreLogin.setVisible(true);
            this.dispose(); 
        });

        // Layout
        GroupLayout gl_contentPane = new GroupLayout(contentPane);
        gl_contentPane.setHorizontalGroup(
            gl_contentPane.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_contentPane.createSequentialGroup()
                    .addGap(74)
                    .addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 500, GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(120, Short.MAX_VALUE))
                .addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
                    .addContainerGap(300, Short.MAX_VALUE)
                    .addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
                    .addGap(280))
        );
        gl_contentPane.setVerticalGroup(
            gl_contentPane.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_contentPane.createSequentialGroup()
                    .addGap(40)
                    .addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                    .addGap(60)
                    .addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(120, Short.MAX_VALUE))
        );
        contentPane.setLayout(gl_contentPane);
    }
}
