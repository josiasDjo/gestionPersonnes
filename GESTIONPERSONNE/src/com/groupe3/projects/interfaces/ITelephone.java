package com.groupe3.projects.interfaces;

import com.groupe3.projects.model.Telephone;
import java.util.List;

public interface ITelephone {
    int nouveau();
    int enregistrer(Telephone telephone);
    int supprimer(int id);
    ITelephone getTelephone(int id);
    List<Telephone> getTelephones();
    String toString();
}
