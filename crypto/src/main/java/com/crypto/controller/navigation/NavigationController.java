package com.crypto.controller.navigation;

import java.sql.Connection;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import com.crypto.service.util.Util;
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
                 Instant instant = changementCoursCrypto.getDate().toInstant();

                // Appliquer un fuseau horaire spécifique (par exemple, Europe/Paris)
                ZoneId zoneId = ZoneId.of("Europe/Paris");
                ZonedDateTime zonedDateTime = instant.atZone(zoneId);

                // Convertir en millisecondes pour le fuseau horaire ajusté
                long adjustedTimestamp = zonedDateTime.toInstant().toEpochMilli();

                temps.add(adjustedTimestamp);
                // temps.add(changementCoursCrypto.getDate().getTime());
            }
            
            model.addAttribute("prix", prix);
            model.addAttribute("temps", temps);
            if(changementCoursCryptos.length>1)model.addAttribute("cryptomonnaie", changementCoursCryptos[0].getCryptomonnaie());

        } catch(Exception err) {
            model.addAttribute("message", err.getMessage());
        }

        return "pages/accueil/coursDetail"; // Utilise home.html avec le layout
    }



    @GetMapping("/portefeuille")
    public String portefeuille(Model model, HttpSession session) {
        // String idUtilisateur = ((Utilisateur)session.getAttribute("utilisateur")).getId();
        String idUtilisateur = "USR000000001";

        try {
            Connection conn=utilDB.getConnection();
            Utilisateur u=new Utilisateur();
            u.setId(idUtilisateur);
            u.setPorteFeuille(conn);
            model.addAttribute("details", u.getPorteFeuille().getPorteFeuilleDetails());
        } catch (Exception err) {
            model.addAttribute("message", "Erreur lors de la récupération des données : " + err.getMessage());
        }

        return "pages/accueil/portefeuille"; // Utilise le fichier portefeuille.html
    }
    @GetMapping("/achat")
    public String achat(Model model, HttpSession session) {

        try {
            Connection conn=utilDB.getConnection();
            Cryptomonnaie[] ltcrypto=Cryptomonnaie.getAll(conn);
            model.addAttribute("listCrypto",ltcrypto);
        } catch (Exception err) {
            model.addAttribute("message", "Erreur lors de la récupération des données : " + err.getMessage());
        }

        return "pages/accueil/achat";
    }

    @GetMapping("/analysecommission")
    public String analysecommission(Model model) {

        try {
            Connection conn=utilDB.getConnection();
            Cryptomonnaie[] ltcrypto=Cryptomonnaie.getAll(conn);
            model.addAttribute("listCrypto",ltcrypto);
        } catch (Exception err) {
            model.addAttribute("message", "Erreur lors de la récupération des données : " + err.getMessage());
        }

        return "pages/accueil/analysecommission";
    }
    @GetMapping("/commission")
    public String commission(Model model) {

        try {
            Connection conn=utilDB.getConnection();
            Cryptomonnaie[] ltcrypto=Cryptomonnaie.getAll(conn);
            model.addAttribute("listCrypto",ltcrypto);
        } catch (Exception err) {
            model.addAttribute("message", "Erreur lors de la récupération des données : " + err.getMessage());
        }

        return "pages/accueil/insertCommission";
    }
    @GetMapping("/transaction")
    public String vente(Model model, HttpSession session) {

        try {
            Connection conn=utilDB.getConnection();
            Utilisateur u=new Utilisateur();
            u.setId("USR000000001");
            u.setTransaction(conn, Util.getDateTimeActuelle().toLocalDateTime());
            model.addAttribute("listVente",u.getTransaction().getVente());
            model.addAttribute("listAchat",u.getTransaction().getAchat());
        } catch (Exception err) {
            model.addAttribute("message", "Erreur lors de la récupération des données : " + err.getMessage());
        }

        return "pages/accueil/transaction";
    }
    @GetMapping("/alea3")
    public String alea3() {
        return "pages/accueil/alea3";
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

    @GetMapping("/confirmationPIN")
    public String confirmationPIN() {

        return "pages/utilisateur/confirmationPIN"; 
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