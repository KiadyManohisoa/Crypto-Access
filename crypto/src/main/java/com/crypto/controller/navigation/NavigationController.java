package com.crypto.controller.navigation;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.crypto.config.DonneesConfig;
import com.crypto.model.crypto.ChangementCoursCrypto;
import com.crypto.model.crypto.Cryptomonnaie;
import com.crypto.model.utilisateur.Genre;
import com.crypto.model.utilisateur.Utilisateur;
import com.crypto.service.AccessAPI;
import com.crypto.service.connection.UtilDB;

import jakarta.servlet.http.HttpSession;

@Controller
public class NavigationController {
       
    @Autowired
    private AccessAPI accessAPI ;

    @Autowired
    private UtilDB utilDB ;
    // Accueil Map
    @GetMapping("/accueil")
    public String accueil() {
        return "pages/accueil/accueil"; // Utilise home.html avec le layout
    }

    @GetMapping("/cours")
    public String cours(Model model) {
        
        try(Connection connection = utilDB.getConnection()) {
            ChangementCoursCrypto[] changementCoursCryptos = ChangementCoursCrypto.getByCriteria(connection, 0);
            model.addAttribute("cryptomonnaies", changementCoursCryptos);

        } catch(Exception err) {
            model.addAttribute("message", err.getMessage());
        }
       
        return "pages/accueil/cours"; // Utilise home.html avec le layout
    }

    @GetMapping("/coursDetail")
    public String coursDetail(Model model, @RequestParam("id") String idCryptomonnaie) {
         try(Connection connection = utilDB.getConnection()) {
            Cryptomonnaie cryptomonnaie = new Cryptomonnaie(idCryptomonnaie);
            ChangementCoursCrypto[] changementCoursCryptos = ChangementCoursCrypto.getById(connection, cryptomonnaie, DonneesConfig.SECONDES_CONSIDEREE);
            List<Double> prix = new ArrayList<>();
            List<Long> temps = new ArrayList<>();

            for (ChangementCoursCrypto changementCoursCrypto : changementCoursCryptos) {
                prix.add(changementCoursCrypto.getValeur());
                temps.add(changementCoursCrypto.getDate().getTime());
            }
            
            model.addAttribute("prix", prix);
            model.addAttribute("temps", temps);
            if(changementCoursCryptos.length>1)model.addAttribute("cryptomonnaie", changementCoursCryptos[0].getCryptomonnaie());

        } catch(Exception err) {
            model.addAttribute("message", err.getMessage());
        }

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
         try(Connection connection = utilDB.getConnection()){
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