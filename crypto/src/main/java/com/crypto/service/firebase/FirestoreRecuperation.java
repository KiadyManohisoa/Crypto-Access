package com.crypto.service.firebase;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.messaging.tcp.TcpConnection;
import org.springframework.stereotype.Service;

import com.crypto.model.firebase.DemandeUtilisateur;
import com.crypto.model.firebase.HistoriqueCryptoFavori;
import com.crypto.model.firebase.HistoriqueOperationUtilisateur;
import com.crypto.model.fond.MouvementFond;
import com.crypto.model.utilisateur.Utilisateur;
import com.crypto.service.firebase.init.FirebaseInitializer;
import com.google.api.core.ApiFuture;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Service
public class FirestoreRecuperation {
    
    FirebaseInitializer firebaseInitializer ; 

    public void setFirebaseInitializer() throws Exception{

        setFirebaseInitializer(new FirebaseInitializer());
        getFirebaseInitializer().initialize();
    }

    public FirestoreRecuperation() throws Exception{
        setFirebaseInitializer();
        
    }

    public void insererDernierEnvoi() throws Exception {
        try {
            Firestore db = getFirebaseInitializer().getFirestore();
            
            Timestamp dateActuelle = Timestamp.now();
            Map<String, Object> data = new HashMap<>();
            data.put("date", dateActuelle);

            CollectionReference collectionRef = db.collection("dernierEnvoi");
            ApiFuture<DocumentReference> result = collectionRef.add(data);

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public Timestamp getDerniereRecuperation() throws Exception {
        Timestamp derniere = null;
        
        try {
            // Obtenir la référence Firestore et exécuter la requête pour obtenir la date maximale
            ApiFuture<QuerySnapshot> future = getFirebaseInitializer().getFirestore()
                    .collection("dernierEnvoi")
                    .orderBy("date", com.google.cloud.firestore.Query.Direction.DESCENDING)
                    .limit(1)
                    .get();
    
            List<QueryDocumentSnapshot> documents = future.get().getDocuments();
    
            if (!documents.isEmpty()) {
                // Obtenir la date maximale
                derniere = documents.get(0).getTimestamp("date");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    
        return derniere;
    }
    
    public HistoriqueOperationUtilisateur[] getHistoriqueApres(Timestamp date) throws Exception {
        List<HistoriqueOperationUtilisateur> documents = new ArrayList<>();
        
        try {
    
            // Exécuter la requête pour obtenir les historiques après la date donnée
            ApiFuture<QuerySnapshot> future = getFirebaseInitializer()
                    .getFirestore()
                    .collection("historiqueOperationUtilisateur")
                    .whereGreaterThanOrEqualTo("dateChangement", date)
                    .get();
    
            List<QueryDocumentSnapshot> queryDocuments = future.get().getDocuments();
    
            for (QueryDocumentSnapshot queryDocument : queryDocuments) {
                ApiFuture<DocumentSnapshot> utilisateurFuture = queryDocument.getReference()
                    .collection("utilisateur") // Accès à la sous-collection
                    .document("utilisateurId") // ID du document (fixé dans ta structure)
                    .get();
    
                DocumentSnapshot utilisateurDoc = utilisateurFuture.get();
                if (utilisateurDoc.exists()) {
                    String idUtilisateur = utilisateurDoc.getString("idUtilisateur");
                    String lienImage = utilisateurDoc.getString("lienImage");
    
                    // Créer l'objet utilisateur si besoin
                    Utilisateur utilisateur = new Utilisateur(idUtilisateur);
                    utilisateur.setLienImage(lienImage);
    
                    // Associer l'utilisateur à l'historique
                    HistoriqueOperationUtilisateur doc = new HistoriqueOperationUtilisateur(
                        queryDocument.getId(),
                        utilisateur,
                        queryDocument.getString("operation"),
                        new java.sql.Timestamp(queryDocument.getTimestamp("dateChangement").toDate().getTime())
                    );
                    documents.add(doc);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    
        return documents.toArray(new HistoriqueOperationUtilisateur[0]);
    }
    
    public HistoriqueCryptoFavori[] getCryptoFavori(Timestamp date) throws Exception {

        List<HistoriqueCryptoFavori> documents = new ArrayList<>();
        
        try {    
            // Exécuter la requête pour obtenir les historiques après la date donnée
            ApiFuture<QuerySnapshot> future = getFirebaseInitializer()
                    .getFirestore()
                    .collection("historiqueCrypto")
                    .whereGreaterThanOrEqualTo("dateChangement", date)
                    .get();
    
            List<QueryDocumentSnapshot> queryDocuments = future.get().getDocuments();
    
            for (QueryDocumentSnapshot queryDocument : queryDocuments) {
              

                // Associer l'utilisateur à l'historique
                HistoriqueCryptoFavori doc = new HistoriqueCryptoFavori(
                    queryDocument.getString("idUtilisateur"),
                    queryDocument.getString("idCryptomonnaie"),
                    queryDocument.getString("operation"),
                    new java.sql.Timestamp(queryDocument.getTimestamp("dateChangement").toDate().getTime())
                );
                documents.add(doc);
                
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    
        return documents.toArray(new HistoriqueCryptoFavori[0]);
    }

    public DemandeUtilisateur[] getFondAttente(Timestamp date) throws Exception {

        List<DemandeUtilisateur> documents = new ArrayList<>();
            
        try {

            // Exécuter la requête pour obtenir les historiques après la date donnée
            ApiFuture<QuerySnapshot> future = getFirebaseInitializer()
                    .getFirestore()
                    .collection("fondAttente")
                    .whereGreaterThanOrEqualTo("dateMouvement", date)
                    .get();

            List<QueryDocumentSnapshot> queryDocuments = future.get().getDocuments();

            for (QueryDocumentSnapshot queryDocument : queryDocuments) {

                // DocumentSnapshot utilisateurDoc = utilisateurFuture.get();
                String idUtilisateur = queryDocument.getString("idUtilisateur");

                // Créer l'objet utilisateur si besoin
                Utilisateur utilisateur = new Utilisateur(idUtilisateur);

                // Associer l'utilisateur à l'historique
                MouvementFond mvt = new MouvementFond();
                mvt.setMontant(queryDocument.getDouble("montant")) ;    
                mvt.setDateMouvement((new java.sql.Timestamp(queryDocument.getTimestamp("dateMouvement").toDate().getTime())).toLocalDateTime()) ;
                mvt.setSigne(queryDocument.getLong("signe").intValue());    
                    
                DemandeUtilisateur demande = new DemandeUtilisateur(utilisateur, mvt);
                documents.add(demande);
                    
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        return documents.toArray(new DemandeUtilisateur[0]);
    
    }

    public void synchronisationUtilisateur(Connection connection, Timestamp derniere) throws Exception {

        HistoriqueOperationUtilisateur[] historiqueOperationUtilisateurs = getHistoriqueApres(derniere) ;
        HistoriqueOperationUtilisateur.update(connection, historiqueOperationUtilisateurs);
        
    }

    void supprimerContenuCollection() throws Exception {
        try {
            Firestore db = getFirebaseInitializer().getFirestore();
            CollectionReference collectionRef = db.collection("cryptomonnaies");

            // Récupérer tous les documents de la collection
            ApiFuture<QuerySnapshot> future = collectionRef.get();
            List<QueryDocumentSnapshot> documents = future.get().getDocuments();

            // Supprimer chaque document
            for (QueryDocumentSnapshot document : documents) {
                document.getReference().delete();
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }


    void synchroniserFavori(Connection connection, Timestamp derniere) throws Exception {

        HistoriqueCryptoFavori[] historiqueCryptoFavoris = getCryptoFavori(derniere) ; 
        HistoriqueCryptoFavori.modifier(connection, historiqueCryptoFavoris) ; 

    } 

    void synchroniserDemandeDepotRetrait(Connection connection, Timestamp derniere) throws Exception {

        DemandeUtilisateur[] demandes = getFondAttente(derniere) ;
        DemandeUtilisateur.inserer(connection, demandes) ;
    }

    
    public void synchroniser(Connection connection) throws Exception {
        
        Timestamp derniere = getDerniereRecuperation() ;
        connection.setAutoCommit(false);

        try {

            synchronisationUtilisateur(connection, derniere);
            synchroniserFavori(connection, derniere);
            synchroniserDemandeDepotRetrait(connection, derniere) ;
            insererDernierEnvoi();
            connection.commit() ;

        } catch (Exception e) {
            connection.rollback() ;
            throw e ; 
        } finally {
            connection.setAutoCommit(true);
        }
       


    }

    // CryptoFavori[] getCryptoFavori(Timestap timestamp) throws Exception {
        
    // }
}
