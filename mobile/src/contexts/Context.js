import React, { createContext, useState, useContext, useEffect } from 'react';
import { getAllCryptos } from "../services/CryptosService";
import { getByMail } from "../services/UtilisateurService";

const UserContext = createContext();
const CryptoContext = createContext();

export const useUser = () => useContext(UserContext);
export const useCryptos = () => useContext(CryptoContext);

export const UserProvider = ({ children }) => {
    const [user, setUser] = useState(null);
    const [utilisateur, setUtilisateur] = useState(null);
    const [isLoading, setIsLoading] = useState(true);

    const loginUser = async (userData) => {
        setUser(userData);
        console.log("mail:" + userData.email);
        const userFromDB = await getByMail(userData.email);
        setUtilisateur(userFromDB);
        setIsLoading(false); // Fin du chargement une fois l'utilisateur récupéré
    };

    const logoutUser = () => {
        setUser(null);
        setIsLoading(false);
    };

    useEffect(() => {
        // Simulation d'un utilisateur déjà connecté (si besoin)
        setTimeout(() => {
            setIsLoading(false); // Supprime le loading même si pas d'utilisateur
        }, 1000);
    }, []);

    return (
        <UserContext.Provider value={{ user, utilisateur, loginUser, logoutUser, isLoading }}>
            {children}
        </UserContext.Provider>
    );
};

export const CryptoProvider = ({ children }) => {
    const [cryptos, setCryptos] = useState([]);
    const [isLoading, setIsLoading] = useState(true);

    useEffect(() => {
        const fetchCryptos = async () => {
            const data = await getAllCryptos();
            setCryptos(data);
            setIsLoading(false); // Fin du chargement une fois les cryptos récupérées
        };
        fetchCryptos();
    }, []);

    return (
        <CryptoContext.Provider value={{ cryptos, setCryptos, isLoading }}>
            {children}
        </CryptoContext.Provider>
    );
};
