package com.crypto.controller.backoffice;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import com.crypto.model.utilisateur.admin.Admin;

@Controller
@RequestMapping("/crypto")
public class NavigationBO {
    
    @GetMapping("/backoffice/connection")
    public String connection(Model model ) {

        Admin admin = new Admin("Rakoto", "0000") ;
        model.addAttribute("admin", admin); 
        return "pages/backoffice/login-admin"; // Utilise home.html avec le layout
    }
}
