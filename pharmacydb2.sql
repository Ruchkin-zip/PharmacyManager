-- MySQL dump 10.13  Distrib 8.0.23, for Win64 (x86_64)
--
-- Host: localhost    Database: pharmacydb2
-- ------------------------------------------------------
-- Server version	8.0.23

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `illness`
--

DROP TABLE IF EXISTS `illness`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `illness` (
  `idillness` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idillness`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `illness`
--

LOCK TABLES `illness` WRITE;
/*!40000 ALTER TABLE `illness` DISABLE KEYS */;
INSERT INTO `illness` VALUES (16,'Головная боль'),(31,'Боль в животе'),(36,'Боль в горле'),(38,'Температура'),(39,'Витамины'),(40,'Противоалиргенное'),(41,'Противовирусное'),(42,'Обезболивающее'),(43,'Testill');
/*!40000 ALTER TABLE `illness` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pharmacy`
--

DROP TABLE IF EXISTS `pharmacy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pharmacy` (
  `idpharmacy` int NOT NULL AUTO_INCREMENT,
  `address` varchar(45) DEFAULT 'unspecified',
  PRIMARY KEY (`idpharmacy`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pharmacy`
--

LOCK TABLES `pharmacy` WRITE;
/*!40000 ALTER TABLE `pharmacy` DISABLE KEYS */;
INSERT INTO `pharmacy` VALUES (6,'Lenina 12'),(17,'Чайковского 115'),(18,'Испытателей 10'),(19,'Мира 75');
/*!40000 ALTER TABLE `pharmacy` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `preparat`
--

DROP TABLE IF EXISTS `preparat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `preparat` (
  `idpreparat` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `price` int DEFAULT NULL,
  `cntSold` int DEFAULT NULL,
  `count` int DEFAULT NULL,
  `idIll` int DEFAULT NULL,
  `id_pharmacy` int DEFAULT NULL,
  PRIMARY KEY (`idpreparat`),
  KEY `test_idx` (`id_pharmacy`),
  KEY `preparat_illness_idx` (`idIll`),
  CONSTRAINT `preparat_illness` FOREIGN KEY (`idIll`) REFERENCES `illness` (`idillness`),
  CONSTRAINT `preparat_pharmacy_fk` FOREIGN KEY (`id_pharmacy`) REFERENCES `pharmacy` (`idpharmacy`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=106 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `preparat`
--

LOCK TABLES `preparat` WRITE;
/*!40000 ALTER TABLE `preparat` DISABLE KEYS */;
INSERT INTO `preparat` VALUES (71,'Арбидол',400,0,111,41,6),(72,'Аскорбиновая кислота',34,50,850,39,6),(77,'Гематоген',50,0,400,39,6),(88,'Витамин D',12,10,338,39,17),(89,'Мезим',200,83,748,31,18),(93,'Люголь',410,1,114,36,18),(94,'Активированный уголь',20,0,1000,31,18),(98,'Парацетамол',25,2,23226,38,17),(99,'Терафлю',250,1,59,36,18),(100,'Витамин С',40,8,247,39,18),(101,'Зодак',320,0,0,40,18),(102,'Гексорал',299,4,11,43,18),(103,'Фурозалидон',200,0,100,31,18),(104,'Нурофен',260,2,88,42,19),(105,'Test',188,0,123,43,18);
/*!40000 ALTER TABLE `preparat` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `soldpreparat`
--

DROP TABLE IF EXISTS `soldpreparat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `soldpreparat` (
  `idSoldPreparat` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `cntSold` int DEFAULT '0',
  `price` int DEFAULT NULL,
  `idIllness` int DEFAULT NULL,
  `idPharmacy` int DEFAULT NULL,
  `idPreparat` int DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  PRIMARY KEY (`idSoldPreparat`),
  KEY `idPharmacy_key_idx` (`idPharmacy`),
  KEY `idIllness_Key_idx` (`idIllness`),
  KEY `idPreparat_Key_idx` (`idPreparat`),
  CONSTRAINT `idIllness_Key` FOREIGN KEY (`idIllness`) REFERENCES `illness` (`idillness`),
  CONSTRAINT `idPharmacy_key` FOREIGN KEY (`idPharmacy`) REFERENCES `pharmacy` (`idpharmacy`),
  CONSTRAINT `idPreparat_Key` FOREIGN KEY (`idPreparat`) REFERENCES `preparat` (`idpreparat`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `soldpreparat`
--

LOCK TABLES `soldpreparat` WRITE;
/*!40000 ALTER TABLE `soldpreparat` DISABLE KEYS */;
INSERT INTO `soldpreparat` VALUES (11,'Терафлю',1,250,36,18,99,'2021-06-03 15:00:55'),(12,'Витамин С',5,40,39,18,100,'2021-06-03 15:01:01'),(13,'Витамин D',1,12,39,17,88,'2021-06-03 15:15:30'),(14,'Витамин D',1,12,39,17,88,'2021-06-03 15:16:10'),(15,'Гексорал',3,299,36,18,102,'2021-06-03 15:27:09'),(16,'Аскорбиновая кислота',50,34,39,6,72,'2021-06-03 15:33:15'),(17,'Витамин D',6,12,39,17,88,'2021-06-03 19:24:30'),(18,'Парацетамол',2,25,38,17,98,'2021-06-03 19:51:29'),(19,'Витамин D',2,12,39,17,88,'2021-06-03 19:51:35'),(20,'Мезим',1,200,31,18,89,'2021-06-03 19:58:53'),(21,'Гексорал',1,299,36,18,102,'2021-06-03 19:59:22'),(22,'Витамин С',2,40,39,18,100,'2021-06-03 19:59:32'),(23,'Нурофен',2,260,42,19,104,'2021-06-03 20:25:04'),(24,'Витамин С',1,40,39,18,100,'2021-06-04 19:16:35');
/*!40000 ALTER TABLE `soldpreparat` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-06-05 20:45:57
