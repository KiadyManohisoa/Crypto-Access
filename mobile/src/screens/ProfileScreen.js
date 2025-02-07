import React, { useState } from 'react';
import { View, Text, Image, TouchableOpacity, StyleSheet, ScrollView } from 'react-native';
import { Ionicons, FontAwesome } from '@expo/vector-icons';
import * as ImagePicker from 'expo-image-picker';
import NavBar from '../components/NavBar';
import theme from '../styles/theme';
import { useNavigation } from '@react-navigation/native';
import {useUser} from "../contexts/Context";

const ProfileScreen = () => {
    const navigation = useNavigation();
    const {utilisateur} =useUser();
    const user = utilisateur;
    const pickImage = async (fromCamera) => {
        let result;
        if (fromCamera) {
            result = await ImagePicker.launchCameraAsync({
                mediaTypes: ImagePicker.MediaTypeOptions.Images,
                allowsEditing: true,
                aspect: [1, 1],
                quality: 1,
            });
        } else {
            result = await ImagePicker.launchImageLibraryAsync({
                mediaTypes: ImagePicker.MediaTypeOptions.Images,
                allowsEditing: true,
                aspect: [1, 1],
                quality: 1,
            });
        }
        if (!result.canceled) {
            setUser({ ...user, urlPicture: result.assets[0].uri });
        }
    };

    return (
        <View style={styles.container}>
            <ScrollView contentContainerStyle={styles.scrollContent}>
                {/* Photo de profil */}
                <Image
                    source={user.urlPicture ? { uri: user.urlPicture } : require('../../assets/male.webp')}
                    style={styles.profileImage}
                />

                {/* Email sous la photo */}
                <Text style={styles.nameText}>{user.nom} {user.prenom} </Text>
                <Text style={styles.emailText}>{user.mail}</Text>

                <View style={styles.buttonContainer}>
                    <TouchableOpacity style={styles.iconButton} onPress={() => pickImage(true)}>
                        <Ionicons name="camera" size={30} color={theme.colors.text} />
                    </TouchableOpacity>
                    <TouchableOpacity style={styles.iconButton} onPress={() => pickImage(false)}>
                        <FontAwesome name="photo" size={30} color={theme.colors.text} />
                    </TouchableOpacity>
                    <TouchableOpacity style={styles.iconConfirm}>
                        <FontAwesome name="save" size={30} color={theme.colors.text} />
                    </TouchableOpacity>
                </View>

                {/* Solde actuel après les icônes */}
                <View style={styles.balanceContainer}>
                    <Text style={styles.balanceText}>💰 Solde actuel : 0 €</Text>
                </View>

                {/* Liste des cryptos détenues */}
                <Text style={styles.sectionTitle}>Mes Cryptos</Text>


                {/* Bouton de déconnexion */}
                <TouchableOpacity style={styles.logOutButton} onPress={() => navigation.navigate('Login')}>
                    <Ionicons name="log-out" size={30} color={theme.colors.text} />
                </TouchableOpacity>
            </ScrollView>

            {/* Barre de navigation */}
            <NavBar activeScreen="Profile" />
        </View>
    );
};

const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: theme.colors.background,
        paddingTop: 60,
    },
    scrollContent: {
        alignItems: 'center',
        paddingHorizontal: 20,
    },
    profileImage: {
        width: 120,
        height: 120,
        borderRadius: 60,
        marginBottom: 10,
    },
    nameText: {
        fontSize: 18,
        color: theme.colors.text,
        marginBottom: 20,
    },
    emailText: {
        fontSize: 14,
        color: theme.colors.secondary,
        marginBottom: 20,
    },
    buttonContainer: {
        flexDirection: 'row',
        justifyContent: 'center',
        marginBottom: 20,
    },
    iconButton: {
        backgroundColor: theme.colors.primary,
        padding: 15,
        borderRadius: 30,
        marginHorizontal: 10,
    },
    iconConfirm: {
        backgroundColor: 'green',
        padding: 15,
        borderRadius: 30,
        marginHorizontal: 10,
    },
    balanceContainer: {
        alignItems: 'center',
        marginBottom: 20,
    },
    balanceText: {
        fontSize: 22,
        color: theme.colors.primary,
        fontWeight: '700',
    },
    soldeText: {
        fontSize: 18,
        fontWeight: 'bold',
        color: theme.colors.text,
        marginBottom: 20,
    },
    sectionTitle: {
        fontSize: 18,
        fontWeight: 'bold',
        color: theme.colors.text,
        marginBottom: 10,
    },
    cryptoItem: {
        flexDirection: 'row',
        justifyContent: 'space-between',
        width: '100%',
        paddingVertical: 10,
        borderBottomWidth: 1,
        borderBottomColor: theme.colors.primary,
    },
    cryptoName: {
        fontSize: 16,
        color: theme.colors.text,
    },
    cryptoQuantity: {
        fontSize: 16,
        fontWeight: 'bold',
        color: theme.colors.text,
    },
    logOutButton: {
        backgroundColor: theme.colors.primary,
        width: 60,
        height: 60,
        borderRadius: 30,
        justifyContent: 'center',
        alignItems: 'center',
        marginTop: 20,
        alignSelf: 'center',
    },
});

export default ProfileScreen;
