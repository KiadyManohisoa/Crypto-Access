package com.crypto;

import java.sql.Connection;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.crypto.model.utilisateur.admin.Admin;
import com.crypto.service.connection.UtilDB;

@SpringBootTest
class CryptoApplicationTests {

	@Autowired
	UtilDB utilDB ;

	@Test
	void contextLoads() {

		// Admin admin = new Admin("Rakoto", "MotDePasseSecurise1!");

        // try(Connection connection = utilDB.getConnection()) {
        //    admin.se_connecter(connection);
		//    System.out.println("Connection réussi");

        // } catch (Exception e) {
        //     // TODO: handle exception
		// 	e.printStackTrace();
        // }
	}

}