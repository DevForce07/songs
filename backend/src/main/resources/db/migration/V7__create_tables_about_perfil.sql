
CREATE TABLE IF NOT EXISTS `perfil` (
  `id` int NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(80) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
   `document` varchar(60) DEFAULT NULL,
    type_perfil varchar(5) DEFAULT NULL,
   `birth_date` DATE DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;