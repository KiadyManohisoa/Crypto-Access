package com.crypto.service;

import java.sql.Connection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.crypto.config.AccessAPIConfig;
import com.crypto.model.reponse.JsonResponse;
import com.crypto.model.utilisateur.Genre;
import com.crypto.model.utilisateur.Utilisateur;
import com.crypto.service.util.Wrapper;

import lombok.Getter;
import lombok.Setter;

@Service
@Getter
@Setter
public class AccessAPI {
    
    @Autowired
    private RestTemplate restTemplate; // Injection du Bean RestTemplate

    public String callSymfonyService() {

       String url = AccessAPIConfig.BASE_URL+"/api/utilisateurs" ;
       try {

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            return response.getBody();
        } catch (HttpClientErrorException | HttpServerErrorException e) {

            return "Erreur: " + e.getStatusCode() + " - " + e.getResponseBodyAsString();
        }

    }

    public Genre[] listeGenres() throws Exception{

        String url = AccessAPIConfig.BASE_URL+"/genres" ;
        try {
 
            ResponseEntity<Genre[]> response = restTemplate.getForEntity(url, Genre[].class);
            return response.getBody();
            
        } catch (HttpClientErrorException | HttpServerErrorException e) {
 
            throw e ;
        }
 
     }


    public JsonResponse connection(Connection connection, Utilisateur utilisateur) throws Exception {
        
        String url = AccessAPIConfig.BASE_URL+"api/auth/login"; 
        return appelerSymfony(url, utilisateur);
       
    }

    public JsonResponse inscription(Connection connction, Utilisateur utilisateur) throws Exception {
        String url = AccessAPIConfig.BASE_URL+"api/utilisateur"; 
        return appelerSymfony(url, utilisateur);
    }

    private JsonResponse appelerSymfony(String url, Utilisateur utilisateur) throws Exception{

        try {
            String jsonBody = (new Wrapper()).enJSON(utilisateur);
        
            // Headers pour la requête
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
    
            // Créer une entité avec le corps et les headers
            HttpEntity<String> entity = new HttpEntity<>(jsonBody, headers);
    
            // Effectuer une requête POST
            ResponseEntity<JsonResponse> responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity, JsonResponse.class);
            return responseEntity.getBody();

        } catch (Exception e) {
            throw e ;
        }
    }
}
