CREATE TABLE IF NOT EXISTS role(id integer not null primary key, name varchar(50) not null);
CREATE TABLE IF NOT EXISTS tuser(id IDENTITY, login VARCHAR(255) NOT NULL UNIQUE, password VARCHAR(255) NOT NULL, email VARCHAR(255) NOT NULL UNIQUE, firstName VARCHAR(255), lastName VARCHAR(255), birthday DATE, role varchar(30) NOT NULL, PRIMARY KEY (id));
INSERT INTO role (id, name) VALUES (1, 'ADMIN');
INSERT INTO role (id, name) VALUES (2, 'USER');

INSERT INTO tuser (login, password, email, firstName, lastName, birthday, role) VALUES ('admin','admin','email1@mail.com','adminFirstName','adminLastName','2007-02-04', 'ADMIN');
INSERT INTO tuser (login, password, email, firstName, lastName, birthday, role) VALUES ('user','user','email2@mail.com','userFirstName','userLastName','2010-02-04', 'USER');