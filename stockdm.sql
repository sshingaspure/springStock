-- MySQL dump 10.13  Distrib 5.1.37, for Win64 (unknown)
--
-- Host: localhost    Database: stock
-- ------------------------------------------------------
-- Server version	5.1.37-community

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
-- Table structure for table `company`
--

DROP TABLE IF EXISTS `company`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `company` (
  `cmp_id` int(11) NOT NULL AUTO_INCREMENT,
  `cmp_name` varchar(100) DEFAULT NULL,
  `share_value` double(10,2) DEFAULT NULL,
  PRIMARY KEY (`cmp_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `company`
--

LOCK TABLES `company` WRITE;
/*!40000 ALTER TABLE `company` DISABLE KEYS */;
INSERT INTO `company` VALUES (1,'IBM',102.00),(2,'Persistent',200.00);
/*!40000 ALTER TABLE `company` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customer` (
  `cust_id` int(11) NOT NULL AUTO_INCREMENT,
  `cust_uname` varchar(100) DEFAULT NULL,
  `cust_name` varchar(100) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `balance` double(10,2) DEFAULT NULL,
  PRIMARY KEY (`cust_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES (1,'sagar','sagar','sagar',4932.00),(2,'parag','parag','parag',10000.00),(3,'sa\r\nsa\r\n','sa','sa',10000.00);
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shares`
--

DROP TABLE IF EXISTS `shares`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `shares` (
  `cust_id` int(11) NOT NULL,
  `cmp_id` int(11) NOT NULL,
  `shares` int(11) DEFAULT NULL,
  PRIMARY KEY (`cust_id`,`cmp_id`),
  KEY `cmp_id` (`cmp_id`),
  CONSTRAINT `shares_ibfk_1` FOREIGN KEY (`cust_id`) REFERENCES `customer` (`cust_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `shares_ibfk_2` FOREIGN KEY (`cmp_id`) REFERENCES `company` (`cmp_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shares`
--

LOCK TABLES `shares` WRITE;
/*!40000 ALTER TABLE `shares` DISABLE KEYS */;
INSERT INTO `shares` VALUES (1,1,36),(1,2,18);
/*!40000 ALTER TABLE `shares` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `timecheck`
--

DROP TABLE IF EXISTS `timecheck`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `timecheck` (
  `timecol` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `timecheck`
--

LOCK TABLES `timecheck` WRITE;
/*!40000 ALTER TABLE `timecheck` DISABLE KEYS */;
INSERT INTO `timecheck` VALUES ('2016-12-12 00:00:00');
/*!40000 ALTER TABLE `timecheck` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transactions`
--

DROP TABLE IF EXISTS `transactions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `transactions` (
  `tran_id` int(11) NOT NULL AUTO_INCREMENT,
  `cust_id` int(11) NOT NULL,
  `cmp_id` int(11) NOT NULL,
  `share_value_at_tran` double(10,2) NOT NULL,
  `number_of_shares` int(10) NOT NULL,
  `tran_type` enum('S','B') NOT NULL,
  `tran_status` varchar(20) NOT NULL,
  `tran_message` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`tran_id`),
  KEY `tran_fk_1` (`cust_id`),
  KEY `tran_fk_2` (`cmp_id`),
  CONSTRAINT `tran_fk_1` FOREIGN KEY (`cust_id`) REFERENCES `customer` (`cust_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `tran_fk_2` FOREIGN KEY (`cmp_id`) REFERENCES `company` (`cmp_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transactions`
--

LOCK TABLES `transactions` WRITE;
/*!40000 ALTER TABLE `transactions` DISABLE KEYS */;
INSERT INTO `transactions` VALUES (1,1,1,1002.00,12,'S','Successful','Succesfully sold the shares'),(2,1,1,1002.00,12,'S','Successful','Succesfully sold the shares'),(3,1,1,102.00,1,'B','Successful','Successfully bought the shares'),(4,1,1,102.00,10,'S','Successful','Successfully sold the shares'),(5,1,1,102.00,2,'S','Successful','Successfully sold the shares'),(6,1,2,200.00,1,'B','Successful','Successfully bought the shares'),(7,1,2,200.00,2,'B','Successful','Successfully bought the shares'),(8,1,2,200.00,2,'S','Successful','Successfully sold the shares');
/*!40000 ALTER TABLE `transactions` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-12-14 18:56:19
