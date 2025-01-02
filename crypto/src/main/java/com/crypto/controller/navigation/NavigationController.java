package com.crypto.controller.navigation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.crypto.model.utilisateur.Genre;
import com.crypto.model.utilisateur.Utilisateur;
import com.crypto.service.AccessAPI;

import jakarta.servlet.http.HttpSession;

@Controller
public class NavigationController {
       
    @Autowired
    private AccessAPI accessAPI ;
    // Accueil Map
    @GetMapping("/accueil")
    public String accueil() {
        return "pages/accueil/accueil"; // Utilise home.html avec le layout
    }

    @GetMapping("/cours")
    public String cours() {
        return "pages/accueil/cours"; // Utilise home.html avec le layout
    }

    @GetMapping("/coursDetail")
    public String coursDetail() {
        return "pages/accueil/coursDetail"; // Utilise home.html avec le layout
    }

    @GetMapping("/detailVente")
    public String detailVente() {
        return "pages/accueil/detailVente"; // Utilise home.html avec le layout
    }

    @GetMapping("/vente")
    public String vente() {
        return "pages/accueil/vente"; // Utilise home.html avec le layout
    }

    @GetMapping("/portefeuille")
    public String portefeuille() {
        return "pages/accueil/portefeuille"; // Utilise home.html avec le layout
    }

    @GetMapping("/deconnection")
    public String deconnection(HttpSession session, Model model) {
        session.removeAttribute("utilisateur");
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setGenre(new Genre());
        model.addAttribute("utilisateur", utilisateur);
        return "pages/utilisateur/connection"; // Utilise home.html avec le layout
    }

    // Utilisateur Map 

    @GetMapping("/connection")
    public String connection(Model model ) {

        Utilisateur utilisateur = new Utilisateur() ;
        utilisateur.setGenre(new Genre());
        model.addAttribute("utilisateur", utilisateur); 
        return "pages/utilisateur/connection"; // Utilise home.html avec le layout
    }

    @GetMapping("/inscription")
    public String inscription(Model model) {
        try{
            model.addAttribute("genres", accessAPI.listeGenres());
        } catch(Exception err) {
            model.addAttribute("message", err.getMessage());
        }
        Utilisateur utilisateur = new Utilisateur() ;
        utilisateur.setGenre(new Genre());
        model.addAttribute("utilisateur", utilisateur); 
        return "pages/utilisateur/inscription"; // Utilise home.html avec le layout
    }
}