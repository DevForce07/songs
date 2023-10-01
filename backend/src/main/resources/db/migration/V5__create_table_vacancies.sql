
CREATE TABLE IF NOT EXISTS `vacancies` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_ong` int NOT NULL,
  `title` varchar(50) DEFAULT NULL,
  `description` LONGTEXT DEFAULT NULL,
  `qtdVacacies` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (id_ong) REFERENCES ongs(id)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;