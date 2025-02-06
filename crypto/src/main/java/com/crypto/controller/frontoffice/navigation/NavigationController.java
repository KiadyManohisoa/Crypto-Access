package com.crypto.controller.frontoffice.navigation;

import java.sql.Connection;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.crypto.config.DonneesConfig;
import com.crypto.model.crypto.ChangementCoursCrypto;
import com.crypto.model.crypto.Cryptomonnaie;
import com.crypto.model.crypto.analyse.Analyseur;
import com.crypto.model.portefeuille.PorteFeuille;
import com.crypto.model.portefeuille.PorteFeuilleDetails;
import com.crypto.model.utilisateur.Genre;
import com.crypto.model.utilisateur.Utilisateur;
import com.crypto.model.vente.Vente;
import com.crypto.service.AccessAPI;
import com.crypto.service.connection.UtilDB;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/crypto")
@Controller
public class NavigationController {
       
    @Autowired
    private UtilDB utilDB ;

    @GetMapping("/form/analyse")
    public String getFormAnalyse(Model model) {
        try(Connection connection = utilDB.getConnection()) {
            Cryptomonnaie [] cryptos = Cryptomonnaie.getAll(connection);
            model.addAttribute("cryptos", cryptos);
            model.addAttribute("typesAnalyse", Analyseur.typesAnalyses);
        } catch(Exception err) {
            model.addAttribute("message", err.getMessage());
        }
        return "pages/frontoffice/accueil/analyse"; // Utilise home.html avec le layout
    }
    
    // Accueil Map
    @GetMapping("/accueil")
    public String accueil() {
        return "pages/frontoffice/accueil/accueil"; // Utilise home.html avec le layout
    }

    @GetMapping("/cours")
    public String cours(Model model) {
        
        try(Connection connection = utilDB.getConnection()) {
            ChangementCoursCrypto[] changementCoursCryptos = ChangementCoursCrypto.getByCriteria(connection, 0);
            model.addAttribute("cryptomonnaies", changementCoursCryptos);

        } catch(Exception err) {
            model.addAttribute("message", err.getMessage());
        }
       
        return "pages/frontoffice/accueil/cours"; // Utilise home.html avec le layout
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

        return "pages/frontoffice/accueil/coursDetail"; // Utilise home.html avec le layout
    }

    @GetMapping("/detailVente")
    public String detailVente(@RequestParam("idVente") String idVente, Model model) {

        try {
            Connection connection = utilDB.getConnection();
            Vente vente=Vente.getById(connection, idVente);
            model.addAttribute("vente", vente);
            System.out.println("Vente effectuée ");

        } catch (Exception err) { 

            model.addAttribute("message", err.getMessage());
            System.err.println("Erreur lors de la récupération des données : " + err.getMessage());
            err.printStackTrace();
        }
        return "pages/frontoffice/accueil/detailVente"; // Utilise home.html avec le layout
    }

    @GetMapping("/vente")
    public String vente(Model model) {
        // model.addAttribute("ventes", Achat.findAll(utilDB.getConnection()));
        try {
            List<Vente> listeVente=Vente.getVenteDisponible(utilDB.getConnection());
            model.addAttribute("listeVente", listeVente);
        } catch (Exception err) {
            System.err.println("Erreur lors de la récupération des données : " + err.getMessage());
            err.printStackTrace();
        }
        return "pages/frontoffice/accueil/vente"; // Utilise home.html avec le layout
    }

    @GetMapping("/portefeuille")
    public String portefeuille(Model model, HttpSession session) {
        String idUtilisateur = ((Utilisateur)session.getAttribute("utilisateur")).getId();
        try {
            PorteFeuille portefeuille = PorteFeuille.getByIdUtilisateur(idUtilisateur, utilDB.getConnection());
            List<PorteFeuilleDetails> details = PorteFeuilleDetails.getPorteFeuilleDetailsByPorteFeuille(
                    portefeuille.getId(),
                    utilDB.getConnection()
            );
            model.addAttribute("details", details);
        } catch (Exception err) {
            model.addAttribute("message", "Erreur lors de la récupération des données : " + err.getMessage());
        }

        return "pages/frontoffice/accueil/portefeuille"; // Utilise le fichier portefeuille.html
    }

    @GetMapping("/deconnection")
    public String deconnection(HttpSession session, Model model) {
        session.removeAttribute("utilisateur");
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setGenre(new Genre());
        model.addAttribute("utilisateur", utilisateur);
        return "redirect:/"; // Utilise home.html avec le layout
    }

}