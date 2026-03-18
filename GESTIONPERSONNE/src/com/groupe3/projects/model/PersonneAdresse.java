package com.groupe3.projects.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.groupe3.projects.database.FactoryConnexion;

public class PersonneAdresse {
    private int id;
    private String avenue;
    private int numero_avenue;

    public PersonneAdresse() {}

    public PersonneAdresse(int id, String avenue, int numero_avenue) {
        this.id = id;
        this.avenue = avenue;
        this.numero_avenue = numero_avenue;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getAvenue() { return avenue; }
    public void setAvenue(String avenue) { this.avenue = avenue; }
    public int getNumero() { return numero_avenue; }
    public void setNumero(int numero_avenue) { this.numero_avenue = numero_avenue; }


    public int nouveau() {
        int nouvelId = 1;
        String sql = "SELECT MAX(id) FROM personne_adresse";
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

    public void enregistrer(PersonneAdresse pa) {
        String sql = "{CALL sp_insert_personne_adresse(?,?,?)}";
        try {
            Connection con = FactoryConnexion.getInstance().getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, pa.getId());
            pst.setString(2, pa.getAvenue());
            pst.setInt(3, pa.getNumero());
            pst.executeUpdate();
            pst.close();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void supprimer(int id) {
        String sql = "{CALL sp_delete_personne_adresse(?)}";
        try {
            Connection con = FactoryConnexion.getInstance().getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, id);
            pst.executeUpdate();
            pst.close();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public static List<PersonneAdresse> getPersonneAdresses() {
        List<PersonneAdresse> liste = new ArrayList<>();
        String sql = "SELECT * FROM personne_adresse";
        try {
            Connection con = FactoryConnexion.getInstance().getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                liste.add(new PersonneAdresse(
                    rs.getInt("id"),
                    rs.getString("avenue"),
                    rs.getInt("numero_avenue")
                ));
            }
            rs.close();
            pst.close();
        } catch (SQLException e) { e.printStackTrace(); }
        return liste;
    }

    @Override
    public String toString() {
        return avenue + " N°" + numero_avenue;
    }
}
