CREATE TABLE Utilisateur(
   id VARCHAR(14)  DEFAULT ('USR') || LPAD(NEXTVAL('s_Utilisateur')::TEXT, 9, '0'),
   mail VARCHAR(50)  NOT NULL,
   nom VARCHAR(100)  NOT NULL,
   prenom VARCHAR(30) ,
   date_naissance DATE NOT NULL,
   lienImage VARCHAR(200) ,
   PRIMARY KEY(id),
   UNIQUE(mail)
);

CREATE TABLE cryptomonnaie(
   id VARCHAR(50)  DEFAULT ('CRYPTO') || LPAD(NEXTVAL('s_crypto')::TEXT, 9, '0'),
   d_valeur NUMERIC(15,2)   default 0,
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
   d_quantite INTEGER default 0,
   idPortefeuille VARCHAR(50)  NOT NULL,
   idCryptomonnaie VARCHAR(50)  NOT NULL,
   PRIMARY KEY(id),
   FOREIGN KEY(idPortefeuille) REFERENCES portefeuille(id),
   FOREIGN KEY(idCryptomonnaie) REFERENCES cryptomonnaie(id)
);

CREATE TABLE vente(
   id VARCHAR(50)  DEFAULT ('VNT') || LPAD(NEXTVAL('s_vente')::TEXT, 9, '0'),
   quantiteVendu INTEGER default 0,
   dateVente TIMESTAMP default current_date,
   d_prixVente NUMERIC(15,2)  ,
   d_reste INTEGER,
   idPortefeuilleDetail VARCHAR(50)  NOT NULL,
   PRIMARY KEY(id),
   FOREIGN KEY(idPortefeuilleDetail) REFERENCES portefeuille_detail(id)
);

CREATE TABLE achat(
   id VARCHAR(50)  DEFAULT ('ACT') || LPAD(NEXTVAL('s_achat')::TEXT, 9, '0'),
   quantiteAchat INTEGER default 0,
   dateAchat TIMESTAMP default current_date,
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

CREATE TABLE historiqueTransaction(
   id VARCHAR(50)  DEFAULT ('HST_TRS') || LPAD(NEXTVAL('s_historique_transaction')::TEXT, 9, '0'),
   dateTransaction TIMESTAMP default current_timestamp,
   quantite INTEGER default 0,
   d_prixUnitaire VARCHAR(50) ,
   d_commission NUMERIC(5,2)   default 0,
   idCryptomonnaie VARCHAR(50)  NOT NULL,
   id_1 VARCHAR(14)  NOT NULL,
   id_2 VARCHAR(14)  NOT NULL,
   PRIMARY KEY(id),
   FOREIGN KEY(idCryptomonnaie) REFERENCES cryptomonnaie(id),
   FOREIGN KEY(id_1) REFERENCES Utilisateur(id),
   FOREIGN KEY(id_2) REFERENCES Utilisateur(id)
);

CREATE TABLE commission(
   id VARCHAR(50)  DEFAULT ('CMS') || LPAD(NEXTVAL('s_commission')::TEXT, 9, '0'),
   pourcentage NUMERIC(5,2)   default 0,
   dateChangement TIMESTAMP,
   PRIMARY KEY(id)
);

CREATE TABLE fond(
   id VARCHAR(50)  DEFAULT ('FND') || LPAD(NEXTVAL('s_fond')::TEXT, 9, '0'),
   montant NUMERIC(15,2)  ,
   dateMouvement TIMESTAMP default current_date,
   idUtilisateur VARCHAR(14)  NOT NULL,
   PRIMARY KEY(id),
   FOREIGN KEY(idUtilisateur) REFERENCES Utilisateur(id)
);

CREATE TABLE admin(
   id VARCHAR(50)  DEFAULT ('ADM') || LPAD(NEXTVAL('s_admin')::TEXT, 9, '0'),
   nom VARCHAR(50)  NOT NULL,
   mdp VARCHAR(200)  NOT NULL,
   PRIMARY KEY(id)
);

CREATE TABLE fondAttente(
   id VARCHAR(50)  DEFAULT ('FND_ATT') || LPAD(NEXTVAL('s_fond_attente')::TEXT, 9, '0'),
   montant NUMERIC(15,2)  ,
   dateMouvement TIMESTAMP default current_date,
   idUtilisateur VARCHAR(14)  NOT NULL,
   PRIMARY KEY(id),
   FOREIGN KEY(idUtilisateur) REFERENCES Utilisateur(id)
);

CREATE TABLE cryptofavori(
   idUtilisateur VARCHAR(14) ,
   idCryptomonnaie VARCHAR(50) ,
   PRIMARY KEY(idUtilisateur, idCryptomonnaie),
   FOREIGN KEY(idUtilisateur) REFERENCES Utilisateur(id),
   FOREIGN KEY(idCryptomonnaie) REFERENCES cryptomonnaie(id)
);

CREATE TABLE historiqueUtilisateur (
    id VARCHAR(50) DEFAULT ('HST_UTL') || LPAD(NEXTVAL('s_historiqueUtilisateur')::TEXT, 9, '0'),
    idUtilisateur VARCHAR(50) NOT NULL,
    operation VARCHAR(50) NOT NULL,
    dateExecution TIMESTAMP DEFAULT CURRENT_TIMESTAMP, 
     PRIMARY KEY(id),
    FOREIGN KEY(idUtilisateur) REFERENCES Utilisateur(id)

);

