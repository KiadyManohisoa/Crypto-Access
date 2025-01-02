package com.crypto.model.utilisateur;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.crypto.exception.model.ValeurInvalideException;

public class Utilisateur {

    String id ;
    String token;
    String nom;
    String prenom;
    Date dateNaissance;
    String mail;

    
    // Getters et Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Date getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public void setDateNaissance(String dateNaissance) throws ValeurInvalideException{
        try {
            Date d = Date.valueOf(dateNaissance);
            setDateNaissance(d);
        } catch(Exception err) {
            throw new ValeurInvalideException("Valeur de la date de naissance invalide");
        }
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    // Constructeur utilisant les setters
    public Utilisateur(String id, String token, String nom, String prenom, Date dateNaissance, String mail) {
        setId(id);
        setToken(token);
        setNom(nom);
        setPrenom(prenom);
        setDateNaissance(dateNaissance);
        setMail(mail);
    }

    public Utilisateur(String nom, String prenom, String dateNaissance, String mail) throws ValeurInvalideException {
      
        setNom(nom);
        setPrenom(prenom);
        setDateNaissance(dateNaissance);
        setMail(mail);
    }


    // Méthode pour insérer un utilisateur dans la base de données
    void insertUtilisateur(Connection connection) throws Exception {
       
        String query = "INSERT INTO utilisateur (id, nom, prenom, date_naissance, mail) VALUES (DEFAULT, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, getNom());
            statement.setString(2, getPrenom());
            statement.setDate(3, getDateNaissance());
            statement.setString(4, getMail());
            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    setId(generatedKeys.getString(1)); // Assigner l'ID généré
                } else {
                    throw new SQLException("Échec de l'insertion, aucun ID généré.");
                }
            }
        }
    }

    // Méthode pour créer un portefeuille
    void creerPortefeuille(Connection connection) throws Exception {
        String query = "INSERT INTO portefeuille (id, id_idUtilisateur) VALUES (DEFAULT, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, getId());
            statement.executeUpdate();
        }
    }

    public void insert(Connection connection) throws Exception {
        System.out.println("Appel de la fonction insert");
        try {
            connection.setAutoCommit(false);
            insertUtilisateur(connection);
            creerPortefeuille(connection);
            connection.commit();
        } catch (Exception e) {
            System.out.println("Erreur :"+e.getMessage());
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public static Utilisateur getByMail(Connection connection, String mail) throws SQLException {
        String query = "SELECT * FROM utilisateur WHERE mail = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, mail);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    // Si un utilisateur est trouvé, on le crée et on retourne l'objet Utilisateur
                    String id = resultSet.getString("id");
                    String nom = resultSet.getString("nom");
                    String prenom = resultSet.getString("prenom");
                    String email = resultSet.getString("mail");
                    Date dateNaissance = resultSet.getDate("date_naissance");
                    return new Utilisateur(id, null, nom, prenom, dateNaissance, email);
                }
            }
        }
                return null;
    }
}
