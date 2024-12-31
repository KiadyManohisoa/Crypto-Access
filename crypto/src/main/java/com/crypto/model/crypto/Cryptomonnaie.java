package com.crypto.model.crypto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.crypto.exception.model.ValeurInvalideException;
import com.crypto.model.utilisateur.Utilisateur;

public class Cryptomonnaie {

    private String id;
    private String nom;
    private int quantite;
    private double valeur;

    // Constructeur vide
    public Cryptomonnaie() {}

    // Getters et setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public double getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public void setQuantite(String quantite) throws ValeurInvalideException{
        try {
            int q = Integer.valueOf(quantite);
            setQuantite(q);
        } catch (Exception e) {
            throw new ValeurInvalideException("Valeur de quantité non numérique");
        }
    }

    public double getValeur() {
        return valeur;
    }

    public void setValeur(double valeur) {
        this.valeur = valeur;
    }

    public void setValeur(String valeur) throws ValeurInvalideException{
        try {
            double d = Double.valueOf(valeur);
            setValeur(d);
        } catch (Exception e) {
            throw new ValeurInvalideException("Valeur de crypto non numérique");
        }
    }

    public static Cryptomonnaie[] getAllByCriteria(Connection connection, Utilisateur utilisateur) throws SQLException {
        
        String query = "SELECT id, nom, quantite, valeur FROM cryptomonnaie WHERE 1=1 ";
        if(utilisateur!=null && !utilisateur.getId().isEmpty()) query+= " and idUtilisateur = ?" ; 
        
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            if(utilisateur!=null && !utilisateur.getId().isEmpty()) statement.setString(1, utilisateur.getId());
            try (ResultSet resultSet = statement.executeQuery()) {
                
                resultSet.last();  
                int rowCount = resultSet.getRow();
                resultSet.beforeFirst(); 

                Cryptomonnaie[] cryptomonnaies = new Cryptomonnaie[rowCount];
                int index = 0;

                while (resultSet.next()) {
                    Cryptomonnaie cryptomonnaie = new Cryptomonnaie();
                    cryptomonnaie.setId(resultSet.getString("id"));
                    cryptomonnaie.setNom(resultSet.getString("nom"));
                    cryptomonnaie.setQuantite(resultSet.getInt("quantite"));
                    cryptomonnaie.setValeur(resultSet.getDouble("valeur"));
                    cryptomonnaies[index++] = cryptomonnaie;
                }

                return cryptomonnaies;
            }
        }
    }

    public static Cryptomonnaie getById(Connection connection, String id) throws SQLException {
        String query = "SELECT id, nom, quantite, valeur FROM cryptomonnaie WHERE id = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Cryptomonnaie cryptomonnaie = new Cryptomonnaie();
                    cryptomonnaie.setId(resultSet.getString("id"));
                    cryptomonnaie.setNom(resultSet.getString("nom"));
                    cryptomonnaie.setQuantite(resultSet.getInt("quantite"));
                    cryptomonnaie.setValeur(resultSet.getDouble("valeur"));
                    return cryptomonnaie;
                }
            }
        }
        
        return null;
    }

    @Override
    public String toString() {
        return "Cryptomonnaie{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", quantite=" + quantite +
                ", valeur=" + valeur +
                '}';
    }
}

