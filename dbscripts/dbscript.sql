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
INSERT INTO Organization (org_id,name) VALUES (1,"WSO2");
INSERT INTO Organization (org_id,name) VALUES (2,"MIT");
INSERT INTO Organization (org_id,name) VALUES (3,"VIRTUSA");
INSERT INTO Organization (org_id,name) VALUES (4,"MICROSOFT");
INSERT INTO Organization (org_id,name) VALUES (5,"IBM");

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
INSERT INTO Project (project_id,name,owner,Organization_org_id) VALUES (1,"UES","Nuwan","1");
INSERT INTO Project (project_id,name,owner,Organization_org_id) VALUES (2,"AF","Dimuthu","1");
INSERT INTO Project (project_id,name,owner,Organization_org_id) VALUES (3,"JSE","Chris","2");
INSERT INTO Project (project_id,name,owner,Organization_org_id) VALUES (4,"IronMountain","Punnadi","3");
INSERT INTO Project (project_id,name,owner,Organization_org_id) VALUES (5,"Windows","Bill Gates","4");
INSERT INTO Project (project_id,name,owner,Organization_org_id) VALUES (6,"WebSphere","Paul","5");
INSERT INTO Project (project_id,name,owner,Organization_org_id) VALUES (7,"APIM","Sumedha","1");
INSERT INTO Project (project_id,name,owner,Organization_org_id) VALUES (8,"Bench","XXX","3");
INSERT INTO Project (project_id,name,owner,Organization_org_id) VALUES (9,"MS-Office","Bill Gates","4");


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
INSERT INTO Version (id,version,Project_project_id) VALUES (1,"1.0.0","1");
INSERT INTO Version (id,version,Project_project_id) VALUES (2,"1.0.1","1");
INSERT INTO Version (id,version,Project_project_id) VALUES (3,"1.2.0","1");
INSERT INTO Version (id,version,Project_project_id) VALUES (4,"1.0.0","2");
INSERT INTO Version (id,version,Project_project_id) VALUES (5,"1.1.0","2");
INSERT INTO Version (id,version,Project_project_id) VALUES (6,"Windows-XP","5");
INSERT INTO Version (id,version,Project_project_id) VALUES (7,"Windows-SERVER","5");
INSERT INTO Version (id,version,Project_project_id) VALUES (8,"Windows-7","5");
INSERT INTO Version (id,version,Project_project_id) VALUES (9,"JSE-London","3");
INSERT INTO Version (id,version,Project_project_id) VALUES (10,"JSE-Joburg","3");

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
INSERT INTO Issue (issue_id,summary,description,type,priority,owner,status,asignee,Version_id,created_time,updated_time,severity) VALUES (1,"summary1","description1","BUG","HIGHEST","dimuthu","OPEN","punnadi","1",NOW(),NOW(),"BLOCKER");
INSERT INTO Issue (issue_id,summary,description,type,priority,owner,status,asignee,Version_id,created_time,updated_time,severity) VALUES (2,"summary2","description2","BUG","HIGH","evanthika","RESOLVED","asanka","2",NOW(),NOW(),"CRITICAL");
INSERT INTO Issue (issue_id,summary,description,type,priority,owner,status,asignee,Version_id,created_time,updated_time,severity) VALUES (3,"summary3","description3","QUERY","NORMAL","ashansa","INPROGRESS","nuwan","3",NOW(),NOW(),"TRIVIAL");
INSERT INTO Issue (issue_id,summary,description,type,priority,owner,status,asignee,Version_id,created_time,updated_time,severity) VALUES (4,"summary4","description4","FEATURE","LOW","punnadi","OPEN","manisha","1",NOW(),NOW(),"MINOR");
INSERT INTO Issue (issue_id,summary,description,type,priority,owner,status,asignee,Version_id,created_time,updated_time,severity) VALUES (5,"summary5","description5","BUG","HIGHEST","microsoft-qa1","OPEN","microsoft-dev1","6",NOW(),NOW(),"BLOCKER");
INSERT INTO Issue (issue_id,summary,description,type,priority,owner,status,asignee,Version_id,created_time,updated_time,severity) VALUES (6,"summary6","description6","BUG","HIGH","microsoft-qa2","RESOLVED","microsoft-dev2","8",NOW(),NOW(),"CRITICAL");
INSERT INTO Issue (issue_id,summary,description,type,priority,owner,status,asignee,Version_id,created_time,updated_time,severity) VALUES (7,"summary7","description7","QUERY","NORMAL","jse-qa1","INPROGRESS","jse-dev1","10",NOW(),NOW(),"TRIVIAL");
INSERT INTO Issue (issue_id,summary,description,type,priority,owner,status,asignee,Version_id,created_time,updated_time,severity) VALUES (8,"summary8","description8","FEATURE","LOW","jse-qa2","OPEN","jse-dev1","9",NOW(),NOW(),"MINOR");



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
INSERT INTO User (user_id,name,Organization_org_id,Project_project_id) VALUES (1,"dimuthu",1,2);
INSERT INTO User (user_id,name,Organization_org_id,Project_project_id) VALUES (2,"evanthika",1,2);
INSERT INTO User (user_id,name,Organization_org_id,Project_project_id) VALUES (3,"ashansa",1,2);
INSERT INTO User (user_id,name,Organization_org_id,Project_project_id) VALUES (4,"manisha",1,2);
INSERT INTO User (user_id,name,Organization_org_id,Project_project_id) VALUES (5,"asanka",1,2);
INSERT INTO User (user_id,name,Organization_org_id,Project_project_id) VALUES (6,"nuwan",1,1);
INSERT INTO User (user_id,name,Organization_org_id,Project_project_id) VALUES (7,"microsoft-qa1",4,6);
INSERT INTO User (user_id,name,Organization_org_id,Project_project_id) VALUES (8,"microsoft-qa2",4,7);
INSERT INTO User (user_id,name,Organization_org_id,Project_project_id) VALUES (9,"microsoft-dev1",4,8);
INSERT INTO User (user_id,name,Organization_org_id,Project_project_id) VALUES (10,"microsoft-dev2",4,7);



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
INSERT INTO Comment (comment_id,description,created_time,edited_time,comment_creater,Issue_issue_id,User_user_id) VALUES (1,"comment1",NOW(),NOW(),"manisha","1","1");
INSERT INTO Comment (comment_id,description,created_time,edited_time,comment_creater,Issue_issue_id,User_user_id) VALUES (2,"comment2",NOW(),NOW(),"ashansa","3","3");
INSERT INTO Comment (comment_id,description,created_time,edited_time,comment_creater,Issue_issue_id,User_user_id) VALUES (3,"comment3",NOW(),NOW(),"evanthika","2","2");
INSERT INTO Comment (comment_id,description,created_time,edited_time,comment_creater,Issue_issue_id,User_user_id) VALUES (4,"comment4",NOW(),NOW(),"asanka","4","5");
INSERT INTO Comment (comment_id,description,created_time,edited_time,comment_creater,Issue_issue_id,User_user_id) VALUES (5,"comment5",NOW(),NOW(),"manisha","1","1");
INSERT INTO Comment (comment_id,description,created_time,edited_time,comment_creater,Issue_issue_id,User_user_id) VALUES (6,"comment6",NOW(),NOW(),"nuwan","6","6");
INSERT INTO Comment (comment_id,description,created_time,edited_time,comment_creater,Issue_issue_id,User_user_id) VALUES (7,"comment7",NOW(),NOW(),"microsoft-qa1","5","7");
INSERT INTO Comment (comment_id,description,created_time,edited_time,comment_creater,Issue_issue_id,User_user_id) VALUES (8,"comment8",NOW(),NOW(),"microsoft-dev1","6","9");


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;