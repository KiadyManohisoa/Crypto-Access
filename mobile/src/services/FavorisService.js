import { collection, getDocs, getFirestore, query, where, addDoc, deleteDoc } from "firebase/firestore";
import { getCryptoByIdFromList } from "./CryptosService";
import { useCryptos } from "../contexts/Context";

const db = getFirestore(); // 🔥 Initialisation de Firestore

// Fonction pour récupérer toutes les cryptomonnaies favoris par ID utilisateur
export const getFavorisById = async (id) => {
    try {
        console.log("Fetching favoris...");
        const objsRef = collection(db, "cryptoFavori");
        const q = query(objsRef, where("iduser", "==", id));
        const snapshot = await getDocs(q);
        const favoris = snapshot.docs.map(doc => ({ id: doc.id, ...doc.data() }));
        console.log("Favoris:", JSON.stringify(favoris));
        return favoris;
    } catch (error) {
        console.error("Erreur lors de la récupération des Favoris:", error);
        return [];
    }
};

// Fonction pour récupérer les cryptomonnaies associées aux favoris d'un utilisateur
export const getFavorisCryptoById = async (id , cryptos) => {
    try {
        const favoris = await getFavorisById(id);
        return favoris.map(fav => (getCryptoByIdFromList(fav.idcrypto, cryptos)));
    } catch (error) {
        console.error("Erreur lors de la récupération des cryptos favoris:", error);
        return [];
    }
};

// Fonction pour créer un favori dans Firebase
export const createFavori = async (iduser, idcrypto) => {
    try {
        const favorisRef = collection(db, "cryptoFavori");
        await addDoc(favorisRef, { iduser, idcrypto });
        console.log("Favori ajouté avec succès !");
    } catch (error) {
        console.error("Erreur lors de l'ajout du favori:", error);
    }
};

// Fonction pour supprimer un favori avec iduser et idcryptomonnaie
export const deleteFavori = async (iduser, idcrypto) => {
    try {
        const favorisRef = collection(db, "cryptoFavori");
        const q = query(favorisRef, where("iduser", "==", iduser), where("idcrypto", "==", idcrypto));
        const snapshot = await getDocs(q);

        const deletePromises = snapshot.docs.map(doc => deleteDoc(doc.ref));
        await Promise.all(deletePromises);

        console.log("Favori supprimé avec succès !");
    } catch (error) {
        console.error("Erreur lors de la suppression du favori:", error);
    }
};


export const fetchFavorites = async (utilisateur , cryptos) => {
    if (!utilisateur) return;
    const favoris = await getFavorisCryptoById(utilisateur.id, cryptos);
    setFavorites(favoris);

    // Filtrer les cryptos pour obtenir celles qui ne sont PAS encore en favoris
    const favorisIds = favoris.map(fav => fav.idCrypto);
    const filteredNonFavorites = cryptos.filter(crypto => !favorisIds.includes(crypto.idCrypto));
    setNonFavorites(filteredNonFavorites);
};

