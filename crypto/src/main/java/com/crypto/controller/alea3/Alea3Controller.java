package com.crypto.controller.alea3;

import com.crypto.model.alea3.Resultat;
import com.crypto.model.commission.Commission;
import com.crypto.model.crypto.Cryptomonnaie;
import com.crypto.service.connection.UtilDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class Alea3Controller {
    @Autowired
    UtilDB utilDB ;

    @PostMapping("/alea3/resultat")
        public String alea3(
                @RequestParam(name = "dateMax") LocalDateTime dateMax,
                Model model
            ) throws Exception {
            Connection connection = utilDB.getConnection();
            List<Resultat> resultats=Resultat.getResultatByDate(dateMax,connection);
            System.out.println("result"+resultats.size());
            model.addAttribute("resultats", resultats);
            return "pages/accueil/alea3";
        }
    }
