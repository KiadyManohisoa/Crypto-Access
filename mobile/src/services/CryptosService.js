import {db} from "../configuration/FirebaseConfig"; // Import de la connexion Firebase
import {collection, getDocs, getFirestore} from "firebase/firestore";

// Fonction pour récupérer toutes les cryptomonnaies
export const getAllCryptos = async () => {
    try {
        const db = getFirestore(); // 🔥 Assure-toi que Firestore est bien initialisé
        const objsRef = collection(db, "cryptomonnaies");

        const objsSnapshot = await getDocs(objsRef);
        const objs = objsSnapshot.docs.map(doc => ({ id: doc.id, ...doc.data() }));
        console.log("Tous les Cryptos :", JSON.stringify(objs, null, 2));
        return objs;
    } catch (error) {
        console.error("Erreur lors de la récupération des cryptomonnaies:", error);
        return [];
    }
};
