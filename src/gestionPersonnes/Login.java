package com.groupe3.projects.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Login extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtUser;
    private JPasswordField txtPassword;

    public Login() {
        setTitle("Connexion - Groupe 3");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 400, 250);
        setLocationRelativeTo(null); 

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(20, 20, 20, 20));
        contentPane.setLayout(new GridLayout(3, 2, 10, 10));
        setContentPane(contentPane);

        contentPane.add(new JLabel("Utilisateur :"));
        txtUser = new JTextField();
        contentPane.add(txtUser);

        contentPane.add(new JLabel("Mot de passe :"));
        txtPassword = new JPasswordField();
        contentPane.add(txtPassword);

        JButton btnConnexion = new JButton("SE CONNECTER");
        btnConnexion.addActionListener(e -> {
            new Workflow().setVisible(true); 
            this.dispose(); 
        });
        contentPane.add(btnConnexion);

        JButton btnAnnuler = new JButton("ANNULER");
        btnAnnuler.addActionListener(e -> System.exit(0));
        contentPane.add(btnAnnuler);
    }
}
