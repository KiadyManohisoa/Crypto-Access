package com.crypto.service.security;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class AuthentificationInterceptor  implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false); // Ne pas créer une nouvelle session si elle n'existe pas
        if (session == null || session.getAttribute("utilisateur") == null) {
            response.sendRedirect("/connection"); // Redirection vers la page de connexion
            return false; // Arrête la requête ici
        }
        return true; // Continue avec la requête
    }
}

