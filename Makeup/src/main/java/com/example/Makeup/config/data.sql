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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account`
--

LOCK TABLES `account` WRITE;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;
INSERT INTO `account` VALUES (1,2,'$2a$10$ibFvhRADmA1pP238b4.8/.l5TO/Z2oVbkaGQHKqJfOSRz4yCjNC9a','0703200286');
/*!40000 ALTER TABLE `account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `appointment`
--

DROP TABLE IF EXISTS `appointment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `appointment` (
  `end_time` time(6) NOT NULL,
  `id` int NOT NULL AUTO_INCREMENT,
  `makeup_date` date NOT NULL,
  `service_makeup_id` int NOT NULL,
  `staff_id` int NOT NULL,
  `start_time` time(6) NOT NULL,
  `status` bit(1) NOT NULL,
  `user_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKcytv6pq9cmagdcsw1379pwxa2` (`service_makeup_id`),
  KEY `FK6j70i8k4wu1m1aahlty936m76` (`staff_id`),
  KEY `FKa8m1smlfsc8kkjn2t6wpdmysk` (`user_id`),
  CONSTRAINT `FK6j70i8k4wu1m1aahlty936m76` FOREIGN KEY (`staff_id`) REFERENCES `staff` (`id`),
  CONSTRAINT `FKa8m1smlfsc8kkjn2t6wpdmysk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKcytv6pq9cmagdcsw1379pwxa2` FOREIGN KEY (`service_makeup_id`) REFERENCES `service_makeup` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `appointment`
--

LOCK TABLES `appointment` WRITE;
/*!40000 ALTER TABLE `appointment` DISABLE KEYS */;
/*!40000 ALTER TABLE `appointment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cart`
--

DROP TABLE IF EXISTS `cart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cart` (
  `create_date` date NOT NULL,
  `id` int NOT NULL AUTO_INCREMENT,
  `total_price` double NOT NULL,
  `total_quantity` int NOT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_9emlp6m95v5er2bcqkjsw48he` (`user_id`),
  CONSTRAINT `FKl70asp4l4w0jmbm1tqyofho4o` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart`
--

LOCK TABLES `cart` WRITE;
/*!40000 ALTER TABLE `cart` DISABLE KEYS */;
/*!40000 ALTER TABLE `cart` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cart_item`
--

DROP TABLE IF EXISTS `cart_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cart_item` (
  `cart_id` int NOT NULL,
  `id` int NOT NULL AUTO_INCREMENT,
  `price` double NOT NULL,
  `product_id` int NOT NULL,
  `quantity` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_r5b99rvdnupujm2h7hh2bh6m7` (`product_id`),
  KEY `FK1uobyhgl1wvgt1jpccia8xxs3` (`cart_id`),
  CONSTRAINT `FK1uobyhgl1wvgt1jpccia8xxs3` FOREIGN KEY (`cart_id`) REFERENCES `cart` (`id`),
  CONSTRAINT `FKjcyd5wv4igqnw413rgxbfu4nv` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart_item`
--

LOCK TABLES `cart_item` WRITE;
/*!40000 ALTER TABLE `cart_item` DISABLE KEYS */;
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
  `rating` int DEFAULT NULL,
  `review_date` date NOT NULL,
  `service_makeup_id` int NOT NULL,
  `user_id` int NOT NULL,
  `comment` varchar(250) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKrjwo69lirkpifjko6r5x9yutm` (`service_makeup_id`),
  KEY `FK6udpx3ddji8j31h2froqoay12` (`user_id`),
  CONSTRAINT `FK6udpx3ddji8j31h2froqoay12` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKrjwo69lirkpifjko6r5x9yutm` FOREIGN KEY (`service_makeup_id`) REFERENCES `service_makeup` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `feed_back`
--

LOCK TABLES `feed_back` WRITE;
/*!40000 ALTER TABLE `feed_back` DISABLE KEYS */;
INSERT INTO `feed_back` VALUES (1,4,'2024-11-15',1,1,'Dịch vụ rất tốt'),(2,3,'2024-11-15',1,1,'Dịch vụ rất tốt'),(3,5,'2024-11-15',1,1,'Dịch vụ rất tốt'),(4,5,'2024-11-15',1,1,'Dịch vụ cực tốt'),(5,2,'2024-11-15',3,1,'Dịch vụ tệ');
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
  `order_id` int NOT NULL,
  `price` double NOT NULL,
  `product_id` int NOT NULL,
  `quantity` int NOT NULL,
  `use_date` date NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_qd4o375qvtt45ierlsgsouhlc` (`product_id`),
  KEY `FKt4dc2r9nbvbujrljv3e23iibt` (`order_id`),
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
  `payment_id` int NOT NULL,
  `status` bit(1) NOT NULL,
  `total_price` double NOT NULL,
  `total_quantity` int NOT NULL,
  `user_id` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_haujdjk1ohmeixjhnhslchrp1` (`payment_id`),
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
  PRIMARY KEY (`id`),
  KEY `FKd9gfnsjgfwjtaxl57udrbocsp` (`sub_category_id`),
  CONSTRAINT `FKd9gfnsjgfwjtaxl57udrbocsp` FOREIGN KEY (`sub_category_id`) REFERENCES `sub_category` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=66 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (1,300,_binary '',19,'X','a','elfwoment1.jpg,elfwomen2.jpg,elfwomen3.jpg,elfwomen4.jpg,elfwoment5.jpg','Christmas Elf Women Girls Family Costumes'),(2,400,_binary '',19,'X','a','rechild1.jpg,redchild2.jpg','Red Children Christmas Costumes with Cute Hat'),(3,400,_binary '',19,'X','a','greenchild1.jpg,greenchild2.png,greenchild3.jpg','Green Child Christmas Costume with Colorful Snowball'),(4,200,_binary '',19,'X','a','christmas1.jpg,christmas2.jpg,christmas3.jpg','Christmas costumes women party dress with white gloves'),(5,400,_binary '',19,'X','a','santa1.png,santa3.jpg,santa2.png,santa4.jpg,santa5.jpg','Christmas Santa Claus Costume With The Beard So Smart'),(6,500,_binary '',19,'X','a','elfmixed1.jpg,elfmixed2.jpg,elfmixed3.jpg,elfmixed4.jpg,elfmixed5.jpg,elfmixed6.jpg,elfmixed7.jpg,elfmixed8.jpg,elfmixed9.jpg,elfmixed10.jpg,elfmixed11.jpg','Christmas Elf Cosplay Dress Red Mixed Green Costume for Christmas Party'),(7,600,_binary '',19,'X','a','newchristmas1.jpg,newchristmas2.jpg,newchristmas3.jpg,newchristmas4.jpg,newchristmas5.jpg,newchristmas6.jpg,newchristmas7.jpg,newchristmas8.jpg,newchristmas9.jpg,newchristmas10.jpg,newchristmas11.jpg','The new Christmas dress party dress Christmas costumes performing long-sleeved dress'),(8,250,_binary '',19,'X','a','newstyle1.jpg,newstyle2.jpg','New Style Women Christmas Santa Claus Costumes'),(9,700,_binary '',19,'X','a','shawl1.jpg,shawl2.jpg,shawl3.jpg,shawl4.jpg,shawl5.jpg,shawl6.jpg,shawl7.jpg,shawl8.jpg,shawl9.jpg,shawl10.jpg,shawl11.jpg','Latest shawl Christmas dress party dress Christmas costume splited detachable skirt'),(10,500,_binary '',18,'X','a','penguin.jpg','Deluxe Penguin Halloween Animal Costume Cute cartoon maomao Uniform'),(11,300,_binary '',18,'X','a','witch1.jpg,witch2.jpg,witch3.jpg,witch4.jpg,witch5.jpg','Halloween Witch Long Dress Cosplay Costume'),(12,400,_binary '',18,'X','a','partywitch1.jpg,partywitch2.jpg,partywitch3.jpg,partywitch4.jpg','Party Witch Costume Halloween Costume Masquerade Performance Costume'),(13,200,_binary '',18,'X','a','vocaloid1.jpg,vocaloid2.jpg,vocaloid3.jpg,vocaloid4.jpg,vocaloid5.jpg,vocaloid6.jpg','Vocaloid Hatsune Miku Rascal Cosplay Meiko Cosplay Costume'),(14,300,_binary '',18,'X','a','sexywitch1.jpg,sexywitch2.jpg,sexywitch3.jpg,sexywitch4.jpg','Halloween Cute Witch Cosplay Costume'),(15,200,_binary '',18,'X','a','halloweenwitch1.jpg,halloweenwitch2.jpg,halloweenwitch3.jpg,halloweenwitch4.jpg,halloweenwitch5.jpg,halloweenwitch6.jpg','Halloween Witch Cosplay Costume'),(16,200,_binary '',18,'X','a','vintage1.jpg,vintage2.jpg,vintage3.jpg,vintage4.jpg,vintage5.jpg,vintage6.jpg,vintage7.jpg,vintage8.jpg,vintage9.jpg,vintage10.jpg','Halloween Vintage Dress Two Colors Cosplay Costume'),(17,200,_binary '',18,'X','a','addams1.jpg,addams2.jpg,addams3.jpg,addams4.jpg,addams5.jpg,addams6.jpg','Addams Family Morticia Halloween Cosplay Costume'),(18,300,_binary '',18,'X','a','cloaks1.jpg,cloaks2.jpg,cloaks3.jpg,cloaks4.jpg,cloaks5.jpg,cloaks6.jpg','Halloween Cloaks Witch Cosplay Costumes Fancy Balls Costumes'),(19,200,_binary '',18,'X','a','vampires1.jpg,vampires2.jpg,vampires3.jpg,vampires4.jpg,vampires5.jpg,vampires6.jpg,vampires7.jpg,vampires8.jpg,vampires9.jpg','Halloween Witch Dress Vampires Cosplay Costume'),(20,300,_binary '',18,'X','a','marryshaw1.jpg,marryshaw2.jpg,marryshaw3.jpg,marryshaw4.jpg,marryshaw5.jpg,marryshaw6.jpg,marryshaw7.jpg,marryshaw8.jpg','Movie Mary Shaw Halloween Dress Cosplay Costume'),(21,300,_binary '',16,'X','a','furina1.jpg, furina2.jpg, furina3.jpg, furina4.jpg, furina5.jpg, furina6.jpg, furina7.jpg, furina8.jpg','The Hydro Archon Dark Furina de Fontaine Focalors Cosplay Costume'),(22,499,_binary '',16,'X','a','ganyu1.jpg, ganyu2.jpg, ganyu3.jpg, ganyu4.jpg, ganyu5.jpg, ganyu6.jpg, ganyu7.jpg, ganyu8.jpg, ganyu9.jpg, ganyu10.jpg, ganyu11.jpg, ganyu12.jpg','Lantern Rite Twilight Blossom Ganyu Cosplay Costume'),(23,111,_binary '',16,'X','a','maivuika1.jpg, maivuika2.jpg, maivuika3.jpg, maivuika4.jpg, maivuika5.jpg, maivuika6.jpg, maivuika7.jpg, maivuika8.jpg, maivuika9.jpg, maivuika10.jpg, maivuika11.jpg, maivuika12.jpg, maivuika13.jpg, maivuika14.jpg, maivuika15.jpg','Mavuika Cosplay Costume Halloween Costume — Standard Edition'),(24,1112,_binary '',16,'X','a','raiden1.jpg, raiden2.jpg, raiden3.jpg, raiden4.jpg, raiden5.jpg, raiden6.jpg, raiden7.jpg, raiden8.jpg, raiden9.jpg, raiden10.jpg','Baal Raiden Shogun Magatsu Mitake Narukami no Mikoto Purple Long Cosplay Wigs'),(25,123,_binary '',16,'X','a','shenhe1.jpg, shenhe2.jpg, shenhe3.jpg, shenhe4.jpg, shenhe5.jpg, shenhe6.jpg, shenhe7.jpg, shenhe8.jpg, shenhe9.jpg, shenhe10.jpg, shenhe11.jpg, shenhe12.jpg, shenhe13.jpg','Lantern Rite Frostflower Dew Shenhe Cosplay Costume'),(26,1123,_binary '',13,'X','a','aponia1.jpg, aponia2.jpg, aponia3.jpg, aponia4.jpg, aponia5.jpg','Honkai Impact 3rd Aponia Blonde Cosplay Wigs'),(27,123,_binary '',13,'X','a','elysia1.jpg, elysia2.jpg, elysia3.jpg, elysia4.jpg, elysia5.jpg, elysia6.jpg, elysia7.jpg, elysia8.jpg, elysia9.jpg','Honkai Impact 3rd Elysia Cosplay Costume'),(28,800,_binary '',13,'X','a','hkasuka01.jpg, hkasuka02.jpg, hkasuka03.jpg, hkasuka04.jpg, hkasuka05.jpg, hkasuka06.jpg, hkasuka07.jpg, hkasuka08.jpg, hkasuka09.jpg, hkasuka10.jpg','EVA Asuka Honkai Impact 3rd Collaboration Cosplay Costume'),(29,200,_binary '',15,'X','A','firefly1.jpg, firefly2.jpg, firefly3.jpg, firefly4.jpg, firefly5.jpg, firefly6.jpg, firefly7.jpg, firefly8.jpg, firefly9.jpg, firefly10.jpg','Honkai Star Rail Firefly Cosplay Costume'),(30,100,_binary '',15,'X','A','kafka1.png, kafka2.png, kafka3.png, kafka4.png','Honkai Star Rail Kafka Cosplay Shoes'),(31,800,_binary '',15,'X','A','sparkle1.jpg, sparkle2.jpg, sparkle3.jpg, sparkle4.jpg, sparkle5.jpg, sparkle6.jpg, sparkle7.jpg, sparkle8.jpg, sparkle9.jpg, sparkle10.jpg','Sparkle Honkai Star Rail Cosplay Costume'),(32,700,_binary '',15,'X','A','yunli1.jpg, yunli2.jpg, yunli3.jpg, yunli4.jpg, yunli5.jpg, yunli6.jpg, yunli7.jpg, yunli8.jpg, yunli9.jpg, yunli10.jpg, yunli11.jpg, yunli12.jpg, yunli13.jpg, yunli14.jpg','Honkai Star Rail Yunli Cosplay Costume'),(33,222,_binary '',14,'X','A','ahri1.jpg, ahri2.jpg, ahri3.jpg, ahri4.jpg, ahri5.jpg, ahri6.jpg, ahri7.jpg, ahri8.jpg, ahri9.jpg, ahri10.jpg, ahri11.jpg','LOL Wild Rift The Nine-Tailed Fox Ahri Cosplay Costume'),(34,300,_binary '',14,'X','A','aitlyn1.jpg, caitlyn2.jpg, caitlyn3.jpg, caitlyn4.jpg, caitlyn5.jpg, caitlyn6.jpg, caitlyn7.jpg, caitlyn8.jpg, caitlyn9.jpg, caitlyn10.jpg, caitlyn11.jpg, caitlyn12.jpg','LOL Heartache & Heartthrob Caitlyn Cosplay Costume Halloween Costume'),(35,111,_binary '',14,'X','A','kaisa1.jpg, kaisa2.jpg, kaisa3.jpg, kaisa4.jpg, kaisa5.jpg, kaisa6.jpg, kaisa7.jpg, kaisa8.jpg, kaisa9.jpg, kaisa10.jpg, kaisa11.jpg','LOL KDA ALL OUT Kaisa Cosplay Costume Halloween Costume'),(36,252,_binary '',14,'X','A','silco1.jpg, silco2.jpg, silco3.jpg, silco4.jpg, silco5.jpg, silco6.jpg','LOL KDA ALL OUT Kaisa Cosplay Costume Halloween Costume'),(37,123,_binary '',8,'x','a','joker1.jpg, joker2.jpg, joker3.jpg, joker4.jpg, joker5.jpg, joker6.jpg, joker7.jpg, joker8.jpg','Joker Purple Cosplay Costumes Coat'),(38,124,_binary '',9,'x','a','spiderman1.jpg, spiderman2.jpg, spiderman3.jpg, spiderman4.jpg','Spider-Man Black Cosplay Costume'),(39,1244,_binary '',9,'x','a','miles1.jpg, miles2.jpg, miles3.jpg, miles4.jpg, miles5.jpg, miles6.jpg','Spider-Man Miles Morales Jumpsuit Cosplay Costume'),(40,1231,_binary '',9,'x','a','miguel1.jpg, miguel2.jpg, miguel3.jpg, miguel4.jpg, miguel5.jpg, miguel6.jpg, miguel7.jpg','Spider-Man 2099 Miguel O\'Hara Cosplay Costume Men Halloween Costume'),(41,121,_binary '',9,'x','a','stacy1.jpg, stacy2.jpg, stacy3.jpg, stacy4.jpg, stacy5.jpg, stacy6.jpg, stacy7.jpg, stacy8.jpg, stacy9.jpg, stacy10.jpg, stacy11.jpg','Spider-Man Gwen Stacy Cosplay Costume'),(42,500,_binary '',1,'x','a','whis-purple1.jpg, whis-purple2.jpg, whis-purple3.jpg, whis-purple4.jpg, whis-purple5.jpg, whis-purple6.jpg, whis-purple7.jpg, whis-purple8.jpg, whis-purple9.jpg, whis-purple10.jpg, whis-purple11.jpg','Dragon Ball Super Whis Purple Anime Cosplay Costumes'),(43,200,_binary '',1,'x','a','vegeta18.jpg, vegeta19.jpg, vegeta20.jpg, vegeta21.jpg, vegeta22.jpg, vegeta23.jpg, vegeta24.jpg, vegeta25.jpg','Dragon Ball Vegeta Cosplay Shoes Boots Custom-Made'),(44,1000,_binary '',1,'x','a','vegeta1.jpg, vegeta2.jpg, vegeta3.jpg, vegeta4.jpg, vegeta5.jpg, vegeta6.jpg, vegeta7.jpg, vegeta8.jpg, vegeta9.jpg, vegeta10.jpg, vegeta11.jpg, vegeta12.jpg, vegeta13.jpg, vegeta14.jpg, vegeta15.jpg, vegeta16.jpg, vegeta17.jpg','Dragon Ball Vegeta 1st Version Cosplay Costume'),(45,1241,_binary '',1,'x','a','picolo1.jpg, picolo2.jpg, picolo3.jpg, picolo4.jpg, picolo5.jpg, picolo6.jpg, picolo7.jpg, picolo8.jpg, picolo9.jpg','Dragon Ball For Piccolo Uniforms Cosplay Costume Purple'),(46,1241,_binary '',1,'x','a','no18-1.jpg, no18-2.jpg, no18-3.jpg, no18-4.jpg, no18-5.jpg, no18-6.jpg','Dragon Ball Z Cyborg 18Gou Cosplay Boots Shoes'),(47,564,_binary '',1,'x','a','no17-1.jpg, no17-2.jpg, no17-3.jpg, no17-4.jpg, no17-5.jpg, no17-6.jpg, no17-7.jpg, no17-8.jpg, no17-9.jpg, no17-10.jpg, no17-11.jpg, no17-12.jpg','Dragon Ball Z Android No.17 Cosplay Costume'),(48,142,_binary '',1,'x','a','master-roshi1.jpg, master-roshi2.jpg, master-roshi3.jpg, master-roshi4.jpg, master-roshi5.jpg, master-roshi6.jpg, master-roshi7.jpg, master-roshi8.jpg, master-roshi9.jpg, master-roshi10.jpg','Dragon Ball Master Roshi 1st Version Cosplay Costume'),(49,14124,_binary '',1,'x','a','goku1.jpg, goku2.jpg, goku3.jpg, goku4.jpg, goku5.jpg, goku6.jpg, goku7.jpg','Dragon Ball Monkey King Son GoKu Kakarot Cosplay Shoes Boots Custom-Made'),(50,1242,_binary '',1,'x','a','gohan1.jpg, gohan2.jpg, gohan3.jpg, gohan4.jpg, gohan5.jpg, gohan6.jpg, gohan7.jpg, gohan8.jpg','Dragon Ball Son Gohan Uniforms Cosplay Costume'),(51,12452,_binary '',1,'x','a','chichi2.jpg, chichi3.jpg, chichi4.jpg, chichi5.jpg, chichi6.jpg, chichi7.jpg, chichi8.jpg, chichi9.jpg','Dragon Ball Chi Chi Cosplay Costume'),(52,121,_binary '',1,'x','a','bulma1.jpg, bulma2.jpg, bulma3.jpg, bulma4.jpg, bulma5.jpg, bulma6.jpg, bulma7.jpg, bulma8.jpg, bulma9.jpg','Dragon Ball Z Bulma Anime Cosplay Costumes'),(53,977,_binary '',6,'x','a','mai1.jpg, mai2.jpg, mai3.jpg, mai4.jpg, mai5.jpg, mai6.jpg, mai7.jpg, mai8.jpg, mai9.jpg, mai10.jpg, mai11.jpg, mai12.jpg, mai13.jpg, mai14.jpg, mai15.jpg, mai16.jpg, mai17.jpg, mai18.jpg','Jujutsu Kaisen Zenin Mai Cosplay Costume'),(54,1154,_binary '',6,'x','a','yuta1.jpg, yuta2.jpg, yuta3.jpg, yuta4.jpg, yuta5.jpg, yuta6.jpg, yuta7.jpg, yuta8.jpg, yuta9.jpg, yuta10.jpg, yuta11.jpg, yuta12.jpg, yuta13.jpg','Jujutsu Kaisen Sorcery Fight Yuta Okkotsu Cosplay Costume'),(55,13123,_binary '',6,'x','a','itadori1.jpg, itadori2.jpg, itadori3.jpg, itadori4.jpg, itadori5.jpg, itadori6.jpg, itadori7.jpg, itadori8.jpg, itadori9.jpg, itadori10.jpg, itadori11.jpg, itadori12.jpg','Anime Jujutsu Kaisen Yuji Itadori Pink mixed Black Men Cosplay Wigs'),(56,124,_binary '',6,'x','a','fushiguro1.jpg, fushiguro2.jpg, fushiguro3.jpg, fushiguro4.jpg, fushiguro5.jpg, fushiguro6.jpg, fushiguro7.jpg, fushiguro8.jpg, fushiguro9.jpg, fushiguro10.jpg','Jujutsu Kaisen Toji Fushiguro Cosplay Costume'),(57,563,_binary '',6,'x','a','geto1.jpg, geto2.jpg, geto3.jpg, geto4.jpg, geto5.jpg, geto6.jpg, geto7.jpg, geto8.jpg, geto9.jpg, geto10.jpg, geto11.jpg, geto12.jpg, geto13.jpg, geto14.jpg','Jujutsu Kaisen Sorcery Fight Suguru Geto Uniform Cosplay Costume'),(58,535,_binary '',6,'x','a','gojo1.jpg, gojo2.jpg, gojo3.jpg, gojo4.jpg, gojo5.jpg, gojo6.jpg, gojo7.jpg, gojo8.jpg, gojo9.jpg, gojo10.jpg','Anime Jujutsu Kaisen Satoru Gojo Short Grey Men Cosplay Wigs'),(59,124,_binary '',6,'x','a','sukuna1.jpg, sukuna2.jpg, sukuna3.jpg, sukuna4.jpg, sukuna5.jpg, sukuna6.jpg, sukuna7.jpg, sukuna8.jpg, sukuna9.jpg, sukuna10.jpg, sukuna11.jpg, sukuna12.jpg, sukuna13.jpg, sukuna14.jpg, sukuna15.jpg, sukuna16.jpg, sukuna17.jpg, sukuna18.jpg','Jujutsu Kaisen Ryoumen Sukuna kimono Bathrobe Cosplay Costume'),(60,5365,_binary '',6,'x','a','momo2.jpg, momo3.jpg, momo4.jpg, momo5.jpg, momo6.jpg, momo7.jpg, momo8.jpg, momo9.jpg, momo10.jpg, momo11.jpg, momo12.jpg, momo13.jpg, momo14.jpg, momo15.jpg, momo16.jpg','Jujutsu Kaisen Nishimiya Momo Cosplay Costume'),(61,1414,_binary '',5,'x','a','terrible1.jpg, terrible2.jpg, terrible3.jpg','One Punch Man Terrible Tornado Cosplay Costumes Customized'),(62,975,_binary '',5,'x','a','speedofsound1.jpg, speedofsound2.jpg','One Punch Man Speed of Sound Sonic Uniform Cosplay Costume'),(63,9742,_binary '',5,'x','a','onepunchman1.jpg,onepunchman2.jpg,onepunchman3.jpg','One Punch Man Saitama Zipper Hoodie Sweater Cosplay Costumes'),(64,214,_binary '',5,'x','a','onepunchman4.jpg,onepunchman5.jpg,onepunchman6.jpg,onepunchman7.jpg','One Punch Man Saitama With Vacant Look Cosplay Costumes Hat'),(65,5385,_binary '',5,'x','a','heliish.jpg','One Punch Man Hellish Blizzard Cosplay Costume Custom Made');
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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `staff`
--

LOCK TABLES `staff` WRITE;
/*!40000 ALTER TABLE `staff` DISABLE KEYS */;
INSERT INTO `staff` VALUES (1,'0909582411','Ngọc Mây'),(2,'0242412345','Nguyễn Túy Vân'),(3,'0922112334','Trúc Mai'),(4,'0714424242','Ngọc Trinh'),(5,'0825241411','Trần Hà Linh');
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
  PRIMARY KEY (`id`),
  KEY `FKgwjnv828joeda2x15sk8h5d8k` (`sub_category_id`),
  CONSTRAINT `FKgwjnv828joeda2x15sk8h5d8k` FOREIGN KEY (`sub_category_id`) REFERENCES `category` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sub_category`
--

LOCK TABLES `sub_category` WRITE;
/*!40000 ALTER TABLE `sub_category` DISABLE KEYS */;
INSERT INTO `sub_category` VALUES (1,1,'DRAGON BALL'),(2,1,'ATTACK ON TITAN'),(3,1,'NARUTO'),(4,1,'ONE PEACE '),(5,1,'ONE PUNCH MAN'),(6,1,'JUJUTSU KAISEN'),(7,2,'RESIDENT EVIL'),(8,2,'SUPERMAN'),(9,2,'SPIDER-MAN '),(10,2,'AQUAMAN'),(11,2,'IT'),(12,2,'CAPTAIN MAVEL'),(13,3,'FATE GRAND ORDER'),(14,3,'LEAGUE OF LEGION'),(15,3,'HONKAI STAR RAIL'),(16,3,'GENSHIN IMPACT '),(17,3,'VANLORANT'),(18,4,'HALLOWEEN'),(19,4,'MERRY CHRISTMAS');
/*!40000 ALTER TABLE `sub_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `account_id` int NOT NULL,
  `id` int NOT NULL AUTO_INCREMENT,
  `phone` varchar(20) NOT NULL,
  `email` varchar(40) DEFAULT NULL,
  `fullname` varchar(40) DEFAULT NULL,
  `address` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_nrrhhb0bsexvi8ch6wnon9uog` (`account_id`),
  CONSTRAINT `FKc3b4xfbq6rbkkrddsdum8t5f0` FOREIGN KEY (`account_id`) REFERENCES `account` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,1,'0703200286','huynhminhquan07072002@gmail.com','Túy Vân','83/1b Tây Lân - Ba Diem - Hoc Mon');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'makeup'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-11-15 23:20:27
