package com.crypto.controller.backoffice.authentification;

import java.sql.Connection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.crypto.model.utilisateur.admin.Admin;
import com.crypto.service.connection.UtilDB;

@Controller
@RequestMapping("/crypto")
public class AuthentificationBO {
    
    @Autowired 
    UtilDB utilDB ;

    @PostMapping("/backoffice/connection")
    public String connection(@ModelAttribute Admin admin, RedirectAttributes redirectAttributes, Model model) {

        String cheminRedirection = "redirect:/crypto/backoffice/connection";
        try(Connection connection = utilDB.getConnection()) {
            System.out.println(admin.toString());
            admin.se_connecter(connection);
        } catch (Exception e) {
            
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }

        return cheminRedirection ;

    }

}
