DROP SCHEMA innlevering_3 CASCADE;

CREATE SCHEMA innlevering_3;
SET search_path TO innlevering_3;

CREATE TABLE avdeling (
    avdelingid SERIAL,
    avdelingsnamn VARCHAR,
    sjefid INTEGER NOT NULL,
    CONSTRAINT avdeling_pk PRIMARY KEY(avdelingid)
    -- må leggje til sjef foreign key etterpå
    -- noko som skal ha NOT NULL?
);

CREATE TABLE ansatt (
    ansattid SERIAL,
    brukarnamn CHAR(4) UNIQUE,
    fornamn VARCHAR,
    etternamn VARCHAR,
    tilsettdato DATE,
    stilling VARCHAR,
    maanadslonn NUMERIC(10,2),
    avdelingid INTEGER,
    CONSTRAINT ansatt_pk PRIMARY KEY(ansattid),
    CONSTRAINT avdelingid_fk FOREIGN KEY (avdelingid) REFERENCES avdeling(avdelingid)
    -- noko som skal ha NOT NULL ?
);


CREATE TABLE prosjekt (
    prosjektid SERIAL,
    prosjektnamn VARCHAR,
    prosjektbeskrivelse VARCHAR,
    CONSTRAINT prosjet_pk PRIMARY KEY(prosjektid)
    -- noko som skal ha NOT NULL?
);

CREATE TABLE prosjektdeltakelse (
    prosjektdelid SERIAL, --brukar kunstig id-nummer/primærnøkkel??
    ansattid INTEGER NOT NULL,
    prosjektid INTEGER NOT NULL,
    arbeidstimar INTEGER,
    rolle VARCHAR,
    CONSTRAINT prosjektdeltakelse_pk PRIMARY KEY(prosjektdelid),
    CONSTRAINT prosjektdeltakelse_unik UNIQUE (ansattid, prosjektid),
    CONSTRAINT ansattid_fk FOREIGN KEY (ansattid) REFERENCES ansatt(ansattid),
    CONSTRAINT prosjektid_fk FOREIGN KEY (prosjektid) REFERENCES prosjekt(prosjektid)
);


INSERT INTO avdeling(avdelingsnamn, sjefid)
VALUES
    ('IT', 1),
    ('Utvikling', 2),
    ('Administrasjon', 5);

INSERT INTO ansatt(brukarnamn, fornamn, etternamn, tilsettdato, stilling, maanadslonn, avdelingid)
VALUES
    ('stgr', 'Stian', 'Grønås', '2019-03-25', 'IT-sjef', 32000, 1),
    ('regr', 'Renate', 'Grønås', '2020-03-25', 'Kjemikar', 36000, 2),
    ('johi', 'Jostein', 'Hindenes', '2020-06-15', 'Elkraft', 31000, 2),
    ('jogr', 'Jonas', 'Grønås', '2021-07-19', 'Gamer', 25000, 3),
    ('ralo', 'Ranveig', 'Løvik', '2019-03-20', 'Administasjonssjef', 37000, 3),
    ('tagr', 'Tara', 'Grønås', '2019-04-01', 'Kjæledyret', 0, 3),
    ('rigr', 'Rita', 'Grønås', '2019-05-02', 'Møbel', 30000, 2),
    ('asgr', 'Asbjørn', 'Grønås', '2019-04-20', 'Ingeniør', 40000, 2),
    ('rulo', 'Rut Malene', 'Løvik', '2019-06-24', 'Fysio', 35000, 3),
    ('olno', 'Ola', 'Normann', '2020-01-01', 'Diverse', 30000, 3);

ALTER TABLE avdeling
ADD CONSTRAINT sjefid_fk FOREIGN KEY (sjefid) REFERENCES ansatt(ansattid);

INSERT INTO prosjekt(prosjektnamn, prosjektbeskrivelse)
VALUES
    ('WS2019 setup', 'oppgradere til Windows Server 2019 versjonen'),
    ('Fortnite pro', 'bli pro gamer i fortnite'),
    ('Nytt kjemikalie', 'utvikle eit nytt og banebrytande kjemikalie'),
    ('Forbetre jobblokale', 'Forbetre jobblokale med nye stolar, gode lys m.m.'),
    ('Nytt prosjekt', 'plassholder for eit nytt ingeniørprosjekt');

INSERT INTO prosjektdeltakelse(ansattid, prosjektid, arbeidstimar, rolle)
VALUES
    (1, 1, 35, 'Prosjektleiar'),
    (4, 2, 40, 'Prosjektleiar'),
    (2, 3, 30, 'Prosjektleiar'),
    (9, 4, 20, 'Konsulent/fysio'),
    (5, 4, 15, 'Prosjektleiar'),
    (10, 2, 25, 'Gamer'),
    (8, 5, 35, 'Prosjektleiar'),
    (3, 5, 35, 'Diverse prosjektting');
    --minke noko?
