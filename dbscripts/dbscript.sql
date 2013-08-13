SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

CREATE SCHEMA IF NOT EXISTS `issueTrackerDb` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
use issueTrackerDb;
-- -----------------------------------------------------
-- Table `issueTrackerDb`.`Project`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS Project (
  `project_id` INTEGER NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL ,
  `owner` VARCHAR(45) NULL ,
  `organization_id` INT NOT NULL ,
  PRIMARY KEY (`project_id`)
)ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `issueTrackerDb`.`Version`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS Version (
  `id` INTEGER NOT NULL AUTO_INCREMENT,
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
CREATE  TABLE IF NOT EXISTS Issue (
  `issue_id` INTEGER NOT NULL AUTO_INCREMENT,
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
-- Table `issueTrackerDb`.`Comment`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS Comment (
  `comment_id` INTEGER NOT NULL AUTO_INCREMENT,
  `description` VARCHAR(45) NULL ,
  `created_time` TIMESTAMP NULL ,
  `edited_time` TIMESTAMP NULL ,
  `comment_creater` VARCHAR(45) NULL ,
  `Issue_issue_id` INT NOT NULL ,
  PRIMARY KEY (`comment_id`) ,
  INDEX `fk_Comment_Issue1_idx` (`Issue_issue_id` ASC) ,
  CONSTRAINT `fk_Comment_Issue1`
    FOREIGN KEY (`Issue_issue_id` )
    REFERENCES `issueTrackerDb`.`Issue` (`issue_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


INSERT INTO Project (name,owner,organization_id) VALUES ('UES','Nuwan','1');
INSERT INTO Project (name,owner,organization_id) VALUES ('AF','Dimuthu','1');
INSERT INTO Project (name,owner,organization_id) VALUES ('JSE','Chris','2');

INSERT INTO Version (version,Project_project_id) VALUES ("1.0.0","1");
INSERT INTO Version (version,Project_project_id) VALUES ("1.0.1","2");
INSERT INTO Version (version,Project_project_id) VALUES ("1.2.0","3");
INSERT INTO Version (version,Project_project_id) VALUES ("1.0.0","4");
INSERT INTO Version (version,Project_project_id) VALUES ("1.1.0","5");
INSERT INTO Version (version,Project_project_id) VALUES ("Windows-XP","6");
INSERT INTO Version (version,Project_project_id) VALUES ("Windows-SERVER","7");

INSERT INTO Issue (summary,description,type,priority,owner,status,asignee,Version_id,created_time,updated_time,severity) VALUES ("summary1","description1","BUG","HIGHEST","dimuthu","OPEN","punnadi","1",NOW(),NOW(),"BLOCKER");
INSERT INTO Issue (summary,description,type,priority,owner,status,asignee,Version_id,created_time,updated_time,severity) VALUES ("summary2","description2","BUG","HIGH","evanthika","RESOLVED","asanka","2",NOW(),NOW(),"CRITICAL");
INSERT INTO Issue (summary,description,type,priority,owner,status,asignee,Version_id,created_time,updated_time,severity) VALUES ("summary3","description3","QUERY","NORMAL","ashansa","INPROGRESS","nuwan","3",NOW(),NOW(),"TRIVIAL");
INSERT INTO Issue (summary,description,type,priority,owner,status,asignee,Version_id,created_time,updated_time,severity) VALUES ("summary4","description4","FEATURE","LOW","punnadi","OPEN","manisha","1",NOW(),NOW(),"MINOR");
INSERT INTO Issue (summary,description,type,priority,owner,status,asignee,Version_id,created_time,updated_time,severity) VALUES ("summary5","description5","BUG","HIGHEST","microsoft-qa1","OPEN","microsoft-dev1","6",NOW(),NOW(),"BLOCKER");

INSERT INTO Comment (description,created_time,edited_time,comment_creater,Issue_issue_id) VALUES ("comment1",NOW(),NOW(),"manisha","1");
INSERT INTO Comment (description,created_time,edited_time,comment_creater,Issue_issue_id) VALUES ("comment2",NOW(),NOW(),"ashansa","3");
INSERT INTO Comment (description,created_time,edited_time,comment_creater,Issue_issue_id) VALUES ("comment3",NOW(),NOW(),"evanthika","2");
INSERT INTO Comment (description,created_time,edited_time,comment_creater,Issue_issue_id) VALUES ("comment4",NOW(),NOW(),"asanka","4");
INSERT INTO Comment (description,created_time,edited_time,comment_creater,Issue_issue_id) VALUES ("comment5",NOW(),NOW(),"manisha","1");
INSERT INTO Comment (description,created_time,edited_time,comment_creater,Issue_issue_id) VALUES ("comment6",NOW(),NOW(),"nuwan","6");




SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
