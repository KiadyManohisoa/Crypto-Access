package com.crypto;


import java.sql.Connection;
import java.sql.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.crypto.model.utilisateur.historique.HistoriqueUtilisateur;
import com.crypto.service.connection.UtilDB;

@SpringBootTest
class CryptoApplicationTests {

	@Autowired
	UtilDB utilDB ; 

	@Test
	void contextLoads() {

		// Admin admin = new Admin("Rakoto", "MotDePasseSecurise1!");

        try(Connection connection = utilDB.getConnection()) {
        //    admin.se_connecter(connection);
		HistoriqueUtilisateur[] historiqueUtilisateurs = HistoriqueUtilisateur.getByDate(connection, Date.valueOf("2025-02-04"));
		for(HistoriqueUtilisateur h : historiqueUtilisateurs) {
			System.out.println(h.toString());
		}
		   System.out.println("Connection réussi");

        } catch (Exception e) {
            // TODO: handle exception
			e.printStackTrace();
        }
	}

}
