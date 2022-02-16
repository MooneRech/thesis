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
-- Temporary view structure for view `get_animelist`
--

DROP TABLE IF EXISTS `get_animelist`;
/*!50001 DROP VIEW IF EXISTS `get_animelist`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `get_animelist` AS SELECT 
 1 AS `id`,
 1 AS `anime_id`,
 1 AS `title_jp`,
 1 AS `title_eng`,
 1 AS `title_lv`,
 1 AS `score`,
 1 AS `type`,
 1 AS `progress`,
 1 AS `episodes`,
 1 AS `usr_status_id`,
 1 AS `user_id`,
 1 AS `usr_status`,
 1 AS `genres`,
 1 AS `tags`*/;
SET character_set_client = @saved_cs_client;

--
-- Final view structure for view `get_animelist`
--

/*!50001 DROP VIEW IF EXISTS `get_animelist`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`lval_user`@`%` SQL SECURITY DEFINER */
/*!50001 VIEW `get_animelist` AS select `lst`.`id` AS `id`,`anime`.`id` AS `anime_id`,`anime`.`title_jp` AS `title_jp`,`anime`.`title_eng` AS `title_eng`,`anime`.`title_lv` AS `title_lv`,`lst`.`score` AS `score`,`GET_CDV_VALUE_BY_ID`(`anime`.`type_id`) AS `type`,`lst`.`progress` AS `progress`,`anime`.`episode_count` AS `episodes`,`lst`.`status_id` AS `usr_status_id`,`lst`.`user_id` AS `user_id`,`GET_CDV_VALUE_BY_ID`(`lst`.`status_id`) AS `usr_status`,`GET_GENRE_LIST`(`anime`.`id`) AS `genres`,`GET_TAG_LIST`(`anime`.`id`,`lst`.`user_id`) AS `tags` from (((`lval_anime_info` `anime` join `lval_user_anime_lists` `lst` on((`anime`.`id` = `lst`.`anime_id`))) left join `lval_anime_genres` `gnr` on((`anime`.`id` = `gnr`.`anime_id`))) left join `lval_user_tags` `tg` on(((`anime`.`id` = `tg`.`anime_id`) and (`lst`.`user_id` = `tg`.`user_id`)))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Dumping events for database 'lvanimelist'
--

--
-- Dumping routines for database 'lvanimelist'
--
/*!50003 DROP FUNCTION IF EXISTS `get_cdf_id_by_code` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`lval_user`@`%` FUNCTION `get_cdf_id_by_code`(p_code VARCHAR(15)) RETURNS int
    DETERMINISTIC
BEGIN
  DECLARE v_id INT DEFAULT 0;
    
    SELECT id
    INTO v_id
  FROM lval_codificators
    WHERE code = p_code;

  RETURN (v_id);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP FUNCTION IF EXISTS `get_cdv_id_by_value` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`lval_user`@`%` FUNCTION `get_cdv_id_by_value`(p_code VARCHAR(50),p_value VARCHAR(50)) RETURNS int
    DETERMINISTIC
BEGIN
	DECLARE v_id INTEGER;
    
    SELECT id
    INTO v_id
    FROM lval_cdf_values
    WHERE name = p_value
		  AND cdf_id = get_cdf_id_by_code(p_code);
    
RETURN v_id;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP FUNCTION IF EXISTS `get_cdv_value_by_id` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`lval_user`@`%` FUNCTION `get_cdv_value_by_id`(p_id INTEGER) RETURNS varchar(100) CHARSET utf8mb4
    DETERMINISTIC
BEGIN
	DECLARE v_name VARCHAR(100) DEFAULT "";
    
    SELECT val.name 
    INTO v_name
    FROM lval_cdf_values val
    WHERE id = p_id;

RETURN v_name;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP FUNCTION IF EXISTS `get_genre_list` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`lval_user`@`%` FUNCTION `get_genre_list`(p_anime_id INTEGER) RETURNS blob
    DETERMINISTIC
BEGIN
	DECLARE v_genres BLOB;
    
    SELECT GROUP_CONCAT(GET_CDV_VALUE_BY_ID(`genre_id`) SEPARATOR ', ') 
    INTO v_genres
    FROM lval_anime_genres
    WHERE anime_id = p_anime_id;
    
RETURN v_genres;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP FUNCTION IF EXISTS `get_tag_list` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`lval_user`@`%` FUNCTION `get_tag_list`(p_anime_id INTEGER, p_user_id INTEGER) RETURNS blob
    DETERMINISTIC
BEGIN

DECLARE v_tags BLOB;

	SELECT GROUP_CONCAT(`tag` SEPARATOR ', ') AS `tags`
    INTO v_tags
    FROM lval_user_tags
    WHERE anime_id = p_anime_id AND user_id = p_user_id;

RETURN v_tags;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `create_animelist_entry` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`lval_user`@`%` PROCEDURE `create_animelist_entry`(	p_user_id INTEGER,
											p_anime_id INTEGER,
                                            p_progress INTEGER,
                                            p_score INTEGER,
                                            p_status_id INTEGER,
                                            OUT p_id INTEGER)
BEGIN

INSERT INTO
        lval_user_anime_lists ( user_id,
                                anime_id,
                                progress,
                                score,
                                status_id)
        VALUES (p_user_id,
                p_anime_id,
                p_progress,
                p_score,
                P_status_id);
                
		SELECT LAST_INSERT_ID() INTO p_id;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `create_anime_entity` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`lval_user`@`%` PROCEDURE `create_anime_entity`(p_title_jp 	VARCHAR(100),
										p_title_en 	VARCHAR(100),
										p_title_lv 	VARCHAR(100),
										p_type_id 	INTEGER,
										p_episodes	INTEGER,
										p_status_id	INTEGER,
										p_aired_from DATE,
										p_aired_to	DATE,
										p_studios_id INTEGER,
										p_source_id	INTEGER,
										p_duration	INTEGER,
										p_rating_id	INTEGER,
										p_image		LONGBLOB,
                                        p_image_name VARCHAR(50),
                                        p_description BLOB,
										OUT p_id INTEGER,
                                        OUT p_image_id INTEGER
                                        )
BEGIN
DECLARE v_id INT DEFAULT null;
DECLARE v_image_id INT DEFAULT null;

START TRANSACTION;
SET autocommit=OFF;

	IF p_image is not null THEN
		INSERT INTO lval_images (image, image_name)
		VALUES (p_image, p_image_name);
		SELECT LAST_INSERT_ID() INTO v_image_id;
    END IF;
    
    INSERT INTO lval_anime_info (title_jp,
								 title_eng,
								 title_lv,
								 type_id,
								 episode_count,
								 status_id,
								 aired_from,
								 aired_to,
								 studios_id,
								 source_id,
								 duration,
								 rating_id,
								 image_id,
                                 `description`)
    VALUES (p_title_jp,
			p_title_en,
            p_title_lv,
            p_type_id,
            p_episodes,
            p_status_id,
            p_aired_from,
            p_aired_to,
            p_studios_id,
            p_source_id,
            p_duration,
            p_rating_id,
            v_image_id,
            p_description);
            
	SELECT LAST_INSERT_ID() INTO v_id;
COMMIT;
SET p_id = v_id;
SET p_image_id = v_image_id;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `delete_anime_entity` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`lval_user`@`%` PROCEDURE `delete_anime_entity`(p_anime_id INTEGER)
BEGIN
	DECLARE v_image_id INT DEFAULT 0;
    START TRANSACTION;
    SET autocommit=off;
    
    SELECT id
    INTO v_image_id
    FROM lval_anime_info
    WHERE id = p_anime_id;
    
    DELETE FROM lval_anime_genres
    WHERE anime_id = p_anime_id;
    
    DELETE FROM lval_anime_info
    WHERE id = p_anime_id;
    
    IF v_image_id is not null THEN
		DELETE FROM lval_images
		WHERE id = v_image_id;
    END IF;
    
    COMMIT;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `delete_anime_from_list` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`lval_user`@`%` PROCEDURE `delete_anime_from_list`(p_id INTEGER)
BEGIN
	DECLARE v_anime_id INTEGER;
    DECLARE v_user_id INTEGER;
    
    SELECT anime_id, user_id
    INTO v_anime_id, v_user_id
    FROM lval_user_anime_list
    WHERE id = p_id;
    
    DELETE FROM lval_user_tags
    WHERE anime_id = v_anime_id AND  user_id = v_user_id;
    
    DELETE FROM lval_user_anime_list
    WHERE id = p_id;
    
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `update_anime_entry` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`lval_user`@`%` PROCEDURE `update_anime_entry`(p_title_jp 	VARCHAR(100),
										p_title_en 	VARCHAR(100),
										p_title_lv 	VARCHAR(100),
										p_type_id 	INTEGER,
										p_episodes	INTEGER,
										p_status_id	INTEGER,
										p_aired_from DATE,
										p_aired_to	DATE,
										p_studios_id INTEGER,
										p_source_id	INTEGER,
										p_duration	INTEGER,
										p_rating_id	INTEGER,
										p_image		LONGBLOB,
                                        p_image_name VARCHAR(50),
										p_id 		INTEGER,
                                        p_image_id INTEGER,
                                        p_description BLOB)
BEGIN
START TRANSACTION;
SET autocommit=OFF;

	IF p_image is not null AND p_image_id is null THEN
		INSERT INTO lval_images (image, image_name)
		VALUES (p_image, p_image_name);
		SELECT LAST_INSERT_ID() INTO p_image_id;
	ELSE IF p_image is not null AND p_image_id IS NOT NULL THEN
		UPDATE lval_images
        SET image = p_image,
			image_name = p_image_name
		WHERE id = p_image_id;
    END IF;END IF;

	UPDATE lval_anime_info
    SET title_jp = p_title_jp,
		title_eng = p_title_en,
		title_lv = p_title_lv,
		type_id = p_type_id,
		episode_count = p_episodes,
		status_id = p_status_id,
		aired_from = p_aired_from,
		aired_to = p_aired_to,
		studios_id = p_studios_id,
		source_id = p_source_id,
		duration = p_duration,
		rating_id = p_rating_id,
        image_id = p_image_id,
        `description` = p_description
	WHERE id = p_id;

COMMIT;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-10-04 18:21:39
