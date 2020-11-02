/*
SQLyog Community v13.1.6 (64 bit)
MySQL - 5.7.31-log : Database - agencia_mysql
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`agencia_mysql` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `agencia_mysql`;

/*Table structure for table `clientes` */

DROP TABLE IF EXISTS `clientes`;

CREATE TABLE `clientes` (
  `DNI` varchar(9) NOT NULL,
  `NOMBRE` varchar(50) NOT NULL,
  `APELLIDO` varchar(50) NOT NULL,
  `FECHA_NAC` date NOT NULL,
  `PROFESION` varchar(50) NOT NULL,
  `ESTADO` varchar(50) NOT NULL,
  PRIMARY KEY (`DNI`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `empleados` */

DROP TABLE IF EXISTS `empleados`;

CREATE TABLE `empleados` (
  `DNI` varchar(9) NOT NULL,
  `NOMBRE` varchar(50) NOT NULL,
  `APELLIDO` varchar(50) NOT NULL,
  `FECHA_NAC` date NOT NULL,
  `F_CONTRATACION` date NOT NULL,
  `NACIONALIDAD` varchar(50) NOT NULL,
  `CARGO` varchar(50) NOT NULL,
  `PASSWORD` varchar(50) NOT NULL,
  `ESTADO` varchar(50) NOT NULL,
  PRIMARY KEY (`DNI`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `v_guiada` */

DROP TABLE IF EXISTS `v_guiada`;

CREATE TABLE `v_guiada` (
  `ID_VISITA` int(11) NOT NULL,
  `DNI_CLI` varchar(9) NOT NULL,
  KEY `ID_VISITA` (`ID_VISITA`),
  KEY `DNI_CLI` (`DNI_CLI`),
  CONSTRAINT `v_guiada_ibfk_1` FOREIGN KEY (`ID_VISITA`) REFERENCES `visitas` (`ID`),
  CONSTRAINT `v_guiada_ibfk_2` FOREIGN KEY (`DNI_CLI`) REFERENCES `clientes` (`DNI`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `visitas` */

DROP TABLE IF EXISTS `visitas`;

CREATE TABLE `visitas` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NOMBRE` varchar(50) NOT NULL,
  `N_MAX_CLI` int(10) NOT NULL,
  `PUNTO_PARTIDA` varchar(50) NOT NULL,
  `CURSO_ACADEMICO` varchar(50) NOT NULL,
  `TEMATICA` varchar(50) NOT NULL,
  `COSTE` float NOT NULL,
  `ESTADO` varchar(50) NOT NULL,
  `DNI_EMPLEADO` varchar(50) NOT NULL,
  `FECHA_HORA` datetime NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `DNI_EMPLEADO` (`DNI_EMPLEADO`),
  CONSTRAINT `visitas_ibfk_1` FOREIGN KEY (`DNI_EMPLEADO`) REFERENCES `empleados` (`DNI`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
