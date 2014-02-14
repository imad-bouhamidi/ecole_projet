CREATE TABLE IF NOT EXISTS `etudiant` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `nom` varchar(100) COLLATE utf8_swedish_ci DEFAULT NULL,
  `prenom` varchar(100) COLLATE utf8_swedish_ci DEFAULT NULL,
  `date_naissance` date DEFAULT NULL,
  `option` varchar(200) COLLATE utf8_swedish_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_swedish_ci ;