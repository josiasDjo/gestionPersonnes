package gestionPersonnes.Class;

import java.sql.PreparedStatement;
import java.sql.SQLException;
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
	
	
	@Override
	public int save() throws SQLException {
		String sql = "INSERT INTO employee(id, nom, postnom, prenom) VALUES(?, ?, ?, ?)";

		try (PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql)) {
			ps.setInt(1, this.getLastId());
			ps.setString(2, this.nom);
			ps.setString(3, this.postnom);
			ps.setString(4, this.prenom);

			int result = ps.executeUpdate();

			return result;
		}
	}
	
}
