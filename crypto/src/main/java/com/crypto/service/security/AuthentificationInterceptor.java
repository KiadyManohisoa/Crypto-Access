package com.crypto.service.security;

import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthentificationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // if (request.getSession(false) == null || request.getSession().getAttribute("utilisateur") == null) {
        //     response.sendRedirect("/connection"); // Redirige vers la page de connexion
        //     return false;
        // }
        return true;
    }
}

