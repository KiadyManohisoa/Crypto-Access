package com.crypto.model.fond;

import com.crypto.model.transactionCrypto.TransactionCrypto;
import com.crypto.model.utilisateur.Utilisateur;
import com.crypto.service.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class MouvementFond {
    String id;
    double montant;
    LocalDateTime dateMouvement;
    TransactionCrypto transactionCrypto;

    public MouvementFond() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public LocalDateTime getDateMouvement() {
        return dateMouvement;
    }

    public void setDateMouvement(LocalDateTime dateMouvement) {
        this.dateMouvement = dateMouvement;
    }

    public TransactionCrypto getTransactionCrypto() {
        return transactionCrypto;
    }

    public void setTransactionCrypto(TransactionCrypto transactionCrypto) {
        this.transactionCrypto = transactionCrypto;
    }

    public void insert(Connection conn, Utilisateur u) throws Exception {
        String query = """
        INSERT INTO fond (id, montant, dateMouvement, idTransactionCrypto, idUtilisateur)
        VALUES (default, ?, ?, ?, ?);
        """;
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setDouble(1, this.getMontant());
            preparedStatement.setTimestamp(2, Util.getDateTimeActuelle());
            if (this.getTransactionCrypto() != null) {
                preparedStatement.setString(3, this.getTransactionCrypto().getId());
            } else {
                preparedStatement.setNull(3, java.sql.Types.VARCHAR);
            }
            preparedStatement.setString(4,u.getId() );

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

}
