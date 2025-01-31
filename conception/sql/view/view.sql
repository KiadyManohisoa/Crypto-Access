
-- Info Vente - utilisateur
  
  create or replace view v_vente_utilisateur_detail as SELECT u.nom,crypto.valeur,details.quantite,v.id AS venteId,v.quantiteVendu,v.dateVente,
  v.d_prixVente,details.id AS portefeuilleDetailId FROM portefeuille pt JOIN utilisateur u ON u.id = pt.id_idUtilisateur
  JOIN LiaisonPorteFeuilleDetails liaison ON pt.id = liaison.idPortefeuille JOIN portefeuille_detail details 
  ON liaison.id = details.id JOIN  cryptomonnaie crypto ON details.idCryptomonnaie = crypto.id
  JOIN vente v ON details.id = v.idPortefeuilleDetail ;