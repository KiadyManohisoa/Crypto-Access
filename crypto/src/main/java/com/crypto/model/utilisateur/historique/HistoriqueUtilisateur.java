package com.crypto.model.utilisateur.historique;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.crypto.model.utilisateur.Utilisateur;

public class HistoriqueUtilisateur {
    
    String id;
    Utilisateur utilisateur;
    Timestamp dateExecution;
    String operation;

    // Constructeur
    public HistoriqueUtilisateur(String id, Utilisateur utilisateur, Timestamp dateExecution, String action) {
        setId(id);
        setUtilisateur(utilisateur);
        setDateExecution(dateExecution);
        setOperation(action);
    }

    // Getters et Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Timestamp getDateExecution() {
        return dateExecution;
    }

    public void setDateExecution(Timestamp dateExecution) {
        this.dateExecution = dateExecution;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String action) {
        this.operation = action;
    }

    // Fonction pour récupérer l'historique par date (simulation avec une liste)
    public static HistoriqueUtilisateur[] getByDate(Connection connection, Date date) throws Exception{

        List<HistoriqueUtilisateur> listes = new ArrayList<>();

        String query = "SELECT * FROM historiqueUtilisateur WHERE dateExecution >= ? ";
        
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setDate(1, date);

            try (ResultSet resultSet = statement.executeQuery()) {
                
                 while (resultSet.next()) {
                   
                    listes.add(new HistoriqueUtilisateur(resultSet.getString("id"), Utilisateur.getById(connection, resultSet.getString("idUtilisateur")), resultSet.getTimestamp("dateExecution"),resultSet.getString("operation")));
                }

            }
        }
        
        return listes.toArray(new HistoriqueUtilisateur[0]);
    }

    // Affichage des détails (optionnel)
    @Override
    public String toString() {
        return "HistoriqueUtilisateur{" +
                "id=" + id +
                ", utilisateur=" + utilisateur +
                ", dateExecution=" + dateExecution +
                ", operation='" + operation + '\'' +
                '}';
    }
}
