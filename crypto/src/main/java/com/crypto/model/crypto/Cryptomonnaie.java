package com.crypto.model.crypto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import com.crypto.exception.model.ValeurInvalideException;
import com.crypto.model.crypto.donnees.Donnees;
import com.crypto.service.util.Util;

public class Cryptomonnaie {

    private String id;
    private String nom;
    private double valeur;


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

    // Constructeur vide
    public Cryptomonnaie() {}
    

    public Cryptomonnaie(String id) {
        setId(id);
    }

    public Cryptomonnaie(String id, String nom, double valeur) {
        setId(id);
        setNom(nom);
        setValeur(valeur);
    }
    

    public static Cryptomonnaie[] getAll(Connection connection) throws SQLException {
        
        String query = "SELECT * FROM cryptomonnaie";
        
        try (PreparedStatement statement = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {            
            
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
                    cryptomonnaie.setValeur(resultSet.getDouble("valeur"));
                    cryptomonnaies[index++] = cryptomonnaie;
                }

                return cryptomonnaies;
            }
        }
    }

    public static Cryptomonnaie getById(Connection connection, String id) throws SQLException {
        String query = "SELECT * FROM cryptomonnaie WHERE id = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Cryptomonnaie cryptomonnaie = new Cryptomonnaie();
                    cryptomonnaie.setId(resultSet.getString("id"));
                    cryptomonnaie.setNom(resultSet.getString("nom"));
                    cryptomonnaie.setValeur(resultSet.getDouble("valeur"));
                    return cryptomonnaie;
                }
            }
        }
        
        return null;
    }

    public void insert(Connection connection) throws Exception {
        String query = "INSERT INTO cryptomonnaie (id, nom, valeur) VALUES (DEFAULT, ?, ?)";

        connection.setAutoCommit(false);
        try (PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {            
            
            statement.setString(1, getNom());
            statement.setDouble(2, getValeur());
            
            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    setId(generatedKeys.getString(1)); // Assigner l'ID généré
                } else {
                    throw new SQLException("Échec de l'insertion, aucun ID généré.");
                }
            }
            
            ChangementCoursCrypto changement = new ChangementCoursCrypto(this);
            changement.insererHistorique(connection);
        } catch(Exception err){
            connection.rollback();
            throw err ;
        } finally{
            connection.setAutoCommit(true);
        }
    }

    public void update(Connection connection) throws Exception {
        String query = "UPDATE cryptomonnaie SET valeur = ? WHERE id = ?";
    
        connection.setAutoCommit(false);
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            
            // Définir les paramètres pour la mise à jour
            statement.setDouble(1, getValeur());
            statement.setString(2, getId());
    
            int affectedRows = statement.executeUpdate();
    
            if (affectedRows == 0) {
                throw new SQLException("Échec de la mise à jour, aucune ligne affectée.");
            }
    
            // Insérer un changement de cours historique
            ChangementCoursCrypto changement = new ChangementCoursCrypto(this);
            changement.insererHistorique(connection);
    
        } catch (Exception err) {
            connection.rollback(); // Annuler les changements en cas d'erreur
            throw err;
        } finally {
            connection.setAutoCommit(true); // Restaurer le mode auto-commit
        }
    }

    public static void updateBatch(Connection connection, Cryptomonnaie[] cryptomonnaies) throws Exception {
        String query = "UPDATE cryptomonnaie SET valeur = ? WHERE id = ?";
        
        connection.setAutoCommit(false);
        try (PreparedStatement statement = connection.prepareStatement(query)) {
    
            for (Cryptomonnaie crypto : cryptomonnaies) {
                statement.setDouble(1, crypto.getValeur());
                statement.setString(2, crypto.getId());
                statement.addBatch(); 
            }
    
            int[] affectedRows = statement.executeBatch();
    
            for (int count : affectedRows) {
                if (count == 0) {
                    throw new SQLException("Échec de la mise à jour pour au moins une cryptomonnaie.");
                }
            }
    
            for (Cryptomonnaie crypto : cryptomonnaies) {
                ChangementCoursCrypto changement = new ChangementCoursCrypto(crypto);
                changement.insererHistorique(connection);
            }
            connection.commit(); 
        } catch (Exception err) {
            connection.rollback(); 
            throw err;
        } finally {
            // connection.setAutoCommit(true); 
        }
    }
    
    public void nouveauCours(Connection connection) throws Exception {

        Cryptomonnaie[] cryptomonnaies = getAll(connection);
        connection.setAutoCommit(false);
        try {
            for (Cryptomonnaie cryptomonnaie : cryptomonnaies) {
                Donnees donnees = new Donnees(cryptomonnaie.getValeur());
                cryptomonnaie.setValeur(donnees.genererValeurAleatoire());
                // cryptomonnaie.setDate(Util.getDateHeureMaintenant());
                // System.out.println("Données est "+donnees.toString());
            }
    
            updateBatch(connection, cryptomonnaies);
            connection.commit();
            // System.out.println("Insérée");
        } catch (Exception e) {
            connection.rollback();
            e.printStackTrace();
            throw e ;
        } finally{
            connection.setAutoCommit(true);
        }

    }

    @Override
    public String toString() {
        return "Cryptomonnaie{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", valeur=" + valeur +
                '}';
    }
}

