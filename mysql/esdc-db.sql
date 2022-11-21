CREATE DATABASE  IF NOT EXISTS `ffms_refactor` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `ffms_refactor`;
-- MySQL dump 10.13  Distrib 8.0.30, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: ffms_refactor
-- ------------------------------------------------------
-- Server version	8.0.30

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
-- Table structure for table `account`
--

DROP TABLE IF EXISTS `account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `account` (
  `ID` bigint NOT NULL AUTO_INCREMENT,
  `CREATED_AT` datetime DEFAULT NULL,
  `ADDRESS` varchar(255) DEFAULT NULL,
  `DOB` datetime DEFAULT NULL,
  `EMAIL` varchar(255) DEFAULT NULL,
  `FULL_NAME` varchar(255) DEFAULT NULL,
  `PASSWORD` varchar(255) DEFAULT NULL,
  `PHONE` varchar(255) DEFAULT NULL,
  `SEX` varchar(255) DEFAULT NULL,
  `UPDATED_AT` datetime DEFAULT NULL,
  `USERNAME` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account`
--

LOCK TABLES `account` WRITE;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;
INSERT INTO `account` VALUES (1,'2022-11-09 20:13:19','170/9 Nguyen Xi','2002-09-04 00:00:00','dungdqch@gmail.com','s1mple','$2a$10$3ax8F2HkAip21djco6y0JeELsXuqYSpdL0TVavzf1tvWEz.n/loI.','0325252522','MALE','2022-11-09 20:13:19','s1mple'),(2,'2022-11-09 20:14:33','170/9 Nguyen Xi','2002-09-04 00:00:00','dungdqch3@gmail.com','dungvjppro','$2a$10$gIs7LbYNMugo61tQ7yY6r.ejKZEQsua5Q75S7LrPXZ3tgYN0mxI6u','0342343249','OTHER','2022-11-09 20:14:33','s1mple3'),(4,'2022-11-11 14:52:35','360 pham van dong','2002-11-12 00:00:00','ka@gmail.com','ka','$2a$10$kDJNrGM1pFPDs2oYKtB.wO2u7l7YaHxCl3uakcYIF2yjHbcwzTsTS','0326042022','MALE','2022-11-11 14:52:35','kavjp'),(5,'2022-11-12 10:36:46','Abc','2022-11-01 03:36:04','thetannguyen193@gmail.com','Nguyễn văn a','$2a$10$W8ZqZYYRyiGzrArWGb0wjuOGlVIzTBDpOqOsWkVtOPnyj7/jeIwrW','0963863758','MALE','2022-11-12 10:36:46','xinchao123'),(6,'2022-11-18 09:06:21','82/3 Dinh Bo Linh','2002-10-22 00:00:00','kientran123@gmail.com','Trần Đỗ Trung Kiên','$2a$10$sj9fqiACOjxLLQhPiGsMY.RJC.U2MNBGdIxco3cYQDWdjhDpgJYmG','0325823951','MALE','2022-11-18 09:06:21','kientran');
/*!40000 ALTER TABLE `account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `account_role`
--

DROP TABLE IF EXISTS `account_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `account_role` (
  `ACCOUNT_ID` bigint NOT NULL,
  `ROLE_ID` bigint NOT NULL,
  PRIMARY KEY (`ACCOUNT_ID`,`ROLE_ID`),
  KEY `FK408ri96h727boiesrwk79jr8h` (`ROLE_ID`),
  CONSTRAINT `FK408ri96h727boiesrwk79jr8h` FOREIGN KEY (`ROLE_ID`) REFERENCES `role` (`ID`),
  CONSTRAINT `FKa9w3ftwoblt2hyu32fnhb3q2m` FOREIGN KEY (`ACCOUNT_ID`) REFERENCES `account` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account_role`
--

LOCK TABLES `account_role` WRITE;
/*!40000 ALTER TABLE `account_role` DISABLE KEYS */;
INSERT INTO `account_role` VALUES (1,1),(1,2),(4,2),(1,3),(2,3),(4,3),(5,3),(6,3);
/*!40000 ALTER TABLE `account_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `booked_ticket`
--

DROP TABLE IF EXISTS `booked_ticket`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `booked_ticket` (
  `ID` bigint NOT NULL AUTO_INCREMENT,
  `CREATED_AT` datetime DEFAULT NULL,
  `NOTE` varchar(255) DEFAULT NULL,
  `PAYMENT_STATUS` varchar(255) DEFAULT NULL,
  `TOTAL_PRICE` int DEFAULT NULL,
  `CUSTOMER_ID` bigint DEFAULT NULL,
  `EMPLOYEE_ID` bigint DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FKd4s1xqf1xuee3tg0ratuqw0ul` (`CUSTOMER_ID`),
  KEY `FK6injcb74hn2kk542no7pk36n0` (`EMPLOYEE_ID`),
  CONSTRAINT `FK6injcb74hn2kk542no7pk36n0` FOREIGN KEY (`EMPLOYEE_ID`) REFERENCES `employee` (`ID`),
  CONSTRAINT `FKd4s1xqf1xuee3tg0ratuqw0ul` FOREIGN KEY (`CUSTOMER_ID`) REFERENCES `customer` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `booked_ticket`
--

LOCK TABLES `booked_ticket` WRITE;
/*!40000 ALTER TABLE `booked_ticket` DISABLE KEYS */;
INSERT INTO `booked_ticket` VALUES (1,'2022-11-09 21:21:46',NULL,'PROCESSING',150000,1,NULL),(2,'2022-11-10 21:58:44',NULL,'PROCESSING',150000,1,NULL),(3,'2022-11-10 21:59:22',NULL,'PROCESSING',150000,1,NULL),(4,'2022-11-11 11:33:55',NULL,'PROCESSING',150000,1,NULL),(5,'2022-11-11 14:46:55',NULL,'PROCESSING',150000,1,NULL),(6,'2022-11-11 21:03:13',NULL,'PROCESSING',150000,1,NULL),(7,'2022-11-11 21:04:02',NULL,'PROCESSING',150000,1,NULL),(8,'2022-11-12 08:53:08',NULL,'PROCESSING',150000,1,NULL),(9,'2022-11-12 09:57:14',NULL,'PROCESSING',150000,1,NULL),(10,'2022-11-12 10:38:07',NULL,'PROCESSING',300000,1,NULL),(11,'2022-11-15 23:40:09',NULL,'PROCESSING',150000,1,NULL),(12,'2022-11-17 10:20:31',NULL,'PROCESSING',150000,1,2),(13,'2022-11-17 11:44:34',NULL,'PROCESSING',150000,1,NULL),(14,'2022-11-18 08:26:19',NULL,'PROCESSING',150000,1,NULL),(15,'2022-11-18 08:26:30',NULL,'PROCESSING',150000,1,NULL),(16,'2022-11-18 08:26:39',NULL,'PROCESSING',150000,1,NULL),(17,'2022-11-18 08:28:58',NULL,'PROCESSING',150000,1,NULL),(18,'2022-11-18 08:35:53',NULL,'PROCESSING',150000,1,NULL),(19,'2022-11-18 08:48:00',NULL,'PROCESSING',200000,1,NULL),(20,'2022-11-18 14:24:17',NULL,'PROCESSING',150000,1,NULL),(21,'2022-11-19 09:54:44',NULL,'PROCESSING',150000,1,NULL),(22,'2022-11-19 09:57:26',NULL,'PROCESSING',180000,1,NULL),(23,'2022-11-19 19:31:24',NULL,'PROCESSING',180000,1,NULL),(24,'2022-11-21 10:25:04',NULL,'PROCESSING',200000,1,NULL);
/*!40000 ALTER TABLE `booked_ticket` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `booked_ticket_detail`
--

DROP TABLE IF EXISTS `booked_ticket_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `booked_ticket_detail` (
  `BOOKED_TICKET_ID` bigint NOT NULL,
  `FOOTBALL_FIELD_ID` bigint NOT NULL,
  `DEPOSIT` int DEFAULT NULL,
  `END_TIME` datetime DEFAULT NULL,
  `ORDER_DATE` datetime DEFAULT NULL,
  `START_TIME` datetime DEFAULT NULL,
  `IS_CANCELED` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`BOOKED_TICKET_ID`,`FOOTBALL_FIELD_ID`),
  KEY `FKill2ctfda1hr6oll9tg8n3e2y` (`FOOTBALL_FIELD_ID`),
  CONSTRAINT `FKf1wop1pxes9hr2etd62t6ije3` FOREIGN KEY (`BOOKED_TICKET_ID`) REFERENCES `booked_ticket` (`ID`),
  CONSTRAINT `FKill2ctfda1hr6oll9tg8n3e2y` FOREIGN KEY (`FOOTBALL_FIELD_ID`) REFERENCES `football_field` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `booked_ticket_detail`
--

LOCK TABLES `booked_ticket_detail` WRITE;
/*!40000 ALTER TABLE `booked_ticket_detail` DISABLE KEYS */;
INSERT INTO `booked_ticket_detail` VALUES (1,2,0,'2022-11-10 06:00:00','2022-11-09 21:21:46','2022-11-10 05:00:00',0),(2,1,0,'2022-11-11 06:00:00','2022-11-10 21:58:44','2022-11-11 05:00:00',0),(3,1,0,'2022-11-11 07:00:00','2022-11-10 21:59:22','2022-11-11 06:00:00',0),(4,2,0,'2022-11-11 17:00:00','2022-11-11 11:33:55','2022-11-11 16:00:00',0),(5,1,0,'2022-11-12 08:00:00','2022-11-11 14:46:55','2022-11-12 07:00:00',0),(6,1,0,'2022-11-12 10:00:00','2022-11-11 21:03:13','2022-11-12 09:00:00',0),(7,1,0,'2022-11-12 12:00:00','2022-11-11 21:04:02','2022-11-12 11:00:00',0),(8,1,0,'2022-11-13 02:49:24','2022-11-12 08:53:09','2022-11-13 01:49:24',0),(9,1,0,'2022-11-13 08:30:16','2022-11-12 09:57:14','2022-11-13 07:30:16',0),(10,1,0,'2022-11-14 09:00:16','2022-11-12 10:38:07','2022-11-14 07:30:16',0),(11,1,0,'2022-11-16 08:00:00','2022-11-15 23:40:09','2022-11-16 07:00:00',0),(12,2,0,'2022-11-17 13:00:00','2022-11-17 10:20:31','2022-11-17 12:00:00',0),(13,1,0,'2022-11-17 15:00:00','2022-11-17 11:44:34','2022-11-17 14:00:00',0),(14,1,0,'2022-11-18 14:00:00','2022-11-18 08:26:19','2022-11-18 13:00:00',0),(15,1,0,'2022-11-19 18:00:00','2022-11-18 08:26:30','2022-11-19 17:00:00',0),(16,1,0,'2022-11-19 11:00:00','2022-11-18 08:26:39','2022-11-19 10:00:00',0),(17,1,0,'2022-11-19 13:00:00','2022-11-18 08:28:58','2022-11-19 12:00:00',0),(18,1,0,'2022-11-20 12:00:00','2022-11-18 08:35:53','2022-11-20 11:00:00',0),(19,3,0,'2022-11-18 11:00:00','2022-11-18 08:48:00','2022-11-18 10:00:00',0),(20,2,0,'2022-11-18 17:00:00','2022-11-18 14:24:17','2022-11-18 16:00:00',0),(21,2,0,'2022-11-19 22:00:00','2022-11-19 09:54:44','2022-11-19 21:00:00',0),(22,2,0,'2022-11-19 21:00:00','2022-11-19 09:57:26','2022-11-19 20:00:00',1),(23,2,0,'2022-11-19 21:00:00','2022-11-19 19:31:24','2022-11-19 20:00:00',0),(24,3,0,'2022-11-22 13:00:00','2022-11-21 10:25:04','2022-11-22 12:00:00',1);
/*!40000 ALTER TABLE `booked_ticket_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer` (
  `ID` bigint NOT NULL AUTO_INCREMENT,
  `CREATED_AT` datetime DEFAULT NULL,
  `REWARD_POINT` bigint DEFAULT '0',
  `UPDATED_AT` datetime DEFAULT NULL,
  `ACCOUNT_ID` bigint DEFAULT NULL,
  `IS_DELETED` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`ID`),
  KEY `FK192vqq0i3q2esrhd3mivortnl` (`ACCOUNT_ID`),
  CONSTRAINT `FK192vqq0i3q2esrhd3mivortnl` FOREIGN KEY (`ACCOUNT_ID`) REFERENCES `account` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES (1,'2022-11-09 20:14:33',0,'2022-11-09 20:14:33',2,0),(2,'2022-11-12 10:36:46',0,'2022-11-18 10:11:55',5,0),(3,'2022-11-18 09:06:21',0,'2022-11-18 10:21:22',6,0);
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employee` (
  `ID` bigint NOT NULL AUTO_INCREMENT,
  `CREATED_AT` datetime DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `IDENTITY_CARD` varchar(255) DEFAULT NULL,
  `SALARY` double DEFAULT '0',
  `UPDATED_AT` datetime DEFAULT NULL,
  `ACCOUNT_ID` bigint DEFAULT NULL,
  `FIELD_GROUP_ID` bigint DEFAULT NULL,
  `IS_DELETED` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FKe1p2i0nh8jge7utgha0nftdvs` (`ACCOUNT_ID`),
  KEY `FKg1i27v2rek00ya2nud3a2o790` (`FIELD_GROUP_ID`),
  CONSTRAINT `FKe1p2i0nh8jge7utgha0nftdvs` FOREIGN KEY (`ACCOUNT_ID`) REFERENCES `account` (`ID`),
  CONSTRAINT `FKg1i27v2rek00ya2nud3a2o790` FOREIGN KEY (`FIELD_GROUP_ID`) REFERENCES `field_group` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee`
--

LOCK TABLES `employee` WRITE;
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
INSERT INTO `employee` VALUES (2,'2022-11-11 14:52:35','Shift 3','1900805822',1000000,'2022-11-18 13:51:03',4,1,1);
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `field_group`
--

DROP TABLE IF EXISTS `field_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `field_group` (
  `ID` bigint NOT NULL AUTO_INCREMENT,
  `CREATED_AT` datetime DEFAULT NULL,
  `ADDRESS` varchar(255) DEFAULT NULL,
  `CLOSE_TIME` time DEFAULT NULL,
  `FIELD_FEE_WEIGHT` double DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `OPEN_TIME` time DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `field_group`
--

LOCK TABLES `field_group` WRITE;
/*!40000 ALTER TABLE `field_group` DISABLE KEYS */;
INSERT INTO `field_group` VALUES (1,'2022-11-09 21:07:46','19 Đ. Nguyễn Hữu Thọ, Tân Hưng, Quận 7, Thành phố Hồ Chí Minh','23:59:59',1,'Tôn đức thắng university','05:00:00');
/*!40000 ALTER TABLE `field_group` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `football_field`
--

DROP TABLE IF EXISTS `football_field`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `football_field` (
  `ID` bigint NOT NULL AUTO_INCREMENT,
  `CREATED_AT` datetime DEFAULT NULL,
  `IMAGE` longtext,
  `NAME` varchar(255) DEFAULT NULL,
  `PRICE` double DEFAULT '0',
  `TYPE` varchar(255) DEFAULT NULL,
  `FIELD_GROUP_ID` bigint DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK1j9hv6b6e7kmoma50mt0mk3dh` (`FIELD_GROUP_ID`),
  CONSTRAINT `FK1j9hv6b6e7kmoma50mt0mk3dh` FOREIGN KEY (`FIELD_GROUP_ID`) REFERENCES `field_group` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `football_field`
--

LOCK TABLES `football_field` WRITE;
/*!40000 ALTER TABLE `football_field` DISABLE KEYS */;
INSERT INTO `football_field` VALUES (1,'2022-11-09 21:08:31','https://i.ibb.co/yYT3V2P/field5-1.png','TDTU sân 1',150000,'FIELD_5',1),(2,'2022-11-09 21:08:52','https://i.ibb.co/SKFs6CJ/field5-2.jpg','TDTU sân 2',150000,'FIELD_5',1),(3,'2022-11-09 21:13:01','https://i.ibb.co/3shFfN5/field7-1.png','TDTU Sân 3',200000,'FIELD_7',1),(4,'2022-11-09 21:13:22','https://i.ibb.co/8gdnpw2/field7-2.jpg','TDTU sân 4',200000,'FIELD_7',1),(5,'2022-11-09 21:13:49','https://i.ibb.co/JtkxcZd/field11-1.jpg','TDTU Sân 5',300000,'FIELD_11',1),(6,'2022-11-09 21:14:14','https://i.ibb.co/L6xNMzd/filed11-2.jpg','TDTU sân 6',300000,'FIELD_11',1),(7,'2022-11-12 10:52:04','https://i.ibb.co/1MTVh2J/filed11-2.jpg','TDTU Sân 7',150000,'FIELD_5',1);
/*!40000 ALTER TABLE `football_field` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `import_receipt`
--

DROP TABLE IF EXISTS `import_receipt`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `import_receipt` (
  `ID` bigint NOT NULL AUTO_INCREMENT,
  `CREATED_AT` datetime DEFAULT NULL,
  `NOTE` varchar(255) DEFAULT NULL,
  `PAYMENT_STATUS` varchar(255) DEFAULT NULL,
  `TOTAL_PRICE` int DEFAULT NULL,
  `EMPLOYEE_ID` bigint DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FKf8not0qyq85olpejisq8d85k9` (`EMPLOYEE_ID`),
  CONSTRAINT `FKf8not0qyq85olpejisq8d85k9` FOREIGN KEY (`EMPLOYEE_ID`) REFERENCES `employee` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `import_receipt`
--

LOCK TABLES `import_receipt` WRITE;
/*!40000 ALTER TABLE `import_receipt` DISABLE KEYS */;
INSERT INTO `import_receipt` VALUES (1,'2022-11-09 20:33:29','','PROCESSING',200000,NULL),(2,'2022-11-09 20:35:47','','PROCESSING',200000,NULL),(3,'2022-11-09 20:36:49','','PROCESSING',300000,NULL),(4,'2022-11-09 20:37:43','','PROCESSING',225000,NULL),(5,'2022-11-09 20:39:18','','PROCESSING',360000,NULL),(6,'2022-11-09 20:40:20','','PROCESSING',5000000,NULL),(7,'2022-11-09 20:41:21','','PROCESSING',10000000,NULL),(8,'2022-11-09 20:42:00','','PROCESSING',5000000,NULL),(9,'2022-11-09 20:47:26','','PROCESSING',200000,NULL),(10,'2022-11-10 21:56:57','Mua thêm','PROCESSING',50000,NULL),(11,'2022-11-11 20:30:47','','PROCESSING',100000,NULL),(12,'2022-11-11 20:35:26','','PROCESSING',100000,NULL),(13,'2022-11-11 20:41:16','','PROCESSING',100000,NULL),(14,'2022-11-11 20:42:12','','PROCESSING',100000,NULL),(15,'2022-11-11 20:46:03','','PROCESSING',50000,NULL),(16,'2022-11-11 20:51:50','','PROCESSING',120000,NULL),(17,'2022-11-12 00:07:42','','PROCESSING',360000,NULL),(18,'2022-11-12 10:53:29','','PROCESSING',100000,NULL);
/*!40000 ALTER TABLE `import_receipt` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `import_receipt_detail`
--

DROP TABLE IF EXISTS `import_receipt_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `import_receipt_detail` (
  `IMPORT_RECEIPT_ID` bigint NOT NULL,
  `ITEM_ID` bigint NOT NULL,
  `DELIVERY_DATE` datetime DEFAULT NULL,
  `ORDER_DATE` datetime DEFAULT NULL,
  `QUANTITY` int DEFAULT NULL,
  PRIMARY KEY (`IMPORT_RECEIPT_ID`,`ITEM_ID`),
  KEY `FK4ajleboatwr8jyup2pkkyfghu` (`ITEM_ID`),
  CONSTRAINT `FK3v1pu4ni8n57ult1qoe61os0p` FOREIGN KEY (`IMPORT_RECEIPT_ID`) REFERENCES `import_receipt` (`ID`),
  CONSTRAINT `FK4ajleboatwr8jyup2pkkyfghu` FOREIGN KEY (`ITEM_ID`) REFERENCES `item` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `import_receipt_detail`
--

LOCK TABLES `import_receipt_detail` WRITE;
/*!40000 ALTER TABLE `import_receipt_detail` DISABLE KEYS */;
INSERT INTO `import_receipt_detail` VALUES (1,1,'2022-11-08 00:00:00','2022-11-09 20:33:29',20),(2,2,'2022-11-11 00:00:00','2022-11-09 20:35:47',20),(3,3,'2022-11-12 00:00:00','2022-11-09 20:36:49',25),(4,4,'2022-11-12 00:00:00','2022-11-09 20:37:43',15),(5,5,'2022-11-10 00:00:00','2022-11-09 20:39:18',30),(6,6,'2022-11-11 00:00:00','2022-11-09 20:40:20',50),(7,7,'2022-11-12 00:00:00','2022-11-09 20:41:21',100),(8,8,'2022-11-25 00:00:00','2022-11-09 20:42:00',50),(9,9,'2022-11-18 00:00:00','2022-11-09 20:47:26',20),(10,1,'2022-11-11 22:56:00','2022-11-10 21:56:57',5),(11,2,'2022-12-03 20:33:00','2022-11-11 20:30:47',10),(12,9,'2022-12-02 23:35:00','2022-11-11 20:35:26',10),(13,9,'2022-11-11 20:41:00','2022-11-11 20:41:16',10),(14,9,'2022-11-11 20:41:00','2022-11-11 20:42:12',10),(15,9,'2022-11-15 20:46:00','2022-11-11 20:46:03',5),(16,3,'2022-11-23 20:51:00','2022-11-11 20:51:50',10),(17,5,'2022-12-03 00:11:00','2022-11-12 00:07:42',30),(18,1,'2022-11-13 10:53:00','2022-11-12 10:53:29',10);
/*!40000 ALTER TABLE `import_receipt_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `item`
--

DROP TABLE IF EXISTS `item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `item` (
  `ID` bigint NOT NULL AUTO_INCREMENT,
  `CREATED_AT` datetime DEFAULT NULL,
  `IMAGE` longtext,
  `IMPORT_PRICE` int DEFAULT NULL,
  `CATEGORY` varchar(255) DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `NOTE` varchar(255) DEFAULT NULL,
  `QUANTITY` int DEFAULT NULL,
  `STATUS` varchar(255) DEFAULT NULL,
  `UNIT` varchar(255) DEFAULT NULL,
  `SUPPLIER_ID` bigint DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK5vdfn4b0b579a13i0jsihhdrs` (`SUPPLIER_ID`),
  CONSTRAINT `FK5vdfn4b0b579a13i0jsihhdrs` FOREIGN KEY (`SUPPLIER_ID`) REFERENCES `supplier` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item`
--

LOCK TABLES `item` WRITE;
/*!40000 ALTER TABLE `item` DISABLE KEYS */;
INSERT INTO `item` VALUES (1,'2022-11-09 20:33:29','https://i.ibb.co/85mYCYF/COCA.jpg',10000,'DRINK','Coca','',5,'AVAILABLE','Chai',1),(2,'2022-11-09 20:35:47','https://i.ibb.co/Ks39y2d/PEPSI.jpg',10000,'DRINK','Pepsi','',30,'AVAILABLE','Lon',2),(3,'2022-11-09 20:36:49','https://i.ibb.co/cxhsBWJ/REVIVE.jpg',12000,'DRINK','revive','',35,'AVAILABLE','Chai',1),(4,'2022-11-09 20:37:43','https://i.ibb.co/yW00PQK/RED-BULL.jpg',15000,'DRINK','Red bull','',15,'AVAILABLE','Lon',1),(5,'2022-11-09 20:39:18','https://i.ibb.co/rZDCVtq/MOUNTAIN-DEW.jpg',12000,'DRINK','Mountain dew','',30,'AVAILABLE','Chai',2),(6,'2022-11-09 20:40:20','https://i.ibb.co/023njDR/AO-PITCH-XANH.jpg',100000,'DRINK','Pitch xanh','',46,'AVAILABLE','Cái',3),(7,'2022-11-09 20:41:21','https://i.ibb.co/YfHb0Rg/AO-PITCH-CAM.jpg',100000,'DRINK','Pitch cam','',45,'AVAILABLE','Cái',3),(8,'2022-11-09 20:42:00','https://i.ibb.co/K9ZM8jZ/AO-PITCH-VANG.jpg',100000,'DRINK','Pitch vàng','',25,'AVAILABLE','Cái',3),(9,'2022-11-09 20:47:26','https://i.ibb.co/k8ySvHZ/PEPSI-CHANH.jpg',0,NULL,'Pepsi vị chanh','',0,'SOLD_OUT',NULL,NULL);
/*!40000 ALTER TABLE `item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `ID` bigint NOT NULL AUTO_INCREMENT,
  `NAME` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UK_epk9im9l9q67xmwi4hbed25do` (`NAME`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'ADMIN'),(2,'EMPLOYEE'),(3,'USER');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `service`
--

DROP TABLE IF EXISTS `service`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `service` (
  `ID` bigint NOT NULL AUTO_INCREMENT,
  `CREATED_AT` datetime DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `SELL_PRICE` int DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `service`
--

LOCK TABLES `service` WRITE;
/*!40000 ALTER TABLE `service` DISABLE KEYS */;
INSERT INTO `service` VALUES (1,'2022-11-09 22:13:39','Bán nước',20000),(2,'2022-11-10 01:09:02','Bán quần áo',150000);
/*!40000 ALTER TABLE `service` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `service_item`
--

DROP TABLE IF EXISTS `service_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `service_item` (
  `SERVICE_ID` bigint NOT NULL,
  `ITEM_ID` bigint NOT NULL,
  PRIMARY KEY (`SERVICE_ID`,`ITEM_ID`),
  KEY `FK55sogfs0l9oe0f3krhy2wy59r` (`ITEM_ID`),
  CONSTRAINT `FK55sogfs0l9oe0f3krhy2wy59r` FOREIGN KEY (`ITEM_ID`) REFERENCES `item` (`ID`),
  CONSTRAINT `FKg4nhkwdmn3d7wh6kgkvedtohb` FOREIGN KEY (`SERVICE_ID`) REFERENCES `service` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `service_item`
--

LOCK TABLES `service_item` WRITE;
/*!40000 ALTER TABLE `service_item` DISABLE KEYS */;
INSERT INTO `service_item` VALUES (1,1),(1,2),(1,3),(1,4),(1,5),(2,6),(2,7),(2,8),(1,9);
/*!40000 ALTER TABLE `service_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `service_receipt`
--

DROP TABLE IF EXISTS `service_receipt`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `service_receipt` (
  `ID` bigint NOT NULL AUTO_INCREMENT,
  `CREATED_AT` datetime DEFAULT NULL,
  `NOTE` varchar(255) DEFAULT NULL,
  `PAYMENT_STATUS` varchar(255) DEFAULT NULL,
  `TOTAL_PRICE` double DEFAULT '0',
  `EMPLOYEE_ID` bigint DEFAULT NULL,
  `USER_ID` bigint DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FKe865gxhnxwvxdod7ndnt5640n` (`EMPLOYEE_ID`),
  KEY `FKn1nkwvm4xvct0drul7229an8g` (`USER_ID`),
  CONSTRAINT `FKe865gxhnxwvxdod7ndnt5640n` FOREIGN KEY (`EMPLOYEE_ID`) REFERENCES `employee` (`ID`),
  CONSTRAINT `FKn1nkwvm4xvct0drul7229an8g` FOREIGN KEY (`USER_ID`) REFERENCES `customer` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `service_receipt`
--

LOCK TABLES `service_receipt` WRITE;
/*!40000 ALTER TABLE `service_receipt` DISABLE KEYS */;
INSERT INTO `service_receipt` VALUES (6,'2022-11-10 17:51:46',NULL,'PROCESSING',0,NULL,1),(7,'2022-11-10 17:56:45',NULL,'PROCESSING',0,NULL,1),(8,'2022-11-10 17:58:27',NULL,'PROCESSING',0,NULL,1),(9,'2022-11-10 18:07:47',NULL,'PROCESSING',4700000,NULL,1),(10,'2022-11-10 18:08:27',NULL,'PROCESSING',4700000,NULL,1),(11,'2022-11-10 18:14:51',NULL,NULL,0,NULL,NULL),(12,'2022-11-10 18:14:51',NULL,NULL,0,NULL,NULL),(13,'2022-11-10 18:14:51',NULL,'PROCESSING',4700000,NULL,1),(14,'2022-11-10 23:43:18',NULL,NULL,0,NULL,NULL),(15,'2022-11-10 23:43:18',NULL,'PROCESSING',60000,NULL,NULL),(16,'2022-11-10 23:52:09',NULL,NULL,0,NULL,NULL),(17,'2022-11-10 23:52:09',NULL,'PROCESSING',340000,NULL,NULL),(18,'2022-11-11 23:45:30',NULL,NULL,0,NULL,NULL),(19,'2022-11-11 23:45:30',NULL,NULL,0,NULL,NULL),(20,'2022-11-11 23:45:30',NULL,'PROCESSING',1700000,NULL,1),(21,'2022-11-12 00:05:14',NULL,NULL,0,NULL,NULL),(22,'2022-11-12 00:05:14',NULL,NULL,0,NULL,NULL),(23,'2022-11-12 00:05:14',NULL,'PROCESSING',1700000,NULL,NULL),(24,'2022-11-12 00:05:51',NULL,NULL,0,NULL,NULL),(25,'2022-11-12 00:05:52',NULL,NULL,0,NULL,NULL),(26,'2022-11-12 00:05:52',NULL,'PROCESSING',1700000,NULL,NULL),(27,'2022-11-12 08:57:48',NULL,NULL,0,NULL,NULL),(28,'2022-11-12 08:57:48',NULL,NULL,0,NULL,NULL),(29,'2022-11-12 08:57:48',NULL,NULL,0,NULL,NULL),(30,'2022-11-12 08:57:48',NULL,'PROCESSING',450000,NULL,NULL),(31,'2022-11-12 09:05:01',NULL,NULL,0,NULL,NULL),(32,'2022-11-12 09:05:01',NULL,'PROCESSING',300000,NULL,NULL),(33,'2022-11-12 09:06:53',NULL,NULL,0,NULL,NULL),(34,'2022-11-12 09:06:53',NULL,'PROCESSING',150000,NULL,NULL),(35,'2022-11-12 09:07:32',NULL,NULL,0,NULL,NULL),(36,'2022-11-12 09:07:32',NULL,'PROCESSING',150000,NULL,NULL),(37,'2022-11-12 09:53:09',NULL,NULL,0,NULL,NULL),(38,'2022-11-12 09:53:10',NULL,NULL,0,NULL,NULL),(39,'2022-11-12 09:53:10',NULL,NULL,0,NULL,NULL),(40,'2022-11-12 09:53:10',NULL,'PROCESSING',450000,NULL,NULL),(41,'2022-11-12 10:38:58',NULL,NULL,0,NULL,NULL),(42,'2022-11-12 10:38:58',NULL,NULL,0,NULL,NULL),(43,'2022-11-12 10:38:58',NULL,'PROCESSING',600000,NULL,NULL),(44,'2022-11-14 21:44:50',NULL,'PROCESSING',1700000,NULL,NULL),(45,'2022-11-14 21:46:14',NULL,'PROCESSING',40000,NULL,2),(46,'2022-11-14 21:47:34',NULL,'PROCESSING',40000,NULL,1),(47,'2022-11-14 21:48:56',NULL,'PROCESSING',40000,NULL,1),(48,'2022-11-14 21:50:00',NULL,'PROCESSING',40000,NULL,2),(49,'2022-11-14 21:53:52',NULL,'PROCESSING',40000,2,2);
/*!40000 ALTER TABLE `service_receipt` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `service_receipt_detail`
--

DROP TABLE IF EXISTS `service_receipt_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `service_receipt_detail` (
  `ITEM_ID` bigint NOT NULL,
  `SERVICE_RECEIPT_ID` bigint NOT NULL,
  `ORDER_DATE` datetime DEFAULT NULL,
  `QUANTITY` int DEFAULT NULL,
  PRIMARY KEY (`ITEM_ID`,`SERVICE_RECEIPT_ID`),
  KEY `FKd7ttclym3ljle11w9bjk1e8v6` (`SERVICE_RECEIPT_ID`),
  CONSTRAINT `FKbrl6bvwawn8mybs0oxmde12fx` FOREIGN KEY (`ITEM_ID`) REFERENCES `item` (`ID`),
  CONSTRAINT `FKd7ttclym3ljle11w9bjk1e8v6` FOREIGN KEY (`SERVICE_RECEIPT_ID`) REFERENCES `service_receipt` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `service_receipt_detail`
--

LOCK TABLES `service_receipt_detail` WRITE;
/*!40000 ALTER TABLE `service_receipt_detail` DISABLE KEYS */;
INSERT INTO `service_receipt_detail` VALUES (1,24,'2022-11-12 00:05:51',10),(1,44,'2022-11-14 21:44:50',10),(1,45,'2022-11-14 21:46:14',1),(1,46,'2022-11-14 21:47:34',1),(1,47,'2022-11-14 21:48:56',1),(1,48,'2022-11-14 21:50:00',1),(1,49,'2022-11-14 21:53:52',1),(5,12,'2022-11-10 18:14:51',10),(5,18,'2022-11-11 23:45:30',10),(5,22,'2022-11-12 00:05:14',10),(6,28,'2022-11-12 08:57:48',1),(6,39,'2022-11-12 09:53:10',1),(6,41,'2022-11-12 10:38:58',2),(7,11,'2022-11-10 18:14:51',30),(7,19,'2022-11-11 23:45:30',10),(7,21,'2022-11-12 00:05:14',10),(7,27,'2022-11-12 08:57:48',1),(7,31,'2022-11-12 09:05:01',2),(7,33,'2022-11-12 09:06:53',1),(7,37,'2022-11-12 09:53:10',1),(8,25,'2022-11-12 00:05:52',10),(8,29,'2022-11-12 08:57:48',1),(8,35,'2022-11-12 09:07:32',1),(8,38,'2022-11-12 09:53:10',1),(8,42,'2022-11-12 10:38:58',2),(8,44,'2022-11-14 21:44:50',10),(9,14,'2022-11-10 23:43:18',3),(9,16,'2022-11-10 23:52:09',17);
/*!40000 ALTER TABLE `service_receipt_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `supplier`
--

DROP TABLE IF EXISTS `supplier`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `supplier` (
  `ID` bigint NOT NULL AUTO_INCREMENT,
  `CREATED_AT` datetime DEFAULT NULL,
  `ADDRESS` varchar(255) DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `NOTE` varchar(255) DEFAULT NULL,
  `PHONE` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `supplier`
--

LOCK TABLES `supplier` WRITE;
/*!40000 ALTER TABLE `supplier` DISABLE KEYS */;
INSERT INTO `supplier` VALUES (1,'2022-11-09 20:29:45','485 Song Hành XL Hà Nội, Phường Linh Trung, Thủ Đức, Thành phố Hồ Chí Minh','Coca-Cola','Chuyên cung cấp nước','02838961000'),(2,'2022-11-09 20:30:47','88 Đồng Khởi, Bến Nghé, Quận 1, Thành phố Hồ Chí Minh','Suntory PepsiCo Việt Nam','','02866848422'),(3,'2022-11-09 20:31:35','67 Đ. Nguyễn Hồng Đào, Phường 14, Tân Bình, Thành phố Hồ Chí Minh 700000','KiwiSport','Chuyên lấy sỉ quần áo','0962471206');
/*!40000 ALTER TABLE `supplier` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-11-21 14:33:20
