package com.crypto.controller.Commission;

import com.crypto.model.commission.Commission;
import com.crypto.model.crypto.Cryptomonnaie;
import com.crypto.model.utilisateur.Utilisateur;
import com.crypto.service.connection.UtilDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class CommissionController {
    @Autowired
    UtilDB utilDB ;
    @PostMapping("/insertCommission")
    public String inserer(
            RedirectAttributes redirectAttributes,
            @RequestParam("pourcentage") double pourcentage,
            @RequestParam("idcryptommonaie") String idcryptommonaie,
            @RequestParam("dateChangement") LocalDateTime dateChangement) {

        try {
            Connection connection = utilDB.getConnection();
            Cryptomonnaie crypto=new Cryptomonnaie();
            crypto.setId(idcryptommonaie);
            Commission com=new Commission();
            com.setDateChangement(dateChangement);
            com.setPourcentage(pourcentage);
            com.insert(connection,crypto);
            redirectAttributes.addFlashAttribute("message", "Commission inserer avec succès !");
        } catch (Exception err) {
            redirectAttributes.addFlashAttribute("message", "Erreur : " + err.getMessage());
        }

        return "redirect:/commission";
    }
    @PostMapping("/analyserCommissions")
    public String analyseCommissions(
            @RequestParam(name = "idcryptommonaie", required = false) String cryptoId,
            @RequestParam(name = "dateMin") LocalDateTime dateMin,
            @RequestParam(name = "dateMax") LocalDateTime dateMax,
            @RequestParam(name = "typeAnalyse") String typeanalyse, Model model
            ) throws Exception {

        Cryptomonnaie crypto = null;
        Connection connection = utilDB.getConnection();
        model.addAttribute("cryptoNom","tous");

        if (cryptoId != null && !cryptoId.isEmpty()) {
            crypto=Cryptomonnaie.getById(connection,cryptoId);
            model.addAttribute("cryptoNom", crypto.getNom());
        }
        List<Commission> commissions = Commission.findByCryptoAndDateBetween(connection,crypto, dateMin, dateMax);
        System.out.println("taillecommission"+commissions.size());
        if(typeanalyse.equals("somme")){
            double total = Commission.calculateTotal(commissions);
            model.addAttribute("somme", total);
        } else if (typeanalyse.equals("moyenne")) {
            double average = Commission.calculateAverage(commissions);
            model.addAttribute("moyenne", average);
        }
        Connection conn=utilDB.getConnection();
        Cryptomonnaie[] ltcrypto=Cryptomonnaie.getAll(conn);
        model.addAttribute("listCrypto",ltcrypto);

        model.addAttribute("typeAnalyse", typeanalyse);

        return "pages/accueil/analysecommission";
    }
}
