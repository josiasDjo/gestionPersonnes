package com.groupe3.projects.interfaces;

import java.util.List;
import com.groupe3.projects.model.Personne;
import com.groupe3.projects.model.Telephone;

public interface Ipersonne {

    int nouveau(); 
    
    int enregistrer(Personne personne); 
    int supprimer(int id); 
    
    Personne getPersonne(int id); 
    
    List<Telephone> getTelephonePersonnes(int id); 

    String toString(); 
}
