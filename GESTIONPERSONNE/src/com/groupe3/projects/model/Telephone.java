package com.groupe3.projects.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.groupe3.projects.database.FactoryConnexion;

public class Telephone {
    private int id;
    private int id_proprietaire;
    private String initial;
    private String numero;

    public Telephone() {}

    public Telephone(int id, int id_proprietaire, String initial, String numero) {
        this.id = id;
        this.id_proprietaire = id_proprietaire;
        this.initial = initial;
        this.numero = numero;
    }

    // Getters et Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getIdProprietaire() { return id_proprietaire; }
    public void setIdProprietaire(int id_proprietaire) { this.id_proprietaire = id_proprietaire; }
    public String getInitial() { return initial; }
    public void setInitial(String initial) { this.initial = initial; }
    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }


    public int genererId() {
        int nouvelId = 1;
        String sql = "SELECT MAX(id) FROM telephone";
        try {
            Connection con = FactoryConnexion.getInstance().getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                nouvelId = rs.getInt(1) + 1;
            }
            rs.close();
            pst.close();
        } catch (SQLException e) { e.printStackTrace(); }
        return nouvelId;
    }

    public void ajouter() {
        String sql = "{CALL sp_insert_telephone(?,?,?,?)}";
        try {
            Connection con = FactoryConnexion.getInstance().getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, this.id);
            pst.setInt(2, this.id_proprietaire);
            pst.setString(3, this.initial);
            pst.setString(4, this.numero);
            pst.executeUpdate();
            pst.close();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void modifier() {
        String sql = "{CALL sp_update_telephone(?,?,?,?)}";
        try {
            Connection con = FactoryConnexion.getInstance().getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, this.id);
            pst.setInt(2, this.id_proprietaire);
            pst.setString(3, this.initial);
            pst.setString(4, this.numero);
            pst.executeUpdate();
            pst.close();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void supprimer(int id) {
        String sql = "{CALL sp_delete_telephone(?)}";
        try {
            Connection con = FactoryConnexion.getInstance().getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, id);
            pst.executeUpdate();
            pst.close();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public static List<Telephone> getTelephones() {
        List<Telephone> liste = new ArrayList<>();
        String sql = "SELECT * FROM telephone";
        try {
            Connection con = FactoryConnexion.getInstance().getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                liste.add(new Telephone(
                    rs.getInt("id"), 
                    rs.getInt("id_proprietaire"),
                    rs.getString("initial"), 
                    rs.getString("numero")
                ));
            }
            rs.close();
            pst.close();
        } catch (SQLException e) { e.printStackTrace(); }
        return liste;
    }

    @Override
    public String toString() {
        return initial + " " + numero;
    }
}
