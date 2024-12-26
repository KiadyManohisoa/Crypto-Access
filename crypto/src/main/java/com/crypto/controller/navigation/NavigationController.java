package com.crypto.controller.navigation;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NavigationController {
       
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
    public String deconnection() {
        return "pages/utilisateur/connection"; // Utilise home.html avec le layout
    }

    // Utilisateur Map 

    @GetMapping("/connection")
    public String connection() {
        return "pages/utilisateur/connection"; // Utilise home.html avec le layout
    }

    @GetMapping("/inscription")
    public String inscription() {
        return "pages/utilisateur/inscription"; // Utilise home.html avec le layout
    }
}