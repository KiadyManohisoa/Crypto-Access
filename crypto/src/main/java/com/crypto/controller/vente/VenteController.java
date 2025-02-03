package com.crypto.controller.vente;

import com.crypto.model.portefeuille.PorteFeuilleDetails;
import com.crypto.model.utilisateur.Utilisateur;
import com.crypto.service.connection.UtilDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.Connection;
import java.time.LocalDateTime;

@Controller
public class VenteController {
    @Autowired
    private UtilDB utilDB ;

    @GetMapping("/portefeuille/vendre")
    public String vendre(
            RedirectAttributes redirectAttributes,
            @RequestParam("quantity") int quantity,
            @RequestParam("idportefeuilledetail") String idportefeuilledetail,
            @RequestParam("date")LocalDateTime dateTransaction) {

        try {
            Connection connection = utilDB.getConnection();
            PorteFeuilleDetails portefeuilleDetail = PorteFeuilleDetails.getById(idportefeuilledetail, connection);
            Utilisateur u=new Utilisateur();
            u.setId("USR000000001");
            u.setFond(connection);
            u.vendre(connection,portefeuilleDetail,quantity,dateTransaction);

            redirectAttributes.addFlashAttribute("message", "Vente effectuée avec succès !");
            } catch (Exception err) {
            redirectAttributes.addFlashAttribute("message", "Erreur : " + err.getMessage());
        }

        return "redirect:/portefeuille";
    }

}
