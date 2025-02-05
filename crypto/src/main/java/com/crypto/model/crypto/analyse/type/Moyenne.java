package com.crypto.model.crypto.analyse.type;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.crypto.model.crypto.ChangementCoursCrypto;
import com.crypto.model.crypto.analyse.TypeAnalyse;

public class Moyenne extends TypeAnalyse {
    
    public Moyenne(String id, String libelle) {
        super(id, libelle);
    }

    public ChangementCoursCrypto getMoyennant(List<ChangementCoursCrypto> lsChangements) {
        if (lsChangements != null && !lsChangements.isEmpty()) {
            double somme = 0;
            for (ChangementCoursCrypto changement : lsChangements) {
                somme += changement.getValeur();
            }
            double moyenne = somme / lsChangements.size();

            ChangementCoursCrypto moyenneChangement = new ChangementCoursCrypto();
            moyenneChangement.setCryptomonnaie(lsChangements.get(0).getCryptomonnaie()); 
            moyenneChangement.setValeur(moyenne);

            return moyenneChangement;
        }
        return null;
    }

    public ChangementCoursCrypto[] appliquerTypeAnalyse(HashMap<String, List<ChangementCoursCrypto>> changements) {
        List<ChangementCoursCrypto> resultats = new ArrayList<>();

        for (Map.Entry<String, List<ChangementCoursCrypto>> entry : changements.entrySet()) {
            List<ChangementCoursCrypto> listeChangements = entry.getValue();
            resultats.add(this.getMoyennant(listeChangements));
        }

        return resultats.toArray(new ChangementCoursCrypto[0]);
    }


}
