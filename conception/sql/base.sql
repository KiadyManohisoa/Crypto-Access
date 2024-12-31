CREATE TABLE Utilisateur(
   id VARCHAR(14)  DEFAULT ('USR') || LPAD(NEXTVAL('s_Utilisateur')::TEXT, 9, '0'),
   mail VARCHAR(50)  NOT NULL,
   nom VARCHAR(100)  NOT NULL,
   prenom VARCHAR(30) ,
   date_naissance DATE NOT NULL,
   PRIMARY KEY(id),
   UNIQUE(mail)
);

CREATE TABLE cryptomonnaie(
   id VARCHAR(50)  DEFAULT ('CRYPTO') || LPAD(NEXTVAL('s_crypto')::TEXT, 9, '0'),
   valeur NUMERIC(15,2)   default 0,
   nom VARCHAR(50)  NOT NULL,
   PRIMARY KEY(id)
);

CREATE TABLE portefeuille(
   id VARCHAR(50)  DEFAULT ('PTF') || LPAD(NEXTVAL('s_portefeuille')::TEXT, 9, '0'),
   id_idUtilisateur VARCHAR(14)  NOT NULL,
   PRIMARY KEY(id),
   UNIQUE(id_idUtilisateur),
   FOREIGN KEY(id_idUtilisateur) REFERENCES Utilisateur(id)
);

CREATE TABLE portefeuille_detail(
   id VARCHAR(50)  DEFAULT ('PTF_DTL') || LPAD(NEXTVAL('s_portefeuille_detail')::TEXT, 9, '0'),
   quantite INTEGER default 0,
   idCryptomonnaie VARCHAR(50)  NOT NULL,
   PRIMARY KEY(id),
   FOREIGN KEY(idCryptomonnaie) REFERENCES cryptomonnaie(id)
);

CREATE TABLE vente(
   id VARCHAR(50)  DEFAULT ('VNT') || LPAD(NEXTVAL('s_vente')::TEXT, 9, '0'),
   quantiteVendu INTEGER default 0,
   dateVente DATE default current_date,
   d_prixVente NUMERIC(15,2)  ,
   idPortefeuilleDetail VARCHAR(50)  NOT NULL,
   PRIMARY KEY(id),
   FOREIGN KEY(idPortefeuilleDetail) REFERENCES portefeuille_detail(id)
);

CREATE TABLE achat(
   id VARCHAR(50)  DEFAULT ('ACT') || LPAD(NEXTVAL('s_achat')::TEXT, 9, '0'),
   quantiteAchat INTEGER default 0,
   dateAchat DATE default current_date,
   idAcheteur VARCHAR(14)  NOT NULL,
   idVente VARCHAR(50)  NOT NULL,
   PRIMARY KEY(id),
   FOREIGN KEY(idAcheteur) REFERENCES Utilisateur(id),
   FOREIGN KEY(idVente) REFERENCES vente(id)
);

CREATE TABLE historiqueCrypto(
   id VARCHAR(50)  DEFAULT ('HISTO_CRYPTO') || LPAD(NEXTVAL('s_histocrypto')::TEXT, 9, '0'),
   cours NUMERIC(15,2)  ,
   dateChangement TIMESTAMP,
   idCryptomonnaie VARCHAR(50)  NOT NULL,
   PRIMARY KEY(id),
   FOREIGN KEY(idCryptomonnaie) REFERENCES cryptomonnaie(id)
);

CREATE TABLE Asso_8(
   idPortefeuille VARCHAR(50) ,
   id VARCHAR(50) ,
   PRIMARY KEY(idPortefeuille, id),
   FOREIGN KEY(idPortefeuille) REFERENCES portefeuille(id),
   FOREIGN KEY(id) REFERENCES portefeuille_detail(id)
);
