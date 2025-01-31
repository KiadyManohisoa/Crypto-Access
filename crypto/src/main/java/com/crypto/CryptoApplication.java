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

    public static void main(String[] args) {

        SpringApplication.run(CryptoApplication.class, args);


    }
}

