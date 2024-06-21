-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: localhost    Database: projetoapplestore
-- ------------------------------------------------------
-- Server version	8.0.35

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
-- Table structure for table `carrinho`
--

DROP TABLE IF EXISTS `carrinho`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `carrinho` (
  `id` int NOT NULL AUTO_INCREMENT,
  `total` double NOT NULL,
  `usuario_id` int NOT NULL,
  `desconto` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK8jwo8e9vk1gdcw8ak7if31ahc` (`usuario_id`),
  CONSTRAINT `FK8jwo8e9vk1gdcw8ak7if31ahc` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `carrinho`
--

LOCK TABLES `carrinho` WRITE;
/*!40000 ALTER TABLE `carrinho` DISABLE KEYS */;
/*!40000 ALTER TABLE `carrinho` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `carrinho_itemcarrinho`
--

DROP TABLE IF EXISTS `carrinho_itemcarrinho`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `carrinho_itemcarrinho` (
  `Carrinho_id` int NOT NULL,
  `itens_id` int NOT NULL,
  UNIQUE KEY `UK_1gdao52jldbe77hp4u2lnj91x` (`itens_id`),
  KEY `FKbyu7jgpw0s5vybc8ucwo674xw` (`Carrinho_id`),
  CONSTRAINT `FK3s7dxcah62go6rgs2n4vfxv3o` FOREIGN KEY (`itens_id`) REFERENCES `itemcarrinho` (`id`),
  CONSTRAINT `FKbyu7jgpw0s5vybc8ucwo674xw` FOREIGN KEY (`Carrinho_id`) REFERENCES `carrinho` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `carrinho_itemcarrinho`
--

LOCK TABLES `carrinho_itemcarrinho` WRITE;
/*!40000 ALTER TABLE `carrinho_itemcarrinho` DISABLE KEYS */;
/*!40000 ALTER TABLE `carrinho_itemcarrinho` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `forma_pagamento`
--

DROP TABLE IF EXISTS `forma_pagamento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `forma_pagamento` (
  `id` int NOT NULL AUTO_INCREMENT,
  `descricao` varchar(255) DEFAULT NULL,
  `nome` varchar(255) DEFAULT NULL,
  `taxa` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `forma_pagamento`
--

LOCK TABLES `forma_pagamento` WRITE;
/*!40000 ALTER TABLE `forma_pagamento` DISABLE KEYS */;
INSERT INTO `forma_pagamento` VALUES (1,'Pagamento instantâneo\'','PIX',0),(2,'Pagamento com acréscimo de 1,99%','Cartao de Credito',1.99);
/*!40000 ALTER TABLE `forma_pagamento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `itemcarrinho`
--

DROP TABLE IF EXISTS `itemcarrinho`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `itemcarrinho` (
  `id` int NOT NULL AUTO_INCREMENT,
  `desconto` double NOT NULL,
  `precounidade` double DEFAULT NULL,
  `quantidade` int DEFAULT NULL,
  `produto_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKtll1u9xahkug9yk2woavlviwt` (`produto_id`),
  CONSTRAINT `FKtll1u9xahkug9yk2woavlviwt` FOREIGN KEY (`produto_id`) REFERENCES `produtos` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `itemcarrinho`
--

LOCK TABLES `itemcarrinho` WRITE;
/*!40000 ALTER TABLE `itemcarrinho` DISABLE KEYS */;
/*!40000 ALTER TABLE `itemcarrinho` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pedidos`
--

DROP TABLE IF EXISTS `pedidos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pedidos` (
  `id` int NOT NULL AUTO_INCREMENT,
  `data` datetime(6) NOT NULL,
  `status` varchar(255) NOT NULL,
  `total` double NOT NULL,
  `carrinho_id` int NOT NULL,
  `forma_pagamento_id` int NOT NULL,
  `usuario_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKde5j44gf297nfml73riwqi0rm` (`carrinho_id`),
  KEY `FKp73jc2340jn567mp02e6ga8e8` (`forma_pagamento_id`),
  KEY `FKonf32qpq8pb2950dfgiyevy1h` (`usuario_id`),
  CONSTRAINT `FKde5j44gf297nfml73riwqi0rm` FOREIGN KEY (`carrinho_id`) REFERENCES `carrinho` (`id`),
  CONSTRAINT `FKonf32qpq8pb2950dfgiyevy1h` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`),
  CONSTRAINT `FKp73jc2340jn567mp02e6ga8e8` FOREIGN KEY (`forma_pagamento_id`) REFERENCES `forma_pagamento` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pedidos`
--

LOCK TABLES `pedidos` WRITE;
/*!40000 ALTER TABLE `pedidos` DISABLE KEYS */;
/*!40000 ALTER TABLE `pedidos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `produtos`
--

DROP TABLE IF EXISTS `produtos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `produtos` (
  `id` int NOT NULL AUTO_INCREMENT,
  `amarzenamento` int DEFAULT NULL,
  `cor` varchar(255) DEFAULT NULL,
  `desconto` double DEFAULT NULL,
  `descricao` varchar(255) DEFAULT NULL,
  `estoque` int DEFAULT NULL,
  `modelo` varchar(255) DEFAULT NULL,
  `preco` double DEFAULT NULL,
  `caminho_imagem` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `produtos`
--

LOCK TABLES `produtos` WRITE;
/*!40000 ALTER TABLE `produtos` DISABLE KEYS */;
INSERT INTO `produtos` VALUES (2,256,'Branco',NULL,'Otimo desempenho',10,'Iphone 11 Pro Max',3050,'C:\\Users\\gui\\eclipse\\jee-2023-09\\eclipse\\ProjetoAppleStore\\src\\main\\resources\\img\\iphone11promax.jpeg'),(3,128,'Branco',NULL,'otimo desempenho',12,'Iphone 11',2799,'C:\\Users\\gui\\eclipse\\jee-2023-09\\eclipse\\ProjetoAppleStore\\src\\main\\resources\\img\\iphone11.png'),(10,64,'Preto',0,'iPhone 11 com 64GB de armazenamento',50,'iPhone 11',2999,'C:\\Users\\gui\\eclipse\\jee-2023-09\\eclipse\\ProjetoAppleStore\\src\\main\\resources\\img\\iphone11.png'),(11,64,'Preto',0,'iPhone 12 com 64GB de armazenamento',50,'iPhone 12',3999,'C:\\Users\\gui\\eclipse\\jee-2023-09\\eclipse\\ProjetoAppleStore\\src\\main\\resources\\img\\iphone12.png'),(12,128,'Branco',0,'iPhone 12 com 128GB de armazenamento',40,'iPhone 12',4499,'C:\\Users\\gui\\eclipse\\jee-2023-09\\eclipse\\ProjetoAppleStore\\src\\main\\resources\\img\\iphone12.png'),(13,128,'Preto',0,'iPhone 13 com 128GB de armazenamento',50,'iPhone 13',5299,'C:\\Users\\gui\\eclipse\\jee-2023-09\\eclipse\\ProjetoAppleStore\\src\\main\\resources\\img\\iphone13.png'),(14,256,'Branco',0,'iPhone 13 com 256GB de armazenamento',40,'iPhone 13',5799,'C:\\Users\\gui\\eclipse\\jee-2023-09\\eclipse\\ProjetoAppleStore\\src\\main\\resources\\img\\iphone13.png'),(15,128,'Preto',0,'iPhone 14 com 128GB de armazenamento',50,'iPhone 14',6299,'C:\\Users\\gui\\eclipse\\jee-2023-09\\eclipse\\ProjetoAppleStore\\src\\main\\resources\\img\\iphone14.jpeg'),(16,256,'Branco',0,'iPhone 14 com 256GB de armazenamento',40,'iPhone 14',6799,'C:\\Users\\gui\\eclipse\\jee-2023-09\\eclipse\\ProjetoAppleStore\\src\\main\\resources\\img\\iphone14.jpeg'),(17,128,'Preto',0,'iPhone 15 com 128GB de armazenamento',50,'iPhone 15 Pro Max',7999,'C:\\Users\\gui\\eclipse\\jee-2023-09\\eclipse\\ProjetoAppleStore\\src\\main\\resources\\img\\iphone15promax.png'),(18,256,'Branco',0,'iPhone 15 com 256GB de armazenamento',40,'iPhone 15 Pro',7199,'C:\\Users\\gui\\eclipse\\jee-2023-09\\eclipse\\ProjetoAppleStore\\src\\main\\resources\\img\\iphone15.png');
/*!40000 ALTER TABLE `produtos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario` (
  `id` int NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `nome` varchar(255) NOT NULL,
  `senha` varchar(255) NOT NULL,
  `tipoUsuario` varchar(255) NOT NULL,
  `carrinho_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_5171l57faosmj8myawaucatdw` (`email`),
  UNIQUE KEY `UK_gpsimv2d8oxg70f4u7uo2en6w` (`carrinho_id`),
  CONSTRAINT `FK8aqc2fihpkw08yob6g2tikqoa` FOREIGN KEY (`carrinho_id`) REFERENCES `carrinho` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (1,'testeadmin@gmail.com','admin','1234','admin',NULL),(2,'testecliente@gmail.com','cliente','1234','cliente',NULL);
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-06-20 19:57:32
