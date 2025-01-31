package com.crypto;

import java.sql.Connection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.crypto.model.utilisateur.admin.Admin;
import com.crypto.service.connection.UtilDB;



@SpringBootApplication
@EnableScheduling
public class CryptoApplication {

    @Autowired 
    UtilDB utilDB ;
    public static void main(String[] args) {

        SpringApplication.run(CryptoApplication.class, args);


        // Admin admin = new Admin("test", "test");

        // try(Connection connection = utilDB.getConnection()) {
           // admin.se_connecter(connection);

        // } catch (Exception e) {
        //     // TODO: handle exception
        // }

    }
}
