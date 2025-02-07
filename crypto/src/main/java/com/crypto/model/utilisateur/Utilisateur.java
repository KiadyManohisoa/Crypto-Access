package com.crypto.model.utilisateur;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.crypto.exception.model.ValeurInvalideException;
import com.crypto.model.portefeuille.PorteFeuille;
import com.crypto.model.transaction.Transaction;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.NoArgsConstructor;

@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Utilisateur {

    String id ;
    String token;
    String nom;
    String prenom;
    Date dateNaissance;
    String mail;
    String mdp ;
    Genre genre ; // Agument pour le founisseur d'identité
    String lienImage ;
    Transaction transaction;
    PorteFeuille porteFeuille;

    public PorteFeuille getPorteFeuille() {
        return porteFeuille;
    }

    public void setPorteFeuilleByConnection(Connection connection) {
        String query = " SELECT p.*,u.* FROM portefeuille p INNER JOIN utilisateur u ON p.idUtilisateur = u.id WHERE u.id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, this.getId());

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    Utilisateur utilisateur = new Utilisateur(rs.getString("idutilisateur"));
                    utilisateur.setNom(rs.getString("nom"));
                    utilisateur.setPrenom(rs.getString("prenom"));
                    utilisateur.setDateNaissance(rs.getDate("date_naissance"));
                    utilisateur.setMail(rs.getString("mail"));
                    this.porteFeuille = new PorteFeuille();
                    this.porteFeuille.setId(rs.getString("id"));
                    this.porteFeuille.setPorteFeuilleDetailsByConnection(connection);
                }
            }
        }
        catch (Exception exception){
            exception.printStackTrace();
        }
    }


    public void setPorteFeuille(PorteFeuille porteFeuille) {
        this.porteFeuille = porteFeuille;
    }


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
    
    @JsonProperty("date_naissance")
    @JsonFormat(pattern = "yyyy-MM-dd")
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

    public String getMdp() {
        return mdp;
    }

    public String getMotdepasse() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }
    public void setTransaction(Connection c,LocalDateTime dateMax) {
        this.transaction = Transaction.getTransactionByUtilisateur(this,c,dateMax);
        System.out.println("tailleacha"+this.transaction.getAchat().size());
        System.out.println("taillevente"+this.transaction.getVente().size());
    }

    @JsonProperty("genre")
    public String getGenreID() {
        if(genre!=null) return genre.getId();
        else return "";
    }
    @JsonIgnore
    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public void setGenre(String idGenre) {
       setGenre(new Genre(idGenre));
    }

    public String getLienImage() {
        return lienImage;
    }

    public void setLienImage(String lienImage) {
        this.lienImage = lienImage;
    }


    // Constructeur utilisant les setters
    public Utilisateur(String id) {
        setId(id);
    }

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

    public static List<Utilisateur> getAll(Connection connection) throws SQLException {
        List<Utilisateur> utilisateurs = new ArrayList<>();
        String query = "SELECT * FROM utilisateur";

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String nom = resultSet.getString("nom");
                String prenom = resultSet.getString("prenom");
                Date dateNaissance = resultSet.getDate("date_naissance");
                String mail = resultSet.getString("mail");

                Utilisateur utilisateur = new Utilisateur();
                utilisateur.setId(id);
                utilisateur.setNom(nom);
                utilisateur.setPrenom(prenom);
                utilisateur.setDateNaissance(dateNaissance);
                utilisateur.setMail(mail);
                utilisateurs.add(utilisateur);
            }
        }

        return utilisateurs;
    }


    // Méthode pour insérer un utilisateur dans la base de données
    void insertUtilisateur(Connection connection) throws Exception {
       
        String query = "INSERT INTO utilisateur (id, nom, prenom, date_naissance, mail, lienImage) VALUES (DEFAULT, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, getNom());
            statement.setString(2, getPrenom());
            statement.setDate(3, getDateNaissance());
            statement.setString(4, getMail());
            statement.setString(5, getLienImage());
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
        String query = "INSERT INTO portefeuille (id, idUtilisateur) VALUES (DEFAULT, ?)";
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

    @Override
    public String toString() {
        // Formater la date de naissance si elle n'est pas nulle
        String formattedDate = (dateNaissance != null) ? dateNaissance.toString() : "N/A"; // Ou vous pouvez utiliser SimpleDateFormat pour formater spécifiquement

        return "Utilisateur{" +
                "id='" + id + '\'' +
                ", token='" + token + '\'' +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", dateNaissance=" + formattedDate +
                ", mail='" + mail + '\'' +
                ", mdp='" + (mdp != null ? "********" : "null") + '\'' +  // Ne jamais afficher le mot de passe en clair
                '}';
    }
}
