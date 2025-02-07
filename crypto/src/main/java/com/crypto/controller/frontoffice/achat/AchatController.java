package com.crypto.controller.frontoffice.achat;

import com.crypto.model.achat.Achat;
import com.crypto.service.connection.UtilDB;
import com.crypto.service.mail.EmailService;
import com.crypto.service.util.Util;
import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/crypto/achat")
public class AchatController {

    @Autowired
    UtilDB utilDB ;

    @Autowired
    private EmailService emailService;

    @Value("${app.base-url}")
    private String baseUrl;

    // String url = baseUrl+"/confirmerAchat?idAchat=";


    // Insertion d'achat
    @PostMapping("/insert")
    public String insertAchat(@RequestParam String quantite, @RequestParam String idVente, HttpSession session, RedirectAttributes redirectAttributes) throws Exception {
        
        System.out.println("Appel de insert");
        // String recepteur = ((Utilisateur)session.getAttribute("utilisateur")).getMail();
        // String idAcheteur = ((Utilisateur)session.getAttribute("utilisateur")).getId();
        String idAcheteur = "USR000000008" ; 
        try {
            Achat obj = new Achat(Integer.parseInt(quantite), Util.getDateActuelle(), idAcheteur, idVente);
            obj = obj.insert(utilDB.getConnection());
    
            String recepteur = "vetsojoella@gmail.com";
            String url  =baseUrl+"/achat/set/crypto?idAchat="+obj.getId();
    
            String objet = "Mail de confirmation d'achat";
            String contenu = "<p>Bonjour,</p>"
                + "<p>Veuillez cliquer sur le lien suivant pour confirmer votre inscription :</p>"
                + "<a href='{{url}}'>Confirmer mon inscription</a>"
                + "<p>Cordialement,</p>";
    
            Map<String, Object> contexte = new HashMap<>();
            contexte.put("url", url);
            emailService.envoyerMail(recepteur, contexte, contenu, objet);

            redirectAttributes.addFlashAttribute("message", "Vérifier votre boite mail por valider l'achat");

    
        } catch(Exception err) {
            err.printStackTrace();
            redirectAttributes.addFlashAttribute("message", err.getMessage());
        }

        return "redirect:/vente";

    }


    @PostMapping("/confirm/email")
    public ModelAndView confirmAchat(@RequestParam String idachat) throws SQLException {
        ModelAndView mv = new ModelAndView("/");
        return mv;
    }

    @GetMapping("/set/crypto")
    public ModelAndView setCrypto(@RequestParam String idAchat, RedirectAttributes redirectAttributes) {

        System.out.println("Valeur de achat est "+idAchat);
        try {
            Achat achat = new Achat(idAchat);
            achat=achat.findById(utilDB.getConnection());

            achat.modifierPorteFeuille(utilDB.getConnection());
            redirectAttributes.addFlashAttribute("message", "Achat effectué");
        }  catch(Exception err) {
            redirectAttributes.addFlashAttribute("message", err.getMessage());
        } 
      
        return new ModelAndView("redirect:/vente");

    }
    
 
}
