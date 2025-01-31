create or replace view 
    v_historique_crypto as 
select h.*, nom, d_valeur from historiquecrypto h 
    join cryptomonnaie c 
on c.id = idcryptomonnaie;