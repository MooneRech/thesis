-- MySQL dump 10.13  Distrib 8.0.26, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: lvanimelist
-- ------------------------------------------------------
-- Server version	8.0.26

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
-- Table structure for table `lval_anime_info`
--

DROP TABLE IF EXISTS `lval_anime_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `lval_anime_info` (
  `id` int NOT NULL AUTO_INCREMENT,
  `title_jp` varchar(150) NOT NULL,
  `title_eng` varchar(150) DEFAULT NULL,
  `title_lv` varchar(150) DEFAULT NULL,
  `type_id` int DEFAULT NULL,
  `episode_count` int DEFAULT NULL,
  `status_id` int DEFAULT NULL,
  `aired_from` date DEFAULT NULL,
  `aired_to` date DEFAULT NULL,
  `studios_id` int DEFAULT NULL,
  `source_id` int DEFAULT NULL,
  `duration` int DEFAULT NULL,
  `rating_id` int DEFAULT NULL,
  `image_id` int DEFAULT NULL,
  `description` blob,
  `creation_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_date` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `fk_type_idx` (`type_id`),
  KEY `fk_status_idx` (`status_id`),
  KEY `fk_studios_idx` (`studios_id`),
  KEY `fk_source_idx` (`source_id`),
  KEY `fk_rating_idx` (`rating_id`),
  KEY `fk_image_idx` (`image_id`),
  CONSTRAINT `fk_image` FOREIGN KEY (`image_id`) REFERENCES `lval_images` (`id`) ON DELETE SET NULL,
  CONSTRAINT `fk_rating` FOREIGN KEY (`rating_id`) REFERENCES `lval_cdf_values` (`id`),
  CONSTRAINT `fk_source` FOREIGN KEY (`source_id`) REFERENCES `lval_cdf_values` (`id`),
  CONSTRAINT `fk_status` FOREIGN KEY (`status_id`) REFERENCES `lval_cdf_values` (`id`),
  CONSTRAINT `fk_studios` FOREIGN KEY (`studios_id`) REFERENCES `lval_cdf_values` (`id`),
  CONSTRAINT `fk_type` FOREIGN KEY (`type_id`) REFERENCES `lval_cdf_values` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lval_anime_info`
--

LOCK TABLES `lval_anime_info` WRITE;
/*!40000 ALTER TABLE `lval_anime_info` DISABLE KEYS */;
INSERT INTO `lval_anime_info` VALUES (2,'test','','',NULL,NULL,24,NULL,NULL,NULL,NULL,24,NULL,NULL,_binary 'aprsksta saglabāšānas tests aprsksta saglabāšānas tests aprsksta saglabāšānas tests aprsksta saglabāšānas tests aprsksta saglabāšānas tests aprsksta saglabāšānas tests aprsksta saglabāšānas tests aprsksta saglabāšānas tests aprsksta saglabāšānas tests aprsksta saglabāšānas tests aprsksta saglabāšānas tests aprsksta saglabāšānas tests aprsksta saglabāšānas tests aprsksta saglabāšānas tests aprsksta saglabāšānas tests aprsksta saglabāšānas tests aprsksta saglabāšānas tests aprsksta saglabāšānas tests aprsksta saglabāšānas tests aprsksta saglabāšānas tests aprsksta saglabāšānas aprsksta saglabāšānas tests aprsksta saglabāšānas tests aprsksta saglabāšānas tests aprsksta saglabāšānas tests aprsksta saglabāšānas tests aprsksta saglabāšānas tests aprsksta saglabāšānas tests aprsksta saglabāšānas tests aprsksta saglabāšānas tests aprsksta saglabāšānas tests aprsksta saglabāšānas tests aprsksta saglabāšānas tests aprsksta saglabāšānas tests aprsksta saglabāšānas tests aprsksta saglabāšānas tests aprsksta saglabāšānas tests aprsksta saglabāšānas tests aprsksta saglabāšānas tests aprsksta saglabāšānas tests aprsksta saglabāšānas tests aprsksta saglabāšānas tests aprsksta saglabāšānas tests aprsksta saglabāšānas tests aprsksta saglabāšānas tests aprsksta saglabāšānas tests aprsksta saglabāšānas tests aprsksta saglabāšānas tests aprsksta saglabāšānas tests aprsksta saglabāšānas tests aprsksta saglabāšānas tests  saglabāšānas tests prsksta saglabāšānas tests aprsksta saglabāšānas tests aprsksta saglabāšānas tests aprsksta saglabāšānas tests aprsksta saglabāšānas tests aprsksta saglabāšānas tests aprsksta saglabāšānas tests aprsksta saglabāšānas tests ','2021-08-14 19:18:41','2021-08-16 19:56:46'),(3,'t','t','t',14,12,24,'2021-08-14',NULL,6,12,24,18,NULL,NULL,'2021-08-14 21:50:16','2021-08-15 15:57:55'),(10,'Acchi Kocchi (TV)','Place to Place','Turp šurp',14,12,27,'2021-08-16','2021-08-16',29,10,24,18,24,_binary 'Feelings may come and go, but true love always remains in the heart. Tsumiki Miniwa is in love with her best friend, Io Otonashi. For her, confessing is nearly impossible; but to her friends, they seem to be the perfect match. Cute and petite, Tsumiki comes off more as a friend, and Io\'s attitude toward her is friendlier than toward others. Despite the constant teasing and obvious hints that his friends have been dropping, Io always seems to miss the signs.\n\nThroughout her everyday school life, Tsumiki spends time with her friends and Io. Will she finally muster enough courage to confess her true feelings?','2021-08-16 17:19:34','2021-08-18 21:26:45'),(11,'Black Jack','Black Jack','Melns Džeks',16,12,27,'1993-02-01','2021-08-16',30,10,50,20,14,NULL,'2021-08-16 17:27:07','2021-08-16 19:04:09'),(12,'Dororo','Dororo','Dororo',14,24,27,'2019-01-01','2019-01-31',30,10,24,23,15,'','2021-08-16 17:34:32','2021-08-31 16:58:39'),(13,'Gintama','Gintama','Gintama',14,201,26,'2017-02-01',NULL,6,10,24,22,16,'','2021-08-16 18:14:38','2021-08-31 16:58:54'),(14,'Hotarubi no Mori e','Into the Forest of Fireflies\' Light','Mežā, kur ugunskura gaisma',15,1,25,'2021-08-31',NULL,9,12,60,18,31,'','2021-08-16 18:19:31','2021-08-31 17:00:35'),(15,'Mahoutsukai no Yome','The Ancient Magus\' Bride','Senā Maga līgava',14,24,27,'2021-08-16','2021-08-16',6,10,24,20,32,'','2021-08-16 18:25:44','2021-08-31 17:01:19'),(16,'Mushishi','Mushi-Shi','Mushishi',14,26,27,'2021-08-16','2021-08-16',8,13,24,20,18,'','2021-08-16 18:29:44','2021-08-31 17:01:37'),(18,'Bleach','Bleach','Bleach',14,366,27,'2004-01-01','2012-01-31',9,10,24,20,25,_binary 'Ichigo Kurosaki is an ordinary high schooler—until his family is attacked by a Hollow, a corrupt spirit that seeks to devour human souls. It is then that he meets a Soul Reaper named Rukia Kuchiki, who gets injured while protecting Ichigo\'s family from the assailant. To save his family, Ichigo accepts Rukia\'s offer of taking her powers and becomes a Soul Reaper as a result.\n\nHowever, as Rukia is unable to regain her powers, Ichigo is given the daunting task of hunting down the Hollows that plague their town. However, he is not alone in his fight, as he is later joined by his friends—classmates Orihime Inoue, Yasutora Sado, and Uryuu Ishida—who each have their own unique abilities. As Ichigo and his comrades get used to their new duties and support each other on and off the battlefield, the young Soul Reaper soon learns that the Hollows are not the only real threat to the human world.','2021-08-16 20:02:03','2021-08-31 17:00:47'),(19,'Test','Test','Testēšana',16,1,27,'2021-08-21','2021-08-21',8,12,24,22,26,_binary 'Anime testēšanas nolūkiem','2021-08-21 21:22:07','2021-08-21 21:22:36'),(20,'Log Horizon','Log Horizon','Log Horizon',14,25,27,'2013-02-28','2014-02-28',6,13,24,20,27,'','2021-08-21 21:24:59','2021-08-21 21:24:59'),(21,'One Piece','One Piece','One Piece',14,NULL,26,'1999-02-01',NULL,7,10,24,20,28,'','2021-08-21 21:26:43','2021-08-21 21:26:43'),(22,'Ayakashi: Japanese Classic Horror','Ayakashi: Japanese Classic Horror','Ayakashi: Japāņu klasiskās šausmu filmas',14,11,27,'2006-02-01','2006-01-28',9,10,24,23,29,'','2021-08-21 21:29:22','2021-08-21 21:29:22'),(23,'Babylon','Babylon','Babilona',14,12,27,'2021-08-17','2021-08-31',8,13,24,23,30,'','2021-08-21 21:31:29','2021-08-21 21:31:29');
/*!40000 ALTER TABLE `lval_anime_info` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-10-04 18:21:37
