CREATE DATABASE IF NOT EXISTS appchallengebd;

USE appchallengebd;

SET foreign_key_checks = 0;

DROP TABLE IF EXISTS evaluacion;

CREATE TABLE evaluacion(
   	id_evaluacion INT(5) NOT NULL AUTO_INCREMENT,
	email VARCHAR(120) NOT NULL,
	nombres VARCHAR(100) NOT NULL,
	calificacion INT(1) NOT NULL,
	fecha_creacion DATETIME NOT NULL,
	PRIMARY KEY (`id_evaluacion`)
) ENGINE=INNODB;
 