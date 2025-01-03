package com.crypto.service.schedule;

import java.sql.Connection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.crypto.model.crypto.Cryptomonnaie;
import com.crypto.service.connection.UtilDB;

@Component
public class CryptomonnaieUpdater {
    
    @Autowired
    UtilDB utilDB ;

    @Scheduled(fixedRate = 10000) // Toutes les 10 secondes
    public void changerCours() throws Exception{

        Cryptomonnaie cryptomonnaie = new Cryptomonnaie();
        try(Connection connection = utilDB.getConnection()){
            cryptomonnaie.nouveauCours(connection);
        } catch(Exception err) {
            throw err ;
        }
        // System.out.println("Cours des cryptomonnaies mis à jour.");
    }
}
