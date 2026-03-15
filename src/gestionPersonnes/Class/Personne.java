package gestionPersonnes.Class;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import gestionPersonnes.Database.DatabaseConnection;
import gestionPersonnes.Interfaces.IPersonne;

public class Personne implements IPersonne {
    public int id;
    public String nom;
    public String postnom;
    public String prenom;
    public String sexe;
    public String nomComptet;
    public List<Telephone> telephonePersonnes;
    
    
    public Personne() {
    }
    
    public Personne(int id, String nom, String postnom, String prenom, String sexe,
            List<Telephone> telephonePersonnes) {
        this.id = id;
        this.nom = nom;
        this.postnom = postnom;
        this.prenom = prenom;
        this.sexe = sexe;
        this.telephonePersonnes = telephonePersonnes;
    }

    public int getId() {
        return id;
    }

	public void setId(int id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPostnom() {
		return postnom;
	}

	public void setPostnom(String postnom) {
		this.postnom = postnom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public void setSexe(String sexe) {
		this.sexe = sexe;
	}
    
    @Override
    public int save() throws SQLException {
         String sql = "{call sp_insert_personne(?, ?, ?, ?, ?)}";
         
         DatabaseConnection dbConnection = new DatabaseConnection();
         
        try (PreparedStatement ps = dbConnection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, this.getLastId());
            ps.setString(2, this.nom);
            ps.setString(3, this.postnom);
            ps.setString(4, this.prenom);
            ps.setString(5, this.sexe);

            int result = ps.executeUpdate();
            
            dbConnection.closeConnection();
            
            return result;
        }
    }
    
    @Override
    public int update() throws SQLException {
        String sql = "{call sp_update_personne(?, ?, ?, ?, ?)}";
        
        DatabaseConnection dbConnection = new DatabaseConnection();
        
        try (PreparedStatement ps = dbConnection.getConnection().prepareStatement(sql)) {
            
            ps.setString(1, this.nom);
            ps.setString(2, this.postnom);
            ps.setString(3, this.prenom);
            ps.setString(4, this.sexe);
            ps.setInt(5, this.id);
            
            int result = ps.executeUpdate();
            
            if (result < 1) {
                throw new IllegalArgumentException(String.format("Invalid ID provided: '%s'.", this.id));
            }
            dbConnection.closeConnection();
            
            return result;
            
        } catch (SQLException e) {
            System.out.println("Erreur lors de la modification : " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
    
	@Override
	public int delete() throws SQLException {
        String sql = "{call sp_update_personne(?)}";
        
        DatabaseConnection dbConnection = new DatabaseConnection();

		try (PreparedStatement ps = dbConnection.getConnection().prepareStatement(sql)) {
			ps.setInt(1, this.id);

			int result = ps.executeUpdate();

			if (result < 1) {
				throw new IllegalArgumentException(String.format("Invalid ID provided: '%s'.", this.id));
			}

			return result;
		}
	}
	
	@Override
	public List<IPersonne> listPersons() throws SQLException {
		String sql = "{call sp_update_personne(?)}";
		List<IPersonne> lstPersons = new ArrayList<>();
		
		DatabaseConnection dbConnection = new DatabaseConnection();
		
		try (PreparedStatement ps = dbConnection.getConnection().prepareStatement(sql)) {

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					IPersonne pers = new Personne();
					((Personne) pers).setId(rs.getInt("id"));
					((Personne) pers).setNom(rs.getString("nom"));
					((Personne) pers).setPostnom(rs.getString("postnom"));
					((Personne) pers).setPrenom(rs.getString("prenom"));
					((Personne) pers).setSexe(rs.getString("sexe"));
					lstPersons.add(pers);
				}
			}
			return lstPersons;
		}
	}
	
	@Override
	public IPersonne personGet(int id) throws SQLException {
		String sql = "{call sp_update_personne(?)}";
		IPersonne pers = new Personne();
		((Personne) pers).setId(id);
		
		DatabaseConnection dbConnection = new DatabaseConnection();
		
		try (PreparedStatement ps = dbConnection.getConnection().prepareStatement(sql)) {
			ps.setInt(1, id);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					((Personne) pers).setNom(rs.getString("nom"));
					((Personne) pers).setPostnom(rs.getString("postnom"));
					((Personne) pers).setPrenom(rs.getString("prenom"));
					((Personne) pers).setSexe(rs.getString("sexe"));
				}
			}
			return pers;
		}
	}
	
	@Override
	public int getLastId() throws SQLException {
		int lastID = 0;
		String sql = "SELECT MAX(id) AS lastId FROM employee";
		
		DatabaseConnection dbConnection = new DatabaseConnection();
		
		try (PreparedStatement ps = dbConnection.getConnection().prepareStatement(sql)) {

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					String result = rs.getString("lastId");
					if (result == null) {
						lastID = 1;
					} else {
						lastID = Integer.parseInt(result) + 1;
					}
				}
			}
		}
		return lastID;
	}
	
	@Override
	public String toString() {
		return String.format("Person: %s %s %s has s : %s", this.nom, this.postnom, this.prenom, this.sexe);
	}
	
}