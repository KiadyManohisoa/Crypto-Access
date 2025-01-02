package com.crypto.controller;

import java.sql.Connection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crypto.model.utilisateur.Utilisateur;
import com.crypto.service.AccessAPI;
import com.crypto.service.connection.UtilDB;

@RestController
public class TestController {

    @Autowired
    private AccessAPI accessAPI; // Injection du service

    @Autowired 
    UtilDB utilDB ;

    @GetMapping("/test-symfony")
    public String testSymfonyService() {
        // Appel du webservice Symfony via le service
        return accessAPI.callSymfonyService();
    }
}
