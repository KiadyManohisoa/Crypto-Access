create or replace view 
    v_historique_crypto as 
select h.*, nom, d_valeur from historiquecrypto h 
    join cryptomonnaie c 
on c.id = idcryptomonnaie;

create or replace view 
    v_transaction_crypto as 
select t.id, t.dateTransaction, t.d_commission, t.idcryptomonnaie, c.nom  from transactioncrypto t    
    join cryptomonnaie c
on c.id=t.idcryptomonnaie;