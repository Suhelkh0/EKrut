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
-- Table structure for table `stockreport`
--

DROP TABLE IF EXISTS `stockreport`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `stockreport` (
  `reportID` varchar(45) NOT NULL,
  `Branch` varchar(45) NOT NULL,
  `Total_inventory` varchar(45) DEFAULT NULL,
  `Total_times_that_lowed_to_threshold` varchar(45) DEFAULT NULL,
  `Total_times_that_lowed_to_zero` varchar(45) DEFAULT NULL,
  `most_available_item` varchar(45) DEFAULT NULL,
  `times_that_loaded` varchar(45) DEFAULT NULL,
  `Month` varchar(45) NOT NULL,
  `Year` varchar(45) NOT NULL,
  PRIMARY KEY (`reportID`,`Branch`,`Month`,`Year`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stockreport`
--

LOCK TABLES `stockreport` WRITE;
/*!40000 ALTER TABLE `stockreport` DISABLE KEYS */;
INSERT INTO `stockreport` VALUES ('1','haifa','150','112','109','cadbury','100','august','2022'),('10','eilat','41','16','3','hot dog','16','may','2020'),('11','dubai','63','26','10','hot dog','25','january','2022'),('12','dubai','25','23','12','mini','23','september','2021'),('13','dubai','10','22','10','jelly belly','22','may','2020'),('14','abudhabi','14','20','11','hot dog','20','january','2022'),('15','abudhabi','24','21','13','maltesers','21','september','2021'),('16','abudhabi','36','25','14','cadbury','25','may','2020'),('17','beersheva','29','24','15','hot dog','24','january','2022'),('18','beersheva','27','23','16','trolli','23','september','2021'),('19','beersheva','38','26','9','maoam','26','may','2020'),('2','haifa','90','80','71','jelly belly','50','january','2022'),('3','haifa','12','21','8','trolli','11','september','2021'),('4','haifa','12','25','8','jelly belly','11','may','2020'),('5','karmiel','15','24','5','takis','24','january','2022'),('6','karmiel','20','23','3','hot dog','23','september','2021'),('7','karmiel','30','36','6','cadbury','36','may','2020'),('8','eilat','25','35','10','mini','35','january','2022'),('9','eilat','53','34','4','trolli','34','september','2021');
/*!40000 ALTER TABLE `stockreport` ENABLE KEYS */;
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
