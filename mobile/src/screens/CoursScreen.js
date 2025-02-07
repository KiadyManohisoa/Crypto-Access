import React from 'react';
import { View, Text, StyleSheet, ScrollView } from 'react-native';
import { BarChart } from 'recharts';
import { Bar, XAxis, YAxis, CartesianGrid, Tooltip, ResponsiveContainer } from 'recharts';
import Header from "../components/Header";
import theme from '../styles/theme';
import NavBar from "../components/NavBar";

export default function CoursScreen() {
    const availableCryptos = [
        { id: '1', name: 'Bitcoin', value: 43000 },
        { id: '2', name: 'Ethereum', value: 3200 },
        { id: '3', name: 'Binance Coin', value: 480 },
        { id: '4', name: 'Litecoin', value: 156 },
        { id: '5', name: 'Solana', value: 140 },
        { id: '6', name: 'Cardano', value: 2.30 },
        { id: '7', name: 'Polkadot', value: 38 },
        { id: '8', name: 'Chainlink', value: 28 },
        { id: '9', name: 'Ripple', value: 1.20 },
        { id: '10', name: 'Dogecoin', value: 0.25 }
    ].sort((a, b) => b.value - a.value);

    return (
        <View style={styles.container}>
            <Header title="Cours Des Cryptos" profileImage={require('../../assets/male.webp')} />

            <ScrollView
                showsVerticalScrollIndicator={false}
                contentContainerStyle={styles.scrollViewContent}
            >
                <View style={styles.chartContainer}>
                    <ResponsiveContainer width="100%" height={200}>
                        <BarChart data={availableCryptos}>
                            <CartesianGrid strokeDasharray="3 3" />
                            <XAxis
                                dataKey="id"
                                tick={{ fill: theme.colors.text }}
                            />
                            <YAxis
                                tick={{ fill: theme.colors.text }}
                            />
                            <Tooltip
                                contentStyle={{ backgroundColor: theme.colors.surface }}
                                labelStyle={{ color: theme.colors.text }}
                                labelFormatter={(id) => availableCryptos.find(c => c.id === id)?.name}
                            />
                            <Bar
                                dataKey="value"
                                fill={theme.colors.primary}
                            />
                        </BarChart>
                    </ResponsiveContainer>
                </View>

                {availableCryptos.map((item) => (
                    <View key={item.id} style={styles.item}>
                        <Text style={styles.id}>#{item.id}</Text>
                        <Text style={styles.name}>{item.name}</Text>
                        <Text style={styles.value}>{item.value}</Text>
                    </View>
                ))}
            </ScrollView>
            <NavBar activeScreen="Cours" />
        </View>
    );
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: theme.colors.background,
        padding: 20,
    },
    scrollViewContent: {
        paddingBottom: 90,
    },
    chartContainer: {
        height: 250,
        marginBottom: 20,
        backgroundColor: theme.colors.surface,
        borderRadius: 10,
        padding: 10,
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
    id: {
        color: theme.colors.primary,
        fontSize: 16,
        marginRight: 10,
    },
    name: {
        color: 'white',
        fontSize: 18,
        flex: 1,
    },
    value: {
        color: theme.colors.primary,
        fontSize: 16,
    },
});
