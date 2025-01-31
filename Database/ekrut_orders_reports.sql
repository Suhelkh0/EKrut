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
-- Table structure for table `orders_reports`
--

DROP TABLE IF EXISTS `orders_reports`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders_reports` (
  `reportID` varchar(45) NOT NULL,
  `Branch` varchar(45) NOT NULL,
  `Year` varchar(45) NOT NULL,
  `Month` varchar(45) NOT NULL,
  `Orders_amount` varchar(45) DEFAULT NULL,
  `Income` varchar(45) DEFAULT NULL,
  `pickUp_method` varchar(45) DEFAULT NULL,
  `Drone_method` varchar(45) DEFAULT NULL,
  `Immediatly` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`reportID`,`Branch`,`Year`,`Month`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders_reports`
--

LOCK TABLES `orders_reports` WRITE;
/*!40000 ALTER TABLE `orders_reports` DISABLE KEYS */;
INSERT INTO `orders_reports` VALUES ('1','haifa','2022','january','150','8500','88','18','44'),('10','abu dhabi','2021','march','134','9870','25','25','84'),('11','karmiel','2022','january','160','10345','40','40','80'),('12','karmiel','2022','february','160','10222','45','45','70'),('13','karmiel','2021','march','150','10344','50','50','50'),('14','karmiel','2021','march','150','9567','54','6','90'),('15','eilat','2022','january','150','9888','50','50','50'),('16','beersheva','2021','march','140','7548','40','12','88'),('17','eilat','2020','february','150','9760','25','25','100'),('18','eilat','2021','march','166','10999','50','17','99'),('19','beersheva','2022','january','168','11340','75','25','68'),('2','haifa','2022','february','132','9000','32','50','50'),('20','beersheva','2020','february','145','9808','60','30','55'),('21','haifa','2020','december','2','237','1','1','0'),('22','karmiel','2022','april','0','0','0','0','0'),('23','haifa','DECEMBER','2022','0','0','0','0','0'),('24','karmiel','DECEMBER','2022','0','0','0','0','0'),('25','beersheva','DECEMBER','2022','0','0','0','0','0'),('26','eilat','DECEMBER','2022','0','0','0','0','0'),('27','abudhabi','DECEMBER','2022','0','0','0','0','0'),('28','dubai','DECEMBER','2022','0','0','0','0','0'),('3','haifa','2021','march','154','8670','66','40','48'),('4','dubai','2022','january','155','8435','60','5','90'),('5','dubai','2020','february','145','8734','52','13','80'),('6','dubai','2021','march','144','8350','54','45','45'),('7','abu dhabi','2022','january','167','9221','57','18','92'),('8','abu dhabi','2022','february','160','9335','50','25','85'),('9','abu dhabi','2020','march','150','8345','50','50','50');
/*!40000 ALTER TABLE `orders_reports` ENABLE KEYS */;
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
