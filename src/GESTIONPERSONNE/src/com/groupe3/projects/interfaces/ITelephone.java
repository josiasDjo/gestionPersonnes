package com.groupe3.projects.interfaces;

import com.groupe3.projects.model.Telephone;

public interface ITelephone {

    int nouveau();

    int enregistrer(Telephone telephone);

    int supprimer(int id);
    
    Telephone getTelephone(int id);

    String toString();
}
