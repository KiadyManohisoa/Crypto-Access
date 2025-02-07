package com.crypto.model.utilisateur;

import java.util.List;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import com.crypto.exception.achat.FondInsuffisantException;
import com.crypto.exception.model.ValeurInvalideException;
import com.crypto.exception.vente.QuantitéInsuffisanteException;
import com.crypto.model.commission.Commission;
import com.crypto.model.crypto.Cryptomonnaie;
import com.crypto.model.fond.Fond;
import com.crypto.model.fond.MouvementFond;
import com.crypto.model.portefeuille.PorteFeuille;
import com.crypto.model.portefeuille.PorteFeuilleDetails;
import com.crypto.model.transaction.Transaction;
import com.crypto.model.transactionCrypto.TransactionCrypto;
import com.crypto.service.util.Util;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.NoArgsConstructor;

@NoArgsConstructor
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
    PorteFeuille porteFeuille;
    Fond fond;
    Transaction transaction;

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

    public PorteFeuille getPorteFeuille() {
        return porteFeuille;
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

    public void setPorteFeuille(PorteFeuille porteFeuille) {
        this.porteFeuille = porteFeuille;
    }
    public void setPorteFeuille(Connection connection) {
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
                        this.porteFeuille.setPorteFeuilleDetails(connection);
                    }
                }
            }
            catch (Exception exception){
                exception.printStackTrace();
            }

    }


    public Fond getFond() {
        return fond;
    }

    public void setFond(Fond fond) {
        this.fond = fond;
    }
    public void setFond(Connection connection) {
        this.fond = Fond.getFondByUtilisateur(this,connection);
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
                utilisateurs.add(utilisateur);
            }
        }

        return utilisateurs;
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
    public double verifierFond(int quantite,double valeur,double commission) throws FondInsuffisantException{
        double total=quantite*valeur+(valeur*quantite*commission);
        if(this.getFond().getMontant()<total){
         throw new FondInsuffisantException("Fond insuffisant");
        }
        return total;
    }
    public void acheter(Connection conn, Cryptomonnaie cryptomonnaie, int quantiteACheter, LocalDateTime dateTransaction) throws QuantitéInsuffisanteException,Exception {
        TransactionCrypto transactionCrypto = new TransactionCrypto();
        try {
            conn.setAutoCommit(false);
            cryptomonnaie.setCommission(conn,dateTransaction);
            double total=verifierFond(quantiteACheter,cryptomonnaie.getValeur(),cryptomonnaie.getCommission().getPourcentage());
            double montantact=this.getFond().getMontant()- total;
            transactionCrypto.setCryptomonnaie(cryptomonnaie);
            transactionCrypto.setQuantite(quantiteACheter);
            transactionCrypto.setD_prixUnitaire(cryptomonnaie.getValeur());
            transactionCrypto.setAcheteur(this);
            transactionCrypto.setD_commission();
            if(dateTransaction!=null){
                transactionCrypto.setDateTransaction(dateTransaction);
            }else{
                transactionCrypto.setDateTransaction(Util.getDateTimeActuelle().toLocalDateTime());
            }
            transactionCrypto.insert(conn);
            PorteFeuilleDetails modif=this.getPorteFeuille().verifier(cryptomonnaie);
            if(modif!=null){
                modif.setQuantite(modif.getQuantite() + quantiteACheter);
                modif.update(conn);
            }
            else {
            PorteFeuilleDetails m=new PorteFeuilleDetails();
            m.setQuantite(quantiteACheter);
            m.setCryptomonnaie(cryptomonnaie);
            m.insert(conn,this.getPorteFeuille());
            }
            MouvementFond mvtFond=new MouvementFond();
            mvtFond.setDateMouvement(transactionCrypto.getDateTransaction());
            mvtFond.setMontant(transactionCrypto.getCryptomonnaie().getValeur()+(transactionCrypto.getCryptomonnaie().getValeur()*transactionCrypto.getD_commission())*quantiteACheter);
            mvtFond.setTransactionCrypto(transactionCrypto);
            mvtFond.insert(conn,this);
            this.getFond().setMontant(montantact);
            conn.commit();


        } catch (Exception e) {
            if (conn != null) {
                conn.rollback();
            }
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }


    }

    public void vendre(Connection conn, PorteFeuilleDetails porteFeuilleDetail, int quantiteAvendre, LocalDateTime dateTransaction) throws QuantitéInsuffisanteException,Exception {
            TransactionCrypto transactionCrypto = new TransactionCrypto();
            try {
                conn.setAutoCommit(false);
                porteFeuilleDetail.getCryptomonnaie().setCommission(conn,dateTransaction);

                transactionCrypto.setCryptomonnaie(porteFeuilleDetail.getCryptomonnaie());
                transactionCrypto.setQuantite(porteFeuilleDetail.getQuantite(), quantiteAvendre);
                transactionCrypto.setD_prixUnitaire(porteFeuilleDetail.getCryptomonnaie().getValeur());
                transactionCrypto.setVendeur(this);
                transactionCrypto.setD_commission();
                if(dateTransaction!=null){
                    transactionCrypto.setDateTransaction(dateTransaction);
                }else{
                    transactionCrypto.setDateTransaction(Util.getDateTimeActuelle().toLocalDateTime());
                }
                transactionCrypto.insert(conn);
                porteFeuilleDetail.setQuantite(porteFeuilleDetail.getQuantite() - quantiteAvendre);
                porteFeuilleDetail.update(conn);
                MouvementFond mvtFond=new MouvementFond();
                mvtFond.setDateMouvement(transactionCrypto.getDateTransaction());
                mvtFond.setMontant(transactionCrypto.getCryptomonnaie().getValeur()-(transactionCrypto.getCryptomonnaie().getValeur()*transactionCrypto.getD_commission())*quantiteAvendre);
                mvtFond.setTransactionCrypto(transactionCrypto);
                mvtFond.insert(conn,this);
                double montantact=this.getFond().getMontant()+mvtFond.getMontant();
                this.getFond().setMontant(montantact);

                conn.commit();


            } catch (Exception e) {
                if (conn != null) {
                    conn.rollback();
                }
                throw e;
            } finally {
                conn.setAutoCommit(true);
                }


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
