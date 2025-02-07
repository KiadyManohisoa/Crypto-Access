package com.crypto.service.firebase;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.crypto.model.utilisateur.Utilisateur;
import com.crypto.service.firebase.init.FirebaseInitializer;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.auth.UserRecord.CreateRequest;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter 
@Service
public class FirestoreUtilisateur {

    Utilisateur utilisateur ;
    FirebaseInitializer firebaseInitializer ; 

    public void setFirebaseInitializer() throws Exception{

        setFirebaseInitializer(new FirebaseInitializer());
        getFirebaseInitializer().initialize();
    }

    public FirestoreUtilisateur() throws Exception{
        setFirebaseInitializer();
    }

    public FirestoreUtilisateur(Utilisateur utilisateur) throws Exception{
        try {
            setFirebaseInitializer();
            setUtilisateur(utilisateur);
        } catch (Exception e) {
            e.printStackTrace();
        }
       
    }

    void synchroniserUtilisateur() throws Exception{
        
        try {

            Firestore db = getFirebaseInitializer().getFirestore();

            // Exemple de données à envoyer
            Map<String, Object> data = new HashMap<>();
            data.put("id", getUtilisateur().getId());
            data.put("nom", getUtilisateur().getNom());
            data.put("prenom", getUtilisateur().getPrenom());
            data.put("mail", getUtilisateur().getMail());
            data.put("date_naissance", getUtilisateur().getDateNaissance());
            data.put("lienImage", getUtilisateur().getLienImage());
            data.put("creation", new java.util.Date().toString());

            // Référence à une collection et ajout de données
            CollectionReference  collectionRef = db.collection("utilisateurs") ;
            ApiFuture<DocumentReference> result = collectionRef.add(data);

            System.out.println("Document ajouté avec l'ID : " + result.get().getId());
        } catch (Exception e) {
            e.printStackTrace();
            throw e ; 
        }
    }

    UserRecord synchroniserAuthentification() throws Exception {
        CreateRequest request = new CreateRequest()
            .setEmail(getUtilisateur().getMail())
            .setPassword(getUtilisateur().getMdp());

        UserRecord userRecord = FirebaseAuth.getInstance().createUser(request);
        return userRecord;
    }

    public void synchroniser() throws Exception {

        System.out.println("Valeur de utilisateur "+getUtilisateur().toString());
        synchroniserAuthentification() ;
        synchroniserUtilisateur();
    }
}

