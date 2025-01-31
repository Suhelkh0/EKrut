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
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `username` varchar(100) NOT NULL,
  `password` varchar(100) DEFAULT NULL,
  `firstName` varchar(100) DEFAULT NULL,
  `lastName` varchar(100) DEFAULT NULL,
  `role` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `phoneNumber` varchar(100) DEFAULT NULL,
  `creditCard` varchar(45) DEFAULT NULL,
  `isLoggedIn` tinyint(1) DEFAULT '0',
  `id` varchar(100) NOT NULL,
  `permissions` varchar(45) DEFAULT NULL,
  `storeName` varchar(100) DEFAULT NULL,
  `expirationDate` varchar(45) DEFAULT NULL,
  `cvv` varchar(45) DEFAULT NULL,
  `city` varchar(45) DEFAULT NULL,
  `street` varchar(45) DEFAULT NULL,
  `house_number` varchar(45) DEFAULT NULL,
  `receiver_name` varchar(45) DEFAULT NULL,
  `receiver_phone` varchar(45) DEFAULT NULL,
  `firstPayment` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES ('ceo','123456','Eliav','Shabat','CEO','ekrutbraude@gmail.com','0543320955','5553-2225-5963-2158',0,'1',NULL,'Karmiel','02/25','865','Karmiel','Senonet','10','Eliav','0523215682',NULL),('costumer','123456','Yossi','Levi','Subscriber','ekrutbraude@gmail.com','0523215682','5553-2225-5963-2158',0,'2',NULL,'karmiel','02/25','572','Karmiel','Senonet','10','Yossi','0523215682','true'),('costumer123','123456','Andrew','Tate','Subscriber','ekrutbraude@gmail.com','0254125544','5553-2225-5963-2158',0,'11','','haifa','03/28','256','Haifa','Ben gurion','12','Andrew','0254125544','false'),('costumer2','123456','Avi','Cohen','WaitForApproval','ekrutbraude@gmail.com','0506562266','5553-2225-5963-2158',0,'3',NULL,'eilat','03/28','345','Haifa','Ben gurion','10','Avi','0523215682',NULL),('costumer3','123456','Eliyahu','Avinaim','NULL','ekrutbraude@gmail.com','0526552347','5553-2225-5963-2158',0,'4',NULL,'dubai','03/28','222','Haifa','Ben gurion','10','Eliyahu','0523215682',NULL),('costumer4','123456','Dana','Prim','NULL','ekrutbraude@gmail.com','0536559231','5553-2225-5963-2158',0,'5',NULL,'abudhabi','03/28','355','Karmiel','Senonet','10','Dana','0523215682',NULL),('costumer5','123456','Kruskal','Yakobiz','NULL','ekrutbraude@gmail.com','0523215682','5553-2225-5963-2158',0,'6',NULL,'beersheva','03/28','666','Karmiel','Senonet','10','Kruskal','0523215682',NULL),('costumer6','123456','Prim','Brand','NULL','ekrutbraude@gmail.com','0523215682','5553-2225-5963-2158',0,'7',NULL,'haifa','03/28','786','Dubai','Zayed','10','Prim','0523215682',NULL),('costumer7','123456','Daniel','Ohayon','NULL','ekrutbraude@gmail.com','0523215682','5553-2225-5963-2158',0,'8',NULL,'karmiel','03/28','889','Dubai','Zayed','10','Danial','0523215682',NULL),('costumerfrozen','123456','Avraham','Yossi','NULL','ekrutbraude@gmail.com','0523215682','5553-2225-5963-2158',0,'9',NULL,'dubai','03/28','086','Dubai','Zayed','10','Avraham','0523215682',NULL),('cse','123456','Yossi','Levi','Costumer_Service_Employee','ekrutbraude@gmail.com','0523215682','5553-2225-5963-2158',0,'10','Approve','haifa','03/28','231','Abudhabi','Najda','10','Yossi','0523215682','true'),('marketingmanager','123456','Sami','Sami','Marketing_Manager','sami@.com','0252255444','5553-2225-5963-2158',0,'12',NULL,'haifa','03/28','102','Abudhabi','Najda','10','Sami','0523215682',NULL),('northmanager','123456','Prim','Cohen','Regional_Manager','ekrutbraude@gmail.com','0507772359','5553-2225-5963-2158',0,'17',NULL,'haifa','03/28','100','Eilat','Arad','10','Prim','0523215682',NULL),('northmarketingemployee','123456','Biran','Lev','Marketing_Employee','ekrutbraude@gmail.com','0543355108','5553-2225-5963-2158',0,'13','','haifa','03/28','444','Abudhabi','Najda','10','Biran','0523215682',NULL),('northoperator','123456','Danial','Jarrous','ShippingOperator','ekrutbraude@gmail.com','0544544545','5553-2225-5963-2158',0,'30',NULL,'haifa','03/28','000','Eilat','Arad','10','Danial','0523215682',NULL),('southmanager','123456','Omri','Cohen','Regional_Manager','ekrutbraude@gmail.com','0507772359','5553-2225-5963-2158',0,'16',NULL,'eilat','03/28','109','Karmiel','Senonet','10','Omri','0523215682',NULL),('southmarketingemployee','123456','Biran','Lev','Marketing_Employee','ekrutbraude@gmail.com','0543355108','5553-2225-5963-2158',0,'14','','eilat','03/28','444','Abudhabi','Najda','10','Biran','0523215682',NULL),('southoperator','123456','Danial','Jarrous','ShippingOperator','ekrutbraude@gmail.com','0544544545','5553-2225-5963-2158',0,'18',NULL,'eilat','03/28','110','Karmiel','Senonet','10','Danial','0523215682',NULL),('storeemployee','123456','May','haifa','Store_Employee','ekrutbraude@gmail.com','0505984413','5553-2225-5963-2158',0,'21',NULL,'Haifa','03/28','111','Eilat','Arad','10','May','0523215682',NULL),('uaemanager','123456','Omri','Cohen','Regional_Manager','ekrutbraude@gmail.com','0507772359','5553-2225-5963-2158',0,'49',NULL,'dubai','03/28','115','Karmiel','Senonet','10','Omri','0523215682',NULL),('uaemarketingemployee','123456','Biran','Lev','Marketing_Employee','ekrutbraude@gmail.com','0543355108','5553-2225-5963-2158',0,'15','','dubai','03/28','444','Abudhabi','Najda','10','Biran','0523215682',NULL),('uaeoperator','123456','Danial','Jarrous','ShippingOperator','ekrutbraude@gmail.com','0544544545','5553-2225-5963-2158',0,'19',NULL,'dubai','03/28','114','Karmiel','Senonet','10','Danial','0523215682',NULL);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
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
