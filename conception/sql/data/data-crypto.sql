
-- Cryptomonnaie 

INSERT INTO cryptomonnaie (id, d_valeur, nom, d_commission) VALUES
('CRYPTO000000001', 42000.50, 'Bitcoin', 25),
('CRYPTO000000002', 3000.75, 'Ethereum', 30),
('CRYPTO000000003', 1.25, 'Cardano', 15),
('CRYPTO000000004', 150.80, 'Solana', 20),
('CRYPTO000000005', 0.10, 'Dogecoin', 5),
('CRYPTO000000006', 0.50, 'Shiba Inu', 2),
('CRYPTO000000007', 230.60, 'Polkadot', 18),
('CRYPTO000000008', 130.20, 'Avalanche', 22),
('CRYPTO000000009', 1.10, 'XRP', 12),
('CRYPTO000000010', 25.75, 'Chainlink', 16);


-- Commission : 

INSERT INTO commission (pourcentage, dateChangement, idCryptomonnaie) VALUES
(25.00, NOW(), 'CRYPTO000000001'),
(30.00, NOW(), 'CRYPTO000000002'),
(15.00, NOW(), 'CRYPTO000000003'),
(20.00, NOW(), 'CRYPTO000000004'),
(5.00, NOW(), 'CRYPTO000000005'),
(2.00, NOW(), 'CRYPTO000000006'),
(18.00, NOW(), 'CRYPTO000000007'),
(22.00, NOW(), 'CRYPTO000000008'),
(12.00, NOW(), 'CRYPTO000000009'),
(16.00, NOW(), 'CRYPTO000000010');
