package com.groupe3.projects.model;
import java.util.List;

public class Personne {

	private int id;
	private String nom;
	private String postnom;
	private String prenom;
	private Sexe sexe;
	private List<Telephone> telephonePersonnes;
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
	public Sexe getSexe() {
		return sexe;
	}
	public void setSexe(Sexe sexe) {
		this.sexe = sexe;
	}
	public List<Telephone> getTelephonePersonnes() {
		return telephonePersonnes;
	}
	public void setTelephonePersonnes(List<Telephone> telephonePersonnes) {
		this.telephonePersonnes = telephonePersonnes;
	}
	public Personne(int id, String nom, String postnom, String prenom, Sexe sexe, List<Telephone> telephonePersonnes) {
		super();
		this.id = id;
		this.nom = nom;
		this.postnom = postnom;
		this.prenom = prenom;
		this.sexe = sexe;
		this.telephonePersonnes = telephonePersonnes;
	}
	
	 public boolean validateName(String nom) {
	        return nom != null && nom.length() >= 2;
	    }

	    @Override
	    public String toString() {
	        return nom + " " + postnom + " " + prenom;
	
}
	    }
