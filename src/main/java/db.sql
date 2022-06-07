/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  pedro
 * Created: 6 de jun. de 2022
 */
DROP DATABASE IF EXISTS javafx_cadastrocursos;
CREATE DATABASE javafx_cadastrocursos;
USE javafx_cadastrocursos;

CREATE TABLE cursos (
  Id int(11) NOT NULL AUTO_INCREMENT,
  Nome varchar(60) DEFAULT NULL,
  Categoria varchar(60) DEFAULT NULL,
  PRIMARY KEY (Id)
);

CREATE TABLE estados (
  Id int(11) NOT NULL AUTO_INCREMENT,
  Estado varchar(60) DEFAULT NULL,
  Uf varchar(60) DEFAULT NULL,
  PRIMARY KEY (Id)
);

CREATE TABLE instrutor (
  Id int(11) NOT NULL AUTO_INCREMENT,
  Nome varchar(60) NOT NULL,
  Email varchar(100) NOT NULL,
  Nascimento datetime NOT NULL,
  Graduacao varchar(100) NOT NULL,
  Faculdade varchar(100) NOT NULL,
  cursosId int(11) NOT NULL,
  PRIMARY KEY (Id),
  FOREIGN KEY (cursosId) REFERENCES cursos (id),
  estadosId int(11) NOT NULL,
  FOREIGN KEY (estadosId) REFERENCES estados (id)
);

CREATE TABLE turma (
  Id int(11) NOT NULL AUTO_INCREMENT,
  Periodo varchar(60) NOT NULL,
  Sala varchar(100) NOT NULL,
  cursosId int(11) NOT NULL,
  instrutorId int(11) NOT NULL,
  PRIMARY KEY (Id),
  FOREIGN KEY (cursosId) REFERENCES cursos (id),
  FOREIGN KEY (instrutorId) REFERENCES instrutor (id)
);

INSERT INTO estados (id, Estado, Uf) VALUES
(1, 'Acre', 'AC'),
(2, 'Alagoas', 'AL'),
(3, 'Amazonas', 'AM'),
(4, 'Amapá', 'AP'),
(5, 'Bahia', 'BA'),
(6, 'Ceará', 'CE'),
(7, 'Distrito Federal', 'DF'),
(8, 'Espírito Santo', 'ES'),
(9, 'Goiás', 'GO'),
(10, 'Maranhão', 'MA'),
(11, 'Minas Gerais', 'MG'),
(12, 'Mato Grosso do Sul', 'MS'),
(13, 'Mato Grosso', 'MT'),
(14, 'Pará', 'PA'),
(15, 'Paraíba', 'PB'),
(16, 'Pernambuco', 'PE'),
(17, 'Piauí', 'PI'),
(18, 'Paraná', 'PR'),
(19, 'Rio de Janeiro', 'RJ'),
(20, 'Rio Grande do Norte', 'RN'),
(21, 'Rondônia', 'RO'),
(22, 'Roraima', 'RR'),
(23, 'Rio Grande do Sul', 'RS'),
(24, 'Santa Catarina', 'SC'),
(25, 'Sergipe', 'SE'),
(26, 'São Paulo', 'SP'),
(27, 'Tocantins', 'TO');
