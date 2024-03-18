-- MySQL Script generated by MySQL Workbench
-- Mon Mar 18 22:48:44 2024
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET
@OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET
@OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET
@OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema joinu
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema joinu
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `joinu` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE
`joinu` ;

-- -----------------------------------------------------
-- Table `joinu`.`calendars`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `joinu`.`calendars`
(
    `id`
    BIGINT
    NOT
    NULL
    AUTO_INCREMENT,
    `author`
    VARCHAR
(
    255
) NULL DEFAULT NULL,
    `category` ENUM
(
    'DAILY',
    'SCHOOL',
    'SPORT',
    'STUDY'
) NULL DEFAULT NULL,
    `color` VARCHAR
(
    255
) NULL DEFAULT NULL,
    `description` VARCHAR
(
    255
) NULL DEFAULT NULL,
    `name` VARCHAR
(
    255
) NULL DEFAULT NULL,
    PRIMARY KEY
(
    `id`
))
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `joinu`.`calendar_event`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `joinu`.`calendar_event`
(
    `id`
    BIGINT
    NOT
    NULL
    AUTO_INCREMENT,
    `description`
    VARCHAR
(
    255
) NULL DEFAULT NULL,
    `end` DATETIME
(
    6
) NULL DEFAULT NULL,
    `start` DATETIME
(
    6
) NULL DEFAULT NULL,
    `title` VARCHAR
(
    100
) NOT NULL,
    `calendars_id` BIGINT NULL DEFAULT NULL,
    PRIMARY KEY
(
    `id`
),
    INDEX `FK4vin5pojcpx0qfpmp3dhkve8r`
(
    `calendars_id` ASC
) VISIBLE,
    CONSTRAINT `FK4vin5pojcpx0qfpmp3dhkve8r`
    FOREIGN KEY
(
    `calendars_id`
)
    REFERENCES `joinu`.`calendars`
(
    `id`
))
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `joinu`.`event`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `joinu`.`event`
(
    `id`
    BIGINT
    NOT
    NULL
    AUTO_INCREMENT,
    `all_day`
    BIT
(
    1
) NULL DEFAULT NULL,
    `color` VARCHAR
(
    30
) NULL DEFAULT NULL,
    `deletable` BIT
(
    1
) NULL DEFAULT NULL,
    `disabled` BIT
(
    1
) NULL DEFAULT NULL,
    `editable` BIT
(
    1
) NULL DEFAULT NULL,
    `end` DATETIME
(
    6
) NULL DEFAULT NULL,
    `start` DATETIME
(
    6
) NULL DEFAULT NULL,
    `title` VARCHAR
(
    100
) NOT NULL,
    PRIMARY KEY
(
    `id`
))
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `joinu`.`member`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `joinu`.`member`
(
    `id`
    BIGINT
    NOT
    NULL
    AUTO_INCREMENT,
    `avator`
    VARCHAR
(
    255
) NULL DEFAULT NULL,
    `birth_date` DATE NOT NULL,
    `email` VARCHAR
(
    30
) NOT NULL,
    `gender` ENUM
(
    'MAN',
    'WOMAN'
) NOT NULL,
    `login_id` VARCHAR
(
    30
) NOT NULL,
    `name` VARCHAR
(
    10
) NOT NULL,
    `password` VARCHAR
(
    100
) NOT NULL,
    PRIMARY KEY
(
    `id`
),
    UNIQUE INDEX `uk_member_login_id`
(
    `login_id` ASC
) VISIBLE)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `joinu`.`member_event`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `joinu`.`member_event`
(
    `id`
    BIGINT
    NOT
    NULL
    AUTO_INCREMENT,
    `event_id`
    BIGINT
    NULL
    DEFAULT
    NULL,
    `member_id`
    BIGINT
    NULL
    DEFAULT
    NULL,
    PRIMARY
    KEY
(
    `id`
),
    INDEX `FKibqfnixfm9mv4qhqwl731nmea`
(
    `event_id` ASC
) VISIBLE,
    INDEX `FKelkrq0cqnuvqxsdj7x1fm6jhf`
(
    `member_id` ASC
) VISIBLE,
    CONSTRAINT `FKelkrq0cqnuvqxsdj7x1fm6jhf`
    FOREIGN KEY
(
    `member_id`
)
    REFERENCES `joinu`.`member`
(
    `id`
),
    CONSTRAINT `FKibqfnixfm9mv4qhqwl731nmea`
    FOREIGN KEY
(
    `event_id`
)
    REFERENCES `joinu`.`event`
(
    `id`
))
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `joinu`.`member_role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `joinu`.`member_role`
(
    `id`
    BIGINT
    NOT
    NULL
    AUTO_INCREMENT,
    `role`
    ENUM
(
    'MEMBER'
) NOT NULL,
    `member_id` BIGINT NULL DEFAULT NULL,
    PRIMARY KEY
(
    `id`
),
    INDEX `fk_member_role_member_id`
(
    `member_id` ASC
) VISIBLE,
    CONSTRAINT `fk_member_role_member_id`
    FOREIGN KEY
(
    `member_id`
)
    REFERENCES `joinu`.`member`
(
    `id`
))
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `joinu`.`team`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `joinu`.`team`
(
    `id`
    BIGINT
    NOT
    NULL
    AUTO_INCREMENT,
    `name`
    VARCHAR
(
    100
) NOT NULL,
    PRIMARY KEY
(
    `id`
))
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `joinu`.`member_team`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `joinu`.`member_team`
(
    `id`
    BIGINT
    NOT
    NULL
    AUTO_INCREMENT,
    `avator`
    VARCHAR
(
    255
) NULL DEFAULT NULL,
    `color` VARCHAR
(
    255
) NULL DEFAULT NULL,
    `group_name` VARCHAR
(
    255
) NULL DEFAULT NULL,
    `member_name` VARCHAR
(
    255
) NULL DEFAULT NULL,
    `sub_name` VARCHAR
(
    255
) NULL DEFAULT NULL,
    `member_id` BIGINT NULL DEFAULT NULL,
    `team_id` BIGINT NULL DEFAULT NULL,
    PRIMARY KEY
(
    `id`
),
    INDEX `FKgqbjnbjtn5tycg5ih6r80wmr1`
(
    `member_id` ASC
) VISIBLE,
    INDEX `FKfly863tmgmm8wnj0u1sqgqu5u`
(
    `team_id` ASC
) VISIBLE,
    CONSTRAINT `FKfly863tmgmm8wnj0u1sqgqu5u`
    FOREIGN KEY
(
    `team_id`
)
    REFERENCES `joinu`.`team`
(
    `id`
),
    CONSTRAINT `FKgqbjnbjtn5tycg5ih6r80wmr1`
    FOREIGN KEY
(
    `member_id`
)
    REFERENCES `joinu`.`member`
(
    `id`
))
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `joinu`.`subscribe_calendars`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `joinu`.`subscribe_calendars`
(
    `id`
    BIGINT
    NOT
    NULL
    AUTO_INCREMENT,
    `alias`
    VARCHAR
(
    255
) NULL DEFAULT NULL,
    `color` VARCHAR
(
    255
) NULL DEFAULT NULL,
    `calendars_id` BIGINT NULL DEFAULT NULL,
    `member_id` BIGINT NULL DEFAULT NULL,
    PRIMARY KEY
(
    `id`
),
    INDEX `FKdao98lsnrdtm7ab555nptvfq4`
(
    `calendars_id` ASC
) VISIBLE,
    INDEX `FKfa2dmfxiufnqi44qvwoes1icd`
(
    `member_id` ASC
) VISIBLE,
    CONSTRAINT `FKdao98lsnrdtm7ab555nptvfq4`
    FOREIGN KEY
(
    `calendars_id`
)
    REFERENCES `joinu`.`calendars`
(
    `id`
),
    CONSTRAINT `FKfa2dmfxiufnqi44qvwoes1icd`
    FOREIGN KEY
(
    `member_id`
)
    REFERENCES `joinu`.`member`
(
    `id`
))
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `joinu`.`team_event`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `joinu`.`team_event`
(
    `id`
    BIGINT
    NOT
    NULL
    AUTO_INCREMENT,
    `end`
    DATETIME
(
    6
) NULL DEFAULT NULL,
    `start` DATETIME
(
    6
) NULL DEFAULT NULL,
    `title` VARCHAR
(
    100
) NOT NULL,
    `member_id` BIGINT NULL DEFAULT NULL,
    `team_id` BIGINT NULL DEFAULT NULL,
    PRIMARY KEY
(
    `id`
),
    INDEX `FKthf7e1r7oohmcd1s2jbdvjbq4`
(
    `member_id` ASC
) VISIBLE,
    INDEX `FK46rv5umqrb19j7xskqa90r4fp`
(
    `team_id` ASC
) VISIBLE,
    CONSTRAINT `FK46rv5umqrb19j7xskqa90r4fp`
    FOREIGN KEY
(
    `team_id`
)
    REFERENCES `joinu`.`team`
(
    `id`
),
    CONSTRAINT `FKthf7e1r7oohmcd1s2jbdvjbq4`
    FOREIGN KEY
(
    `member_id`
)
    REFERENCES `joinu`.`member`
(
    `id`
))
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


SET
SQL_MODE=@OLD_SQL_MODE;
SET
FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET
UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
