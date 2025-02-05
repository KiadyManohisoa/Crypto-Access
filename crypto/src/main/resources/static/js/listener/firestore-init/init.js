// Config Firebase
import { initializeApp } from 'https://www.gstatic.com/firebasejs/9.6.1/firebase-app.js';
import { getFirestore, collection, query, orderBy, limit } from 'https://www.gstatic.com/firebasejs/9.6.1/firebase-firestore.js';



const firebaseConfig = {
    apiKey: "TON_API_KEY",
    authDomain: "TON_PROJET.firebaseapp.com",
    projectId: "TON_PROJET",
    storageBucket: "TON_PROJET.appspot.com",
    messagingSenderId: "TON_ID",
    appId: "TON_APP_ID"
};

// Initialiser Firebase
const app = initializeApp(firebaseConfig);

// Initialiser Firestore
const db = getFirestore(app);

getDateDerniereModification();

function getDateDerniereModification() {
    const collectionRef = collection(db, 'derniereRecuperation'); // Collection Firestore

    // Créer une requête qui trie par 'dateRecuperation' de manière décroissante (plus récente en premier)
    const q = query(collectionRef, orderBy('dateRecuperation', 'desc'), limit(1)); // Prend seulement le premier document

    // Récupérer les documents
    getDocs(q).then((querySnapshot) => {
        if (!querySnapshot.empty) {
            const doc = querySnapshot.docs[0]; // Premier document
            console.log(doc.id, '=>', doc.data()); // Afficher le document avec la date la plus récente
            console.log('Valeur en seconde est ', new Date(doc.data().dateRecuperation.seconds*1000));
        } else {
            console.log('Aucun document trouvé.');
        }
    }).catch((error) => {
        console.error('Erreur lors de la récupération des données :', error);
    });
}
