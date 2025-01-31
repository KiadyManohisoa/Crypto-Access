
-- Cryptomonnaie 

INSERT INTO cryptomonnaie (id, d_valeur, nom, d_commission) VALUES
('CRYPTO000000001', 0, 'Bitcoin', 25),
('CRYPTO000000002', 0, 'Ethereum', 30),
('CRYPTO000000003', 0, 'Cardano', 15),
('CRYPTO000000004', 0, 'Solana', 20),
('CRYPTO000000005', 0, 'Dogecoin', 5),
('CRYPTO000000006', 0, 'Shiba Inu', 2),
('CRYPTO000000007', 0, 'Polkadot', 18),
('CRYPTO000000008', 0, 'Avalanche', 22),
('CRYPTO000000009', 0, 'XRP', 12),
('CRYPTO000000010', 0, 'Chainlink', 16);



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
