import React from 'react';
import { View, Text, StyleSheet, ScrollView, Image } from 'react-native';
import NavBar from '../components/NavBar';
import Header from '../components/Header';
import theme from '../styles/theme';

const cryptoNotifications = [
    {
        id: 1,
        name: 'Bitcoin',
        symbol: 'BTC',
        price: 42500,
        change: 2.5,
        logo: require('../../assets/btc-logo.webp')
    },
    {
        id: 2,
        name: 'Ethereum',
        symbol: 'ETH',
        price: 2200,
        change: -1.2,
        logo: require('../../assets/btc-logo.webp')
    },
    {
        id: 3,
        name: 'Binance Coin',
        symbol: 'BNB',
        price: 320,
        change: 0.8,
        logo: require('../../assets/btc-logo.webp')
    }
];

const NotificationsScreen = () => {
    return (
        <View style={styles.container}>
            <Header
                title="Notifications"
            />
            <ScrollView style={styles.notificationsContainer}>
                {cryptoNotifications.map((crypto) => (
                    <View key={crypto.id} style={styles.notificationItem}>
                        <View style={styles.notificationHeader}>
                            <Image source={crypto.logo} style={styles.notificationLogo} />
                            <View style={styles.notificationDetails}>
                                <Text style={styles.cryptoName}>{crypto.name} ({crypto.symbol})</Text>
                                <Text style={styles.cryptoPrice}>{crypto.price}€</Text>
                            </View>
                        </View>
                        <Text
                            style={[
                                styles.cryptoChange,
                                crypto.change > 0 ? styles.positiveChange : styles.negativeChange
                            ]}
                        >
                            {crypto.change > 0 ? '+' : ''}{crypto.change}%
                        </Text>
                    </View>
                ))}
            </ScrollView>
            <NavBar activeScreen="Notifications" />
        </View>
    );
};

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
    notificationsContainer: {
        flex: 1,
    },
    notificationItem: {
        backgroundColor: theme.colors.surface,
        padding: 15,
        marginBottom: 10,
        borderRadius: 10,
    },
    notificationHeader: {
        flexDirection: 'row',
        marginBottom: 10,
    },
    notificationLogo: {
        width: 40,
        height: 40,
        marginRight: 10,
    },
    notificationDetails: {
        justifyContent: 'center',
    },
    cryptoName: {
        fontSize: 16,
        fontWeight: '600',
        color: theme.colors.text,
    },
    cryptoPrice: {
        fontSize: 14,
        color: theme.colors.text,
    },
    cryptoChange: {
        fontSize: 14,
        fontWeight: 'bold',
    },
    positiveChange: {
        color: theme.colors.success,
    },
    negativeChange: {
        color: theme.colors.error,
    },
});

export default NotificationsScreen;