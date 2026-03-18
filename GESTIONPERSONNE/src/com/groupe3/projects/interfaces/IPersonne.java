package com.groupe3.projects.interfaces;

import com.groupe3.projects.model.Personne;
import com.groupe3.projects.model.Telephone;

import java.sql.SQLException;
import java.util.List;

public interface IPersonne {
    int nouveau();
    int enregistrer(Personne p);
    int supprimer(int id);
    Personne getPersonne(int id);
    List<Telephone> getTelephonePersonnes(int id);
    List<Personne> lister() throws SQLException; 
    

    String toString(); 
}
