-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Versión del servidor:         8.0.21 - MySQL Community Server - GPL
-- SO del servidor:              Win64
-- HeidiSQL Versión:             11.0.0.5919
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Volcando estructura de base de datos para angenciasgrupo1
CREATE DATABASE IF NOT EXISTS `angenciasgrupo1` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `angenciasgrupo1`;

-- Volcando estructura para tabla angenciasgrupo1.clientes
CREATE TABLE IF NOT EXISTS `clientes` (
  `DNI` varchar(50) NOT NULL,
  `NOMBRE` varchar(50) NOT NULL,
  `APELLIDO` varchar(50) NOT NULL,
  `FECHA_NAC` date NOT NULL,
  `PROFESION` varchar(50) NOT NULL,
  `ESTADO` varchar(50) NOT NULL,
  PRIMARY KEY (`DNI`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla angenciasgrupo1.empleados
CREATE TABLE IF NOT EXISTS `empleados` (
  `DNI` varchar(50) NOT NULL,
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

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla angenciasgrupo1.visitas
CREATE TABLE IF NOT EXISTS `visitas` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `NOMBRE` varchar(50) NOT NULL,
  `N_MAX_CLI` int NOT NULL,
  `PUNTO_PARTIDA` varchar(50) NOT NULL,
  `CURSO_ACADEMICO` varchar(50) NOT NULL,
  `TEMATICA` varchar(50) NOT NULL,
  `COSTE` float NOT NULL,
  `ESTADO` varchar(50) NOT NULL,
  `DNI_EMPLEADO` varchar(50) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `DNI_EMPLEADO` (`DNI_EMPLEADO`),
  CONSTRAINT `visitas_ibfk_1` FOREIGN KEY (`DNI_EMPLEADO`) REFERENCES `empleados` (`DNI`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla angenciasgrupo1.v_guiada
CREATE TABLE IF NOT EXISTS `v_guiada` (
  `ID_VISITA` int NOT NULL,
  `DNI_CLI` varchar(50) NOT NULL,
  KEY `ID_VISITA` (`ID_VISITA`),
  KEY `DNI_CLI` (`DNI_CLI`),
  CONSTRAINT `v_guiada_ibfk_1` FOREIGN KEY (`ID_VISITA`) REFERENCES `visitas` (`ID`),
  CONSTRAINT `v_guiada_ibfk_2` FOREIGN KEY (`DNI_CLI`) REFERENCES `clientes` (`DNI`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- La exportación de datos fue deseleccionada.

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
