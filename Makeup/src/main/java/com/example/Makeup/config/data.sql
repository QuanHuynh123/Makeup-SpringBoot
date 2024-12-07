-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: localhost    Database: makeup
-- ------------------------------------------------------
-- Server version	9.1.0

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
  `id` int NOT NULL AUTO_INCREMENT,
  `role_id` int NOT NULL,
  `password` varchar(250) NOT NULL,
  `username` varchar(250) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKd4vb66o896tay3yy52oqxr9w0` (`role_id`),
  CONSTRAINT `FKd4vb66o896tay3yy52oqxr9w0` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account`
--

LOCK TABLES `account` WRITE;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;
INSERT INTO `account` VALUES (3,3,'$2a$10$bRlVVfJJ7g2CagQttSDOr.S7L8fXvDtZqRdSA89fyY8//zKG1H5yO','1234567891'),(4,3,'$2a$10$bRlVVfJJ7g2CagQttSDOr.S7L8fXvDtZqRdSA89fyY8//zKG1H5yO','1234567892'),(5,3,'$2a$10$bRlVVfJJ7g2CagQttSDOr.S7L8fXvDtZqRdSA89fyY8//zKG1H5yO','1234567893'),(6,3,'$2a$10$bRlVVfJJ7g2CagQttSDOr.S7L8fXvDtZqRdSA89fyY8//zKG1H5yO','1234567894'),(7,3,'$2a$10$bRlVVfJJ7g2CagQttSDOr.S7L8fXvDtZqRdSA89fyY8//zKG1H5yO','1234567895'),(19,2,'$2a$10$oUom2q6cC.seembhEtEWn.LY5PTGX8X6m1YESvd0YI/pq.y6naCSy','0703200286'),(20,1,'$2a$10$oUom2q6cC.seembhEtEWn.LY5PTGX8X6m1YESvd0YI/pq.y6naCSy','0703200288');
/*!40000 ALTER TABLE `account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `appointment`
--

DROP TABLE IF EXISTS `appointment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `appointment` (
  `id` int NOT NULL AUTO_INCREMENT,
  `end_time` time(6) NOT NULL,
  `makeup_date` date NOT NULL,
  `start_time` time(6) NOT NULL,
  `status` bit(1) NOT NULL,
  `service_makeup_id` int NOT NULL,
  `staff_id` int DEFAULT NULL,
  `user_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKcytv6pq9cmagdcsw1379pwxa2` (`service_makeup_id`),
  KEY `FK6j70i8k4wu1m1aahlty936m76` (`staff_id`),
  KEY `FKa8m1smlfsc8kkjn2t6wpdmysk` (`user_id`),
  CONSTRAINT `FK6j70i8k4wu1m1aahlty936m76` FOREIGN KEY (`staff_id`) REFERENCES `staff` (`id`),
  CONSTRAINT `FKa8m1smlfsc8kkjn2t6wpdmysk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKcytv6pq9cmagdcsw1379pwxa2` FOREIGN KEY (`service_makeup_id`) REFERENCES `service_makeup` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `appointment`
--

LOCK TABLES `appointment` WRITE;
/*!40000 ALTER TABLE `appointment` DISABLE KEYS */;
INSERT INTO `appointment` VALUES (4,'10:53:00.000000','2024-12-08','08:53:00.000000',_binary '\0',3,3,8),(5,'00:25:00.000000','2024-12-27','22:25:00.000000',_binary '\0',2,2,8),(6,'10:31:00.000000','2024-12-10','08:31:00.000000',_binary '',3,2,8),(7,'10:31:00.000000','2024-12-10','08:31:00.000000',_binary '',3,4,8),(8,'10:31:00.000000','2024-12-10','08:31:00.000000',_binary '\0',3,2,8);
/*!40000 ALTER TABLE `appointment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cart`
--

DROP TABLE IF EXISTS `cart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cart` (
  `id` int NOT NULL AUTO_INCREMENT,
  `create_date` date NOT NULL,
  `total_price` double NOT NULL,
  `total_quantity` int NOT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_9emlp6m95v5er2bcqkjsw48he` (`user_id`),
  CONSTRAINT `FKl70asp4l4w0jmbm1tqyofho4o` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart`
--

LOCK TABLES `cart` WRITE;
/*!40000 ALTER TABLE `cart` DISABLE KEYS */;
INSERT INTO `cart` VALUES (1,'2024-12-07',60,2,8),(2,'2024-12-07',0,0,9);
/*!40000 ALTER TABLE `cart` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cart_item`
--

DROP TABLE IF EXISTS `cart_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cart_item` (
  `id` int NOT NULL AUTO_INCREMENT,
  `price` double NOT NULL,
  `quantity` int NOT NULL,
  `use_date` date NOT NULL,
  `cart_id` int NOT NULL,
  `product_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK1uobyhgl1wvgt1jpccia8xxs3` (`cart_id`),
  KEY `FKjcyd5wv4igqnw413rgxbfu4nv` (`product_id`),
  CONSTRAINT `FK1uobyhgl1wvgt1jpccia8xxs3` FOREIGN KEY (`cart_id`) REFERENCES `cart` (`id`),
  CONSTRAINT `FKjcyd5wv4igqnw413rgxbfu4nv` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart_item`
--

LOCK TABLES `cart_item` WRITE;
/*!40000 ALTER TABLE `cart_item` DISABLE KEYS */;
INSERT INTO `cart_item` VALUES (2,30,1,'2024-12-22',1,40),(3,30,1,'2024-12-15',1,25);
/*!40000 ALTER TABLE `cart_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,'ANIME'),(2,'MOVIE'),(3,'GAME'),(4,'FESTIVAL');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `feed_back`
--

DROP TABLE IF EXISTS `feed_back`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `feed_back` (
  `id` int NOT NULL AUTO_INCREMENT,
  `comment` varchar(250) NOT NULL,
  `rating` int DEFAULT NULL,
  `review_date` date NOT NULL,
  `service_makeup_id` int NOT NULL,
  `user_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKrjwo69lirkpifjko6r5x9yutm` (`service_makeup_id`),
  KEY `FK6udpx3ddji8j31h2froqoay12` (`user_id`),
  CONSTRAINT `FK6udpx3ddji8j31h2froqoay12` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKrjwo69lirkpifjko6r5x9yutm` FOREIGN KEY (`service_makeup_id`) REFERENCES `service_makeup` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `feed_back`
--

LOCK TABLES `feed_back` WRITE;
/*!40000 ALTER TABLE `feed_back` DISABLE KEYS */;
/*!40000 ALTER TABLE `feed_back` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_item`
--

DROP TABLE IF EXISTS `order_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_item` (
  `id` int NOT NULL AUTO_INCREMENT,
  `price` double NOT NULL,
  `quantity` int NOT NULL,
  `use_date` date NOT NULL,
  `order_id` int NOT NULL,
  `product_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKt4dc2r9nbvbujrljv3e23iibt` (`order_id`),
  KEY `FK551losx9j75ss5d6bfsqvijna` (`product_id`),
  CONSTRAINT `FK551losx9j75ss5d6bfsqvijna` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `FKt4dc2r9nbvbujrljv3e23iibt` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_item`
--

LOCK TABLES `order_item` WRITE;
/*!40000 ALTER TABLE `order_item` DISABLE KEYS */;
/*!40000 ALTER TABLE `order_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `id` int NOT NULL AUTO_INCREMENT,
  `order_date` date NOT NULL,
  `pickup_date` date DEFAULT NULL,
  `status` bit(1) NOT NULL,
  `total_price` double NOT NULL,
  `total_quantity` int NOT NULL,
  `payment_id` int NOT NULL,
  `user_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKag8ppnkjvx255gj7lm3m18wkj` (`payment_id`),
  KEY `FKel9kyl84ego2otj2accfd8mr7` (`user_id`),
  CONSTRAINT `FKag8ppnkjvx255gj7lm3m18wkj` FOREIGN KEY (`payment_id`) REFERENCES `payment` (`id`),
  CONSTRAINT `FKel9kyl84ego2otj2accfd8mr7` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payment`
--

DROP TABLE IF EXISTS `payment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payment` (
  `id` int NOT NULL AUTO_INCREMENT,
  `status` bit(1) NOT NULL,
  `name_payment_method` varchar(250) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payment`
--

LOCK TABLES `payment` WRITE;
/*!40000 ALTER TABLE `payment` DISABLE KEYS */;
INSERT INTO `payment` VALUES (1,_binary '','Cash Payment'),(2,_binary '','Online Payment');
/*!40000 ALTER TABLE `payment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `id` int NOT NULL AUTO_INCREMENT,
  `price` double NOT NULL,
  `status` bit(1) DEFAULT NULL,
  `sub_category_id` int DEFAULT NULL,
  `size` varchar(10) NOT NULL,
  `description` varchar(250) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `nameproduct` varchar(255) NOT NULL,
  `rental_count` int DEFAULT NULL,
  `created_at` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKd9gfnsjgfwjtaxl57udrbocsp` (`sub_category_id`),
  CONSTRAINT `FKd9gfnsjgfwjtaxl57udrbocsp` FOREIGN KEY (`sub_category_id`) REFERENCES `sub_category` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=79 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (1,30,_binary '',19,'X','a','elfwoment1.jpg,elfwomen2.jpg,elfwomen3.jpg,elfwomen4.jpg,elfwoment5.jpg','Christmas Elf Women Girls Family Costumes',1,'2024-11-15'),(2,30,_binary '',19,'X','a','rechild1.jpg,redchild2.jpg','Red Children Christmas Costumes with Cute Hat',2,'2024-11-15'),(3,30,_binary '',19,'X','a','greenchild1.jpg,greenchild2.png,greenchild3.jpg','Green Child Christmas Costume with Colorful Snowball',1,'2024-11-15'),(4,30,_binary '',19,'X','a','christmas1.jpg,christmas2.jpg,christmas3.jpg','Christmas costumes women party dress with white gloves',1,'2024-11-15'),(5,30,_binary '',19,'X','a','santa1.png,santa3.jpg,santa2.png,santa4.jpg,santa5.jpg','Christmas Santa Claus Costume With The Beard So Smart',1,'2024-11-15'),(6,30,_binary '',19,'X','a','elfmixed1.jpg,elfmixed2.jpg,elfmixed3.jpg,elfmixed4.jpg,elfmixed5.jpg,elfmixed6.jpg,elfmixed8.jpg,elfmixed9.jpg,elfmixed11.jpg','Christmas Elf Cosplay Dress Red Mixed Green Costume for Christmas Party',1,'2024-11-15'),(7,30,_binary '',19,'X','a','newchristmas1.jpg,newchristmas2.jpg,newchristmas3.jpg,newchristmas4.jpg,newchristmas6.jpg,newchristmas7.jpg,newchristmas8.jpg,newchristmas9.jpg,newchristmas11.jpg','The new Christmas dress party dress Christmas costumes performing long-sleeved dress',1,'2024-11-15'),(8,30,_binary '',19,'X','a','newstyle1.jpg,newstyle2.jpg','New Style Women Christmas Santa Claus Costumes',1,'2024-11-15'),(9,30,_binary '',19,'X','a','shawl1.jpg,shawl2.jpg,shawl3.jpg,shawl4.jpg,shawl6.jpg,shawl8.jpg,shawl9.jpg,shawl11.jpg','Latest shawl Christmas dress party dress Christmas costume splited detachable skirt',1,'2024-11-15'),(10,30,_binary '',18,'X','a','penguin.jpg','Deluxe Penguin Halloween Animal Costume Cute cartoon maomao Uniform',1,'2024-11-15'),(11,30,_binary '',18,'X','a','witch1.jpg,witch2.jpg,witch3.jpg,witch4.jpg,witch5.jpg','Halloween Witch Long Dress Cosplay Costume',0,'2024-11-15'),(12,30,_binary '',18,'X','a','partywitch1.jpg,partywitch2.jpg,partywitch3.jpg,partywitch4.jpg','Party Witch Costume Halloween Costume Masquerade Performance Costume',5,'2024-11-15'),(13,30,_binary '',18,'X','a','vocaloid1.jpg,vocaloid2.jpg,vocaloid3.jpg,vocaloid4.jpg,vocaloid5.jpg,vocaloid6.jpg','Vocaloid Hatsune Miku Rascal Cosplay Meiko Cosplay Costume',0,'2024-11-15'),(14,30,_binary '',18,'X','a','sexywitch1.jpg,sexywitch2.jpg,sexywitch3.jpg,sexywitch4.jpg','Halloween Cute Witch Cosplay Costume',4,'2024-11-15'),(15,30,_binary '',18,'X','a','halloweenwitch1.jpg,halloweenwitch2.jpg,halloweenwitch3.jpg,halloweenwitch4.jpg,halloweenwitch5.jpg,halloweenwitch6.jpg','Halloween Witch Cosplay Costume',1,'2024-11-15'),(16,30,_binary '',18,'X','a','vintage2.jpg,vintage4.jpg,vintage5.jpg,vintage6.jpg,vintage7.jpg,vintage9.jpg,vintage10.jpg','Halloween Vintage Dress Two Colors Cosplay Costume',1,'2024-11-15'),(17,30,_binary '',18,'X','a','addams1.jpg,addams2.jpg,addams3.jpg,addams4.jpg,addams5.jpg,addams6.jpg','Addams Family Morticia Halloween Cosplay Costume',0,'2024-11-15'),(18,30,_binary '',18,'X','a','cloaks1.jpg,cloaks2.jpg,cloaks3.jpg,cloaks4.jpg,cloaks5.jpg,cloaks6.jpg','Halloween Cloaks Witch Cosplay Costumes Fancy Balls Costumes',0,'2024-11-15'),(19,30,_binary '',18,'X','a','vampires1.jpg,vampires2.jpg,vampires3.jpg,vampires5.jpg,vampires6.jpg,vampires7.jpg','Halloween Witch Dress Vampires Cosplay Costume',0,'2024-11-15'),(20,30,_binary '',18,'X','a','marryshaw1.jpg,marryshaw2.jpg,marryshaw3.jpg,marryshaw5.jpg,marryshaw6.jpg,marryshaw7.jpg,marryshaw8.jpg','Movie Mary Shaw Halloween Dress Cosplay Costume',0,'2024-11-15'),(21,30,_binary '',16,'X','a','furina1.jpg, furina2.jpg, furina3.jpg, furina4.jpg, furina5.jpg, furina6.jpg, furina7.jpg, furina8.jpg','The Hydro Archon Dark Furina de Fontaine Focalors Cosplay Costume',0,'2024-11-15'),(22,30,_binary '',16,'X','a','ganyu1.jpg, ganyu2.jpg, ganyu3.jpg, ganyu4.jpg, ganyu5.jpg, ganyu6.jpg, ganyu7.jpg, ganyu8.jpg, ganyu9.jpg, ganyu10.jpg, ganyu11.jpg, ganyu12.jpg','Lantern Rite Twilight Blossom Ganyu Cosplay Costume',0,'2024-11-15'),(23,30,_binary '',16,'X','a','maivuika1.jpg, maivuika2.jpg, maivuika3.jpg, maivuika4.jpg, maivuika5.jpg, maivuika6.jpg, maivuika7.jpg, maivuika8.jpg, maivuika9.jpg, maivuika10.jpg, maivuika11.jpg, maivuika12.jpg, maivuika13.jpg, maivuika14.jpg, maivuika15.jpg','Mavuika Cosplay Costume Halloween Costume — Standard Edition',6,'2024-11-15'),(24,30,_binary '',16,'X','a','raiden1.jpg, raiden2.jpg, raiden3.jpg, raiden4.jpg, raiden5.jpg, raiden6.jpg, raiden7.jpg, raiden8.jpg, raiden9.jpg, raiden10.jpg','Baal Raiden Shogun Magatsu Mitake Narukami no Mikoto Purple Long Cosplay Wigs',2,'2024-11-15'),(25,30,_binary '',16,'X','a','shenhe1.jpg, shenhe2.jpg, shenhe3.jpg, shenhe4.jpg, shenhe5.jpg, shenhe6.jpg, shenhe7.jpg, shenhe8.jpg, shenhe9.jpg','Lantern Rite Frostflower Dew Shenhe Cosplay Costume',9,'2024-11-15'),(26,30,_binary '',13,'X','a','aponia1.jpg, aponia2.jpg, aponia3.jpg, aponia4.jpg, aponia5.jpg','Honkai Impact 3rd Aponia Blonde Cosplay Wigs',2,'2024-11-15'),(27,30,_binary '',13,'X','a','elysia1.jpg, elysia2.jpg, elysia3.jpg, elysia4.jpg, elysia5.jpg, elysia6.jpg, elysia7.jpg, elysia8.jpg, elysia9.jpg','Honkai Impact 3rd Elysia Cosplay Costume',5,'2024-11-15'),(28,30,_binary '',13,'X','a','hkasuka01.jpg, hkasuka02.jpg, hkasuka03.jpg, hkasuka04.jpg, hkasuka05.jpg, hkasuka06.jpg, hkasuka07.jpg, hkasuka08.jpg, hkasuka09.jpg, hkasuka10.jpg','EVA Asuka Honkai Impact 3rd Collaboration Cosplay Costume',7,'2024-11-15'),(29,30,_binary '',15,'X','A','firefly1.jpg, firefly2.jpg, firefly3.jpg, firefly4.jpg, firefly5.jpg, firefly6.jpg, firefly7.jpg, firefly8.jpg, firefly9.jpg, firefly10.jpg','Honkai Star Rail Firefly Cosplay Costume',8,'2024-11-15'),(30,30,_binary '',15,'X','A','kafka1.png, kafka2.png, kafka3.png, kafka4.png','Honkai Star Rail Kafka Cosplay Shoes',3,'2024-11-15'),(31,30,_binary '',15,'X','A','sparkle1.jpg, sparkle2.jpg, sparkle3.jpg, sparkle4.jpg, sparkle5.jpg, sparkle6.jpg, sparkle7.jpg, sparkle8.jpg','Sparkle Honkai Star Rail Cosplay Costume',2,'2024-11-15'),(32,30,_binary '',15,'X','A','yunli1.jpg, yunli2.jpg, yunli3.jpg, yunli4.jpg, yunli5.jpg, yunli6.jpg, yunli7.jpg, yunli8.jpg, yunli9.jpg','Honkai Star Rail Yunli Cosplay Costume',8,'2024-11-15'),(33,30,_binary '',14,'X','A','ahri1.jpg, ahri2.jpg, ahri3.jpg, ahri4.jpg, ahri5.jpg, ahri6.jpg, ahri7.jpg, ahri8.jpg, ahri9.jpg, ahri10.jpg, ahri11.jpg','LOL Wild Rift The Nine-Tailed Fox Ahri Cosplay Costume',0,'2024-11-15'),(34,30,_binary '',14,'X','A','caitlyn1.jpg, caitlyn2.jpg, caitlyn3.jpg, caitlyn4.jpg, caitlyn5.jpg, caitlyn6.jpg, caitlyn7.jpg, caitlyn8.jpg, caitlyn9.jpg, caitlyn10.jpg, caitlyn11.jpg, caitlyn12.jpg','LOL Heartache & Heartthrob Caitlyn Cosplay Costume Halloween Costume',0,'2024-11-15'),(35,30,_binary '',14,'X','A','kaisa1.jpg, kaisa2.jpg, kaisa3.jpg, kaisa4.jpg, kaisa5.jpg, kaisa6.jpg, kaisa7.jpg, kaisa8.jpg, kaisa9.jpg, kaisa10.jpg, kaisa11.jpg','LOL KDA ALL OUT Kaisa Cosplay Costume Halloween Costume',4,'2024-11-15'),(36,30,_binary '',14,'X','A','silco1.jpg, silco2.jpg, silco3.jpg, silco4.jpg, silco5.jpg, silco6.jpg','LOL KDA ALL OUT Kaisa Cosplay Costume Halloween Costume',2,'2024-11-19'),(37,30,_binary '',8,'x','a','joker1.jpg, joker2.jpg, joker3.jpg, joker4.jpg','Joker Purple Cosplay Costumes Coat',1,'2024-11-19'),(38,30,_binary '',9,'x','a','spiderman1.jpg, spiderman2.jpg, spiderman3.jpg, spiderman4.jpg','Spider-Man Black Cosplay Costume',3,'2024-11-19'),(39,30,_binary '',9,'x','a','miles1.jpg, miles2.jpg, miles3.jpg, miles4.jpg, miles5.jpg, miles6.jpg','Spider-Man Miles Morales Jumpsuit Cosplay Costume',7,'2024-11-19'),(40,30,_binary '',9,'x','a','miguel1.jpg, miguel2.jpg, miguel3.jpg, miguel4.jpg, miguel5.jpg, miguel6.jpg, miguel7.jpg','Spider-Man 2099 Miguel O\'Hara Cosplay Costume Men Halloween Costume',9,'2024-11-19'),(41,30,_binary '',9,'x','a','stacy1.jpg, stacy2.jpg, stacy3.jpg, stacy4.jpg, stacy5.jpg, stacy6.jpg, stacy7.jpg, stacy8.jpg, stacy9.jpg, stacy10.jpg, stacy11.jpg','Spider-Man Gwen Stacy Cosplay Costume',4,'2024-11-19'),(42,30,_binary '',1,'x','a','whis-purple1.jpg, whis-purple2.jpg, whis-purple3.jpg, whis-purple4.jpg, whis-purple5.jpg, whis-purple6.jpg','Dragon Ball Super Whis Purple Anime Cosplay Costumes',2,'2024-11-19'),(43,30,_binary '',1,'x','a','vegeta3.jpg, vegeta4.jpg','Dragon Ball Vegeta Cosplay Shoes Boots Custom-Made',4,'2024-11-19'),(44,30,_binary '',1,'x','a','vegeta1.jpg, vegeta2.jpg','Dragon Ball Vegeta 1st Version Cosplay Costume',6,'2024-11-19'),(45,30,_binary '',1,'x','a','picolo1.jpg, picolo2.jpg, picolo3.jpg, picolo4.jpg','Dragon Ball For Piccolo Uniforms Cosplay Costume Purple',8,'2024-11-19'),(46,30,_binary '',1,'x','a','no18-1.jpg, no18-2.jpg, no18-3.jpg','Dragon Ball Z Cyborg 18Gou Cosplay Boots Shoes',9,'2024-11-19'),(47,30,_binary '',1,'x','a','no17-1.jpg, no17-2.jpg, no17-3.jpg, no17-4.jpg, no17-5.jpg','Dragon Ball Z Android No.17 Cosplay Costume',4,'2024-11-19'),(48,30,_binary '',1,'x','a','master-roshi1.jpg, master-roshi2.jpg, master-roshi3.jpg, master-roshi4.jpg, master-roshi5.jpg','Dragon Ball Master Roshi 1st Version Cosplay Costume',2,'2024-11-19'),(49,30,_binary '',1,'x','a','goku1.jpg, goku2.jpg, goku3.jpg','Dragon Ball Monkey King Son GoKu Kakarot Cosplay Shoes Boots Custom-Made',1,'2024-11-19'),(50,30,_binary '',1,'x','a','gohan1.jpg, gohan2.jpg, gohan3.jpg, gohan4.jpg','Dragon Ball Son Gohan Uniforms Cosplay Costume',5,'2024-11-19'),(51,30,_binary '',1,'x','a','chichi1.jpg,chichi2.jpg, chichi3.jpg, chichi4.jpg, chichi5.jpg, chichi6.jpg, chichi7.jpg','Dragon Ball Chi Chi Cosplay Costume',8,'2024-11-19'),(52,30,_binary '',1,'x','a','bulma1.jpg, bulma2.jpg, bulma3.jpg, bulma4.jpg, bulma5.jpg, bulma6.jpg, bulma7.jpg, bulma8.jpg','Dragon Ball Z Bulma Anime Cosplay Costumes',0,'2024-11-19'),(53,30,_binary '',6,'x','a','mai1.jpg, mai2.jpg, mai3.jpg, mai4.jpg, mai5.jpg, mai6.jpg, mai7.jpg, mai8.jpg, mai9.jpg','Jujutsu Kaisen Zenin Mai Cosplay Costume',6,'2024-11-19'),(54,30,_binary '',6,'x','a','yuta1.jpg, yuta2.jpg, yuta3.jpg, yuta4.jpg, yuta5.jpg, yuta6.jpg','Jujutsu Kaisen Sorcery Fight Yuta Okkotsu Cosplay Costume',3,'2024-11-19'),(55,30,_binary '',6,'x','a','itadori1.jpg, itadori2.jpg, itadori3.jpg','Anime Jujutsu Kaisen Yuji Itadori Pink mixed Black Men Cosplay Wigs',2,'2024-11-19'),(56,30,_binary '',6,'x','a','fushiguro1.jpg, fushiguro2.jpg, fushiguro3.jpg, fushiguro4.jpg, fushiguro5.jpg','Jujutsu Kaisen Toji Fushiguro Cosplay Costume',6,'2024-11-19'),(57,30,_binary '',6,'x','a','geto1.jpg, geto2.jpg, geto3.jpg, geto4.jpg, geto5.jpg, geto6.jpg','Jujutsu Kaisen Sorcery Fight Suguru Geto Uniform Cosplay Costume',8,'2024-11-19'),(58,30,_binary '',6,'x','a','gojo1.jpg, gojo2.jpg, gojo3.jpg, gojo4.jpg, gojo5.jpg','Anime Jujutsu Kaisen Satoru Gojo Short Grey Men Cosplay Wigs',9,'2024-11-19'),(59,30,_binary '',6,'x','a','sukuna1.jpg, sukuna2.jpg, sukuna3.jpg, sukuna4.jpg, sukuna5.jpg, sukuna6.jpg','Jujutsu Kaisen Ryoumen Sukuna kimono Bathrobe Cosplay Costume',0,'2024-11-19'),(60,30,_binary '',6,'x','a','momo1.jpg, momo2.jpg','Jujutsu Kaisen Nishimiya Momo Cosplay Costume',4,'2024-11-19'),(61,30,_binary '',5,'x','a','terrible1.jpg, terrible2.jpg, terrible3.jpg','One Punch Man Terrible Tornado Cosplay Costumes Customized',2,'2024-11-19'),(62,30,_binary '',5,'x','a','speedofsound1.jpg, speedofsound2.jpg','One Punch Man Speed of Sound Sonic Uniform Cosplay Costume',1,'2024-11-19'),(63,30,_binary '',5,'x','a','onepunchman1.jpg,onepunchman2.jpg,onepunchman3.jpg','One Punch Man Saitama Zipper Hoodie Sweater Cosplay Costumes',5,'2024-11-19'),(64,30,_binary '',5,'x','a','onepunchman4.jpg,onepunchman5.jpg,onepunchman6.jpg,onepunchman7.jpg','One Punch Man Saitama With Vacant Look Cosplay Costumes Hat',7,'2024-11-15'),(65,30,_binary '',5,'x','a','heliish.jpg','One Punch Man Hellish Blizzard Cosplay Costume Custom Made',9,'2024-11-15'),(66,30,_binary '',1,'x','1','no16-1.jpg,no16-2.jpg,no16-3.jpg,no16-4.jpg,no16-5.jpg','Dragonball Z Android No.16 Cosplay Costume',2,'2024-11-15'),(67,30,_binary '',20,'x','1','ai1.jpg,ai2.jpg,ai3.jpg,ai4.jpg,ai5.jpg,ai6.jpg','AI',2,'2024-11-15'),(68,30,_binary '',20,'x','1','auquamarine1.jpg,auquamarine2.jpg,auquamarine3.jpg,auquamarine4.jpg','Aquamarine Hoshino',2,'2024-11-15'),(69,30,_binary '',20,'x','1','ruby1.jpg,ruby2.jpg,ruby3.jpg,ruby4.jpg','Oshi no Ko Ruby Hoshino Blonde Mixed Red Cosplay Wigs',4,'2024-11-15'),(70,30,_binary '',20,'x','1','ruby5.jpg,ruby6.jpg,ruby7.jpg,ruby8.jpg,ruby9.jpg,ruby10.jpg,ruby11.jpg,ruby12.jpg,ruby13.jpg','OSHI NO KO Ruby Hoshino Cosplay Costume',4,'2024-11-15'),(71,30,_binary '',21,'X','1','damian1.jpg,damian2.jpg,damian3.jpg,damian4.jpg,damian5.jpg,damian6.jpg,damian7.jpg,damian8.jpg','SPY×FAMILY Damian Uniform Cosplay Costume',4,'2024-11-15'),(72,30,_binary '',21,'X','1','anya1.jpg,anya2.jpg,anya3.jpg,anya4.jpg','SPY×FAMILY Forger Anya Black Uniform Daily Cosplay Costume',4,'2024-11-15'),(73,30,_binary '',21,'X','1','anya5.jpg,anya6.jpg,anya7.jpg,anya8.jpg,anya9.jpg,anya10.jpg','SPY×FAMILY Forger Anya Pink Cosplay Wigs',4,'2024-11-15'),(74,30,_binary '',21,'x','1','franky1.jpg,franky2.jpg,franky3.jpg,franky4.jpg','SPY x FAMILY Franky Franklin Red Glasses Cosplay Prop',4,'2024-11-15'),(75,30,_binary '',21,'x','1','loid1.jpg,loid2.jpg,loid3.jpg,loid4.jpg,loid5.jpg','SPY×FAMILY Twilight Loid Forger Cosplay Costume',4,'2024-11-15'),(76,30,_binary '',21,'x','1','sylvia1.jpg,sylvia2.jpg,sylvia3.jpg,sylvia4.jpg,sylvia5.jpg,sylvia6.jpg,sylvia7.jpg,sylvia8.jpg,sylvia9.jpg,sylvia10.jpg','Spy x Family Sylvia Sherwood Cosplay Costume',4,'2024-11-15'),(77,30,_binary '',21,'x','1','yor1.jpg,yor2.jpg,yor3.jpg,yor4.jpg,yor5.jpg,yor6.jpg','SPY×FAMILY Forger Yor Black Long Cosplay Wigs',4,'2024-11-15'),(78,30,_binary '',21,'x','1','yor7.jpg,yor8.jpg,yor9.jpg,yor10.jpg,yor11.jpg,yor12.jpg,yor13.jpg','SPY×FAMILY Yor Forger Red Dress Daily Cosplay Costume',4,'2024-11-15');
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `id` int NOT NULL AUTO_INCREMENT,
  `namerole` varchar(40) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'ADMIN'),(2,'USER'),(3,'STAFF');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `service_makeup`
--

DROP TABLE IF EXISTS `service_makeup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `service_makeup` (
  `id` int NOT NULL AUTO_INCREMENT,
  `price` double NOT NULL,
  `time` int DEFAULT NULL,
  `description` varchar(250) DEFAULT NULL,
  `nameservice` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `service_makeup`
--

LOCK TABLES `service_makeup` WRITE;
/*!40000 ALTER TABLE `service_makeup` DISABLE KEYS */;
INSERT INTO `service_makeup` VALUES (1,500,2,'s','Makeup Đám Cưới'),(2,500,2,'s','Makeup Đi Chơi/Event'),(3,500,2,'s','Makeup Mẫu Ảnh'),(4,500,2,'s','Makeup Model'),(5,500,2,'s','Makeup Công Sở'),(6,500,2,'s','Makeup Sexy');
/*!40000 ALTER TABLE `service_makeup` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `staff`
--

DROP TABLE IF EXISTS `staff`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `staff` (
  `id` int NOT NULL AUTO_INCREMENT,
  `phone` varchar(20) DEFAULT NULL,
  `name` varchar(40) DEFAULT NULL,
  `account_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKs9jl798sgmtrl79dm4svocvaw` (`account_id`),
  CONSTRAINT `FKs9jl798sgmtrl79dm4svocvaw` FOREIGN KEY (`account_id`) REFERENCES `account` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `staff`
--

LOCK TABLES `staff` WRITE;
/*!40000 ALTER TABLE `staff` DISABLE KEYS */;
INSERT INTO `staff` VALUES (1,'0909582411','Ngọc Mây',3),(2,'0242412345','Nguyễn Túy Vân',4),(3,'0922112334','Trúc Mai',5),(4,'0714424242','Ngọc Trinh',6),(5,'0825241411','Trần Hà Linh',7);
/*!40000 ALTER TABLE `staff` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sub_category`
--

DROP TABLE IF EXISTS `sub_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sub_category` (
  `id` int NOT NULL AUTO_INCREMENT,
  `sub_category_id` int NOT NULL,
  `name` varchar(100) NOT NULL,
  `category_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKgwjnv828joeda2x15sk8h5d8k` (`sub_category_id`),
  KEY `FKl65dyy5me2ypoyj8ou1hnt64e` (`category_id`),
  CONSTRAINT `FKl65dyy5me2ypoyj8ou1hnt64e` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sub_category`
--

LOCK TABLES `sub_category` WRITE;
/*!40000 ALTER TABLE `sub_category` DISABLE KEYS */;
INSERT INTO `sub_category` VALUES (1,1,'DRAGON BALL',1),(2,1,'ATTACK ON TITAN',1),(3,1,'NARUTO',1),(4,1,'ONE PEACE ',1),(5,1,'ONE PUNCH MAN',1),(6,1,'JUJUTSU KAISEN',1),(7,2,'RESIDENT EVIL',2),(8,2,'SUPERMAN',2),(9,2,'SPIDER-MAN ',2),(10,2,'AQUAMAN',2),(11,2,'IT',2),(12,2,'CAPTAIN MAVEL',2),(13,3,'FATE GRAND ORDER',3),(14,3,'LEAGUE OF LEGION',3),(15,3,'HONKAI STAR RAIL',3),(16,3,'GENSHIN IMPACT ',3),(17,3,'VANLORANT',3),(18,4,'HALLOWEEN',4),(19,4,'MERRY CHRISTMAS',4),(20,1,'OSHI NO KO',1),(21,1,'SPY x FAMILY',1);
/*!40000 ALTER TABLE `sub_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `address` varchar(50) DEFAULT NULL,
  `email` varchar(40) DEFAULT NULL,
  `fullname` varchar(40) DEFAULT NULL,
  `phone` varchar(20) NOT NULL,
  `account_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_nrrhhb0bsexvi8ch6wnon9uog` (`account_id`),
  CONSTRAINT `FKc3b4xfbq6rbkkrddsdum8t5f0` FOREIGN KEY (`account_id`) REFERENCES `account` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (8,'83/1b Tây Lân - Ba Diem - Hoc Monn','huynhminhquan07072002@gmail.com','Huynh Minh Quan','0703200286',19),(9,'ggg','quan07072002@gmail.com','Quan','0703200288',20),(10,'fqfqf','bestleague07072002@gmail.com','0703200QQ286','0703200286',NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-12-07 14:44:58
