package com.crypto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.crypto.model.crypto.Cryptomonnaie;
import com.crypto.service.connection.UtilDB;


@SpringBootApplication
public class CryptoApplication {


    public static void main(String[] args) {
        // Lancer Spring Boot et récupérer le contexte
        SpringApplication.run(CryptoApplication.class, args);

        try {
			UtilDB utilDB = new UtilDB("crypto", "postgres", "postgres");
            // Utilisateur utilisateur = Utilisateur.getByMail(utilDB.getConnection(), "vetso@gmail.com");
            new Cryptomonnaie().nouveauCours(utilDB.getConnection());
          
            utilDB.getConnection().close();
        } catch (Exception e) {
            e.printStackTrace();
		
        }
    }
}
