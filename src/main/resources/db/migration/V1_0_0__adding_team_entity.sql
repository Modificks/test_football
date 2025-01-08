CREATE TABLE team
(
    id         BIGSERIAL PRIMARY KEY,
    name       VARCHAR(256)                                                NOT NULL UNIQUE,
    country    VARCHAR(256)                                                NOT NULL,
    budget     NUMERIC(19, 3) CHECK ( budget >= 0 )                        NOT NULL,
    commission NUMERIC(4, 2) CHECK ( commission >= 0 AND commission <= 10) NOT NULL
);

INSERT INTO team (id, name, country, budget, commission)
VALUES (1, 'Manchester United', 'England', 900000000.000, 10.00),
       (2, 'Real Madrid', 'Spain', 850000000.000, 4.50),
       (3, 'FC Barcelona', 'Spain', 800000000.000, 4.80),
       (4, 'Bayern Munich', 'Germany', 750000000.000, 5.20),
       (5, 'Paris Saint-Germain', 'France', 950000000.000, 4.70),
       (6, 'Juventus', 'Italy', 600000000.000, 5.10),
       (7, 'Liverpool', 'England', 700000000.000, 5.00),
       (8, 'Chelsea', 'England', 850000000.000, 4.60),
       (9, 'Manchester City', 'England', 1000000000.000, 4.90),
       (10, 'Arsenal', 'England', 500000000.000, 5.30),
       (11, 'AC Milan', 'Italy', 400000000.000, 5.10),
       (12, 'Tottenham Hotspur', 'England', 350000000.000, 5.00),
       (13, 'Atletico Madrid', 'Spain', 600000000.000, 4.90),
       (14, 'Inter Milan', 'Italy', 450000000.000, 5.20),
       (15, 'AS Roma', 'Italy', 300000000.000, 5.00),
       (16, 'BVB Dortmund', 'Germany', 400000000.000, 0),
       (17, 'Lyon', 'France', 250000000.000, 4.60),
       (18, 'RB Leipzig', 'Germany', 500000000.000, 5.10),
       (19, 'Boca Juniors', 'Argentina', 150000000.000, 5.00),
       (20, 'River Plate', 'Argentina', 180000000.000, 4.70),
       (21, 'Flamengo', 'Brazil', 300000000.000, 10.00),
       (22, 'São Paulo', 'Brazil', 250000000.000, 4.90),
       (23, 'Corinthians', 'Brazil', 200000000.000, 5.00),
       (24, 'Palmeiras', 'Brazil', 220000000.000, 4.90),
       (25, 'Vasco da Gama', 'Brazil', 180000000.000, 4.70),
       (26, 'Santos', 'Brazil', 170000000.000, 4.60),
       (27, 'New York City FC', 'USA', 250000000.000, 4.80),
       (28, 'LA Galaxy', 'USA', 300000000.000, 4.70),
       (29, 'Club América', 'Mexico', 350000000.000, 0),
       (30, 'Chivas Guadalajara', 'Mexico', 300000000.000, 4.90);

SELECT setval('team_id_seq', (SELECT MAX(id) FROM team));
