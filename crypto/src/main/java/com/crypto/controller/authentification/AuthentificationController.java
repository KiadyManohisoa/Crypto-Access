package com.crypto.controller.authentification;

import java.sql.Connection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.crypto.model.reponse.JsonResponse;
import com.crypto.model.utilisateur.Utilisateur;
import com.crypto.service.AccessAPI;
import com.crypto.service.connection.UtilDB;

import jakarta.servlet.http.HttpSession;

@Controller
public class AuthentificationController {
 
    @Autowired
    private AccessAPI accessAPI; // Injection du service

    @Autowired 
    UtilDB utilDB ;


    @PostMapping("/crypto/connection")
    public String connection(@ModelAttribute Utilisateur utilisateur, RedirectAttributes redirectAttributes, Model model) {
        
        String cheminRedirection = "redirect:/connection";
        System.out.println("Dans connection");
        
        try(Connection connection = utilDB.getConnection()) {
            JsonResponse<Object> jsonResponse = accessAPI.connection(connection, utilisateur) ;

            if(jsonResponse.getError()==null && jsonResponse.getCode()==200) {
                utilisateur = Utilisateur.getByMail(connection, utilisateur.getMail());
                model.addAttribute("id", ((Utilisateur)jsonResponse.getData()).getId());
                model.addAttribute("mail", utilisateur.getMail());
                model.addAttribute("message", jsonResponse.getData());
                cheminRedirection = "pages/utilisateur/confirmationPIN";


            } 
            else redirectAttributes.addFlashAttribute("message", jsonResponse.getData());
    
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            
        }
        System.out.println("Redirection ...");
        return cheminRedirection;
    }

    @PostMapping("/crypto/PIN")
    public String PIN(HttpSession session, @RequestParam("mail") String mail, @RequestParam("id") String id, @RequestParam("n1") String n1, @RequestParam("n2") String n2, @RequestParam("n3") String n3, @RequestParam("n4") String n4, RedirectAttributes redirectAttributes, Model model) {        
        
        String cheminRedirection = "redirect:/connection";
        StringBuilder pin = new StringBuilder();
        pin.append(n1).append(n2).append(n3).append(n4); 
        
        try(Connection connection = utilDB.getConnection()) {
            // JsonResponse<Map<String,String>> jsonResponse = (JsonResponse<Map<String,String>>)accessAPI.verification(connection, id, pin.toString()) ;
            JsonResponse<Map<String,String>> jsonResponse = (JsonResponse<Map<String,String>>)accessAPI.verification(connection, id, pin.toString()) ;

            if(jsonResponse.getError()==null && jsonResponse.getCode()==200) {

                Utilisateur utilisateur = Utilisateur.getByMail(connection, mail);
                session.setAttribute("utilisateur", utilisateur);
                model.addAttribute("id", ((Map<String,String>)jsonResponse.getData()).get("id"));
                cheminRedirection = "redirect:/accueil";


            } 
            redirectAttributes.addFlashAttribute("message", jsonResponse.getData());
    
            
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

         try(Connection connection = utilDB.getConnection()) {
            // Utilisateur utilisateur = new Utilisateur("Joe","Marah", "2004-12-12", "joemarah64@gmail.com");
            // utilisateur.setMdp("0000");
            String genre = "GR02";
            utilisateur.setGenre(genre);

            JsonResponse jsonResponse = accessAPI.inscription(connection, utilisateur) ;
            if(jsonResponse.getError()==null && jsonResponse.getCode()==200){
                utilisateur.insert(utilDB.getConnection());
                session.setAttribute("utilisateur", utilisateur);
                cheminRedirection = "redirect:/accueil";
                redirectAttributes.addFlashAttribute("message", "Inscription réussie");


            } else redirectAttributes.addFlashAttribute("message", jsonResponse.getError());

            // cheminRedirection = new Wrapper().enJSON(utilisateur);
            
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            
        }

        return cheminRedirection;
    }
}