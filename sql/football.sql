drop database if exists football;
create database football;	
use football;

CREATE TABLE usuario (
	id BINARY(16) NOT NULL,
	loginid VARCHAR(16) NOT NULL UNIQUE,
	password VARCHAR(16) NOT NULL,
	email VARCHAR (255) NOT NULL,
	balance FLOAT(10,2) NOT NULL,
	PRIMARY KEY (id)
);

CREATE TABLE usuario_rol (
	userid BINARY(16) NOT NULL,
	rol ENUM ('registrado', 'admin'),
	FOREIGN KEY (usuarioid) REFERENCES usuario(id) on delete cascade,
	PRIMARY KEY (usuarioid, rol)
);

CREATE TABLE auth_tokens (
	userid BINARY(16) NOT NULL,
	token BINARY(16) NOT NULL,
	FOREIGN KEY (usuarioid) REFERENCES usuario(id) on delete cascade,
	PRIMARY KEY (token)
);

CREATE TABLE equipo(
id BINARY(16) NOT NULL,
nombre VARCHAR(50) NOT NULL,
nomenclatura VARCHAR(5) NOT NULL,
valor INTEGER(50) NOT NULL,
PRIMARY KEY(id)
);

CREATE TABLE liga(
id BINARY(16) NOT NULL,
equipoid BINARY(16) NOT NULL,
nombreequipo VARCHAR(50) NOT NULL,
ranking INTEGER(16) NOT NULL,
puntos INTEGER(16) NOT NULL,
golesfavor INTEGER(16) NOT NULL,
golescontra INTEGER(16) NOT NULL,
diferencia INTEGER(16) NOT NULL,
FOREIGN KEY(equipoid) REFERENCES equipo(id) on delete cascade,
PRIMARY KEY(id)
);

CREATE TABLE jugadores(
equipoid BINARY(16) NOT NULL,
nombre VARCHAR(50) NOT NULL,
dorsal INTEGER(16) NOT NULL,
nacionalidad VARCHAR(50) NOT NULL,
valor VARCHAR(50) NOT NULL,
nacimiento DATE NOT NULL,
fincontrato DATE NOT NULL,
FOREIGN KEY(equipoid) REFERENCES equipo(id) on delete cascade
);

CREATE TABLE partido(
id BINARY(16) NOT NULL,
local BINARY(16) NOT NULL,
visitante BINARY(16) NOT NULL,
jornada INTEGER(16) NOT NULL, 
fecha DATE NOT NULL,
goleslocal INTEGER(16),
golesvisitante INTEGER(16),
estado ENUM ('programado','finalizado'),
FOREIGN KEY(visitante) REFERENCES equipo(id) on delete cascade,
FOREIGN KEY(local) REFERENCES equipo(id) on delete cascade,
PRIMARY KEY(id)
);

CREATE TABLE apuesta(
id BINARY(16) NOT NULL,
partidoid BINARY(16) NOT NULL,
cuota1 FLOAT(10,2) NOT NULL,
cuotax FLOAT(10,2) NOT NULL,
cuota2 FLOAT(10,2) NOT NULL,
ganadora ENUM ('1','x','2'),
estado ENUM ('activa','finalizada'),
FOREIGN KEY(partidoid) REFERENCES partido(id) on delete cascade,
PRIMARY KEY(id)
);

CREATE TABLE apuesta_usuario(
id BINARY(16) NOT NULL,
usuarioid BINARY(16) NOT NULL,
apuestaid BINARY(16) NOT NULL,
resultado ENUM('1','x','2'),
valor FLOAT(10,2) NOT NULL,
resolucion ENUM ('ganada','perdida'),
balance FLOAT(10,2) NOT NUll,
FOREIGN KEY (usuarioid) REFERENCES usuario(id) on delete cascade,
FOREIGN KEY (apuestaid) REFERENCES apuesta(id) on delete cascade,
PRIMARY KEY (id)
);
