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
-- Table structure for table `lval_anime_genres`
--

DROP TABLE IF EXISTS `lval_anime_genres`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `lval_anime_genres` (
  `anime_id` int NOT NULL,
  `genre_id` int NOT NULL,
  KEY `fk_anime_idx` (`anime_id`),
  KEY `fk_genre_idx` (`genre_id`),
  CONSTRAINT `fk_anime` FOREIGN KEY (`anime_id`) REFERENCES `lval_anime_info` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_genre` FOREIGN KEY (`genre_id`) REFERENCES `lval_cdf_values` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lval_anime_genres`
--

LOCK TABLES `lval_anime_genres` WRITE;
/*!40000 ALTER TABLE `lval_anime_genres` DISABLE KEYS */;
INSERT INTO `lval_anime_genres` VALUES (3,3),(11,28),(10,3),(19,1),(19,3),(19,4),(20,4),(21,3),(21,4),(21,5),(21,28),(22,1),(12,4),(12,5),(12,28),(13,3),(14,28),(18,4),(18,3),(18,5),(18,28),(15,28),(15,4),(15,3),(16,28);
/*!40000 ALTER TABLE `lval_anime_genres` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-10-04 18:21:38
