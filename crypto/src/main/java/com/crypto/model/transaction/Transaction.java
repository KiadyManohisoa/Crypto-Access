package com.crypto.model.transaction;

import com.crypto.model.crypto.Cryptomonnaie;
import com.crypto.model.crypto.TransactionCrypto;
import com.crypto.model.utilisateur.Utilisateur;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Transaction {
    List<TransactionCrypto> vente;
    List<TransactionCrypto> achat;

    public Transaction() {
    }

    public List<TransactionCrypto> getVente() {
        return vente;
    }

    public void setVente(List<TransactionCrypto> vente) {
        this.vente = vente;
    }

    public List<TransactionCrypto> getAchat() {
        return achat;
    }

    public void setAchat(List<TransactionCrypto> achat) {
        this.achat = achat;
    }
    public static Transaction getTransactionByUtilisateur(Utilisateur u, Connection connection,LocalDateTime dateMax){
        List<TransactionCrypto> achats  = new ArrayList<>();
        List<TransactionCrypto> ventes  = new ArrayList<>();
        TransactionCrypto transactionCrypto=new TransactionCrypto();
        Transaction rep=new Transaction();
        String idAcheteur="";
        String idVendeur="";
        String query = "SELECT * FROM transactionCrypto WHERE dateTransaction <= ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setTimestamp(1,Timestamp.valueOf(dateMax));
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    transactionCrypto.setId(rs.getString("id"));
                    transactionCrypto.setDateTransaction(rs.getTimestamp("dateTransaction").toLocalDateTime());
                    transactionCrypto.setQuantite(rs.getInt("quantite"));
                    transactionCrypto.setD_commission(rs.getDouble("d_commission"));
                    transactionCrypto.setD_prixUnitaire(rs.getDouble("d_prixUnitaire"));
                    transactionCrypto.setCryptomonnaie(Cryptomonnaie.getById(connection,rs.getString("idCryptomonnaie")));
                    idVendeur = rs.getString("idVendeur");
                    idAcheteur = rs.getString("idAcheteur");
                    if (idVendeur != null && !idVendeur.isEmpty()) {
                        transactionCrypto.setVendeur(u);
                        ventes.add(transactionCrypto);
                    }
                    if (idAcheteur != null && !idAcheteur.isEmpty()) {
                        transactionCrypto.setAcheteur(u);
                        achats.add(transactionCrypto);
                    }

                }
            }
            rep.setAchat(achats);
            rep.setVente(ventes);
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return rep;
    }
    public static double calculateTotal(List<TransactionCrypto> transactionCryptos) {
        double total = 0;
        for (TransactionCrypto transactionCrypto : transactionCryptos) {
            total += transactionCrypto.getQuantite()*transactionCrypto.getD_prixUnitaire();
        }
        return total;
    }


}
