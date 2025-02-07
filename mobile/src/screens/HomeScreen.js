import { useNavigation } from '@react-navigation/native';
import React from 'react';
import { View, Text, StyleSheet, TouchableOpacity } from 'react-native';
import NavBar from '../components/NavBar';
import Header from "../components/Header";
import theme from '../styles/theme';
import {useUser} from "../contexts/Context";

export default function HomeScreen() {
  const {user , utilisateur} = useUser();
  console.log("user now :"+ JSON.stringify(user) );
  console.log("utilisateur now :"+ JSON.stringify(utilisateur) );
  const navigation = useNavigation();

  return (
      <View style={styles.container}>
        <Header
            title="Home"
            profileImage={require('../../assets/male.webp')}
        />
        <Text style={styles.title}>Bienvenue {utilisateur.nom} {utilisateur.prenom}  🚀</Text>
        <TouchableOpacity style={styles.button} onPress={() => navigation.navigate('Login')}>
          <Text style={styles.buttonText}>Se Déconnecter</Text>
        </TouchableOpacity>
        <NavBar activeScreen="Home" />
      </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: theme.colors.background,
    paddingHorizontal: 20,
    paddingVertical: 30,
  },
  title: {
    fontSize: 24,
    fontWeight: 'bold',
    color: theme.colors.primary,
    marginBottom: 30,
    textAlign: 'center',
  },
  button: {
    backgroundColor: theme.colors.primary,
    paddingVertical: 15,
    paddingHorizontal: 30,
    borderRadius: 30,
    marginTop: 20,
    alignSelf: 'center',
  },
  buttonText: {
    color: theme.colors.text,
    fontSize: 18,
    fontWeight: '700',
    textAlign: 'center',
  },
});