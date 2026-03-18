package com.groupe3.projects.model;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.groupe3.projects.database.FactoryConnexion;
import com.groupe3.projects.interfaces.IPersonne;

public class Personne implements IPersonne {
    private int id;
    private String nom;
    private String postnom;
    private String prenom;
    private Sexe sex; 
    private List<Telephone> telephonePersonnes;

    // Constructeurs
    public Personne() {
        this.telephonePersonnes = new ArrayList<>();
    }

    public Personne(int id, String nom, String postnom, String prenom, Sexe sex) {
        this.id = id;
        this.nom = nom;
        this.postnom = postnom;
        this.prenom = prenom;
        this.sex = sex;
        this.telephonePersonnes = new ArrayList<>();
    }
    public int genererId() {
        int nouvelId = 1;
        String sql = "SELECT MAX(id) FROM Personne";
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

    @Override
    public List<Personne> lister() throws SQLException {
        List<Personne> liste = new ArrayList<>();
        String sql = "{CALL sp_select_personnes()}";
        Connection conn = FactoryConnexion.getInstance().getConnection();
        try (CallableStatement cstmt = conn.prepareCall(sql);
             ResultSet rs = cstmt.executeQuery()) {
            while (rs.next()) {
                Personne p = new Personne();
                p.setId(rs.getInt("id"));
                p.setNom(rs.getString("nom"));
                p.setPostnom(rs.getString("postnom"));
                p.setPrenom(rs.getString("prenom"));
                String s = rs.getString("sexe"); 
                p.setSex(s != null && s.equalsIgnoreCase("F") ? Sexe.Féminin : Sexe.Masculin);
                liste.add(p);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return liste;
    }

  
    public int enregistrer(Personne p) {
    	   if (p.getId() == 0) {
    	        p.setId(this.genererId());
    	    }
        String sql = "{CALL sp_insert_personne(?, ?, ?, ?, ?)}";
        Connection conn = null;
		try {
			conn = FactoryConnexion.getInstance().getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
        try (CallableStatement cstmt = conn.prepareCall(sql)) {
            cstmt.setInt(1, p.getId());
            cstmt.setString(2, p.getNom());
            cstmt.setString(3, p.getPostnom());
            cstmt.setString(4, p.getPrenom());
            cstmt.setString(5, p.getSex() != null ? p.getSex().toString().substring(0, 1) : null); 
            return cstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int supprimer(int id) {
        String sql = "{CALL sp_delete_personne(?)}";
        Connection conn = null;
		try {
			conn = FactoryConnexion.getInstance().getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
        try (CallableStatement cstmt = conn.prepareCall(sql))
 {
            cstmt.setInt(1, id);
            return cstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public List<Telephone> getTelephonePersonnes(int id) {
        return this.telephonePersonnes;
    }

    @Override
    public String toString() {
        return nom + " " + prenom;
    }


    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPostnom() { return postnom; }
    public void setPostnom(String postnom) { this.postnom = postnom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public Sexe getSex() { return sex; }
    public void setSex(Sexe sex) { this.sex = sex; }

    public boolean validateName(String nom) {
        return nom != null && !nom.trim().isEmpty();
    }

    public static List<Personne> getPersonnes() {
        try {
            return new Personne().lister();
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

	@Override
	public int nouveau() {
	
		 return genererId();
	}

	@Override
	public Personne getPersonne(int id) {
		return null;
	}

}
