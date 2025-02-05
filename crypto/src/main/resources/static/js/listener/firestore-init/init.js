// Config Firebase
import { initializeApp } from 'https://www.gstatic.com/firebasejs/9.6.1/firebase-app.js';
import { getFirestore, collection, query, orderBy, limit } from 'https://www.gstatic.com/firebasejs/9.6.1/firebase-firestore.js';

const firebaseConfig = {
    apiKey: "AIzaSyApdM40OFJfJm7DPOVtpJVEEn0na01Bk9I",
    authDomain: "first-firebase-5ca70.firebaseapp.com",
    databaseURL: "https://first-firebase-5ca70-default-rtdb.firebaseio.com",
    projectId: "first-firebase-5ca70",
    storageBucket: "first-firebase-5ca70.firebasestorage.app",
    messagingSenderId: "352504038508",
    appId: "1:352504038508:web:c9369eb3cfa7ae9aca8449",
    measurementId: "G-6ZGHBGG14J"
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
