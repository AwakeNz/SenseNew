CREATE TABLE IF NOT EXISTS `character_talents` (
  `charId` INT UNSIGNED NOT NULL,
  `talent_id` INT NOT NULL,
  `talent_level` TINYINT(1) NOT NULL DEFAULT 1,
  `class_index` TINYINT(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`charId`, `talent_id`, `class_index`),
  FOREIGN KEY (`charId`) REFERENCES `characters`(`charId`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
