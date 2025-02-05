package com.crypto.controller.API;

import java.sql.Connection;
import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.crypto.model.utilisateur.historique.HistoriqueUtilisateur;
import com.crypto.service.connection.UtilDB;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api")
public class HistoriqueController {
    
    @Autowired
    UtilDB utilDB ;
    static String utilisateurSession = "utilisateur" ; 

    @GetMapping("/historiques")
    public Object getHistoriqueUtilisateurs(@RequestParam("dateChangement") Date date) throws Exception{

        try(Connection connection = utilDB.getConnection()) {
            return HistoriqueUtilisateur.getByDate(connection, date) ;
        } 
    }
    
    @PostMapping("/session") 
    public Object getSession(HttpSession session) throws Exception{

        return session.getAttribute(utilisateurSession);
        
    }


}
