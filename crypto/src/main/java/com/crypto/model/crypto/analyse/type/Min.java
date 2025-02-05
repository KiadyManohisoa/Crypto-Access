package com.crypto.model.crypto.analyse.type;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.crypto.model.crypto.ChangementCoursCrypto;
import com.crypto.model.crypto.analyse.TypeAnalyse;

public class Min extends TypeAnalyse {
    
    public Min(String id, String libelle) {
        super(id, libelle);
    }


    public ChangementCoursCrypto[] appliquerTypeAnalyse(HashMap<String, List<ChangementCoursCrypto>> changements) {
        List<ChangementCoursCrypto> resultats = new ArrayList<>();

        for (Map.Entry<String, List<ChangementCoursCrypto>> entry : changements.entrySet()) {
            List<ChangementCoursCrypto> listeChangements = entry.getValue();

            if (listeChangements != null && !listeChangements.isEmpty()) {
                ChangementCoursCrypto maxChangement = Collections.min(listeChangements, Comparator.comparingDouble(c -> c.getValeur()));
                resultats.add(maxChangement);
            }
        }

        return resultats.toArray(new ChangementCoursCrypto[0]);
    }

}
