package com.crypto.controller.authentification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.ui.Model;

import com.crypto.model.reponse.JsonResponse;
import com.crypto.model.utilisateur.Utilisateur;
import com.crypto.service.AccessAPI;
import com.crypto.service.connection.UtilDB;
import com.crypto.service.util.Wrapper;

import jakarta.servlet.http.HttpSession;

@Controller
public class AuthentificationController {
 
    @Autowired
    private AccessAPI accessAPI; // Injection du service

    @Autowired 
    UtilDB utilDB ;


    @PostMapping("/crypto/connection")
    public String connection(HttpSession session, @ModelAttribute Utilisateur utilisateur, RedirectAttributes redirectAttributes) {
        
        String cheminRedirection = "redirect:/connection";
        System.out.println("Dans connection");
        
        try {
            JsonResponse jsonResponse = accessAPI.connection(utilDB.getConnection(), utilisateur) ;

            if(jsonResponse.getError()==null && jsonResponse.getCode()==200) {
                utilisateur = Utilisateur.getByMail(utilDB.getConnection(), utilisateur.getMail());
                session.setAttribute("utilisateur", utilisateur);
                cheminRedirection = "redirect:/accueil";
                System.out.println("Vérification réussie");

            } else redirectAttributes.addFlashAttribute("message", jsonResponse.getError());
    
            
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            
        }
        System.out.println("Redirection ...");
        return cheminRedirection;
    }


    @PostMapping("/crypto/inscription")
    public String inscription(HttpSession session, @ModelAttribute Utilisateur utilisateur, RedirectAttributes redirectAttributes) {
        
        String cheminRedirection = "redirect:/inscription";

        try {
            // Utilisateur utilisateur = new Utilisateur("Joe","Marah", "2004-12-12", "joemarah64@gmail.com");
            // utilisateur.setMdp("0000");
            String genre = "GR02";
            utilisateur.setGenre(genre);

            JsonResponse jsonResponse = accessAPI.inscription(utilDB.getConnection(), utilisateur) ;
            if(jsonResponse.getError()==null && jsonResponse.getCode()==200){
                utilisateur.insert(utilDB.getConnection());
                session.setAttribute("utilisateur", utilisateur);
                cheminRedirection = "redirect:/accueil";

            } else redirectAttributes.addFlashAttribute("message", jsonResponse.getError());

            // cheminRedirection = new Wrapper().enJSON(utilisateur);
            
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            
        }
        redirectAttributes.addFlashAttribute("message", "HELLO");

        return cheminRedirection;
    }
}
