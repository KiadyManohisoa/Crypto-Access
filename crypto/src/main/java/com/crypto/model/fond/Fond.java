package com.crypto.model.fond;


import com.crypto.model.crypto.Cryptomonnaie;
import com.crypto.model.portefeuille.PorteFeuilleDetails;
import com.crypto.model.utilisateur.Utilisateur;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Fond {
    double montant;
    List<MouvementFond> depot;
    List<MouvementFond> retrait;

    public Fond() {
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public List<MouvementFond> getDepot() {
        return depot;
    }

    public void setDepot(List<MouvementFond> depot) {
        this.depot = depot;
    }

    public List<MouvementFond> getRetrait() {
        return retrait;
    }

    public void setRetrait(List<MouvementFond> retrait) {
        this.retrait = retrait;
    }
    public static Fond getFondByUtilisateur(Utilisateur u,Connection connection){
        List<MouvementFond> depot  = new ArrayList<>();
        List<MouvementFond> retrait  = new ArrayList<>();
        MouvementFond dep=new MouvementFond();
        MouvementFond ret=new MouvementFond();
        Fond rep=new Fond();
        double montant=0;
        double montantTemp = 0;
        String query = "SELECT * FROM fond";
        try {
        PreparedStatement statement = connection.prepareStatement(query);
        try (ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                montantTemp = rs.getDouble("montant");
                if(montantTemp > 0 && rs.getString("idTransactionCrypto").equals("")){
                    dep.setId(rs.getString("id"));
                    dep.setMontant(montantTemp);
                    Timestamp timestamp = rs.getTimestamp("dateMouvement");
                    if (timestamp != null) {
                        LocalDateTime localDateTime = timestamp.toLocalDateTime();
                        dep.setDateMouvement(localDateTime);
                    }
                    depot.add(dep);
                }
                if(montantTemp < 0 && rs.getString("idTransactionCrypto").equals("")){
                    ret.setId(rs.getString("id"));
                    ret.setMontant(montantTemp);
                    Timestamp timestamp = rs.getTimestamp("dateMouvement");
                    if (timestamp != null) {
                        LocalDateTime localDateTime = timestamp.toLocalDateTime();
                        ret.setDateMouvement(localDateTime);
                    }
                    retrait.add(dep);

                }
                montant+=montantTemp;
            }
        }
        rep.setMontant(montant);
        rep.setDepot(depot);
        rep.setRetrait(retrait);
    }
        catch(Exception ex){
        ex.printStackTrace();
    }
return rep;
    }


}
