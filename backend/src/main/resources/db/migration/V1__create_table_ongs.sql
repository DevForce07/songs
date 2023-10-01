
CREATE TABLE IF NOT EXISTS `ongs` (
  `id` int NOT NULL AUTO_INCREMENT,
  `cnpj` varchar(20) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(80) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `phone_number` varchar(20)  DEFAULT NULL,
  `url_image` varchar(255) DEFAULT NULL,
    `description` LONGTEXT DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT UC_USER_SONG_CNPJ UNIQUE (cnpj)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;