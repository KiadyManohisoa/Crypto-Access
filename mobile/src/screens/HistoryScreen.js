import React, { useState } from 'react';
import { View, Text, TouchableOpacity, StyleSheet, ScrollView } from 'react-native';
import NavBar from '../components/NavBar';
import Header from '../components/Header';
import theme from '../styles/theme';

const HistoryScreen = () => {
    const [balance, setBalance] = useState(1000);
    const [selectedTab, setSelectedTab] = useState('achat');

    const purchaseHistory = [
        { id: 1, date: '2025-02-01', amount: 200, description: 'Achat produit A' },
        { id: 2, date: '2025-02-03', amount: 150, description: 'Achat produit B' },
    ];

    const saleHistory = [
        { id: 1, date: '2025-02-02', amount: 300, description: 'Vente produit C' },
        { id: 2, date: '2025-02-04', amount: 100, description: 'Vente produit D' },
    ];

    const renderHistory = () => {
        const history = selectedTab === 'achat' ? purchaseHistory : saleHistory;
        return history.map((item) => (
            <View key={item.id} style={styles.historyItem}>
                <View style={styles.historyItemHeader}>
                    <Text style={styles.historyDate}>{item.date}</Text>
                    <Text style={[styles.historyAmount, selectedTab === 'achat' ? styles.negativeChange : styles.positiveChange]}>
                        {selectedTab === 'achat' ? '-' : '+'} {item.amount}€
                    </Text>
                </View>
                <Text style={styles.historyDescription}>{item.description}</Text>
            </View>
        ));
    };

    return (
        <View style={styles.container}>
            <Header title="Historique" profileImage={require('../../assets/male.webp')} />

            <View style={styles.tabsContainer}>
                <TouchableOpacity
                    style={[styles.tabButton, selectedTab === 'achat' && styles.activeTab]}
                    onPress={() => setSelectedTab('achat')}
                >
                    <Text style={[styles.tabText, selectedTab === 'achat' && styles.activeTabText]}>Achats</Text>
                </TouchableOpacity>
                <TouchableOpacity
                    style={[styles.tabButton, selectedTab === 'vente' && styles.activeTab]}
                    onPress={() => setSelectedTab('vente')}
                >
                    <Text style={[styles.tabText, selectedTab === 'vente' && styles.activeTabText]}>Ventes</Text>
                </TouchableOpacity>
            </View>

            <ScrollView style={styles.historyContainer} showsVerticalScrollIndicator={false}>
                {renderHistory()}
            </ScrollView>

            <NavBar activeScreen="History" />
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
    balanceContainer: {
        alignItems: 'center',
        marginBottom: 20,
    },
    balanceText: {
        fontSize: 22,
        color: theme.colors.primary,
        fontWeight: '700',
    },
    tabsContainer: {
        flexDirection: 'row',
        backgroundColor: theme.colors.secondary,
        borderRadius: 10,
        marginBottom: 20,
    },
    tabButton: {
        flex: 1,
        paddingVertical: 12,
        alignItems: 'center',
    },
    activeTab: {
        backgroundColor: theme.colors.primary,
    },
    tabText: {
        color: theme.colors.text,
        fontSize: 16,
        fontWeight: '600',
    },
    activeTabText: {
        color: theme.colors.background,
        fontWeight: '700',
    },
    historyContainer: {
        flex: 1,
    },
    historyItem: {
        backgroundColor: theme.colors.secondary,
        padding: 15,
        marginBottom: 10,
        borderRadius: 10,
    },
    historyItemHeader: {
        flexDirection: 'row',
        justifyContent: 'space-between',
        marginBottom: 5,
    },
    historyDate: {
        color: theme.colors.text,
        fontSize: 14,
    },
    historyAmount: {
        fontSize: 16,
        fontWeight: '700',
        padding: 6,
        borderRadius: 6,
    },
    positiveChange: {
        color: theme.colors.success,
    },
    negativeChange: {
        color: theme.colors.error,
    },
    historyDescription: {
        color: theme.colors.text,
        fontSize: 14,
    },
});

export default HistoryScreen;