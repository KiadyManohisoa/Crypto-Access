package com.crypto.service;

import java.net.ConnectException;
import java.sql.Connection;


import com.crypto.exception.authentification.InscriptionEchoueException;
import com.crypto.model.utilisateur.Utilisateur;

public class AccessAPI {
    
    public void inscription(Connection connection, Utilisateur utilisateur) throws InscriptionEchoueException {
        
//         try (CloseableHttpClient httpClient = CloseableHttpClients.createDefault()) {
//             // Créer une requête POST
//             HttpPost post = new HttpPost(AccessAPIConfig.ACCESS_INSCRIPTION);

//             // Configurer les headers
//             post.setHeader("Content-Type", "application/json");
//             post.setHeader("Accept", "application/json");

//             // Convertir l'objet Java en JSON
//             ObjectMapper mapper = new ObjectMapper();
//             String json = mapper.writeValueAsString(utilisateur);

//             // Ajouter le JSON au corps de la requête
//             post.setEntity(new StringEntity(json));

//             // Envoyer la requête et lire la réponse
//             try (CloseableHttpResponse response = httpClient.execute(post)) {
//                 int statusCode = response.getCode();
                
//                 // Vérifier si la réponse est réussie (status 200)
//                 if (statusCode == 200) {
//                     String responseBody = new String(response.getEntity().getContent().readAllBytes());

//                     // Convertir la réponse JSON en objet JsonResponse
//                     JsonResponse<Utilisateur> jsonResponse = mapper.readValue(responseBody,
//                         new TypeReference<JsonResponse<Utilisateur>>() {});

//                     // Afficher ou traiter la réponse
//                     System.out.println("Réponse réussie : " + jsonResponse);
//                 } else {
//                     // Gérer une réponse d'erreur (par exemple, code 400 ou 500)
//                     throw new InscriptionEchoueException("Échec de l'inscription, code HTTP : " + statusCode);
//                 }
//             } catch (IOException e) {
//                 // Gestion de l'erreur pour le corps de la réponse
//                 throw new InscriptionEchoueException("Erreur lors de la lecture de la réponse du serveur", e);
//             }
//         } catch (IOException e) {
//             // Gestion de l'erreur pour la requête HTTP
//             throw new InscriptionEchoueException("Erreur lors de la requête HTTP", e);
//         }
    }

    public void connection(Connection connction, Utilisateur utilisateur) throws ConnectException {}
}
