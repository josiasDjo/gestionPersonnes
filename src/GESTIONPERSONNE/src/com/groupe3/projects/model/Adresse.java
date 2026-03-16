package com.groupe3.projects.model;

public class Adresse {
    private int id;
    private String quartier;
    private String commune;
    private String ville;
    private String pays;

    public Adresse() {
    }

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

    @Override
    public String toString() {
        return quartier + ", " + commune + ", " + ville + " (" + pays + ")";
    }
}
