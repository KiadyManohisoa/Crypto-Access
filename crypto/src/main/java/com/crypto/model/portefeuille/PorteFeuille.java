package com.crypto.model.portefeuille;

import com.crypto.model.crypto.Cryptomonnaie;
import com.crypto.model.utilisateur.Utilisateur;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PorteFeuille {
    private String id;
    private List<PorteFeuilleDetails> porteFeuilleDetails;

    public PorteFeuille() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<PorteFeuilleDetails> getPorteFeuilleDetails() {
        return porteFeuilleDetails;
    }

    public void setPorteFeuilleDetails(List<PorteFeuilleDetails> porteFeuilleDetails) {
        this.porteFeuilleDetails = porteFeuilleDetails;
    }
    public void setPorteFeuilleDetails(Connection connection) {
    List<PorteFeuilleDetails> details = new ArrayList<>();
    String query = "SELECT * FROM portefeuille_detail  WHERE idPortefeuille = ?";
        try {
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, this.getId());
        try (ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                PorteFeuilleDetails detail = PorteFeuilleDetails.getById(rs.getString("id"),connection);
                details.add(detail);
            }
        }
    }
        catch(Exception ex){
        ex.printStackTrace();
    }
        this.porteFeuilleDetails =details;
    }
        public PorteFeuilleDetails verifier(Cryptomonnaie cryptomonnaie){
        for (PorteFeuilleDetails pt:this.getPorteFeuilleDetails()){
            if(pt.getCryptomonnaie().getId().equals(cryptomonnaie.getId())){
                return pt;
            }
        }
        return  null;
    }
}
