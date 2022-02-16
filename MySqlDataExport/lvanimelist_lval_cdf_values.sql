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
-- Table structure for table `lval_cdf_values`
--

DROP TABLE IF EXISTS `lval_cdf_values`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `lval_cdf_values` (
  `id` int NOT NULL AUTO_INCREMENT,
  `cdf_id` int DEFAULT NULL,
  `name` varchar(25) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id_idx` (`cdf_id`),
  CONSTRAINT `fk_cdv` FOREIGN KEY (`cdf_id`) REFERENCES `lval_codificators` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lval_cdf_values`
--

LOCK TABLES `lval_cdf_values` WRITE;
/*!40000 ALTER TABLE `lval_cdf_values` DISABLE KEYS */;
INSERT INTO `lval_cdf_values` VALUES (1,1,'test','žanrs testēšanas nolūkiem'),(3,1,'Komēdija','Komēdija ir luga, kurā attēloti konflikti un raksturi, kas izraisa smieklus'),(4,1,'Piedzīvojumu filma',''),(5,1,'Asa sižeta filma',''),(6,5,'Sunrise',''),(7,5,'J.C.Staff',''),(8,5,'Gonzo',''),(9,5,'Bones',''),(10,6,'Manga',''),(11,6,'Vizuālā novele',''),(12,6,'Oriģināls',''),(13,6,'Novele',''),(14,3,'TV',''),(15,3,'Filma',''),(16,3,'OVA','Original video animation '),(17,3,'ONA','Original net animation'),(18,2,'U','Bez vecuma ierobežojuma. Seriāls paredzēts visu vecumu skatītājiem (universālai auditorijai)'),(19,2,'N7','Līdz 7 g.v. Seriāls paredzēts personai, kas sasniegusi vismaz 7 gadu vecumu'),(20,2,'N12','Līdz 12 g.v. Seriālā ir atsevišķi leksikas un darbību elementi, kuri varētu būt nepiemēroti bērniem līdz 12 gadu vecumam'),(22,2,'A16','Līdz 16 g.v. Seriālā var parādīties rupja leksika, šausmas un vardarbība, kailums, kā arī narkotiku lietošana u.tml. '),(23,2,'N18','Līdz 18 g.v. Seriāls aizliegts nepilngadīgai personai'),(24,4,'Rediģēšanā','Ieraksts pieejams tikai administrācijai'),(25,4,'Nav iznācis',''),(26,4,'Iznāk',''),(27,4,'Iznācis',''),(28,1,'Drāma',''),(29,5,'AIC',''),(30,5,'Tezuka Productions',''),(31,7,'Skatāms',''),(32,7,'Pabeigts',''),(33,7,'Pieturēts',''),(34,7,'Nomests',''),(35,7,'Plāno skatīties',''),(48,8,'10','Meistardarbs'),(49,8,'9','Izcils'),(50,8,'8','Ļoti labs'),(51,8,'7','Labs'),(52,8,'6','Labs'),(53,8,'5','Vidējs'),(54,8,'4','Slikts'),(55,8,'3','Ļoti slikts'),(56,8,'2','Šausmīgs'),(57,8,'1','Šausmīgs');
/*!40000 ALTER TABLE `lval_cdf_values` ENABLE KEYS */;
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
