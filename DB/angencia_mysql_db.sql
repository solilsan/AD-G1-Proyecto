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
  `DNI` varchar(9) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '0',
  `NOMBRE` varchar(50) DEFAULT NULL,
  `APELLIDOS` varchar(50) DEFAULT NULL,
  `FECHA_NACIMIENTO` date DEFAULT NULL,
  `PROFESION` varchar(50) DEFAULT NULL,
  `ESTADO` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`DNI`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla angenciasgrupo1.clientes_visitas_guiadas
CREATE TABLE IF NOT EXISTS `clientes_visitas_guiadas` (
  `ID_VISITA` int DEFAULT NULL,
  `DNI_CLIENTE` varchar(9) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla angenciasgrupo1.visitas
CREATE TABLE IF NOT EXISTS `visitas` (
  `ID` int NOT NULL,
  `NOMBRE` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '0',
  `CLIENTES_MAXIMOS` int NOT NULL DEFAULT '0',
  `PUNTO_PARTIDA` varchar(50) NOT NULL DEFAULT '0',
  `CURSO_ACADEMICO` varchar(50) NOT NULL DEFAULT '0',
  `TEMATICA` varchar(50) NOT NULL DEFAULT '0',
  `ESTADO` varchar(50) NOT NULL DEFAULT '0',
  `DNI_EMPLEADO` varchar(9) NOT NULL DEFAULT '0',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- La exportación de datos fue deseleccionada.

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
