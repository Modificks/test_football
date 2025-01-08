CREATE TABLE player
(
    id            BIGSERIAL PRIMARY KEY,
    name          VARCHAR(256)  NOT NULL,
    surname       VARCHAR(256)  NOT NULL,
    date_of_birth DATE          NOT NULL,
    experience    NUMERIC(6, 1) NOT NULL,
    salary        BIGINT        NOT NULL,
    team_id       BIGINT
);

ALTER TABLE player
    ADD CONSTRAINT players_team_fk FOREIGN KEY (team_id) REFERENCES team (id);

INSERT INTO player (id, name, surname, date_of_birth, experience, salary, team_id)
VALUES (1, 'David', 'De Gea', '1990-11-07', 500.0, 1500000, 1),
       (2, 'Cristiano', 'Ronaldo', '1985-02-05', 800.0, 25000000, 1),
       (3, 'Marcus', 'Rashford', '1997-10-31', 200.0, 1200000, 1),
       (4, 'Karim', 'Benzema', '1987-12-19', 700.0, 22000000, 2),
       (5, 'Luka', 'Modrić', '1985-09-09', 600.0, 18000000, 2),
       (6, 'Sergio', 'Ramos', '1986-03-30', 750.0, 23000000, 2),
       (7, 'Lionel', 'Messi', '1987-06-24', 900.0, 40000000, 3),
       (8, 'Sergi', 'Roberto', '1992-02-07', 500.0, 1500000, 3),
       (9, 'Robert', 'Lewandowski', '1988-08-21', 850.0, 25000000, 4),
       (10, 'Thomas', 'Müller', '1989-09-13', 700.0, 22000000, 4),
       (11, 'Kylian', 'Mbappé', '1998-12-20', 900.0, 30000000, 5),
       (12, 'Neymar', 'Jr.', '1992-02-05', 800.0, 25000000, 5),
       (13, 'Gianluigi', 'Buffon', '1978-01-28', 850.0, 12000000, 6),
       (14, 'Paulo', 'Dybala', '1993-11-15', 500.0, 15000000, 6),
       (15, 'Virgil', 'Van Dijk', '1991-07-08', 700.0, 25000000, 7),
       (16, 'Mohamed', 'Salah', '1992-06-15', 800.0, 28000000, 7),
       (17, 'N’Golo', 'Kanté', '1991-03-29', 600.0, 15000000, 8),
       (18, 'Mason', 'Mount', '1999-01-10', 300.0, 5000000, 8),
       (19, 'Raheem', 'Sterling', '1994-12-08', 750.0, 22000000, 9),
       (20, 'Kevin', 'De Bruyne', '1991-06-28', 850.0, 30000000, 9),
       (21, 'Pierre-Emerick', 'Aubameyang', '1989-06-18', 600.00, 15000000, 10),
       (22, 'Bukayo', 'Saka', '2001-09-05', 400.0, 7000000, 10),
       (23, 'Zlatan', 'Ibrahimović', '1981-10-03', 850.0, 10000000, 11),
       (24, 'Stefano', 'Sensi', '1995-08-05', 500.0, 12000000, 11),
       (25, 'Harry', 'Kane', '1993-07-28', 750.0, 22000000, 12),
       (26, 'Heung-Min', 'Son', '1992-07-08', 600.0, 20000000, 12),
       (27, 'Antoine', 'Griezmann', '1991-03-21', 800.0, 26000000, 13),
       (28, 'Diego', 'Costa', '1988-10-07', 700.0, 20000000, 13),
       (29, 'Romelu', 'Lukaku', '1993-05-13', 750.0, 24000000, 14),
       (30, 'Mauro', 'Icardi', '1993-02-19', 600.0, 21000000, 14);

SELECT setval('player_id_seq', (SELECT MAX(id) FROM player));
