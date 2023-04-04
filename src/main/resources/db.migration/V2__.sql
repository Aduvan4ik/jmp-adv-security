INSERT INTO person (email, password)
VALUES ('admin@epam.com', '$2a$12$Y0y9KRxTlRDqSrlyAcrKROiN85tqcDWaUQjnvymh2WV0qbUBKauPi');

INSERT INTO person (email, password)
VALUES ('user1@epam.com', '$2a$12$jgRL6X0Rs0/X2eeh.BQi0.sVrcuoZSyaVotKKFoxyrDO7dKIrbkz2');

INSERT INTO person (email, password)
VALUES ('user2@epam.com', '$2a$12$N3Cy4IU3ZQicsibuzOf4SO346ZzhEbY71cgcA.Gj8cMImyMXfCCcS');

INSERT INTO authority (name)
VALUES ('VIEW_INFO');

INSERT INTO authority (name)
VALUES ('VIEW_ADMIN');

INSERT INTO authority_person (authority_id, person_id)
VALUES (1, 1);
INSERT INTO authority_person (authority_id, person_id)
VALUES (2, 1);
INSERT INTO authority_person (authority_id, person_id)
VALUES (1, 2);
INSERT INTO authority_person (authority_id, person_id)
VALUES (1, 3);