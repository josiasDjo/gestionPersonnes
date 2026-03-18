package com.groupe3.projects.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.groupe3.projects.database.FactoryConnexion;

public class Adresse {
    private int id;
    private String quartier;
    private String commune;
    private String ville;
    private String pays;

    public Adresse() {}

    public Adresse(int id, String quartier, String commune, String ville, String pays) {
        this.id = id;
        this.quartier = quartier;
        this.commune = commune;
        this.ville = ville;
        this.pays = pays;
    }

  
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getQuartier() { return quartier; }
    public void setQuartier(String quartier) { this.quartier = quartier; }
    public String getCommune() { return commune; }
    public void setCommune(String commune) { this.commune = commune; }
    public String getVille() { return ville; }
    public void setVille(String ville) { this.ville = ville; }
    public String getPays() { return pays; }
    public void setPays(String pays) { this.pays = pays; }


    public int genererId() {
        int nouvelId = 1;
        String sql = "SELECT MAX(id) FROM adresse";
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
        String sql = "{CALL sp_adresse(?,?,?,?,?)}";
        try {
            Connection con = FactoryConnexion.getInstance().getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, this.id);
            pst.setString(2, this.quartier);
            pst.setString(3, this.commune);
            pst.setString(4, this.ville);
            pst.setString(5, this.pays);
            pst.executeUpdate();
            pst.close(); 
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void modifier() {
        String sql = "{CALL sp_adresse(?,?,?,?,?)}";
        try {
            Connection con = FactoryConnexion.getInstance().getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, this.id);
            pst.setString(2, this.quartier);
            pst.setString(3, this.commune);
            pst.setString(4, this.ville);
            pst.setString(5, this.pays);
            pst.executeUpdate();
            pst.close();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void supprimer(int id) {
        String sql = "{CALL sp_adresse(?)}";
        try {
            Connection con = FactoryConnexion.getInstance().getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, id);
            pst.executeUpdate();
            pst.close();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public static List<Adresse> getAdresses() {
        List<Adresse> liste = new ArrayList<>();
        String sql = "SELECT * FROM adresse";
        try {
            Connection con = FactoryConnexion.getInstance().getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                liste.add(new Adresse(
                    rs.getInt("id"), rs.getString("quartier"),
                    rs.getString("commune"), rs.getString("ville"), rs.getString("pays")
                ));
            }
            rs.close();
            pst.close();
        } catch (SQLException e) { e.printStackTrace(); }
        return liste;
    }

    @Override
    public String toString() {
        return quartier + ", " + commune + " (" + ville + ")";
    }
}
