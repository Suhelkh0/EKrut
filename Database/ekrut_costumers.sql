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
-- Table structure for table `costumers`
--

DROP TABLE IF EXISTS `costumers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `costumers` (
  `costumerID` int NOT NULL,
  `username` varchar(100) NOT NULL,
  `debt` varchar(100) DEFAULT NULL,
  `permissions` enum('APPROVED','FROZEN','NOT_APPROVED') DEFAULT NULL,
  `storeCredit` varchar(100) DEFAULT NULL,
  `creditCard` varchar(100) DEFAULT NULL,
  `expirationDate` varchar(100) DEFAULT NULL,
  `cvv` varchar(100) DEFAULT NULL,
  `city` varchar(45) DEFAULT NULL,
  `street` varchar(45) DEFAULT NULL,
  `house_number` varchar(45) DEFAULT NULL,
  `receiver_name` varchar(45) DEFAULT NULL,
  `receiver_phone` varchar(45) DEFAULT NULL,
  `firstPayment` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`costumerID`),
  UNIQUE KEY `username_UNIQUE` (`username`),
  KEY `username_costumer` (`username`),
  CONSTRAINT `username_costumer` FOREIGN KEY (`username`) REFERENCES `users` (`username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `costumers`
--

LOCK TABLES `costumers` WRITE;
/*!40000 ALTER TABLE `costumers` DISABLE KEYS */;
INSERT INTO `costumers` VALUES (1,'costumer','0','APPROVED','0','5553-2225-5963-2158','02/25','572','Karmiel','Senonet','10','Yossi','0523215682','false'),(2,'costumer2','0','APPROVED','0','5553-2225-5963-2158','02/25','694','Haifa','Ben gurion','5','Avi','0506562266','false'),(3,'costumer3','0','APPROVED','0','5553-2255-5963-2158','02/27','684','Abu Dhabi','Najda','2','Eliyahu','0526552347',NULL),(4,'costumer4','0','APPROVED','0','5553-4225-5963-2158','04/25','123','Karmiel','Senonet','3','Dana','0536559231',NULL),(5,'costumer5','0','APPROVED','0','5553-2225-5963-2158',NULL,NULL,'Haifa','Ben gurion','17','Kruskal','0523215682',NULL),(6,'costumer6','0','NOT_APPROVED','0',NULL,NULL,NULL,'Dubai','Zayed','8','Prim','0523215682',NULL),(7,'costumer7','0','NOT_APPROVED','0',NULL,NULL,NULL,'Beersheva','Afek','9','Daniel','0523215682',NULL),(8,'costumerfrozen','0','FROZEN','0',NULL,NULL,NULL,'Eilat','Arad','10','Avraham','0523215682',NULL),(9,'costumer123','0','APPROVED','0','5553-2225-5963-2158','03/28','256','Haifa','Ben gurion','12','Andrew','0254125544','false');
/*!40000 ALTER TABLE `costumers` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-01-23  3:14:31
