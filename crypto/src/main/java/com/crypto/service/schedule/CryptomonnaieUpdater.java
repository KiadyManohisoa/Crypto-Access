package com.crypto.service.schedule;

import java.sql.Connection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.crypto.model.crypto.Cryptomonnaie;
import com.crypto.service.connection.UtilDB;

@Component
public class CryptomonnaieUpdater {
    
    @Autowired
    UtilDB utilDB ;

    // public String changerCours(@RequestParam String param) {

    //     Cryptomonnaie cryptomonnaie = new Cryptomonnaie();
    //     try{
    //         cryptomonnaie.nouveauCours(utilDB.getConnection());
    //     } catch(Exception err) {
    //         return new String(err.getMessage());
    //     }
    //     return null ;
    // }

    @Scheduled(fixedRate = 10000) // Toutes les 10 secondes
    public void changerCours() throws Exception{

        Cryptomonnaie cryptomonnaie = new Cryptomonnaie();
        try(Connection connection = utilDB.getConnection()){
            cryptomonnaie.nouveauCours(connection);
        } catch(Exception err) {
            throw err ;
        }
        System.out.println("Cours des cryptomonnaies mis à jour.");
    }
}
