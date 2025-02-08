package com.crypto.controller.backoffice.utilisateur;

import java.sql.Connection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.crypto.model.fond.MouvementFond;
import com.crypto.model.fond.MouvementFondAttente;
import com.crypto.model.utilisateur.admin.Admin;
import com.crypto.service.connection.UtilDB;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RequestMapping("/crypto")
@Controller
public class ValidationFondController {
            
    @Autowired
    private UtilDB utilDB ;
    
    @GetMapping("/utilisateur/validerFond")
    public String validerFondUtilisateur(@RequestParam("idFondAttente") String idFond, RedirectAttributes redirectAttributes, HttpSession session) {
        try (Connection co = this.utilDB.getConnection()) {
            Admin admin =(Admin) session.getAttribute("utilisateur");
            MouvementFondAttente mvt = new MouvementFondAttente(idFond);
            admin.validerDemandeFond(co, mvt.getById(co));
            redirectAttributes.addFlashAttribute("message", "Succès : Demande de fond validé");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/crypto/utilisateur/fondEnAttente";
    }
    

}
