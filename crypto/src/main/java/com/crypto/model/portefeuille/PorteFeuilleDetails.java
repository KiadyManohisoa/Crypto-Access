package com.crypto.model.portefeuille;

import com.crypto.model.crypto.Cryptomonnaie;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PorteFeuilleDetails {
    private String id;
    private double quantite;
    private PorteFeuille porteFeuille;
    private Cryptomonnaie cryptomonnaie;

    public PorteFeuilleDetails() {
    }

    public PorteFeuilleDetails(String id, double quantite, PorteFeuille porteFeuille, Cryptomonnaie cryptomonnaie) {
        this.id = id;
        this.quantite = quantite;
        this.porteFeuille = porteFeuille;
        this.cryptomonnaie = cryptomonnaie;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getQuantite() {
        return quantite;
    }

    public void setQuantite(double quantite) {
        this.quantite = quantite;
    }

    public PorteFeuille getPorteFeuille() {
        return porteFeuille;
    }

    public void setPorteFeuille(PorteFeuille porteFeuille) {
        this.porteFeuille = porteFeuille;
    }

    public Cryptomonnaie getCryptomonnaie() {
        return cryptomonnaie;
    }

    public void setCryptomonnaie(Cryptomonnaie cryptomonnaie) {
        this.cryptomonnaie = cryptomonnaie;
    }

    // Fonction : Obtenir les détails par portefeuille
    public static List<PorteFeuilleDetails> getPorteFeuilleDetailsByPorteFeuille(String idPortefeuille,Connection connection) throws SQLException {
        List<PorteFeuilleDetails> details = new ArrayList<>();
        String query = """
                SELECT pd.id, pd.quantite, c.id AS cryptoId, c.nom, c.valeur
                FROM portefeuille_detail pd
                INNER JOIN LiaisonPorteFeuilleDetails l ON pd.id = l.id
                INNER JOIN cryptomonnaie c ON pd.idCryptomonnaie = c.id
                WHERE l.idPortefeuille = ?;
                """;

        try {
             PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, idPortefeuille);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    Cryptomonnaie cryptomonnaie = new Cryptomonnaie(
                            rs.getString("cryptoId"),
                            rs.getString("nom"),
                            rs.getDouble("valeur")
                    );
                    PorteFeuilleDetails detail = new PorteFeuilleDetails(
                            rs.getString("id"),
                            rs.getDouble("quantite"),
                            null,
                            cryptomonnaie
                    );
                    details.add(detail);
                }
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return details;
    }

    // Fonction : Obtenir un détail par ID
    public static PorteFeuilleDetails getById(String id, Connection connection) throws SQLException {
        String query = """
            SELECT pd.id, pd.quantite, c.id AS cryptoId, c.nom, c.valeur
            FROM portefeuille_detail pd
            INNER JOIN cryptomonnaie c ON pd.idCryptomonnaie = c.id
            WHERE pd.id = ?;
            """;
        PorteFeuilleDetails detail = null;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, id);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    Cryptomonnaie cryptomonnaie = new Cryptomonnaie(
                            rs.getString("cryptoId"),
                            rs.getString("nom"),
                            rs.getDouble("valeur")
                    );
                    detail = new PorteFeuilleDetails(
                            rs.getString("id"),
                            rs.getDouble("quantite"),
                            null, // Remplacez par une valeur si nécessaire
                            cryptomonnaie
                    );
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }
        return detail;
    }
    public static void updateQuantite(String id, double nouvelleQuantite, Connection connection) throws SQLException {
        String query = """
            UPDATE portefeuille_detail
            SET quantite = ?
            WHERE id = ?;
            """;

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setDouble(1, nouvelleQuantite);
            preparedStatement.setString(2, id);

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Quantité mise à jour avec succès pour l'id : " + id);
            } else {
                System.out.println("Aucun enregistrement trouvé pour l'id : " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public PorteFeuilleDetails update(Connection connection ) throws SQLException {
        String query = "UPDATE portefeuilledetails SET quantite = ? WHERE id = ?  ";

        connection.setAutoCommit(false);

        try (PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setDouble(1, this.getQuantite());
            statement.setString(2, this.getId());

            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        }

        return this;
    }

}
