package com.groupe3.projects.model;

public class Telephone {
    private int id;
    private int id_proprietaire;
    private String initial;
    private String numero;

    public Telephone() {
    }

    public Telephone(int id, int id_proprietaire, String initial, String numero) {
        this.id = id;
        this.id_proprietaire = id_proprietaire;
        this.initial = initial;
        this.numero = numero;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getIdProprietaire() { return id_proprietaire; }
    public void setIdProprietaire(int id_proprietaire) { this.id_proprietaire = id_proprietaire; }

    public String getInitial() { return initial; }
    public void setInitial(String initial) { this.initial = initial; }

    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }

    @Override
    public String toString() {
        return initial + " " + numero;
    }
}
