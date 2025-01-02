package com.crypto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.crypto.model.utilisateur.Utilisateur;
import com.crypto.service.connection.UtilDB;

import ch.qos.logback.classic.pattern.Util;

@SpringBootApplication
public class CryptoApplication {


    public static void main(String[] args) {
        // Lancer Spring Boot et récupérer le contexte
        SpringApplication.run(CryptoApplication.class, args);

        try {
			UtilDB utilDB = new UtilDB("crypto", "postgres", "postgres");
            Utilisateur utilisateur = Utilisateur.getByMail(utilDB.getConnection(), "vetso@gmail.com");
            System.out.println(utilisateur.getId());
			utilDB.getConnection().close();
        } catch (Exception e) {
            e.printStackTrace();
		
        }
    }
}
