package com.crypto.controller.achat;

import com.crypto.model.crypto.Cryptomonnaie;
import com.crypto.model.utilisateur.Utilisateur;
import com.crypto.service.connection.UtilDB;
import com.crypto.service.mail.EmailService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


@Controller
public class AchatController {

    @Autowired
    UtilDB utilDB ;

    @Autowired
    private EmailService emailService;

    @Value("${app.base-url}")
    private String baseUrl;

    @GetMapping("/acheter")
    public String acheter(
            RedirectAttributes redirectAttributes,
            @RequestParam("quantity") int quantity,
            @RequestParam("idcryptommonaie") String idcryptommonaie,
            @RequestParam("date") LocalDateTime dateTransaction) {

        try {
            Connection connection = utilDB.getConnection();
            Cryptomonnaie crypto=Cryptomonnaie.getById(connection,idcryptommonaie);
            Utilisateur u=new Utilisateur();
            u.setId("USR000000001");
            u.setFond(connection);
            u.setPorteFeuille(connection);
            u.acheter(connection,crypto,quantity,dateTransaction);
            redirectAttributes.addFlashAttribute("message", "Achat effectuée avec succès !");
        } catch (Exception err) {
            redirectAttributes.addFlashAttribute("message", "Erreur : " + err.getMessage());
        }

        return "redirect:/achat";
    }


}
