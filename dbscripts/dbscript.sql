SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

CREATE SCHEMA IF NOT EXISTS `issueTrackerDb` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `issueTrackerDb` ;

-- -----------------------------------------------------
-- Table `issueTrackerDb`.`Organization`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `issueTrackerDb`.`Organization` (
  `org_id` INT NOT NULL ,
  `name` VARCHAR(45) NULL ,
  PRIMARY KEY (`org_id`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `issueTrackerDb`.`Project`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `issueTrackerDb`.`Project` (
  `project_id` INT NOT NULL ,
  `name` VARCHAR(45) NULL ,
  `owner` VARCHAR(45) NULL ,
  `Organization_org_id` INT NOT NULL ,
  PRIMARY KEY (`project_id`) ,
  INDEX `fk_Project_Organization1_idx` (`Organization_org_id` ASC) ,
  CONSTRAINT `fk_Project_Organization1`
    FOREIGN KEY (`Organization_org_id` )
    REFERENCES `issueTrackerDb`.`Organization` (`org_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `issueTrackerDb`.`Version`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `issueTrackerDb`.`Version` (
  `id` INT NOT NULL ,
  `version` VARCHAR(45) NULL ,
  `Project_project_id` INT NOT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_Version_Project1_idx` (`Project_project_id` ASC) ,
  CONSTRAINT `fk_Version_Project1`
    FOREIGN KEY (`Project_project_id` )
    REFERENCES `issueTrackerDb`.`Project` (`project_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `issueTrackerDb`.`Issue`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `issueTrackerDb`.`Issue` (
  `issue_id` INT NOT NULL ,
  `summary` VARCHAR(45) NULL ,
  `description` VARCHAR(45) NULL ,
  `type` VARCHAR(45) NULL ,
  `priority` VARCHAR(45) NULL ,
  `owner` VARCHAR(45) NULL ,
  `status` VARCHAR(45) NULL ,
  `asignee` VARCHAR(45) NULL ,
  `Version_id` INT NOT NULL ,
  `created_time` TIMESTAMP NULL ,
  `updated_time` TIMESTAMP NULL ,
  `severity` VARCHAR(45) NULL ,
  PRIMARY KEY (`issue_id`) ,
  INDEX `fk_Issue_Version1_idx` (`Version_id` ASC) ,
  CONSTRAINT `fk_Issue_Version1`
    FOREIGN KEY (`Version_id` )
    REFERENCES `issueTrackerDb`.`Version` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `issueTrackerDb`.`User`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `issueTrackerDb`.`User` (
  `user_id` INT NOT NULL ,
  `name` VARCHAR(45) NULL ,
  `Organization_org_id` INT NOT NULL ,
  `Project_project_id` INT NOT NULL ,
  PRIMARY KEY (`user_id`) ,
  INDEX `fk_User_Organization1_idx` (`Organization_org_id` ASC) ,
  INDEX `fk_User_Project1_idx` (`Project_project_id` ASC) ,
  CONSTRAINT `fk_User_Organization1`
    FOREIGN KEY (`Organization_org_id` )
    REFERENCES `issueTrackerDb`.`Organization` (`org_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_User_Project1`
    FOREIGN KEY (`Project_project_id` )
    REFERENCES `issueTrackerDb`.`Project` (`project_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `issueTrackerDb`.`Comment`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `issueTrackerDb`.`Comment` (
  `comment_id` INT NOT NULL ,
  `description` VARCHAR(45) NULL ,
  `created_time` TIMESTAMP NULL ,
  `edited_time` TIMESTAMP NULL ,
  `comment_creater` VARCHAR(45) NULL ,
  `Issue_issue_id` INT NOT NULL ,
  `User_user_id` INT NOT NULL ,
  PRIMARY KEY (`comment_id`) ,
  INDEX `fk_Comment_Issue1_idx` (`Issue_issue_id` ASC) ,
  INDEX `fk_Comment_User1_idx` (`User_user_id` ASC) ,
  CONSTRAINT `fk_Comment_Issue1`
    FOREIGN KEY (`Issue_issue_id` )
    REFERENCES `issueTrackerDb`.`Issue` (`issue_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Comment_User1`
    FOREIGN KEY (`User_user_id` )
    REFERENCES `issueTrackerDb`.`User` (`user_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
