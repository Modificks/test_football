INSERT INTO team (name, country, budget, commission)
VALUES ('Test_1', 'Ukraine', 100000, 0),
       ('Test_2', 'Poland', 50000, 0),
       ('Test_3', 'Spain', 90000, 5);

INSERT INTO player (name, surname, date_of_birth, experience, salary, team_id)
VALUES ('Tester_1', 'Any_1', '1990-05-28', 300, 210000, 31),
       ('Tester_2', 'Any_2', '2005-05-07', 200, 1234567, 32),
       ('Tester_3', 'Any_3', '2001-05-01', 5, 3500, 33),
       ('Tester_4', 'Any_4', '2001-05-01', 400, 123, null);
