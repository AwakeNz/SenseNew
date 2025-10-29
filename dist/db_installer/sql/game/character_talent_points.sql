CREATE TABLE IF NOT EXISTS `character_talent_points` (
  `charId` INT UNSIGNED NOT NULL,
  `available_points` INT NOT NULL DEFAULT 0,
  `spent_power` TINYINT(2) NOT NULL DEFAULT 0,
  `spent_mastery` TINYINT(2) NOT NULL DEFAULT 0,
  `spent_protection` TINYINT(2) NOT NULL DEFAULT 0,
  `pending_points` INT NOT NULL DEFAULT 0,
  PRIMARY KEY (`charId`),
  FOREIGN KEY (`charId`) REFERENCES `characters`(`charId`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
