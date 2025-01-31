-- MySQL dump 10.13  Distrib 8.0.31, for Win64 (x86_64)
--
-- Host: localhost    Database: ekrut
-- ------------------------------------------------------
-- Server version	8.0.31

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
-- Table structure for table `costumersreport`
--

DROP TABLE IF EXISTS `costumersreport`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `costumersreport` (
  `reportID` int NOT NULL,
  `Branch` varchar(45) NOT NULL,
  `0To3` varchar(45) DEFAULT NULL,
  `3To5` varchar(45) DEFAULT NULL,
  `5To10` varchar(45) DEFAULT NULL,
  `10Plus` varchar(45) DEFAULT NULL,
  `Month` varchar(45) NOT NULL,
  `Year` varchar(45) NOT NULL,
  PRIMARY KEY (`reportID`,`Branch`,`Month`,`Year`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `costumersreport`
--

LOCK TABLES `costumersreport` WRITE;
/*!40000 ALTER TABLE `costumersreport` DISABLE KEYS */;
INSERT INTO `costumersreport` VALUES (1,'haifa','7','11','10','122','august','2022'),(2,'haifa','11','50','12','37','january','2021'),(3,'haifa','2','2','1','0','september','2020'),(4,'abudhabi','1','4','6','5','february','2020'),(5,'beersheva','3','5','6','9','february','2020'),(6,'dubai','6','3','1','4','february','2022'),(7,'eilat','1','5','2','3','february','2022'),(8,'karmiel','3','5','1','2','february','2022'),(9,'abudhabi','7','5','3','6','march','2021'),(10,'beersheva','4','4','6','5','march','2021'),(11,'dubai','1','5','2','0','march','2022'),(12,'eilat','4','5','8','2','march','2022'),(13,'karmiel','8','5','4','2','march','2022'),(14,'abudhabi','2','4','1','7','may','2022'),(15,'beersheva','4','2','5','3','may','2022'),(16,'dubai','7','5','1','8','may','2021'),(17,'eilat','5','6','3','1','may','2022'),(18,'karmiel','4','5','6','3','may','2022'),(19,'haifa','0','0','0','0','2022','DECEMBER'),(20,'karmiel','0','0','0','0','2022','DECEMBER'),(21,'beersheva','0','0','0','0','2022','DECEMBER'),(22,'eilat','0','0','0','0','2022','DECEMBER'),(23,'abudhabi','0','0','0','0','2022','DECEMBER'),(24,'dubai','0','0','0','0','2022','DECEMBER');
/*!40000 ALTER TABLE `costumersreport` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-01-23  3:14:32
