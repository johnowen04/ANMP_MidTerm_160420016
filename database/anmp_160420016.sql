-- MySQL dump 10.13  Distrib 8.0.30, for Win64 (x86_64)
--
-- Host: localhost    Database: anmp_160420016
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
-- Table structure for table `bathroom_types`
--

DROP TABLE IF EXISTS `bathroom_types`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bathroom_types` (
  `id` smallint unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bathroom_types`
--

LOCK TABLES `bathroom_types` WRITE;
/*!40000 ALTER TABLE `bathroom_types` DISABLE KEYS */;
INSERT INTO `bathroom_types` VALUES (1,'Dalam'),(2,'Luar');
/*!40000 ALTER TABLE `bathroom_types` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `favorite_properties`
--

DROP TABLE IF EXISTS `favorite_properties`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `favorite_properties` (
  `users_username` varchar(50) NOT NULL,
  `properties_id` int unsigned NOT NULL,
  PRIMARY KEY (`users_username`,`properties_id`),
  KEY `fk_users_has_properties_properties1_idx` (`properties_id`),
  KEY `fk_users_has_properties_users1_idx` (`users_username`),
  CONSTRAINT `fk_users_has_properties_properties1` FOREIGN KEY (`properties_id`) REFERENCES `properties` (`id`),
  CONSTRAINT `fk_users_has_properties_users1` FOREIGN KEY (`users_username`) REFERENCES `users` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `favorite_properties`
--

LOCK TABLES `favorite_properties` WRITE;
/*!40000 ALTER TABLE `favorite_properties` DISABLE KEYS */;
INSERT INTO `favorite_properties` VALUES ('160420016',1),('160420016',2),('160420026',2),('160420016',3);
/*!40000 ALTER TABLE `favorite_properties` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `users_username` varchar(50) NOT NULL,
  `properties_id` int unsigned NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `date_until` datetime NOT NULL,
  `payment_methods_id` smallint unsigned NOT NULL,
  PRIMARY KEY (`users_username`,`properties_id`,`created_at`),
  KEY `fk_users_has_properties_properties2_idx` (`properties_id`),
  KEY `fk_users_has_properties_users2_idx` (`users_username`),
  KEY `fk_orders_payment_methods1_idx` (`payment_methods_id`),
  CONSTRAINT `fk_orders_payment_methods1` FOREIGN KEY (`payment_methods_id`) REFERENCES `payment_methods` (`id`),
  CONSTRAINT `fk_users_has_properties_properties2` FOREIGN KEY (`properties_id`) REFERENCES `properties` (`id`),
  CONSTRAINT `fk_users_has_properties_users2` FOREIGN KEY (`users_username`) REFERENCES `users` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES ('160420016',1,'2023-04-10 10:27:11','2023-05-10 17:27:11',1),('160420026',2,'2023-02-10 10:40:40','2023-03-10 17:40:40',1);
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payment_methods`
--

DROP TABLE IF EXISTS `payment_methods`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payment_methods` (
  `id` smallint unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payment_methods`
--

LOCK TABLES `payment_methods` WRITE;
/*!40000 ALTER TABLE `payment_methods` DISABLE KEYS */;
INSERT INTO `payment_methods` VALUES (1,'Cash'),(2,'Transfer');
/*!40000 ALTER TABLE `payment_methods` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `properties`
--

DROP TABLE IF EXISTS `properties`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `properties` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `address` varchar(255) NOT NULL,
  `distance` int unsigned NOT NULL DEFAULT '0',
  `pricepermonth` double unsigned NOT NULL DEFAULT '0',
  `capacity` smallint unsigned NOT NULL DEFAULT '1',
  `bathroom` smallint unsigned NOT NULL DEFAULT '1',
  `rating` float(2,1) unsigned DEFAULT '0.0',
  `main_photo` varchar(255) DEFAULT NULL,
  `types_id` smallint unsigned NOT NULL,
  `bathroom_types_id` smallint unsigned NOT NULL,
  `owner_id` varchar(50) NOT NULL,
  `description` text,
  PRIMARY KEY (`id`),
  KEY `fk_properties_types_idx` (`types_id`),
  KEY `fk_properties_users_idx` (`owner_id`),
  KEY `fk_properties_bathroom_types_idx` (`bathroom_types_id`),
  CONSTRAINT `fk_properties_bathroom_types` FOREIGN KEY (`bathroom_types_id`) REFERENCES `bathroom_types` (`id`),
  CONSTRAINT `fk_properties_types` FOREIGN KEY (`types_id`) REFERENCES `types` (`id`),
  CONSTRAINT `fk_properties_users` FOREIGN KEY (`owner_id`) REFERENCES `users` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `properties`
--

LOCK TABLES `properties` WRITE;
/*!40000 ALTER TABLE `properties` DISABLE KEYS */;
INSERT INTO `properties` VALUES (1,'Kost ABC','Rungkut Mejoyo Selatan I-5',100,1000,20,0,4.5,'https://www.saniharto.com/assets/news/perbedaan_mansion_dan_rumah.jpg',1,1,'160420016','Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry\'s standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.'),(2,'Kost MNO','Rungkut Mejoyo Selatan III-9',500,1900,25,10,0.0,'https://berita.99.co/wp-content/uploads/2022/01/mansion-adalah-rumah-mewah.jpg',3,2,'160420046','Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry\'s standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.'),(3,'Kost GHI','Rungkut Mejoyo Selatan I-3',250,1800,15,0,0.0,'https://d3p0bla3numw14.cloudfront.net/news-content/img/2021/10/04170015/Apa-itu-Rumah-Mansion.jpg',2,1,'160420106','Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry\'s standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.'),(4,'Kost JKL','Rungkut Mejoyo Selatan II-9',300,1900,25,0,0.0,'https://i.pinimg.com/736x/ac/a8/45/aca845725a75871141e3f7b50f63dd13--my-dream-house-my-house.jpg',3,1,'160420056','Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry\'s standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.'),(5,'Kost MNO','Rungkut Mejoyo Selatan III-9',500,1900,25,10,0.0,'https://thumb.viva.co.id/media/frontend/thumbs3/2012/11/12/179719_lelang-mansion-di-beverly-hills_1265_711.jpg',3,2,'160420046','Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry\'s standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.');
/*!40000 ALTER TABLE `properties` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `types`
--

DROP TABLE IF EXISTS `types`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `types` (
  `id` smallint unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `types`
--

LOCK TABLES `types` WRITE;
/*!40000 ALTER TABLE `types` DISABLE KEYS */;
INSERT INTO `types` VALUES (1,'Pria'),(2,'Wanita'),(3,'Campuran');
/*!40000 ALTER TABLE `types` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `username` varchar(50) NOT NULL,
  `firstname` varchar(45) NOT NULL,
  `lastname` varchar(45) NOT NULL,
  `password` varchar(255) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES ('160420016','Test','Test','test','2023-04-10 07:38:26','2023-04-10 07:38:26'),('160420026','Test','Test','test','2023-04-10 07:39:01','2023-04-10 07:39:01'),('160420036','Test','Test','test','2023-04-10 07:39:03','2023-04-10 07:39:03'),('160420046','Test','Test','test','2023-04-10 07:39:03','2023-04-10 07:39:03'),('160420056','Test','Test','test','2023-04-10 07:38:57','2023-04-10 07:38:57'),('160420066','Test','Test','test','2023-04-10 07:38:51','2023-04-10 07:38:51'),('160420106','Test','Test','test','2023-04-10 08:01:14','2023-04-10 08:01:14');
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

-- Dump completed on 2023-04-11  0:02:25
