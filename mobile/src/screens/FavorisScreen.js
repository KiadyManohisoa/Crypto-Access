import React, { useState } from 'react';
import { View, Text, FlatList, TouchableOpacity, Modal, StyleSheet, useWindowDimensions } from 'react-native';
import { Picker } from '@react-native-picker/picker';
import Icon from 'react-native-vector-icons/FontAwesome';
import NavBar from '../components/NavBar';
import Header from "../components/Header";
import theme from '../styles/theme';

const availableCryptos = [
    { id: '1', nom: 'Bitcoin', valeur: '$43,000' },
    { id: '2', nom: 'Ethereum', valeur: '$3,200' },
    { id: '3', nom: 'Ripple', valeur: '$1.20' },
    { id: '4', nom: 'Litecoin', valeur: '$150' }
];

export default function FavorisScreen() { // <-- C'est ici l'unique export par défaut
    const { height } = useWindowDimensions();
    const [favorites, setFavorites] = useState([
        { id: '1', nom: 'Bitcoin', valeur: '$43,000' },
        { id: '2', nom: 'Ethereum', valeur: '$3,200' }
    ]);
    const [modalVisible, setModalVisible] = useState(false);
    const [selectedCrypto, setSelectedCrypto] = useState("");

    const removeFavorite = (id) => {
        setFavorites(favorites.filter(fav => fav.id !== id));
    };

    const addFavorite = () => {
        if (selectedCrypto === "") return; // Ne rien ajouter si aucune crypto n'est sélectionnée
        const cryptoToAdd = availableCryptos.find(crypto => crypto.id === selectedCrypto);
        if (cryptoToAdd) {
            setFavorites([...favorites, cryptoToAdd]);
            setSelectedCrypto(""); // Réinitialiser après ajout
            setModalVisible(false);
        }
    };

    const nonFavoriteCryptos = availableCryptos.filter(crypto => !favorites.some(fav => fav.id === crypto.id));

    return (
        <View style={styles.container}>
            <Header
                title="Favoris"
                profileImage={require('../../assets/male.webp')}
            />

            <FlatList
                data={favorites}
                keyExtractor={(item) => item.id}
                renderItem={({ item }) => (
                    <View style={styles.item}>
                        <Text style={styles.nom}>{item.nom}</Text>
                        <Text style={styles.valeur}>{item.valeur}</Text>
                        <TouchableOpacity onPress={() => removeFavorite(item.id)}>
                            <Icon nom="heart" size={24} color="red" />
                        </TouchableOpacity>
                    </View>
                )}
                ListHeaderComponent={<Text style={styles.title}>Favorites</Text>}
                contentContainerStyle={styles.scrollContent}
            />

            {/* Bouton Ajouter */}
            <TouchableOpacity
                style={[styles.addButton, { bottom: height * 0.15 }]}
                onPress={() => setModalVisible(true)}
            >
                <Icon nom="plus" size={30} color="white" />
            </TouchableOpacity>

            {/* Modal d'ajout */}
            <Modal visible={modalVisible} animationType="slide" transparent>
                <View style={styles.modalContainer}>
                    <View style={styles.modalContent}>
                        <Text style={styles.modalTitle}>Add Favorite</Text>
                        <Picker
                            selectedValue={selectedCrypto}
                            onValueChange={(itemValue) => setSelectedCrypto(itemValue)}
                            style={styles.picker}
                        >
                            <Picker.Item label="Select a crypto" valeur="" />
                            {nonFavoriteCryptos.map(crypto => (
                                <Picker.Item key={crypto.id} label={crypto.nom} valeur={crypto.id} />
                            ))}
                        </Picker>
                        <View style={styles.buttonsContainer}>
                            <TouchableOpacity style={styles.modalButton} onPress={addFavorite}>
                                <Text style={styles.modalButtonText}>Create</Text>
                            </TouchableOpacity>
                            <TouchableOpacity style={styles.modalButton} onPress={() => setModalVisible(false)}>
                                <Text style={styles.modalButtonText}>Cancel</Text>
                            </TouchableOpacity>
                        </View>
                    </View>
                </View>
            </Modal>

            <NavBar activeScreen="Favoris" />
        </View>
    );
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: theme.colors.background,
        padding: 20,
    },
    title: {
        fontSize: 24,
        fontWeight: 'bold',
        color: theme.colors.primary,
        marginBottom: 20,
    },
    scrollContent: {
        paddingBottom: 90,
    },
    item: {
        flexDirection: 'row',
        justifyContent: 'space-between',
        alignItems: 'center',
        padding: 15,
        backgroundColor: '#333',
        borderRadius: 10,
        marginBottom: 10,
    },
    nom: {
        color: 'white',
        fontSize: 18,
    },
    valeur: {
        color: theme.colors.primary,
        fontSize: 16,
    },
    addButton: {
        position: 'absolute',
        right: 20,
        backgroundColor: theme.colors.primary,
        width: 60,
        height: 60,
        borderRadius: 30,
        justifyContent: 'center',
        alignItems: 'center',
        elevation: 5,
    },
    modalContainer: {
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center',
        backgroundColor: 'rgba(0,0,0,0.5)',
    },
    modalContent: {
        backgroundColor: theme.colors.secondary,
        padding: 20,
        borderRadius: 10,
        width: '80%',
        alignItems: 'center',
    },
    modalTitle: {
        color: theme.colors.text,
        fontSize: 18,
        marginBottom: 15,
        textAlign: 'center',
    },
    picker: {
        width: '100%',
        height: 50,
        marginBottom: 20,
    },
    buttonsContainer: {
        flexDirection: 'row',
        justifyContent: 'space-between',
        width: '100%',
    },
    modalButton: {
        backgroundColor: theme.colors.secondary,
        padding: 10,
        borderRadius: 5,
        flex: 1,
        marginHorizontal: 5,
        alignItems: 'center',
    },
    modalButtonText: {
        color: theme.colors.text,
        fontWeight: 'bold',
    },
});
