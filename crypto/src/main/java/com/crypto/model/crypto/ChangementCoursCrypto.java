package com.crypto.model.crypto;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;

import com.crypto.exception.model.ValeurInvalideException;

public class ChangementCoursCrypto {
    
    String id ; 
    Cryptomonnaie cryptomonnaie ;
    double valeur ; 
    Date date ; 

    // Getter et setter
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Cryptomonnaie getCryptomonnaie() {
        return this.cryptomonnaie;
    }

    public void setCryptomonnaie(Cryptomonnaie cryptomonnaie) {
        this.cryptomonnaie = cryptomonnaie;
    }

    public void setCryptomonnaie(String idCryptomonnaie) {
        setCryptomonnaie(new Cryptomonnaie(idCryptomonnaie));
    }

    public double getValeur() {
        return this.valeur;
    }

    public void setValeur(double valeur) {
        this.valeur = valeur;
    }

    public void setValeur(String valeur) throws ValeurInvalideException {
        try {
            double d = Double.valueOf(valeur) ;
            setValeur(d);
        } catch (Exception e) {
           throw new ValeurInvalideException("Nouvelle valeur de crypto on numérique");
        }
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        if(date==null) {
            this.date = new Date(System.currentTimeMillis());
        }
        else this.date = date;
    }

    public void setDate(String date) throws ValeurInvalideException{
        try {
            Date d = Date.valueOf(date);
            setDate(d);
        } catch (Exception e) {
            setDate(new Date(System.currentTimeMillis()));
        }
        
    }

    // Constructeur
    public ChangementCoursCrypto() {
    }

    public ChangementCoursCrypto(Cryptomonnaie cryptomonnaie, double valeur, Date date) {
        setCryptomonnaie(cryptomonnaie);
        setValeur(valeur);
        setDate(date);
    }

    public ChangementCoursCrypto(String id, Cryptomonnaie cryptomonnaie, double valeur, Date date) {
        setId(id);
        setCryptomonnaie(cryptomonnaie);
        setValeur(valeur);
        setDate(date);
    }

    public ChangementCoursCrypto(String idCryptomonnaie, String valeur, String date) throws ValeurInvalideException{
        setCryptomonnaie(idCryptomonnaie);
        setValeur(valeur);
        setDate(date);
    }

    public ChangementCoursCrypto(Cryptomonnaie cryptomonnaie) throws ValeurInvalideException{
        setCryptomonnaie(cryptomonnaie);
        setValeur(cryptomonnaie.getValeur());
        setDate("");
    }

    public void insererHistorique(Connection connection) throws Exception {

        String query = "INSERT INTO historiqueCrypto (id, cours, dateChangement, idCryptomonnaie) VALUES (DEFAULT, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setDouble(1, getValeur());  // Action spécifique, ici "Insertion"
            statement.setDate(2, getDate());  // Action spécifique, ici "Insertion"
            statement.setString(3, getCryptomonnaie().getId());  // Action spécifique, ici "Insertion"
            // Exécuter la requête pour insérer l'historique
            statement.executeUpdate();
        }
    }
    
}
