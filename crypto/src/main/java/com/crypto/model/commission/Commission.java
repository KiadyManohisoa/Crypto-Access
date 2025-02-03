package com.crypto.model.commission;

import com.crypto.model.crypto.ChangementCoursCrypto;
import com.crypto.model.crypto.Cryptomonnaie;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Commission {
    String id;
    double pourcentage;
    LocalDateTime dateChangement;

    public Commission() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getPourcentage() {
        return pourcentage;
    }

    public void setPourcentage(double pourcentage) {
        this.pourcentage = pourcentage;
    }

    public LocalDateTime getDateChangement() {
        return dateChangement;
    }

    public void setDateChangement(LocalDateTime dateChangement) {
        this.dateChangement = dateChangement;
    }

    public static Commission getByIdCryptoAndDate(Connection connection,Cryptomonnaie crypto,LocalDateTime date) throws SQLException {
        String query = "SELECT * FROM commission WHERE idCryptomonnaie = ? and dateChangement<=? order by dateChangement desc limit 1 ";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1,crypto.getId() );
            statement.setTimestamp(2, Timestamp.valueOf(date));
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Commission commission = new Commission();
                    commission.setId(resultSet.getString("id"));
                    commission.setPourcentage(resultSet.getDouble("pourcentage"));
                    commission.setDateChangement(resultSet.getTimestamp("dateChangement").toLocalDateTime());
                    return commission;
                }
            }
        }

        return null;
    }
    public void insert(Connection connection, Cryptomonnaie crypto) throws SQLException {
        String query = "INSERT INTO commission (idCryptomonnaie, pourcentage, dateChangement) VALUES (?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, crypto.getId());
            statement.setDouble(2, this.getPourcentage());
            statement.setTimestamp(3, Timestamp.valueOf(this.getDateChangement()));
            statement.executeUpdate();
        }
    }
    public static List<Commission> findByCryptoAndDateBetween(Connection connection, Cryptomonnaie crypto, LocalDateTime dateMin, LocalDateTime dateMax) throws SQLException {
        String query = "SELECT * FROM commission WHERE dateChangement BETWEEN ? AND ?";
        if (crypto != null) {
            query += " AND idCryptomonnaie = ?";
        }
        query += " ORDER BY dateChangement ASC";

        List<Commission> commissions = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setTimestamp(1, Timestamp.valueOf(dateMin));
            statement.setTimestamp(2, Timestamp.valueOf(dateMax));

            if (crypto != null) {
                statement.setString(3, crypto.getId());
            }

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Commission commission = new Commission();
                    commission.setId(resultSet.getString("id"));
                    commission.setPourcentage(resultSet.getDouble("pourcentage"));
                    commission.setDateChangement(resultSet.getTimestamp("dateChangement").toLocalDateTime());
                    commissions.add(commission);
                }
            }
        }
        return commissions;
    }
        public static double calculateTotal(List<Commission> commissions) {
            double total = 0;
            for (Commission commission : commissions) {
                total += commission.getPourcentage();
            }
            return total;
        }
    public static double calculateAverage(List<Commission> commissions) {
        if (commissions.isEmpty()) {
            return 0;
        }
        double total = calculateTotal(commissions);
        return total / commissions.size();
    }
}
