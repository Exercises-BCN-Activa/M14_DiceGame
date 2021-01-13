-- -----------------------------------------------------
-- Schema diceGame
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `diceGame` ;

-- -----------------------------------------------------
-- Schema diceGame
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `diceGame` DEFAULT CHARACTER SET utf8 ;
USE `diceGame` ;

-- -----------------------------------------------------
-- Table `diceGame`.`player`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `diceGame`.`player` ;

CREATE TABLE IF NOT EXISTS `diceGame`.`player` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(255) NOT NULL,
  `first_name` VARCHAR(255) NOT NULL,
  `last_name` VARCHAR(255) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  `registration` DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(),
  `type` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`));


-- -----------------------------------------------------
-- Table `diceGame`.`dice`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `diceGame`.`dice` ;

CREATE TABLE IF NOT EXISTS `diceGame`.`dice` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `registration` DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(),
  `value1` INT NOT NULL,
  `value2` INT NOT NULL,
  `player_id` BIGINT NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`player_id`)
    REFERENCES `diceGame`.`player` (`id`));

