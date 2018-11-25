DROP TABLE IF EXISTS SETTING CASCADE;

DROP TABLE IF EXISTS SETTING_DETAIL CASCADE;

CREATE TABLE SETTING
(
  ID   SERIAL NOT NULL UNIQUE,
  TYPE VARCHAR(10) NOT NULL,
  ITEM INT         NOT NULL,
  PRIMARY KEY (ID)
);

CREATE TABLE SETTING_DETAIL
(
  ID         SERIAL      NOT NULL UNIQUE,
  SETTING_ID INT         NOT NULL,
  CREATE_AT  TIMESTAMPTZ NOT NULL,
  PRIMARY KEY (ID, SETTING_ID),
  FOREIGN KEY (SETTING_ID) REFERENCES SETTING (ID) ON DELETE CASCADE
);

INSERT INTO SETTING
  (ID, TYPE, ITEM)
VALUES (1, 'SETTING1', 1),
       (2, 'SETTING2', 2),
       (3, 'SETTING3', 3),
       (4, 'SETTING4', 4),
       (5, 'SETTING5', 5);

INSERT INTO SETTING_DETAIL
  (ID, SETTING_ID, CREATE_AT)
VALUES (1, 1, '2018-11-24 12:00:00 UTC'),
       (2, 2, '2018-11-24 12:00:00 UTC'),
       (3, 3, '2018-11-24 12:00:00 UTC'),
       (4, 3, '2018-11-24 12:00:00 UTC'),
       (5, 3, '2018-11-24 12:00:00 UTC'),
       (6, 4, '2018-11-24 12:00:00 UTC'),
       (7, 5, '2018-11-24 12:00:00 UTC');

