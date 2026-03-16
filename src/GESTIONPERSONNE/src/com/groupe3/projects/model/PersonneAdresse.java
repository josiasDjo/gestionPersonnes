package com.groupe3.projects.model;

import com.groupe3.projects.database.Utilitaire;

public class PersonneAdresse {
    private int id;
    private int id_adresse;
    private int id_personne;
    private String avenue;
    private int numero_avenue;

    public PersonneAdresse() {}

    public PersonneAdresse(int id, int id_adresse, int id_personne, String avenue, int numero) {
        this.id = id;
        this.id_adresse = id_adresse;
        this.id_personne = id_personne;
        this.avenue = avenue;
        this.numero_avenue = numero;
    }

    // --- Getters et Setters ---
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getId_adresse() { return id_adresse; }
    public void setId_adresse(int id_adresse) { this.id_adresse = id_adresse; }

    public int getId_personne() { return id_personne; }
    public void setId_personne(int id_personne) { this.id_personne = id_personne; }

    public String getAvenue() { return avenue; }
    public void setAvenue(String avenue) { this.avenue = avenue; }

    public int getNumero() { return numero_avenue; }
    public void setNumero(int numero) { this.numero_avenue = numero; }

    public int nouveau() {
        return Utilitaire.genererProchainId("personne_adresse");
    }

    public int enregistrer(PersonneAdresse pa) {
        String sql = "INSERT INTO personne_adresse (id, id_adresse, id_personne, avenue, numero_avenue) VALUES (?, ?, ?, ?, ?)";
        return Utilitaire.executer(sql, pa.getId(), pa.getId_adresse(), pa.getId_personne(), pa.getAvenue(), pa.getNumero());
    }

    public int supprimer(int id) {
        String sql = "DELETE FROM personne_adresse WHERE id = ?";
        return Utilitaire.executer(sql, id);
    }

    @Override
    public String toString() {
        return "Avenue " + avenue + ", n° " + numero_avenue;
    }
}
