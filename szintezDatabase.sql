CREATE TABLE person(
personid AUTOINCREMENT,
veznev VARCHAR(20),
kernev VARCHAR(20),
email VARCHAR(20),
passwd VARCHAR(20),
PRIMARY KEY(personid)
);

CREATE TABLE adatok(
adatokid AUTOINCREMENT,
personid INT,
behigh INT,
below INT,
eq1 INT,
at1 INT,
eq2 INT,
at2 INT,
eq3 INT,
at3 INT,
kilow INT,
kihigh INT,
tilt INT,
sweeplow INT,
sweephigh INT,
PRIMARY KEY(adatokid)
);

ALTER TABLE adatok ADD CONSTRAINT fk1 FOREIGN KEY (personid) REFERENCES person(personid);
