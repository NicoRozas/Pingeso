-- MySQL dump 10.13  Distrib 5.7.9, for Win64 (x86_64)
--
-- Host: localhost    Database: mydb
-- ------------------------------------------------------
-- Server version	5.7.9-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `area`
--

DROP TABLE IF EXISTS `area`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `area` (
  `idArea` int(11) NOT NULL AUTO_INCREMENT,
  `nombreArea` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idArea`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `area`
--

LOCK TABLES `area` WRITE;
/*!40000 ALTER TABLE `area` DISABLE KEYS */;
INSERT INTO `area` VALUES (1,'Laboratorio SML'),(2,'LACRIM'),(3,'LABOCAR'),(4,'Tanatología SML'),(5,'Otro');
/*!40000 ALTER TABLE `area` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cargo`
--

DROP TABLE IF EXISTS `cargo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cargo` (
  `idCargo` int(11) NOT NULL AUTO_INCREMENT,
  `nombreCargo` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idCargo`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cargo`
--

LOCK TABLES `cargo` WRITE;
/*!40000 ALTER TABLE `cargo` DISABLE KEYS */;
INSERT INTO `cargo` VALUES (15,'Digitador'),(16,'Administrativo'),(17,'Perito'),(18,'Tecnico'),(19,'Jefe de area'),(20,'Externo'),(22,'Chofer');
/*!40000 ALTER TABLE `cargo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `edicion_formulario`
--

DROP TABLE IF EXISTS `edicion_formulario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `edicion_formulario` (
  `idEdicion` int(11) NOT NULL AUTO_INCREMENT,
  `Usuario_idUsuario` int(11) NOT NULL,
  `Formulario_NUE` int(11) NOT NULL,
  `fechaEdicion` datetime DEFAULT NULL,
  `observaciones` varchar(400) DEFAULT NULL,
  PRIMARY KEY (`idEdicion`),
  KEY `fk_Edicion_Formulario_Usuario1_idx` (`Usuario_idUsuario`),
  KEY `fk_Edicion_Formulario_Formulario1_idx` (`Formulario_NUE`),
  CONSTRAINT `fk_Edicion_Formulario_Formulario1` FOREIGN KEY (`Formulario_NUE`) REFERENCES `formulario` (`NUE`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Edicion_Formulario_Usuario1` FOREIGN KEY (`Usuario_idUsuario`) REFERENCES `usuario` (`idUsuario`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `edicion_formulario`
--

LOCK TABLES `edicion_formulario` WRITE;
/*!40000 ALTER TABLE `edicion_formulario` DISABLE KEYS */;
INSERT INTO `edicion_formulario` VALUES (1,10,1000,'2015-12-22 12:58:28','ed1'),(2,10,1001,'2015-12-22 13:04:38','ed1'),(3,10,1001,'2015-12-22 13:04:46','ed2'),(4,10,432234,'2015-12-24 21:15:42','no era leon, era leona y no leon.'),(5,10,432234,'2015-12-24 21:17:41','ed2'),(6,10,432234,'2015-12-24 21:48:36','mi vaso esta vacio!!!'),(7,10,1000,'2015-12-24 22:09:40','ed2'),(8,10,1003,'2015-12-26 03:07:12','era homicidio frustrado'),(9,10,78,'2015-12-26 05:35:49','ed 1');
/*!40000 ALTER TABLE `edicion_formulario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `evidencia`
--

DROP TABLE IF EXISTS `evidencia`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `evidencia` (
  `idEvidencia` int(11) NOT NULL AUTO_INCREMENT,
  `nombreEvidencia` varchar(45) DEFAULT NULL,
  `Tipo_Evidencia_idTipo_Evidencia` int(11) NOT NULL,
  PRIMARY KEY (`idEvidencia`),
  KEY `fk_Evidencia_Tipo_Evidencia1_idx` (`Tipo_Evidencia_idTipo_Evidencia`),
  CONSTRAINT `fk_Evidencia_Tipo_Evidencia1` FOREIGN KEY (`Tipo_Evidencia_idTipo_Evidencia`) REFERENCES `tipo_evidencia` (`idTipoEvidencia`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `evidencia`
--

LOCK TABLES `evidencia` WRITE;
/*!40000 ALTER TABLE `evidencia` DISABLE KEYS */;
/*!40000 ALTER TABLE `evidencia` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `formulario`
--

DROP TABLE IF EXISTS `formulario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `formulario` (
  `NUE` int(11) NOT NULL,
  `fechaIngreso` datetime DEFAULT NULL,
  `RUC` varchar(45) DEFAULT NULL,
  `RIT` varchar(45) DEFAULT NULL,
  `direccionFotografia` varchar(60) DEFAULT NULL,
  `fechaOcurrido` datetime DEFAULT NULL,
  `lugarLevantamiento` varchar(50) DEFAULT NULL,
  `numeroParte` int(11) DEFAULT NULL,
  `observaciones` varchar(300) DEFAULT NULL,
  `direccionSS` varchar(50) DEFAULT NULL,
  `delitoRef` varchar(45) DEFAULT NULL,
  `descripcionEspecieFormulario` varchar(300) DEFAULT NULL,
  `ultimaEdicion` datetime DEFAULT NULL,
  `Usuario_idUsuario` int(11) NOT NULL,
  `Usuario_idUsuario1` int(11) NOT NULL,
  `bloqueado` tinyint(1) NOT NULL,
  PRIMARY KEY (`NUE`),
  KEY `fk_Formulario_Usuario1_idx` (`Usuario_idUsuario`),
  KEY `fk_Formulario_Usuario2_idx` (`Usuario_idUsuario1`),
  CONSTRAINT `fk_Formulario_Usuario1` FOREIGN KEY (`Usuario_idUsuario`) REFERENCES `usuario` (`idUsuario`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Formulario_Usuario2` FOREIGN KEY (`Usuario_idUsuario1`) REFERENCES `usuario` (`idUsuario`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `formulario`
--

LOCK TABLES `formulario` WRITE;
/*!40000 ALTER TABLE `formulario` DISABLE KEYS */;
INSERT INTO `formulario` VALUES (78,'2015-12-26 05:28:26','','','C:','2015-12-01 08:30:00','san fernando',0,'','violetas 788','Homicidio','difunto rut 7854247-9','2015-12-26 05:35:49',8,13,1),(79,'2015-12-26 07:53:07','','','C:','2015-12-01 10:35:00','cocina',0,'','los marinos 656','Homicidio','','2015-12-26 07:53:07',8,13,0),(1000,'2015-12-22 12:58:20','','','C:','2015-12-22 03:11:00','Melipilla',0,'obs1','Correa','Agresion sexual','desp1','2015-12-24 22:09:40',10,10,0),(1001,'2015-12-22 13:04:31','','','C:','2015-12-23 09:33:00','Melipilla',0,'obs1','Galilea','Homicidio','desp1','2015-12-22 13:04:46',10,10,0),(1003,'2015-12-26 03:06:07','','','C:','2015-12-26 03:06:06','cocina',0,'','los marinos 656','homicidio','','2015-12-26 03:07:12',10,10,0),(342345,'2015-12-25 18:06:45','','','C:','2015-12-25 18:06:45','tanatologia',2454235,'','sml','homicidio','','2015-12-25 18:06:45',10,10,0),(432234,'2015-12-24 21:15:16','','','C:','2015-12-24 21:15:16','tanatologia',0,'','sml','homicidio','1','2015-12-24 21:48:36',10,10,0);
/*!40000 ALTER TABLE `formulario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `formulario_evidencia`
--

DROP TABLE IF EXISTS `formulario_evidencia`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `formulario_evidencia` (
  `Formulario_NUE` int(11) NOT NULL,
  `Evidencia_idEvidencia` int(11) NOT NULL,
  `cantidad` int(11) DEFAULT NULL,
  PRIMARY KEY (`Formulario_NUE`,`Evidencia_idEvidencia`),
  KEY `fk_Formulario_Evidencia_Formulario1_idx` (`Formulario_NUE`),
  KEY `fk_Formulario_Evidencia_Evidencia1_idx` (`Evidencia_idEvidencia`),
  CONSTRAINT `fk_Formulario_Evidencia_Evidencia1` FOREIGN KEY (`Evidencia_idEvidencia`) REFERENCES `evidencia` (`idEvidencia`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Formulario_Evidencia_Formulario1` FOREIGN KEY (`Formulario_NUE`) REFERENCES `formulario` (`NUE`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `formulario_evidencia`
--

LOCK TABLES `formulario_evidencia` WRITE;
/*!40000 ALTER TABLE `formulario_evidencia` DISABLE KEYS */;
/*!40000 ALTER TABLE `formulario_evidencia` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `formulario_relacionado`
--

DROP TABLE IF EXISTS `formulario_relacionado`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `formulario_relacionado` (
  `Formulario_NUE` int(11) NOT NULL,
  `Formulario_NUE1` int(11) NOT NULL,
  PRIMARY KEY (`Formulario_NUE1`,`Formulario_NUE`),
  KEY `fk_Formulario_Relacionado_Formulario1_idx` (`Formulario_NUE`),
  CONSTRAINT `fk_Formulario_Relacionado_Formulario1` FOREIGN KEY (`Formulario_NUE`) REFERENCES `formulario` (`NUE`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Formulario_Relacionado_Formulario2` FOREIGN KEY (`Formulario_NUE1`) REFERENCES `formulario` (`NUE`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `formulario_relacionado`
--

LOCK TABLES `formulario_relacionado` WRITE;
/*!40000 ALTER TABLE `formulario_relacionado` DISABLE KEYS */;
/*!40000 ALTER TABLE `formulario_relacionado` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `peritaje`
--

DROP TABLE IF EXISTS `peritaje`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `peritaje` (
  `idPeritaje` int(11) NOT NULL AUTO_INCREMENT,
  `fechaPeritaje` datetime DEFAULT NULL,
  `Usuario_idUsuario` int(11) NOT NULL,
  `Formulario_NUE` int(11) NOT NULL,
  PRIMARY KEY (`idPeritaje`),
  KEY `fk_Peritaje_Formulario1_idx` (`Formulario_NUE`),
  KEY `fk_Peritaje_Usuario1` (`Usuario_idUsuario`),
  CONSTRAINT `fk_Peritaje_Formulario1` FOREIGN KEY (`Formulario_NUE`) REFERENCES `formulario` (`NUE`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Peritaje_Usuario1` FOREIGN KEY (`Usuario_idUsuario`) REFERENCES `usuario` (`idUsuario`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `peritaje`
--

LOCK TABLES `peritaje` WRITE;
/*!40000 ALTER TABLE `peritaje` DISABLE KEYS */;
/*!40000 ALTER TABLE `peritaje` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipo_evidencia`
--

DROP TABLE IF EXISTS `tipo_evidencia`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tipo_evidencia` (
  `idTipoEvidencia` int(11) NOT NULL AUTO_INCREMENT,
  `nombreTipoEvidencia` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idTipoEvidencia`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipo_evidencia`
--

LOCK TABLES `tipo_evidencia` WRITE;
/*!40000 ALTER TABLE `tipo_evidencia` DISABLE KEYS */;
/*!40000 ALTER TABLE `tipo_evidencia` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipo_motivo`
--

DROP TABLE IF EXISTS `tipo_motivo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tipo_motivo` (
  `idMotivo` int(11) NOT NULL AUTO_INCREMENT,
  `tipoMotivo` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idMotivo`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipo_motivo`
--

LOCK TABLES `tipo_motivo` WRITE;
/*!40000 ALTER TABLE `tipo_motivo` DISABLE KEYS */;
INSERT INTO `tipo_motivo` VALUES (1,'Peritaje'),(2,'Custodia'),(3,'Traslado');
/*!40000 ALTER TABLE `tipo_motivo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipo_usuario`
--

DROP TABLE IF EXISTS `tipo_usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tipo_usuario` (
  `idTipoUsuario` int(11) NOT NULL AUTO_INCREMENT,
  `nombreTipo` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idTipoUsuario`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipo_usuario`
--

LOCK TABLES `tipo_usuario` WRITE;
/*!40000 ALTER TABLE `tipo_usuario` DISABLE KEYS */;
INSERT INTO `tipo_usuario` VALUES (1,'SML'),(2,'Externo');
/*!40000 ALTER TABLE `tipo_usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `traslado`
--

DROP TABLE IF EXISTS `traslado`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `traslado` (
  `idInvolucrado` int(11) NOT NULL AUTO_INCREMENT,
  `fechaEntrega` datetime DEFAULT NULL,
  `observaciones` varchar(300) DEFAULT NULL,
  `Tipo_Motivo_idMotivo` int(11) NOT NULL,
  `Usuario_idUsuario1` int(11) NOT NULL,
  `Usuario_idUsuario` int(11) NOT NULL,
  `Formulario_NUE` int(11) NOT NULL,
  PRIMARY KEY (`idInvolucrado`),
  KEY `fk_Involucrado_Tipo_Motivo1_idx` (`Tipo_Motivo_idMotivo`),
  KEY `fk_Involucrado_Usuario2_idx` (`Usuario_idUsuario1`),
  KEY `fk_Involucrado_Formulario1_idx` (`Formulario_NUE`),
  KEY `fk_Traslado_Usuario1_idx` (`Usuario_idUsuario`),
  CONSTRAINT `fk_Involucrado_Formulario1` FOREIGN KEY (`Formulario_NUE`) REFERENCES `formulario` (`NUE`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Involucrado_Tipo_Motivo1` FOREIGN KEY (`Tipo_Motivo_idMotivo`) REFERENCES `tipo_motivo` (`idMotivo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Involucrado_Usuario2` FOREIGN KEY (`Usuario_idUsuario1`) REFERENCES `usuario` (`idUsuario`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Traslado_Usuario1` FOREIGN KEY (`Usuario_idUsuario`) REFERENCES `usuario` (`idUsuario`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `traslado`
--

LOCK TABLES `traslado` WRITE;
/*!40000 ALTER TABLE `traslado` DISABLE KEYS */;
INSERT INTO `traslado` VALUES (1,'2015-12-24 00:05:00','obs',1,10,11,1000),(2,'2015-12-26 05:35:05','abro esta cc para realizar peritaje.',1,13,10,78);
/*!40000 ALTER TABLE `traslado` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usuario` (
  `idUsuario` int(11) NOT NULL AUTO_INCREMENT,
  `nombreUsuario` varchar(45) DEFAULT NULL,
  `apellidoUsuario` varchar(45) DEFAULT NULL,
  `rutUsuario` varchar(30) DEFAULT NULL,
  `passUsuario` varchar(45) DEFAULT NULL,
  `mailUsuario` varchar(45) DEFAULT NULL,
  `cuentaUsuario` varchar(30) DEFAULT NULL,
  `estadoUsuario` tinyint(1) DEFAULT NULL,
  `unidad` varchar(45) DEFAULT NULL,
  `Cargo_idCargo` int(11) NOT NULL,
  `Area_idArea` int(11) NOT NULL,
  `Tipo_Usuario_idTipoUsuario` int(11) NOT NULL,
  PRIMARY KEY (`idUsuario`),
  KEY `fk_Usuario_Cargo1_idx` (`Cargo_idCargo`),
  KEY `fk_Usuario_Area1_idx` (`Area_idArea`),
  KEY `fk_Usuario_Tipo_Usuario1_idx` (`Tipo_Usuario_idTipoUsuario`),
  CONSTRAINT `fk_Usuario_Area1` FOREIGN KEY (`Area_idArea`) REFERENCES `area` (`idArea`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Usuario_Cargo1` FOREIGN KEY (`Cargo_idCargo`) REFERENCES `cargo` (`idCargo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Usuario_Tipo_Usuario1` FOREIGN KEY (`Tipo_Usuario_idTipoUsuario`) REFERENCES `tipo_usuario` (`idTipoUsuario`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (8,'Aracelly','Altamirano','174212074','conManjar','ara@correo.com','aaltamirano',1,'unidad A',15,1,1),(9,'Nicolas','Rozas','182953202','nikoazul1','nico@correo.com','nrozas',1,'unidad B',18,1,1),(10,'Sebastian','Acevedo','18486956k','sapito','zack@correo.com','sacevedo',1,'unidad B',17,1,1),(11,'Patricia','Riquelme','188444407','patita','paty@correo.com','priquelme',1,'unidad B',19,1,1),(13,'Armando','Casas','82843973','codo','armando@correo.com','acasas',1,'unidad C',22,1,1),(14,'Zulema','Sanchez','131457502','sol','zul@correo.com','zsanchez',1,'unidad A',16,1,1);
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-12-27  0:19:02